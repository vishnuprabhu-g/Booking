package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDO {

    public void add(Reservation obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into reservation (pnr ,journey_id ,reservation_status ,user_id,class_id,train_class_status_id ) values (? , ?, ?,? ,? ,?)";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.pnr);
        ps.setLong(2, obj.journeyID);
        ps.setInt(3, obj.ReservationStatus);
        ps.setInt(4, obj.userId);
        ps.setLong(5, obj.classId);
        ps.setLong(6, obj.trainClassStausID);
        ps.executeUpdate();

    }

    public void update(Reservation obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update reservation set pnr= ?,journey_id= ?,reservation_status= ? where pnr = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.pnr);
        ps.setLong(2, obj.journeyID);
        ps.setInt(3, obj.ReservationStatus);
        //ps.setT(4, obj.timestamp);
        ps.setLong(4, obj.pnr);
        ps.executeUpdate();

    }

    public List<Reservation> getAll() throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from reservation ";
        PreparedStatement ps = con.prepareStatement(q);
        ResultSet rs = ps.executeQuery();
        List<Reservation> out = new ArrayList<Reservation>();
        while (rs.next()) {
            Reservation obj = new Reservation();
            obj.pnr = rs.getLong("pnr");
            obj.journeyID = rs.getLong("journey_id");
            obj.ReservationStatus = rs.getInt("reservation_status");
            obj.timestamp = rs.getTimestamp("timestamp");
            obj.classId = rs.getLong("class_id");
            obj.userId = rs.getInt("user_id");
            out.add(obj);
        }

        return out;
    }

    public List<Reservation> getAllWithUserId(int userId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from reservation where user_id=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        List<Reservation> out = new ArrayList<Reservation>();
        while (rs.next()) {
            Reservation obj = new Reservation();
            obj.pnr = rs.getLong("pnr");
            obj.journeyID = rs.getLong("journey_id");
            obj.ReservationStatus = rs.getInt("reservation_status");
            obj.timestamp = rs.getTimestamp("timestamp");
            obj.classId = rs.getLong("class_id");
            out.add(obj);
        }

        return out;
    }

    public Reservation get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from reservation where pnr =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        Reservation obj = null;
        if (rs.next()) {
            obj = new Reservation();
            obj.pnr = rs.getLong("pnr");
            obj.journeyID = rs.getLong("journey_id");
            obj.ReservationStatus = rs.getInt("reservation_status");
            obj.timestamp = rs.getTimestamp("timestamp");
            obj.classId = rs.getLong("class_id");
            obj.trainClassStausID = rs.getLong("train_class_status_id");
        }

        return obj;
    }
}
