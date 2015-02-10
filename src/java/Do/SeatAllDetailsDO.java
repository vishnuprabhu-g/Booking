/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import Domain.TrainClassSeatStatus;
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
public class SeatAllDetailsDO {

    public List<TrainClassSeatStatus> getAll(long tcs) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "Select * from seat_all_details where train_class_status_id=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, tcs);
        ResultSet rs = ps.executeQuery();
        List<TrainClassSeatStatus> tcssList = new ArrayList<TrainClassSeatStatus>();
        while (rs.next()) {
            TrainClassSeatStatus tcss = new TrainClassSeatStatus();
            tcss.availability = rs.getBoolean("availability");
            tcss.pnr = rs.getLong("pnr");
            tcss.seatNo = rs.getInt("seat_no");
            tcss.typeId = rs.getInt("seat_type_id");
            tcssList.add(tcss);
        }
        return tcssList;
    }
}
