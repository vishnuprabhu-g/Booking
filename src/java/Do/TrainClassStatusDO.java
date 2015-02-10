package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainClassStatusDO {

    public void add(TrainClassStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into train_class_status (trian_class_status_id ,status_id ,class_id ,total ,available ,max_waiting ) values (? , ?, ?, ?, ?, ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.trianClassStatusId);
        ps.setLong(2, obj.statusId);
        ps.setLong(3, obj.classId);
        ps.setInt(4, obj.total);
        ps.setInt(5, obj.available);
        ps.setInt(6, obj.maxWaiting);
        ps.executeUpdate();

    }

    public void update(TrainClassStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update train_class_status set available= ?,sno=?,waiting=?,initial_waiting=?,rac=?,last_status_id=?,last_seat_no=?,a_available=? where train_class_status_id = ?;";
        PreparedStatement ps = con.prepareStatement(q);

        ps.setInt(1, obj.available);
        ps.setInt(2, obj.sno);
        ps.setInt(3, obj.waiting);
        ps.setInt(4, obj.initialWaiting);
        ps.setInt(5, obj.rac);
        ps.setLong(9, obj.trianClassStatusId);
        ps.setInt(7, obj.last_seat_no);
        ps.setInt(6, obj.last_status_id);
        ps.setInt(8, obj.a_available);
        ps.executeUpdate();

    }

    public List<TrainClassStatus> getAll(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_status where trian_class_status_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        List<TrainClassStatus> out = new ArrayList<TrainClassStatus>();
        while (rs.next()) {
            TrainClassStatus obj = new TrainClassStatus();
            obj.trianClassStatusId = rs.getLong("trian_class_status_id");
            obj.statusId = rs.getLong("status_id");
            obj.classId = rs.getLong("class_id");
            obj.total = rs.getInt("total");
            obj.available = rs.getInt("available");
            obj.maxWaiting = rs.getInt("max_waiting");
            out.add(obj);
        }

        return out;
    }

    public TrainClassStatus get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_status where status_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        TrainClassStatus obj = new TrainClassStatus();
        if (rs.next()) {
            obj.trianClassStatusId = rs.getLong("train_class_status_id");
            obj.statusId = rs.getLong("status_id");
            obj.classId = rs.getLong("class_id");
            obj.total = rs.getInt("total");
            obj.available = rs.getInt("available");
            obj.maxWaiting = rs.getInt("max_waiting");
            obj.rac = rs.getInt("rac");
            obj.waiting = rs.getInt("waiting");
            obj.initialWaiting = rs.getInt("initial_waiting");
            obj.sno = rs.getInt("sno");
            obj.maxRac = rs.getInt("max_rac");
            obj.chart = rs.getBoolean("chart");
            obj.last_status_id = rs.getInt("last_status_id");
            obj.last_seat_no = rs.getInt("last_seat_no");
            obj.a_available = rs.getInt("a_available");

        }

        return obj;
    }

    public int getSno(long trianClassId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_status where train_class_status_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trianClassId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int temp = rs.getInt("sno");

            return temp;
        } else {

            return 0;
        }
    }

    public int getWaiting(long trianClassId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_status where train_class_status_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trianClassId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int temp = rs.getInt("waiting");

            return temp;
        } else {

            return 0;
        }
    }

    public int getWaitingInitial(long trianClassId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_status where train_class_status_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trianClassId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int temp = rs.getInt("initial_waiting");

            return temp;
        } else {

            return 0;
        }
    }

    public int getRAC(long trianClassId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_status where train_class_status_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trianClassId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int temp = rs.getInt("rac");

            return temp;
        } else {

            return 0;
        }
    }

    public void updateWaiting(TrainClassStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update train_class_status set waiting=? where train_class_status_id = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setInt(1, obj.waiting);
        ps.setLong(2, obj.trianClassStatusId);
        ps.executeUpdate();

    }

}
