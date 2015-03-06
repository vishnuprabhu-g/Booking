/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import Domain.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vishnu-pt517
 */
public class UserProfileDO {

    public UserProfile get(long userID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from user_profile_view where user_id=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, userID);
        ResultSet rs = ps.executeQuery();
        UserProfile up = null;
        if (rs.next()) {
            up = new UserProfile();
            up.name = rs.getString("name");
            up.username = rs.getString("username");
            up.email = rs.getString("email");
            up.userID = rs.getLong("user_id");
            up.role = rs.getLong("role_id");
        }
        return up;
    }
}
