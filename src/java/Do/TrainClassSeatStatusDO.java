package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainClassSeatStatusDO {

    public void add(TrainClassSeatStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into train_class_seat_status (t_class_status_id ,seat_no ,availability ,pnr ,type_id ) values (? , ?, ?, ?, ? )";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.tClassStatusId);
        ps.setInt(2, obj.seatNo);
        ps.setBoolean(3, obj.availability);
        ps.setLong(4, obj.pnr);
        ps.setLong(5, obj.typeId);
        ps.executeUpdate();

    }

    public void update(TrainClassSeatStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update train_class_seat_status set availability= ? where train_class_seat_status_id = ?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setBoolean(1, obj.availability);
        ps.setLong(2, obj.trainClassSeatStatusId);
        ps.executeUpdate();

    }

    public void updateAfter(TrainClassSeatStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "update train_class_seat_status set availability= ? where train_class_status_id = ? and seat_no=?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setBoolean(1, obj.availability);
        ps.setLong(2, obj.tClassStatusId);
        ps.setInt(3, obj.seatNo);
        ps.executeUpdate();

    }

    public List<TrainClassSeatStatus> getAll(long id) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_seat_status where train_class_status_id =?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();
        List<TrainClassSeatStatus> out = new ArrayList<TrainClassSeatStatus>();
        while (rs.next()) {
            TrainClassSeatStatus obj = new TrainClassSeatStatus();
            obj.trainClassSeatStatusId = rs.getLong("train_class_seat_status_id");
            obj.tClassStatusId = rs.getLong("train_class_status_id");
            obj.seatNo = rs.getInt("seat_no");
            obj.availability = rs.getBoolean("availability");
            obj.typeId = rs.getLong("seat_type_id");
            out.add(obj);
        }

        return out;
    }

    public TrainClassSeatStatus get(long id, int near, int box) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q;
        PreparedStatement ps;
        if (box == 0) {
            q = " select * from train_class_seat_status where availability=1 and train_class_status_id=? order by abs(seat_no-?) limit 1;";
            ps = con.prepareStatement(q);
            ps.setLong(1, id);
            ps.setInt(2, near);
        } else {
            q = " select * from train_class_seat_status where availability=1 and train_class_status_id=? and box=? order by abs(seat_no-?) limit 1;";
            ps = con.prepareStatement(q);
            ps.setLong(1, id);
            ps.setInt(3, near);
            ps.setInt(2, box);
        }
        ResultSet rs = ps.executeQuery();
        TrainClassSeatStatus obj;
        if (rs.next()) {
            obj = new TrainClassSeatStatus();
            //obj.tClassStatusId = rs.getLong("t_class_status_id");
            obj.seatNo = rs.getInt("seat_no");
            //obj.availability = rs.getBoolean("availability");
            obj.typeId = rs.getLong("seat_type_id");
            obj.trainClassSeatStatusId = rs.getLong("train_class_seat_status_id");
            obj.box = rs.getInt("box");
        } else {
            obj = null;
        }

        return obj;
    }

    public int getCount(long classId, long seatTypeID) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select COUNT(*) as rowcount from train_class_seat_status where train_class_status_id =? and seat_type_id=? and availability=1;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, classId);
        ps.setLong(2, seatTypeID);
        ResultSet rs = ps.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt("rowcount");
        }

        return count;
    }

    public void addSleeper(long classStatusId) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        int i;
        for (i = 1; i <= 72; i++) {
            String q = "insert into train_class_seat_status (train_class_status_id ,seat_no ,availability ,seat_type_id ) values (? , ?, ?, ? )";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setLong(1, classStatusId);
            ps.setInt(2, i);
            ps.setBoolean(3, true);
            int j = i % 8;
            if (j > 3 && j < 7) {
                j -= 3;
            } else if (j == 7) {
                continue;
            } else if (j == 0) {
                j = 4;
            }
            ps.setLong(4, j);
            ps.executeUpdate();
        }

    }

    public TrainClassSeatStatus getPref(long id, int type_id, int near, int box) throws SQLException {
        System.out.println(box);
        Connection con = util.ConnectionUtil.getConnection();
        String q;
        PreparedStatement ps;
        if (box == 0) {
            q = "select * from train_class_seat_status where train_class_status_id =? and seat_type_id=? and availability=1 order by abs(seat_no-?) limit 1;";
            ps = con.prepareStatement(q);
            ps.setLong(1, id);
            ps.setInt(2, type_id);
            ps.setInt(3, near);
        } else {
            q = "select * from train_class_seat_status where train_class_status_id =? and seat_type_id=? and availability=1 and box=? order by abs(seat_no-?) limit 1;";
            ps = con.prepareStatement(q);
            ps.setLong(1, id);
            ps.setInt(2, type_id);
            ps.setInt(4, near);
            ps.setInt(3, box);
        }
        ResultSet rs = ps.executeQuery();
        TrainClassSeatStatus obj = new TrainClassSeatStatus();
        if (rs.next()) {
            //obj.tClassStatusId = rs.getLong("t_class_status_id");
            obj.seatNo = rs.getInt("seat_no");
            obj.trainClassSeatStatusId = rs.getLong("train_class_seat_status_id");
            //obj.availability = rs.getBoolean("availability");
            obj.typeId = type_id;
            obj.box = rs.getInt("box");
        } else {
            obj = null;
        }

        return obj;
    }

    public TrainClassSeatStatus getTrainClassSeat(long id, int seat_no) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = " select * from train_class_seat_status where seat_no=? and train_class_status_id=? ";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setInt(1, seat_no);
        ps.setLong(2, id);
        ResultSet rs = ps.executeQuery();
        TrainClassSeatStatus obj;
        if (rs.next()) {
            obj = new TrainClassSeatStatus();
            //obj.tClassStatusId = rs.getLong("t_class_status_id");
            obj.seatNo = rs.getInt("seat_no");
            //obj.availability = rs.getBoolean("availability");
            obj.typeId = rs.getLong("seat_type_id");
            obj.trainClassSeatStatusId = rs.getLong("train_class_seat_status_id");
        } else {
            obj = null;
        }

        return obj;
    }

    public int getBoxFreeforPassengers(long tcsID, int noOfpassengers) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = " select count(*) as free,box from train_class_seat_status where availability=1 and train_class_status_id=? group by box;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, tcsID);
        int box, free, lastBox = 0, diff = 9;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            free = rs.getInt("free");
            box = rs.getInt("box");
            if (free < noOfpassengers) {
                continue;
            } else if (free == noOfpassengers) {
                return box;
            } else {
                if ((free - noOfpassengers) < diff) {
                    lastBox = box;
                    diff = free - noOfpassengers;
                }
            }
        }
        return lastBox;
    }
    
    public List<Integer> getBoxsFreeforPassengers(long tcsID, int noOfpassengers) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = " select count(*) as free,box from train_class_seat_status where availability=1 and train_class_status_id=? group by box;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, tcsID);
        int box, free;
        List<Integer> boxs=new ArrayList<Integer>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            free = rs.getInt("free");
            box = rs.getInt("box");
            if (free < noOfpassengers) {
                continue;
            }
            else
            {
               boxs.add(box);
            }
        }
        return boxs;
    }
    
     public int getBoxFreeforPassengersRelaxed(long tcsID, int noOfpassengers) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = " select count(*) as free,box from train_class_seat_status where availability=1 and train_class_status_id=? group by box;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, tcsID);
        int box, free, lastBox = 0, diff = 9,maxFree=0,freeBox=0;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            free = rs.getInt("free");
            box = rs.getInt("box");
            if (free < noOfpassengers) {
                if(free>maxFree)
                {
                    maxFree=free;
                    freeBox=box;
                }
            } else if (free == noOfpassengers) {
                return box;
            } else {
                if ((free - noOfpassengers) < diff) {
                    lastBox = box;
                    diff = free - noOfpassengers;
                }
            }
        }
        if(lastBox==0)
            return freeBox;
        return lastBox;
    }
    
}
