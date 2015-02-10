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
        String q = "insert into station (id ,name ) values (? , ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.id);
        ps.setString(2, obj.name);
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

    public List<Station> getAll(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from station where id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        List<Station> out = new ArrayList<Station>();
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

}
