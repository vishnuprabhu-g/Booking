/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vishnu-pt517
 */
public class ClassDistanceFare {

    public double getFare(long classId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from class_distance_fare where class_id =? ;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, classId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            double val = rs.getDouble("fare_per_km");

            return val;
        } else {

            return -1;
        }
    }
}
