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
public class ReservationStatusDO {

    public ReservationStatus get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from reservation_status where id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        ReservationStatus obj = new ReservationStatus();
        if (rs.next()) {
            obj.id = rs.getLong("id");
            obj.status = rs.getString("status");
        }

        return obj;
    }

}
