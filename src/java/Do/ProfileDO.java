/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import Domain.Profile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vishnu-pt517
 */
public class ProfileDO {

    public void addProfile(Profile p) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String query = "insert into user_profile values(?,?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, p.userID);
        ps.setString(2, p.name);
        ps.setString(3, p.email);
        ps.execute();
    }

    public Profile getProfile(Long userID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String query = "select * from user_profile where user_id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, userID);
        ResultSet rs = ps.executeQuery();
        Profile p = null;
        while (rs.next()) {
            p = new Profile();
            p.userID = userID;
            p.email = rs.getString("email");
            p.name = rs.getString("name");
        }
        return p;
    }

    public void delete(long userID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "delete from user_profile where user_id=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, userID);
        ps.executeUpdate();
    }

    public void update(Profile p) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String query = "update user_profile set name=?,email=? where user_id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(3, p.userID);
        ps.setString(1, p.name);
        ps.setString(2, p.email);
        ps.executeUpdate();
    }
}
