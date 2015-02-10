package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnderPassengerDO {

    public void add(UnderPassenger obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into under_passenger (pnr ,name ,age ,gender ) values (? , ?, ?, ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.pnr);
        ps.setString(2, obj.name);
        ps.setInt(3, obj.age);
        ps.setInt(4, obj.gender);
        ps.executeUpdate();

    }

    public void update(UnderPassenger obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update under_passenger set pnr= ?,name= ?,age= ?,gender= ? where pnr = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.pnr);
        ps.setString(2, obj.name);
        ps.setInt(3, obj.age);
        ps.setInt(4, obj.gender);
        ps.setLong(5, obj.pnr);
        ps.executeUpdate();

    }

    public List<UnderPassenger> getAll(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from under_passenger where pnr =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        List<UnderPassenger> out = new ArrayList<UnderPassenger>();
        while (rs.next()) {
            UnderPassenger obj = new UnderPassenger();
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
            out.add(obj);
        }

        return out;
    }

    public UnderPassenger get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from under_passenger where pnr =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        UnderPassenger obj = new UnderPassenger();
        if (rs.next()) {
            obj.pnr = rs.getLong("pnr");
            obj.name = rs.getString("name");
            obj.age = rs.getInt("age");
            obj.gender = rs.getInt("gender");
        }

        return obj;
    }

    public void delete(UnderPassenger obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "delete from under_passenger where pnr= ? and name= ? and age= ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.pnr);
        ps.setString(2, obj.name);
        ps.setInt(3, obj.age);
        ps.executeUpdate();

    }
}
