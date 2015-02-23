/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;
import Do.*;
import Domain.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author vishnu-pt517
 */
public class CoachBookUtil {

    CoachDO cdo=new CoachDO();
    void CoachBook(String coach,List<Passenger> pass) throws SQLException {
        Coach selectedCoach=cdo.loadCoach(coach);
        /*This is the coach i'm going to book in no other changes*/
        int required=pass.size();
        int noPref
        for(Passenger p:pass)
        {
            if(p.seat_no==0)
                
        }
    }
    
}
