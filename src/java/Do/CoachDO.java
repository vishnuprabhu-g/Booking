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

    public String getCoachesForPassengers(List<Passenger> passengers, long tcsID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String query = "select compartment,count(*) from train_class_seat_status where availability=1 and train_class_status_id=? group by compartment";
        PreparedStatement ps1 = con.prepareCall(query);
        ps1.setLong(1, tcsID);
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
                    System.out.println(preffFact);
                    if (preffFact == passCount) {
                        return coachs[index];
                    } else if (preffFact > maxMatch) {
                        maxMatchCoach = coachs[index];
                        maxMatch = preffFact;
                    }
                }
            }
            return maxMatchCoach;
        } else {
            return "NO";
        }
    }

    public double getPrefFactor(List<Passenger> passengers, String coach) throws SQLException {
        double fact = 0, fact2 = 0;
        int box = 0;
        int boxVal = tcssdo.maxBoxInCoach(coach);
        for (Passenger p : passengers) {
            TrainClassSeatStatus tcss = getInCoach(p.seat_no, coach, boxVal);
            if (tcss == null) {
                fact += 0.5;
            } else {
                if (box == 0) {
                    box = tcss.box;
                }
                if (box != tcss.box) {
                    fact2 += 0.5;
                }
                fact += 1;
                tcss.availability = false;
                tcssdo.update(tcss);
                box = tcss.box;
            }
        }
        util.CommitUtil.rollBack();
        return (fact - fact2);
    }

    /**
     * This method is used to calculate the pref factor.
     *
     * @param pref
     * @param coach
     * @param box
     * @return
     * @throws SQLException
     */
    public TrainClassSeatStatus getInCoach(int pref, String coach, int box) throws SQLException {
        TrainClassSeatStatus tcss;
        if (pref != 0) {
            tcss = tcssdo.getPrefInCoach(tcsid, pref, 0, box, coach);
            if (tcss == null) {
                tcss = tcssdo.getPrefInCoach(tcsid, pref, 0, 0, coach);
            }
        } else {
            tcss = tcssdo.getInCoach(tcsid, 0, box, coach);
            if (tcss == null) {
                tcss = tcssdo.getInCoach(tcsid, 0, 0, coach);
            }
        }
        return tcss;
    }

    public Coach loadCoach(String coach) throws SQLException {
        Coach c = new Coach();
        Connection con = util.ConnectionUtil.getConnection();
        String query = "select * from train_class_seat_status where compartment=? order by seat_no";
        PreparedStatement ps1 = con.prepareStatement(query);
        ps1.setString(1, coach);
        ResultSet rs = ps1.executeQuery();
        int BoxNo = 1;
        while (rs.next()) {
            Box box = new Domain.Box();
            for (int i = 0; i < 7; i++) {
                TrainClassSeatStatus tcss = new TrainClassSeatStatus();
                tcss.availability = rs.getBoolean("availability");
                tcss.typeId = rs.getInt("seat_type_id");
                if (tcss.availability) {
                    if (tcss.typeId == 1 || tcss.typeId == 5) {
                        box.Lower++;
                        if (i == 1) {
                            box.Lowers[0] = 1;
                        } else {
                            box.Lowers[1] = 1;
                        }
                    } else if (tcss.typeId == 2 || tcss.typeId == 6) {
                        box.Middle++;
                        if (i == 2) {
                            box.Middles[0] = 1;
                        } else {
                            box.Middles[1] = 1;
                        }
                    } else if (tcss.typeId == 3 || tcss.typeId == 7) {
                        box.Upper++;
                        if (i == 3) {
                            box.Uppers[0] = 1;
                        } else {
                            box.Uppers[1] = 1;
                        }
                    } else {
                        box.Side = 1;
                    }
                }
                if (i != 6) {
                    rs.next();
                }
            }
            box.total = box.Lower + box.Middle + box.Upper + box.Side;
            box.boxNo = BoxNo++;
            c.boxs.add(box);
        }
        return c;
    }

    public TrainClassSeatStatus getPrefInAllCoach(long tcsID, int pref) throws SQLException {
        if (tcsID == 2) {
            pref += 4;
        }
        Connection con = util.ConnectionUtil.getConnection();
        String query = "select * from train_class_seat_status where availability=1 and seat_type_id=? and train_class_status_id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, pref);
        ps.setLong(2, tcsID);
        ResultSet rs = ps.executeQuery();
        TrainClassSeatStatus tcss;
        if (rs.next()) {
            tcss = new TrainClassSeatStatus();
            tcss.compartment = rs.getString("compartment");
            tcss.seatNo = rs.getInt("seat_no");
            tcss.trainClassSeatStatusId = rs.getLong("train_class_seat_status_id");
            tcss.typeId = pref;
        } else {
            tcss = null;
        }
        return tcss;
    }

    public TrainClassSeatStatus getInAllCoach(long tcsID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String query = "select * from train_class_seat_status where availability=1 and train_class_status_id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, tcsID);
        ResultSet rs = ps.executeQuery();
        TrainClassSeatStatus tcss;
        if (rs.next()) {
            tcss = new TrainClassSeatStatus();
            tcss.compartment = rs.getString("compartment");
            tcss.seatNo = rs.getInt("seat_no");
            tcss.trainClassSeatStatusId = rs.getLong("train_class_seat_status_id");
            tcss.typeId = rs.getInt("seat_type_id");
        } else {
            tcss = null;
        }
        return tcss;
    }

    public List<String> getAllCoachOfTrainClassStatus(long tcsID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String query = "select distinct compartment from train_class_seat_status where train_class_status_id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, tcsID);
        ResultSet rs = ps.executeQuery();
        List<String> coachList = new ArrayList<>();
        while (rs.next()) {
            coachList.add(rs.getString(1));
        }
        return coachList;
    }
}
