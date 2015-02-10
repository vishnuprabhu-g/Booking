package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainStatusDO {

    public void add(TrainStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into train_status (staus_id ,journey_id ) values (? , ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.statusId);
        ps.setLong(2, obj.journeyId);
        ps.executeUpdate();

    }

    public void update(TrainStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update train_status set staus_id= ?,journey_id= ? where staus_id = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.statusId);
        ps.setLong(2, obj.journeyId);
        ps.setLong(3, obj.statusId);
        ps.executeUpdate();

    }

    public List<TrainStatus> getAll(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_status where staus_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        List<TrainStatus> out = new ArrayList<TrainStatus>();
        while (rs.next()) {
            TrainStatus obj = new TrainStatus();
            obj.statusId = rs.getLong("staus_id");
            obj.journeyId = rs.getLong("journey_id");
            out.add(obj);
        }

        return out;
    }

    public TrainStatus get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_status where journey_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        TrainStatus obj = new TrainStatus();
        if (rs.next()) {
            obj.statusId = rs.getLong("status_id");
            obj.journeyId = rs.getLong("journey_id");
        }

        return obj;
    }
}
