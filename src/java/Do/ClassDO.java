package Do;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassDO {

    public void add(Domain.Class obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into class (class_id ,name ,code ) values (NULL , ?, ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, obj.name);
        ps.setString(2, obj.name);
        ps.executeUpdate();
    }

    public void update(Domain.Class obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update class set id= ?,name= ?,code= ? where id = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.id);
        ps.setString(2, obj.name);
        ps.setString(3, obj.code);
        ps.setLong(4, obj.id);
        ps.executeUpdate();
    }

    public List<Domain.Class> getAll() throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from class;";
        PreparedStatement ps = con.prepareStatement(q);
        ResultSet rs = ps.executeQuery();
        List<Domain.Class> out = new ArrayList<>();
        while (rs.next()) {
            Domain.Class obj = new Domain.Class();
            obj.id = rs.getLong("class_id");
            obj.name = rs.getString("name");
            obj.code = rs.getString("code");
            out.add(obj);
        }
        return out;
    }

    public Domain.Class get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from class where class_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        Domain.Class obj = new Domain.Class();
        if (rs.next()) {
            obj.id = rs.getLong("class_id");
            obj.name = rs.getString("name");
            obj.code = rs.getString("code");
        }
        return obj;
    }

}
