package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengerDO {

    public void add(Passenger obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into passengers (pnr ,name ,age ,gender ,seat_no,initial_seat_no ,status_id,initial_status_id ,sno,fare,coach ) values (? , ? ,? ,? , ?, ?, ?, ?, ?,?,? )";
        PreparedStatement ps = con.prepareStatement(q);
        int init;
        if (obj.initialSeatNo != 0) {
            init = obj.initialSeatNo;
        } else {
            init = obj.seat_no;
        }

        int sta;
        if (obj.initialStatusId != 0) {
            sta = obj.initialStatusId;
        } else {
            sta = obj.statusId;
        }
        ps.setLong(1, obj.pnr);
        ps.setString(2, obj.name);
        ps.setInt(3, obj.age);
        ps.setInt(4, obj.gender);
        ps.setInt(5, obj.seat_no);
        ps.setInt(6, init);
        ps.setLong(7, obj.statusId);
        ps.setLong(8, sta);
        ps.setInt(9, obj.sno);
        ps.setInt(10, obj.fare);
        ps.setString(11, obj.coach);
        ps.executeUpdate();

    }

    public void update(Passenger obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update passengers set status_id=?,seat_no=? where pnr = ? and sno=?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.statusId);
        ps.setInt(2, obj.seat_no);
        ps.setInt(4, obj.sno);
        ps.setLong(3, obj.pnr);
        ps.executeUpdate();

    }

    public List<Passenger> getAll(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from passengers where pnr =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        List<Passenger> out = new ArrayList<Passenger>();
        while (rs.next()) {
            Passenger obj = new Passenger();
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
            obj.seat_no = rs.getInt("seat_no");
            obj.statusId = rs.getInt("status_id");
            obj.sno = rs.getInt("sno");
            obj.initialSeatNo = rs.getInt("initial_seat_no");
            obj.initialStatusId = rs.getInt("initial_status_id");
            obj.fare = rs.getInt("fare");
            obj.coach = rs.getString("coach");
            out.add(obj);
        }

        return out;
    }

    public List<Passenger> getAllRac(long trainClassId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from all_passengers where train_class_status_id=? and status_id=2 order by sno;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trainClassId);
        ResultSet rs = ps.executeQuery();
        List<Passenger> out = new ArrayList<Passenger>();
        while (rs.next()) {
            Passenger obj = new Passenger();
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
            obj.seat_no = rs.getInt("seat_no");
            obj.statusId = rs.getInt("status_id");
            obj.sno = rs.getInt("sno");
            obj.initialSeatNo = rs.getInt("initial_seat_no");
            obj.initialStatusId = rs.getInt("initial_status_id");

            out.add(obj);
        }
        //System.out.println(out.size());

        return out;
    }

    public List<Passenger> getAllWl(long trainClassId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from all_passengers where train_class_status_id=? and status_id=3 order by sno;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trainClassId);
        ResultSet rs = ps.executeQuery();
        List<Passenger> out = new ArrayList<Passenger>();
        while (rs.next()) {
            Passenger obj = new Passenger();
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
            obj.seat_no = rs.getInt("seat_no");
            obj.statusId = rs.getInt("status_id");
            obj.sno = rs.getInt("sno");
            obj.initialSeatNo = rs.getInt("initial_seat_no");
            obj.initialStatusId = rs.getInt("initial_status_id");
            out.add(obj);
        }

        return out;
    }

    public Passenger get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from passenger where pnr =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        Passenger obj = new Passenger();
        if (rs.next()) {
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
            obj.seat_no = rs.getInt("seat_no");
            obj.statusId = rs.getInt("status_id");
            obj.sno = rs.getInt("sno");
            obj.initialSeatNo = rs.getInt("initial_seat_no");
            obj.initialStatusId = rs.getInt("initial_status_id");
            obj.fare = rs.getInt("fare");

        }

        return obj;
    }

    public Passenger get(long pnr, int sno) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from passengers where pnr =? and sno=?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, pnr);
        ps.setInt(2, sno);
        ResultSet rs = ps.executeQuery();
        Passenger obj = new Passenger();
        if (rs.next()) {
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
            obj.seat_no = rs.getInt("seat_no");
            obj.statusId = rs.getInt("status_id");
            obj.sno = rs.getInt("sno");
            obj.initialSeatNo = rs.getInt("initial_seat_no");
            obj.initialStatusId = rs.getInt("initial_status_id");
            obj.fare = rs.getInt("fare");

        }

        return obj;
    }

    public Passenger getNextRac(long class_status_id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from all_passengers where train_class_status_id =? and status_id=2 order by sno limit 1;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, class_status_id);
        ResultSet rs = ps.executeQuery();
        Passenger obj = null;
        if (rs.next()) {
            obj = new Passenger();
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
            obj.seat_no = rs.getInt("seat_no");
            obj.statusId = rs.getInt("status_id");
            obj.sno = rs.getInt("sno");
            obj.initialSeatNo = rs.getInt("initial_seat_no");
            obj.initialStatusId = rs.getInt("initial_status_id");
        }

        return obj;
    }

    public Passenger getNextwl(Long trainClassStatusId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from all_passengers where train_class_status_id =? and status_id=3 order by sno limit 1;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trainClassStatusId);
        ResultSet rs = ps.executeQuery();
        Passenger obj = null;
        if (rs.next()) {
            obj = new Passenger();
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
            obj.seat_no = rs.getInt("seat_no");
            obj.statusId = rs.getInt("status_id");
            obj.sno = rs.getInt("sno");
            obj.initialSeatNo = rs.getInt("initial_seat_no");
            obj.initialStatusId = rs.getInt("initial_status_id");
        }

        return obj;
    }

    public Passenger getBySeat(long pnr, int seatno) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from passengers where pnr =? and seat_no=? ;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, pnr);
        ps.setInt(2, seatno);
        ResultSet rs = ps.executeQuery();
        Passenger obj = new Passenger();
        if (rs.next()) {
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
            obj.seat_no = rs.getInt("seat_no");
            obj.statusId = rs.getInt("status_id");
            obj.sno = rs.getInt("sno");
            obj.initialSeatNo = rs.getInt("initial_seat_no");
            obj.initialStatusId = rs.getInt("initial_status_id");
            obj.fare = rs.getInt("fare");
        }

        return obj;
    }

    public Passenger getByCNFSeat(long pnr, int seatno) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from passengers where pnr =? and seat_no=? and status_id=1;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, pnr);
        ps.setInt(2, seatno);
        ResultSet rs = ps.executeQuery();
        Passenger obj = new Passenger();
        if (rs.next()) {
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
            obj.seat_no = rs.getInt("seat_no");
            obj.statusId = rs.getInt("status_id");
            obj.sno = rs.getInt("sno");
            obj.initialSeatNo = rs.getInt("initial_seat_no");
            obj.initialStatusId = rs.getInt("initial_status_id");
            obj.fare = rs.getInt("fare");
        }

        return obj;
    }

    public boolean checkUncancelled(long pnr) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from passengers where pnr =? and status_id!=4;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, pnr);
        ResultSet rs = ps.executeQuery();
        boolean st = rs.next();

        return st;
    }
}
