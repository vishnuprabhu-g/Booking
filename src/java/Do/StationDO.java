package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationDO {

    public void add(Station obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into station (id ,name ) values (NULL , ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, obj.name);
        ps.executeUpdate();
    }

    public void update(Station obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update station set id= ?,name= ? where id = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.id);
        ps.setString(2, obj.name);
        ps.setLong(3, obj.id);
        ps.executeUpdate();
    }

    public List<Station> getAll() throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from station";
        PreparedStatement ps = con.prepareStatement(q);
        ResultSet rs = ps.executeQuery();
        List<Station> out = new ArrayList<>();
        while (rs.next()) {
            Station obj = new Station();
            obj.id = rs.getLong("id");
            obj.name = rs.getString("name");
            out.add(obj);
        }
        return out;
    }

    public Station get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from station where id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        Station obj = new Station();
        if (rs.next()) {
            obj.id = rs.getLong("id");
            obj.name = rs.getString("name");
        }
        return obj;
    }

    public Station getByName(String name) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from station where name =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        Station obj = null;
        if (rs.next()) {
            obj = new Station();
            obj.id = rs.getLong("id");
            obj.name = rs.getString("name");
        }
        return obj;
    }

    public List<Station> search(String name) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from station where name like '%" + name + "%' ";
        PreparedStatement ps = con.prepareStatement(q);
        ResultSet rs = ps.executeQuery();
        List<Station> user = new ArrayList<>();
        while (rs.next()) {
            Station u = new Station();
            u.name = rs.getString("name");
            u.id = rs.getLong("id");
            user.add(u);
        }
        return user;
    }

}
