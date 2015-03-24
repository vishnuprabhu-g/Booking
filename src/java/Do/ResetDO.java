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
public class ResetDO {

    public void add(ResetKeyVal reset) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String delete = "delete from reset where uid=?";
        PreparedStatement psDeleteOld = con.prepareStatement(delete);
        psDeleteOld.setLong(1, reset.uid);
        psDeleteOld.executeUpdate();
        String query = "insert into reset values(?,?);";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, reset.uid);
        ps.setLong(2, reset.key);
        ps.execute();
    }

    public boolean isValid(ResetKeyVal reset) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from reset where uid=? and reset_key=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, reset.uid);
        ps.setLong(2, reset.key);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String q2 = "delete from reset where uid=? and reset_key=?";
            PreparedStatement ps2 = con.prepareStatement(q2);
            ps2.setLong(1, reset.uid);
            ps2.setLong(2, reset.key);
            ps2.executeUpdate();
            return true;
        } else {
            return false;
        }
    }
}
