/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vishnu-pt517
 */
public class CoachDO {

    TrainClassSeatStatusDO tcssdo;
    long tcsid = 1;

    public CoachDO() {
        tcssdo = new TrainClassSeatStatusDO();
    }

    public String getCoachesForPassengers(List<Passenger> passengers) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String query = "select compartment,count(*) from train_class_seat_status where availability=1 group by compartment";
        PreparedStatement ps1 = con.prepareCall(query);
        ResultSet rs = ps1.executeQuery();
        String[] coachs = new String[10];
        int[] count = new int[10];
        int i = 0, totalCoaches = 2;
        int passCount = passengers.size();
        double maxMatch = 0;
        String maxMatchCoach = "NO";
        boolean coachAvail = false;
        while (rs.next()) {
            coachs[i] = rs.getString(1);
            count[i] = rs.getInt(2);
            if (count[i] >= passCount) {
                coachAvail = true;
            }
            i++;
        }
        if (coachAvail) {
            for (int index = 0; index < totalCoaches; index++) {
                if (count[index] >= passCount) {
                    double preffFact = getPrefFactor(passengers, coachs[index]);
                    if (preffFact == passCount) {
                        return coachs[index];
                    } else if (preffFact > maxMatch) {
                        maxMatchCoach = coachs[index];
                    }
                }
            }
            return maxMatchCoach;
        } else {
            return "NO";
        }
    }

    public double getPrefFactor(List<Passenger> passengers, String coach) throws SQLException {
        double fact = 0;
        for (Passenger p : passengers) {
            TrainClassSeatStatus tcss = getInCoach(p.seat_no, coach);
            if (tcss == null) {
                fact += 0.5;
            } else {
                fact += 1;
                tcss.availability = false;
                tcssdo.update(tcss);
            }
        }
        util.CommitUtil.rollBack();
        return fact;
    }

    public TrainClassSeatStatus getInCoach(int pref, String coach) throws SQLException {
        if (pref != 0) {
            return tcssdo.getPrefInCoach(tcsid, pref, 0, 0, coach);
        } else {
            return tcssdo.getInCoach(tcsid, 0, 0, coach);
        }
    }

    public List<Coach> loadCoach(String coach) throws SQLException {
        List<Coach> coachList = new ArrayList<>();
        Connection con = util.ConnectionUtil.getConnection();
        String query = "select * from train_class_seat_status where compartment=? order by seat_no";
        PreparedStatement ps1 = con.prepareStatement(query);
        ps1.setString(1, coach);
        ResultSet rs = ps1.executeQuery();
        while (rs.next()) {
           Box box=new Domain.Box();
           for(int i=0;i<7;i++)
           {
               TrainClassSeatStatus tcss=new TrainClassSeatStatus();
               tcss.availability=rs.getBoolean("availability");
               tcss.typeId=rs.getInt("seat_type_id");
           }
        }
        return coachList;
    }

}
