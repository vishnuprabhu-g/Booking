package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainClassDO {

    public void add(TrainClass obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into train_class (train_id ,class_id,total_compartment ) values (? , ? ,?)";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.trainId);
        ps.setLong(2, obj.classID);
        ps.setInt(3, obj.total_compartment);
        ps.executeUpdate();
    }

    public void update(TrainClass obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update train_class set total_compartment=? where train_id = ? and class_id=?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setInt(1, obj.total_compartment);
        ps.setLong(3, obj.classID);
        ps.setLong(2, obj.trainId);
        ps.executeUpdate();
    }

    public List<TrainClass> getAll(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class where train_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        List<TrainClass> out = new ArrayList<>();
        while (rs.next()) {
            TrainClass obj = new TrainClass();
            obj.trainId = rs.getLong("train_id");
            obj.classID = rs.getLong("class_id");
            out.add(obj);
        }
        return out;
    }

    public TrainClass get(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class where train_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        TrainClass obj = new TrainClass();
        if (rs.next()) {
            obj.trainId = rs.getLong("train_id");
            obj.classID = rs.getLong("class_id");
        }
        return obj;
    }

    public int getCountOfCompartment(long trainID, long classID) throws SQLException {
        int compCount = 0;
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class where train_id =? and class_id=?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trainID);
        ps.setLong(2, classID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            compCount = rs.getInt("total_compartment");
        }
        return compCount;
    }

    public boolean isUpdate(long trainID, long classID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class where train_id =? and class_id=?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, trainID);
        ps.setLong(2, classID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }

}
