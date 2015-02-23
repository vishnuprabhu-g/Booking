/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import Domain.Passenger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author vishnu-pt517
 */
public class CoachDO {

    public void getCoachesForPassengers(List<Passenger> passengers) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String query = "select compartment,count(*) from train_class_seat_status where availability=1 group by compartment";
        PreparedStatement ps1 = con.prepareCall(query);
        ResultSet rs=ps1.executeQuery();
        while(rs.next())
        {
            
        }
    }
    
}