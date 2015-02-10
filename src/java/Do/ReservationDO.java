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
        String q = "insert into reservation (pnr ,journey_id ,reservation_status  ) values (? , ?, ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.pnr);
        ps.setLong(2, obj.journeyID);
        ps.setInt(3, obj.ReservationStatus);
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
        Reservation obj = new Reservation();
        if (rs.next()) {
            obj.pnr = rs.getLong("pnr");
            obj.journeyID = rs.getLong("journey_id");
            obj.ReservationStatus = rs.getInt("reservation_status");
            obj.timestamp = rs.getTimestamp("timestamp");
        }

        return obj;
    }
}
