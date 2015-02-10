package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainClassRacStatusDO {

    public void add(TrainClassRacStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into r3.train_class_rac_status (train_class_status_id ,rac_no ,seat_no ,availability ) values (? , ?, ?, ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.trainClassStatusId);
        ps.setLong(2, obj.racNo);
        ps.setLong(3, obj.seatNo);
        ps.setBoolean(4, obj.availability);
        ps.executeUpdate();

    }

    public void update(TrainClassRacStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update train_class_rac_status set rac_no= ?,seat_no= ?,availability= ? where train_class_rac_status_id = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.racNo);
        ps.setLong(2, obj.seatNo);
        ps.setBoolean(3, obj.availability);
        ps.setLong(4, obj.trainClassRacStatusId);
        ps.executeUpdate();

    }

    public void updateAfter(TrainClassRacStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update train_class_rac_status set availability= ? where train_class_status_id = ? and rac_no=?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(2, obj.trainClassStatusId);
        ps.setLong(3, obj.racNo);
        ps.setBoolean(1, obj.availability);
        ps.executeUpdate();

    }

    public List<TrainClassRacStatus> getAll(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_rac_status where train_class_status_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        List<TrainClassRacStatus> out = new ArrayList<TrainClassRacStatus>();
        while (rs.next()) {
            TrainClassRacStatus obj = new TrainClassRacStatus();
            obj.trainClassStatusId = rs.getLong("train_class_status_id");
            obj.racNo = rs.getLong("rac_no");
            obj.seatNo = rs.getLong("seat_no");
            obj.availability = rs.getBoolean("availability");
            out.add(obj);
        }

        return out;
    }

    public TrainClassRacStatus get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_rac_status where train_class_status_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        TrainClassRacStatus obj = new TrainClassRacStatus();
        if (rs.next()) {
            obj.trainClassStatusId = rs.getLong("train_class_status_id");
            obj.racNo = rs.getLong("rac_no");
            obj.seatNo = rs.getLong("seat_no");
            obj.availability = rs.getBoolean("availability");
        }

        return obj;
    }

    public TrainClassRacStatus getAvail(long trianClassId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_rac_status where train_class_status_id =? and availability=1;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trianClassId);
        ResultSet rs = ps.executeQuery();
        TrainClassRacStatus obj = new TrainClassRacStatus();
        if (rs.next()) {
            //obj.tClassStatusId = rs.getLong("t_class_status_id");
            obj.seatNo = rs.getInt("seat_no");
            obj.racNo = rs.getInt("rac_no");
            obj.trainClassRacStatusId = rs.getLong(1);
            //obj.trainClassSeatStatusId = rs.getLong("train_class_seat_status_id");
            //obj.availability = rs.getBoolean("availability");
            // obj.pnr = rs.getLong("pnr");
            //obj.typeId = type_id;
        } else {
            obj = null;
        }

        return obj;
    }

    public int getCount(long classStatusId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select COUNT(*) as rowcount from train_class_rac_status where train_class_status_id =? and availability=1;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, classStatusId);
        ResultSet rs = ps.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt("rowcount");
        }

        return count;
    }
}
