/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package executable;

import Do.TrainClassSeatStatusDO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vishnu-pt517
 */
public class Main {

    public static void main(String... arg) throws SQLException {
        TrainClassSeatStatusDO tcssdo = new TrainClassSeatStatusDO();
        try {
            tcssdo.addSleeper(2L);
            System.out.println("Added");
            util.CommitUtil.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
