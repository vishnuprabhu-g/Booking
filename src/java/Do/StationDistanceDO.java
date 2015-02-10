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
public class StationDistanceDO {

    public double getDistance(long from, long to) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from station_distance where from_id =? and to_id=?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, from);
        ps.setLong(2, to);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            double temp = rs.getDouble("distance");

            return temp;
        } else {

            return -1;
        }
    }
}
