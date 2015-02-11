package booking;

import Do.*;
import Do.TrainClassRacStatusDO;
import Do.TrainClassSeatStatusDO;
import Domain.*;
import Domain.TrainClassRacStatus;
import Domain.TrainClassSeatStatus;
import java.sql.SQLException;
import java.util.List;

public class CancellingClass {

    public long pnr;
    List<Integer> cancel;
    ReservationDO rsdo = new ReservationDO();
    PassengerDO pdo = new PassengerDO();
    TrainClassStatusDO trainClassStatusDO;
    TrainClassSeatStatusDO tcssdo;
    TrainClassStatus tcs;
    public long trainClassStatusId = 1;
    public int maxRac = 18;
    public int wait = 1, refund = 0;
    //public int initial_wait=1;
    boolean waitUpdated = false;
    int amountRefund = 0;

    public CancellingClass() {
        tcssdo = new TrainClassSeatStatusDO();
        this.trainClassStatusDO = new TrainClassStatusDO();
        try {
            tcs = trainClassStatusDO.get(trainClassStatusId);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public boolean cancelCNF(Passenger p) throws SQLException {
        int seat = p.seat_no;
        p.statusId = 4;
        pdo.update(p);
        tcs = trainClassStatusDO.get(trainClassStatusId);
        tcs.available++;
        trainClassStatusDO.update(tcs);

        TrainClassSeatStatus tcss = tcssdo.getTrainClassSeat(trainClassStatusId, seat);

        int racSize = this.RAC2CNF(seat);
        if (racSize == -1)//No rac hence seat should be set as available
        {
            System.out.println("Available the seat:" + seat);
            this.availTheSeat(seat);
        } else if (racSize < maxRac) {
            this.availTheRac(racSize);//Just available the last rac
        } else {
            //there are some wl also and make first wl as last rac and others as seat-=1
            int waitingSize = this.WL2RAC(maxRac);
            if (waitingSize == -1) {
                System.out.println("Available the last rac:" + maxRac);
                this.availTheRac(maxRac);
            }
        }
        QueryDO qdo = new QueryDO();
        qdo.executeQry("delete from seat_passenger where train_class_seat_status_id=" + tcss.trainClassSeatStatusId);
        return true;
    }

    public boolean cancelRAC(Passenger p) throws SQLException {

        List<Passenger> list = pdo.getAllRac(trainClassStatusId);
        int sno = p.sno;
        p.statusId = 4;
        System.out.println("In cancel rac:" + p.sno);
        pdo.update(p);
        System.out.println("RAC list size:" + list.size());
        for (Passenger ps : list) {
            if (ps.sno > sno) {
                ps.seat_no -= 1;
                pdo.update(ps);
            }
        }

        if (list.size() < maxRac) {
            this.availTheRac(list.size());
        } else {
            int waitingCount = this.WL2RAC(maxRac);
            if (waitingCount == -1)//We need to set last rac available
            {
                System.out.println("Available the rac:" + maxRac);
                this.availTheRac(maxRac);
            }
        }
        return true;
    }

    public boolean cancelWL(Passenger p) throws SQLException {
        int waitno = p.seat_no;
        p.statusId = 4;
        pdo.update(p);
        wait = p.seat_no;
        wait--;
        waitUpdated = true;
        List<Passenger> wl = pdo.getAllWl(trainClassStatusId);
        for (Passenger ps : wl) {
            if (ps.seat_no < waitno) {
                continue;
            } else {
                ps.seat_no -= 1;
                wait = ps.seat_no;//
                waitUpdated = true;
                pdo.update(ps);
            }
        }
        //if(wl.size()==0)
        return true;
    }

    private int RAC2CNF(int seat) throws SQLException {
        List<Passenger> list = pdo.getAllRac(trainClassStatusId);
        //System.out.println("Size of Plist" + list.size());
        if (!list.isEmpty()) {
            Passenger p = list.get(0);
            p.statusId = 1;
            p.seat_no = seat;
            pdo.update(p);
        } else {
            return -1;
        }
        for (int i = 1; i < list.size(); i++) {
            Passenger ps = list.get(i);
            ps.seat_no -= 1;
            pdo.update(ps);
        }
        return list.size();
    }

    private int WL2RAC(int rac) throws SQLException {
        List<Passenger> wl = pdo.getAllWl(trainClassStatusId);
        if (!wl.isEmpty()) {
            Passenger p = wl.get(0);
            p.statusId = 2;
            p.seat_no = rac;
            pdo.update(p);
            wait = 0;//
            waitUpdated = true;
        } else {
            return -1;
        }
        for (int i = 1; i < wl.size(); i++) {
            Passenger ps = wl.get(i);
            ps.seat_no -= 1;
            wait = ps.seat_no;//
            pdo.update(ps);
            waitUpdated = true;
        }
        return wl.size();
    }

    private boolean availTheSeat(int seat_no) throws SQLException {
        TrainClassSeatStatusDO tcssdo = new TrainClassSeatStatusDO();
        TrainClassSeatStatus tcss = new TrainClassSeatStatus();
        tcss.tClassStatusId = this.trainClassStatusId;
        tcss.seatNo = seat_no;
        tcss.availability = true;
        tcssdo.updateAfter(tcss);
        return true;
    }

    private boolean availTheRac(int seat_no) throws SQLException {
        TrainClassRacStatusDO tcssdo = new TrainClassRacStatusDO();
        TrainClassRacStatus tcss = new TrainClassRacStatus();
        tcss.trainClassStatusId = this.trainClassStatusId;
        tcss.racNo = seat_no;
        tcss.availability = true;
        tcssdo.updateAfter(tcss);
        return true;
    }

    public void finalise() throws SQLException {
        TrainClassStatusDO statusDo = new TrainClassStatusDO();
        TrainClassStatus tcs = new TrainClassStatus();
        tcs.trianClassStatusId = this.trainClassStatusId;
        tcs.waiting = this.wait + 1;
        if (waitUpdated) {
            statusDo.updateWaiting(tcs);
        }

        boolean anyTicket = pdo.checkUncancelled(pnr);
        if (!anyTicket) {
            refund = 1;
            Reservation res = rsdo.get(pnr);
            res.ReservationStatus = 3;
            rsdo.update(res);
        } else {
            Reservation res = rsdo.get(pnr);
            res.ReservationStatus = 2;
            rsdo.update(res);
        }
    }
}
