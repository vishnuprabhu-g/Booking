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

/**
 *
 * @author vishnu-pt517
 */
public class SeatPassengerDO {

    public void add(SeatPassenger obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into seat_passenger values (? , ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.seat_id);
        ps.setLong(2, obj.pnr);
        ps.executeUpdate();

    }

    public void update(SeatPassenger obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update seat_passenger set train_class_seat_status_id= ?,pnr= ? where train_class_seat_status_id = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.seat_id);
        ps.setLong(2, obj.pnr);
        ps.setLong(3, obj.seat_id);
        ps.executeUpdate();

    }

    public SeatPassenger get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from seat_passenger where train_class_seat_status_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        SeatPassenger obj = new SeatPassenger();
        if (rs.next()) {
            obj.seat_id = rs.getLong("train_class_seat_status_id");
            obj.pnr = rs.getLong("pnr");
        }

        return obj;
    }

    public void delete(long seatID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "delete from seat_passenger where train_class_seat_status_id=?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, seatID);
        ps.executeUpdate();

    }
}
