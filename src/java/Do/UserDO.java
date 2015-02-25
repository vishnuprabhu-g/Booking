/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import Domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vishnu-pt517
 */
public class UserDO {

    public User getUser(String username, String password) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from user where username=? and password=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        User user;
        if (rs.next()) {
            user = new User();
            user.username = username;
            user.id = rs.getLong(1);
            user.role = rs.getInt("role_id");
        } else {
            user = null;
        }

        return user;
    }
}
