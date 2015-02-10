/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import Domain.Passenger;
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
public class ChartDO {

    public long trainClassStausId = 1;

    public List<Passenger> getAllNewCnf() throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from all_passengers where train_class_status_id=? and initial_status_id!=1 and status_id=1";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trainClassStausId);
        List<Passenger> cnfList = new ArrayList<Passenger>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Passenger p = new Passenger();
            p.age = rs.getInt("age");
            p.gender = rs.getInt("gender");
            p.name = rs.getString("name");
            p.pnr = rs.getLong("pnr");
            p.initialSeatNo = rs.getInt("initial_seat_no");
            p.seat_no = rs.getInt("seat_no");
            p.statusId = rs.getInt("status_id");
            p.initialStatusId = rs.getInt("initial_status_id");
            p.sno = rs.getInt("sno");
            cnfList.add(p);
        }

        return cnfList;
    }

    public List<Passenger> getAllCNFofPNR(long pnr) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from all_passengers where train_class_status_id=? and initial_status_id=1 and pnr=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trainClassStausId);
        ps.setLong(2, pnr);
        List<Passenger> cnfList = new ArrayList<Passenger>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Passenger p = new Passenger();
            p.age = rs.getInt("age");
            p.gender = rs.getInt("gender");
            p.name = rs.getString("name");
            p.pnr = rs.getLong("pnr");
            p.initialSeatNo = rs.getInt("initial_seat_no");
            p.seat_no = rs.getInt("seat_no");
            p.statusId = rs.getInt("status_id");
            p.initialStatusId = rs.getInt("initial_status_id");
            p.sno = rs.getInt("sno");
            cnfList.add(p);
        }

        return cnfList;
    }

    public Passenger getSeatNo(int seat_no) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from all_passengers where initial_status_id!=1 and status_id=1 and train_class_status_id=? order by abs(?-seat_no) limit 1;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trainClassStausId);
        ps.setInt(2, seat_no);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Passenger p = new Passenger();
            p.seat_no = rs.getInt("seat_no");
            p.sno = rs.getInt("sno");
            p.gender = rs.getInt("gender");
            p.pnr = rs.getLong("pnr");
            p.name = rs.getString("name");
            p.initialSeatNo = rs.getInt("initial_seat_no");
            p.statusId = rs.getInt("status_id");

            return p;
        } else {

            return null;
        }
    }

    public List<Passenger> getAllByStatus(int status) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from all_passengers where train_class_status_id=? and status_id=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trainClassStausId);
        ps.setInt(2, status);
        List<Passenger> cnfList = new ArrayList<Passenger>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Passenger p = new Passenger();
            p.age = rs.getInt("age");
            p.gender = rs.getInt("gender");
            p.name = rs.getString("name");
            p.pnr = rs.getLong("pnr");
            p.initialSeatNo = rs.getInt("initial_seat_no");
            p.seat_no = rs.getInt("seat_no");
            p.statusId = rs.getInt("status_id");
            p.initialStatusId = rs.getInt("initial_status_id");
            p.sno = rs.getInt("sno");
            cnfList.add(p);
        }

        return cnfList;
    }

    public void close(long trainClassStatusID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update train_class_status set chart=? where train_class_status_id=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setBoolean(1, true);
        ps.setLong(2, trainClassStausId);
        ps.executeUpdate();

    }
}
