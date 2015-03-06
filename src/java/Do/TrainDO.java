package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainDO {

    public void add(Train obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into train (train_id ,name ,from_station_id ,to_station_id ,travel_time ) values (? , ?, ?, ?, ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.trainId);
        ps.setString(2, obj.name);
        ps.setLong(3, obj.fromStationId);
        ps.setLong(4, obj.toStationId);
        ps.setInt(5, obj.travelTime);
        ps.executeUpdate();
    }

    public void update(Train obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update train set train_id= ?,name= ?,from_station_id= ?,to_station_id= ?,travel_time= ? where train_id = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.trainId);
        ps.setString(2, obj.name);
        ps.setLong(3, obj.fromStationId);
        ps.setLong(4, obj.toStationId);
        ps.setInt(5, obj.travelTime);
        ps.setLong(6, obj.trainId);
        ps.executeUpdate();
    }

    public List<Train> getAll() throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train";
        PreparedStatement ps = con.prepareStatement(q);
        ResultSet rs = ps.executeQuery();
        List<Train> out = new ArrayList<>();
        while (rs.next()) {
            Train obj = new Train();
            obj.trainId = rs.getLong("train_id");
            obj.name = rs.getString("name");
            obj.fromStationId = rs.getLong("from_station_id");
            obj.toStationId = rs.getLong("to_station_id");
            obj.travelTime = rs.getInt("travel_time");
            out.add(obj);
        }
        return out;
    }

    public Train get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train where train_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        Train obj = new Train();
        if (rs.next()) {
            obj.trainId = rs.getLong("train_id");
            obj.name = rs.getString("name");
            obj.fromStationId = rs.getLong("from_station_id");
            obj.toStationId = rs.getLong("to_station_id");
            obj.travelTime = rs.getInt("travel_time");
        }
        return obj;
    }

}
