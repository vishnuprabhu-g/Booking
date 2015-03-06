/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import Domain.User;
import Domain.UserProfile;
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
public class UserDO {

    public void add(User u) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String query = "insert into user values(NULL,?,?,?);";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, u.username);
        ps.setString(2, u.password);
        ps.setInt(3, u.role);
        ps.execute();
    }

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

    public UserProfile getUserByUsername(String username) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from user_profile_view where username=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        UserProfile user;
        if (rs.next()) {
            user = new UserProfile();
            user.username = username;
            user.email = rs.getString("email");
            user.name = rs.getString("name");
            user.userID = rs.getLong("user_id");
        } else {
            user = null;
        }
        return user;
    }

    public List<User> getAllUser(int role_id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from user where role_id=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setInt(1, role_id);
        ResultSet rs = ps.executeQuery();
        List<User> user = new ArrayList<User>();
        while (rs.next()) {
            User u = new User();
            u.username = rs.getString("username");
            u.id = rs.getLong("user_id");
            u.role = rs.getInt("role_id");
            user.add(u);
        }
        return user;
    }

    public boolean isExist(String username) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from user where username=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public List<User> search(String username) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from user where role_id!=1 and username like '%" + username + "%' ";
        PreparedStatement ps = con.prepareStatement(q);
        ResultSet rs = ps.executeQuery();
        List<User> user = new ArrayList<>();
        while (rs.next()) {
            User u = new User();
            u.username = rs.getString("username");
            u.id = rs.getLong("user_id");
            u.role = rs.getInt("role_id");
            user.add(u);
        }
        return user;
    }

    public void delete(long userID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "delete from user where user_id=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, userID);
        ps.executeUpdate();
    }

    public void updatePassByUid(long uid, String pass) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update user set password=? where user_id=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(2, uid);
        ps.setString(1, pass);
        ps.executeUpdate();
    }
}
