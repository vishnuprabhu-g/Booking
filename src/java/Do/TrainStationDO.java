/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Do;

import Domain.TrainStation;
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
public class TrainStationDO {

    public List<Long> getTrainFromTo(long fromId, long toId) throws SQLException {
        List<Long> trainList = new ArrayList<>();
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select a.station_id as from_id,b.station_id as to_id,a.train_id,a.departure as departure,b.arrival as arrival from train_station a,train_station b"
                + " where a.train_id=b.train_id and a.station_id=? "
                + "and b.station_id=? and( a.order_visit<b.order_visit or b.order_visit=-1)";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, fromId);
        ps.setLong(2, toId);
        ResultSet rs = ps.executeQuery();
        System.out.println(ps);
        while (rs.next()) {
            trainList.add(rs.getLong("train_id"));
        }
        return trainList;
    }

    public void add(TrainStation obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into train_station (train_id ,station_id,order_visit ) values (? , ?, ?)";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.trainId);
        ps.setLong(2, obj.stationId);
        //ps.setTime(3, obj.dept);
        //ps.setTime(4, obj.arrival);
        ps.setInt(3, obj.order);
        ps.executeUpdate();
    }

    public static void main(String[] args) throws SQLException {
        for (Long i : new TrainStationDO().getTrainFromTo(1, 2)) {
            System.out.println(i);
        }
    }
}
