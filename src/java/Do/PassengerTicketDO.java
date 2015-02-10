package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengerTicketDO {

    public void add(PassengerTicket obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into passengers_tickets (pnr,from_station_id ,to_stattion_id ,total_fare ,adult ,children,train_class_status_id,basic_fare,reservation_fare,service_charge ) values ( ?, ?, ?, ?, ?, ?,?,?,?,? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.pnr);
        ps.setLong(2, obj.fromStationId);
        ps.setLong(3, obj.toStationId);
        ps.setInt(4, obj.totalFare);
        ps.setInt(5, obj.Adult);
        ps.setInt(6, obj.Children);
        ps.setLong(7, obj.trainClassStatusId);
        ps.setDouble(8, obj.basicfare);
        ps.setDouble(9, obj.reservationFare);
        ps.setDouble(10, obj.serviceCharge);
        ps.executeUpdate();

    }

    public void update(PassengerTicket obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update passengers_tickets set pnr= ?,from_station_id= ?,to_stattion_id= ?,total_fare= ?,adult= ?,children= ? where pnr = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.pnr);
        ps.setLong(2, obj.fromStationId);
        ps.setLong(3, obj.toStationId);
        ps.setInt(4, obj.totalFare);
        ps.setInt(5, obj.Adult);
        ps.setInt(6, obj.Children);
        ps.setLong(7, obj.pnr);
        ps.executeUpdate();

    }

    public List<PassengerTicket> getAll() throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from passengers_tickets ;";
        PreparedStatement ps = con.prepareStatement(q);
        ResultSet rs = ps.executeQuery();
        List<PassengerTicket> out = new ArrayList<PassengerTicket>();
        while (rs.next()) {
            PassengerTicket obj = new PassengerTicket();
            obj.pnr = rs.getLong("pnr");
            //obj.fromStationId = rs.getLong("from_station_id");
            //obj.toStationId = rs.getLong("to_stattion_id");
            // obj.totalFare = rs.getInt("total_fare");
            obj.Adult = rs.getInt("adult");
            // obj.Children = rs.getInt("children");
            // obj.timestamp = rs.getLong("timestamp");
            out.add(obj);
        }

        return out;
    }

    public PassengerTicket get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from passengers_tickets where pnr =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        PassengerTicket obj = new PassengerTicket();
        if (rs.next()) {
            obj.pnr = rs.getLong("pnr");
            obj.fromStationId = rs.getLong("from_station_id");
            obj.toStationId = rs.getLong("to_stattion_id");
            obj.totalFare = rs.getInt("total_fare");
            obj.Adult = rs.getInt("adult");
            obj.Children = rs.getInt("children");
            obj.timestamp = rs.getLong("timestamp");
            obj.trainClassStatusId = rs.getLong("train_class_status_id");
            obj.reservationFare = rs.getDouble("reservation_fare");
            obj.basicfare = rs.getDouble("basic_fare");
            obj.serviceCharge = rs.getDouble("service_charge");
        }

        return obj;
    }
}
