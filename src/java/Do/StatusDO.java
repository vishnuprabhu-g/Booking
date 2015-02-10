package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatusDO {

    public void add(Status obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into status (id ,name ) values (? , ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.id);
        ps.setString(2, obj.name);
        ps.executeUpdate();

    }

    public void update(Status obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update status set id= ?,name= ? where id = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.id);
        ps.setString(2, obj.name);
        ps.setLong(3, obj.id);
        ps.executeUpdate();

    }

    public List<Status> getAll(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from status where id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        List<Status> out = new ArrayList<Status>();
        while (rs.next()) {
            Status obj = new Status();
            obj.id = rs.getLong("id");
            obj.name = rs.getString("name");
            out.add(obj);
        }

        return out;
    }

    public Status get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from status where id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        Status obj = new Status();
        if (rs.next()) {
            obj.id = rs.getLong("id");
            obj.name = rs.getString("status");
        }

        return obj;
    }
}
