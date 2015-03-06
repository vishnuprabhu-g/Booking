package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainClassSeatNewDO {

    public TrainClassSeatStatus getInCoachInBoxWithPref(String coach, int box, int pref) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        if (coach.contains("B")) {
            System.out.println("Getting seat:" + pref);
            pref += 4;
            System.out.println("After seat:" + pref);
        }
        String query = "select * from train_class_seat_status where availability=1 and compartment=? and box=? and seat_type_id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, coach);
        ps.setInt(2, box);
        ps.setInt(3, pref);
        ResultSet rs = ps.executeQuery();
        TrainClassSeatStatus tcss;
        if (rs.next()) {
            tcss = new TrainClassSeatStatus();
            tcss.compartment = coach;
            tcss.availability = true;
            tcss.box = box;
            tcss.typeId = pref;
            tcss.trainClassSeatStatusId = rs.getLong("train_class_seat_status_id");
            tcss.seatNo = rs.getInt("seat_no");
        } else {
            tcss = null;
        }
        return tcss;
    }

    public TrainClassSeatStatus getInCoachInBoxWithOutPref(String coach, int box) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String query = "select * from train_class_seat_status where availability=1 and compartment=? and box=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, coach);
        ps.setInt(2, box);
        ResultSet rs = ps.executeQuery();
        TrainClassSeatStatus tcss;
        if (rs.next()) {
            tcss = new TrainClassSeatStatus();
            tcss.compartment = coach;
            tcss.availability = true;
            tcss.box = box;
            tcss.seatNo = rs.getInt("seat_no");
            tcss.trainClassSeatStatusId = rs.getLong("train_class_seat_status_id");
        } else {
            tcss = null;
        }
        return tcss;
    }

}
