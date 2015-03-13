package Do;

import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrainClassSeatStatusDO {

    public void add(TrainClassSeatStatus obj) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "insert into train_class_seat_status (train_class_status_id ,seat_no ,availability ,seat_type_id ,compartment,box) values (? , ?, ?, ?, ? ,?)";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, obj.tClassStatusId);
        ps.setInt(2, obj.seatNo);
        ps.setBoolean(3, obj.availability);
        ps.setLong(4, obj.typeId);
        ps.setString(5, obj.compartment);
        ps.setInt(6, obj.box);
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
        String q = "update train_class_seat_status set availability= ? where train_class_status_id = ? and seat_no=? and compartment=?;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setBoolean(1, obj.availability);
        ps.setLong(2, obj.tClassStatusId);
        ps.setInt(3, obj.seatNo);
        ps.setString(4, obj.compartment);
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

    public List<TrainClassSeatStatus> getAllOfCoach(long id, String compartment) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select * from train_class_seat_status where train_class_status_id =? and compartment=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, id);
        ps.setString(2, compartment);
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
        for (i = 1; i <= 64; i++) {
            String q = "insert into r5.train_class_seat_status (train_class_status_id ,seat_no ,availability ,seat_type_id ) values (? , ?, ?, ? )";
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

    public TrainClassSeatStatus getTrainClassSeat(long id, int seat_no, String coach) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = " select * from train_class_seat_status where seat_no=? and train_class_status_id=? and compartment=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setInt(1, seat_no);
        ps.setLong(2, id);
        ps.setString(3, coach);
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

    public TrainClassSeatStatus getTrainClassSeatOld(long id, int seat_no, String coach) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = " select * from train_class_seat_status where seat_no=? and train_class_status_id=? and compartment=?";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setInt(1, seat_no);
        ps.setLong(2, id);
        ps.setString(3, coach);
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
        List<Integer> boxs = new ArrayList<Integer>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            free = rs.getInt("free");
            box = rs.getInt("box");
            if (free < noOfpassengers) {
                continue;
            } else {
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
        int box, free, lastBox = 0, diff = 9, maxFree = 0, freeBox = 0;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            free = rs.getInt("free");
            box = rs.getInt("box");
            if (free < noOfpassengers) {
                if (free > maxFree) {
                    maxFree = free;
                    freeBox = box;
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
        if (lastBox == 0) {
            return freeBox;
        }
        return lastBox;
    }

    class BoxNFree {

        int box;
        int free;

        public BoxNFree() {
        }

        public BoxNFree(int box, int free) {
            this.box = box;
            this.free = free;
        }

    }

    public int[] getCloserOfSize(int size) throws SQLException {
        int free, total = 0, box[] = new int[20], ibox;
        Connection con = util.ConnectionUtil.getConnection();
        String q = " select count(*) as free,box from train_class_seat_status where availability=1 and train_class_status_id=? group by box;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, 1);
        ResultSet rs = ps.executeQuery();
        List<BoxNFree> boxFree = new ArrayList<BoxNFree>();
        while (rs.next()) {
            free = rs.getInt("free");
            ibox = rs.getInt("box");
            total += free;
            box[ibox] = free;
            boxFree.add(new BoxNFree(ibox, free));
        }
        if (total <= size) {
            return new int[0];
        }

        Collections.sort(boxFree, new Comparator<BoxNFree>() {
            @Override
            public int compare(BoxNFree t, BoxNFree t1) {
                if (t.free > t1.free) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        for (BoxNFree bf : boxFree) {
            System.out.println(bf.box + "-" + bf.free);
        }
        //int maxIndex = boxFree.size() - 1;
        int maxIndex = 9;
        int minWidth = 20, minA = 0, minB = 0;
        for (BoxNFree bf : boxFree) {
            int currBox = bf.box;
            int currFree = 0;
            int a = currBox, b = currBox;
            int TotFree;
            while (currFree < size) {
                if (a > 0) {
                    a--;
                }
                if (b < maxIndex) {
                    b++;
                }
                TotFree = 0;
                for (int x = a; x <= b; x++) {
                    TotFree += box[x];
                }
                currFree = TotFree;
            }
            if ((b - a) < minWidth) {
                minA = a;
                minB = b;
                minWidth = b - a;
            }
        }
        System.out.println(minA + "--" + minB + "--" + minWidth);
        return new int[]{minA, minB};
    }

    public int[] getCloserOfSizeInCoach(int size, String coach) throws SQLException {
        int free, total = 0, box[] = new int[20], ibox;
        Connection con = util.ConnectionUtil.getConnection();
        String q = " select count(*) as free,box from train_class_seat_status where availability=1 and train_class_status_id=? and compartment=? group by box;";
        PreparedStatement ps = con.prepareStatement(q);
        ps.setLong(1, 1);
        ps.setString(2, coach);
        ResultSet rs = ps.executeQuery();
        List<BoxNFree> boxFree = new ArrayList<BoxNFree>();
        while (rs.next()) {
            free = rs.getInt("free");
            ibox = rs.getInt("box");
            total += free;
            box[ibox] = free;
            boxFree.add(new BoxNFree(ibox, free));
        }
        if (total <= size) {
            return new int[0];
        }

        Collections.sort(boxFree, new Comparator<BoxNFree>() {
            @Override
            public int compare(BoxNFree t, BoxNFree t1) {
                if (t.free > t1.free) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        for (BoxNFree bf : boxFree) {
            System.out.println(bf.box + "-" + bf.free);
        }
        //int maxIndex = boxFree.size() - 1;
        int maxIndex = 9;
        int minWidth = 20, minA = 0, minB = 0;
        for (BoxNFree bf : boxFree) {
            int currBox = bf.box;
            int currFree = 0;
            int a = currBox, b = currBox;
            int TotFree;
            while (currFree < size) {
                if (a > 0) {
                    a--;
                }
                if (b < maxIndex) {
                    b++;
                }
                TotFree = 0;
                for (int x = a; x <= b; x++) {
                    TotFree += box[x];
                }
                currFree = TotFree;
            }
            if ((b - a) < minWidth) {
                minA = a;
                minB = b;
                minWidth = b - a;
            }
        }
        System.out.println(minA + "--" + minB + "--" + minWidth);
        return new int[]{minA, minB};
    }

    public TrainClassSeatStatus getPrefInCoach(long tcsid, int type_id, int near, int box, String coach) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q;
        PreparedStatement ps;
        if (box == 0) {
            q = "select * from train_class_seat_status where train_class_status_id =? and seat_type_id=? and availability=1 and compartment=? order by abs(seat_no-?) limit 1;";
            ps = con.prepareStatement(q);
            ps.setLong(1, tcsid);
            ps.setInt(2, type_id);
            ps.setString(3, coach);
            ps.setInt(4, near);
        } else {
            q = "select * from train_class_seat_status where train_class_status_id =? and seat_type_id=? and availability=1 and box=? and compartment=? order by abs(seat_no-?) limit 1;";
            ps = con.prepareStatement(q);
            ps.setLong(1, tcsid);
            ps.setInt(2, type_id);
            ps.setInt(3, box);
            ps.setString(4, coach);
            ps.setInt(5, near);
        }
        ResultSet rs = ps.executeQuery();
        TrainClassSeatStatus obj = new TrainClassSeatStatus();
        if (rs.next()) {
            obj.seatNo = rs.getInt("seat_no");
            obj.trainClassSeatStatusId = rs.getLong("train_class_seat_status_id");
            obj.typeId = type_id;
            obj.box = rs.getInt("box");
        } else {
            obj = null;
        }

        return obj;
    }

    public TrainClassSeatStatus getInCoach(long id, int near, int box, String coach) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q;
        PreparedStatement ps;
        if (box == 0) {
            q = " select * from train_class_seat_status where availability=1 and train_class_status_id=? and compartment=? order by abs(seat_no-?) limit 1;";
            ps = con.prepareStatement(q);
            ps.setLong(1, id);
            ps.setString(2, coach);
            ps.setInt(3, near);
        } else {
            q = " select * from train_class_seat_status where availability=1 and train_class_status_id=? and box=? and compartment=? order by abs(seat_no-?) limit 1;";
            ps = con.prepareStatement(q);
            ps.setLong(1, id);
            ps.setInt(2, box);
            ps.setString(3, coach);
            ps.setInt(4, near);
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

    int maxBoxInCoach(String coach) throws SQLException {
        Connection con = util.ConnectionUtil.getConnection();
        String q = "select box,count(*) as counts from train_class_seat_status where availability=1 group by box order by counts desc";
        PreparedStatement ps = con.prepareStatement(q);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 1;
    }

}
