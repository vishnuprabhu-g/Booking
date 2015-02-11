package booking;

import Domain.*;
import Do.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class BookingClass {

    public boolean onlyConfirm;
    public boolean notConfirm;
    public boolean BookOnlyPreferred;
    public boolean prefViolated;
    public int totalPassenger;
    public int totalChild;
    public int prefrredPassengers;
    public int requiredLower;
    public int bookedLower;
    TrainClassStatusDO trainClassStatusDO;
    TrainClassSeatStatusDO tcsdo;
    PassengerDO pdo;
    TrainClassRacStatusDO racDo;
    List<Passenger> queue = new ArrayList<Passenger>();
    List<UnderPassenger> child = new ArrayList<UnderPassenger>();
    List<TrainClassSeatStatus> undo = new ArrayList<TrainClassSeatStatus>();
    public int sno;
    long pnr;
    public long trianClassId = 1, journey_id = 1;
    int near = 1, adult = 0, half = 0, senior, box = 0;
    int bookedRac = 0;
    public int wait, initial_wait, racVal, max_rac = 18, max_waiting = 50;//fetch it from db
    public int preferredLower;// How many selected lower
    public long class_id, from_id, to_id;
    public String message, coach = "S1";
    TrainClassStatus trainClassStatus;
    int lastStatus, lastSeat;
    SeatPassengerDO seatPDo = new SeatPassengerDO();

    public BookingClass() {
        this.BookOnlyPreferred = false;
        this.prefViolated = false;
        this.bookedLower = 0;
        this.requiredLower = 0;
        this.preferredLower = 0;
        this.totalChild = 0;
        this.totalPassenger = 0;
        this.prefrredPassengers = 0;
        tcsdo = new TrainClassSeatStatusDO();
        racDo = new TrainClassRacStatusDO();
        pdo = new PassengerDO();
        trainClassStatusDO = new TrainClassStatusDO();
        try {
            trainClassStatus = trainClassStatusDO.get(trianClassId);
            sno = trainClassStatus.sno;
            wait = trainClassStatus.waiting;
            initial_wait = trainClassStatus.initialWaiting;
            racVal = trainClassStatus.rac;
        } catch (SQLException ex) {
            System.out.println("Exception in getting the sno\n" + ex);
        }
        pnr = System.currentTimeMillis();
        class_id = 1;
        from_id = 1;
        to_id = 2;
    }

    public void boxSelect() throws SQLException {
        this.box = tcsdo.getBoxFreeforPassengers(trianClassId, totalPassenger);
    }

    public boolean BookPrefrredTicket(Passenger p, int pref) throws SQLException {
        TrainClassSeatStatus tcss = tcsdo.getPref(trianClassId, pref, near, box);
        if (tcss == null) {
            /*System.out.println("In the book pref-getting null in the selected box");
            tcss = tcsdo.getPref(trianClassId, pref, near, 0);
            */
        }

        if (tcss == null) {
            System.out.println("Required berth not available..!");
            return false;
        } else {
            tcss.availability = false;
            tcsdo.update(tcss);
            undo.add(tcss);
            if (tcss.typeId == 1) {
                this.bookedLower++;
            }
            if (racVal > 1) {
                if (racVal <= 4) {
                    p.initialSeatNo = racVal++;
                    p.initialStatusId = 2;
                } else {
                    p.initialSeatNo = initial_wait++;
                    p.initialStatusId = 3;
                }
            } else {
                p.initialSeatNo = tcss.seatNo;
                p.initialStatusId = 1;
            }
            p.seat_no = tcss.seatNo;
            this.near = tcss.seatNo;
            this.box = tcss.box;
            p.sno = sno++;
            p.pnr = pnr;
            p.statusId = 1;
            queue.add(p);
        }
        return true;
    }

    public int checkInBox(Passenger p) throws SQLException {
        if (p.seat_no == 0) {
            TrainClassSeatStatus tcs = tcsdo.get(trianClassId, near, box);
            if (tcs == null) {
                tcs = tcsdo.get(trianClassId, near, 0);
            }
            if (tcs == null) {
                return -1;
            } else {
                box = tcs.box;
                return tcs.box;
            }
        } else {
            TrainClassSeatStatus tcss = tcsdo.getPref(trianClassId, p.seat_no, near, box);
            if (tcss == null) {
                tcss = tcsdo.getPref(trianClassId, p.seat_no, near, 0);
            }
            if (tcss == null) {
                return -1;
            } else {
                box = tcss.box;
                return tcss.box;
            }
        }
    }

    public boolean checkInBoxX(Passenger p, int t_box) throws SQLException {
        if (p.seat_no == 0) {
            TrainClassSeatStatus tcs = tcsdo.get(trianClassId, near, t_box);
            if (tcs == null) {
                return false;
            } else {
                return true;
            }
        } else {
            TrainClassSeatStatus tcss = tcsdo.getPref(trianClassId, p.seat_no, near, t_box);
            if (tcss == null) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean isSameBox(int arr[]) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] != arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public int getCountBox(int[] arr, int num) {
        int count = 0;
        for (int x : arr) {
            if (x == num) {
                count++;
            }
        }
        return count;
    }

    public int getPopularElement(int[] a) {
        int count = 1, tempCount;
        int popular = a[0];
        int temp = 0;
        for (int i = 0; i < (a.length - 1); i++) {
            temp = a[i];
            tempCount = 0;
            for (int j = 1; j < a.length; j++) {
                if (temp == a[j]) {
                    tempCount++;
                }
            }
            if (tempCount > count) {
                popular = temp;
                count = tempCount;
            }
        }
        return popular;
    }

    public boolean bookNear(Passenger p) throws SQLException {
        System.out.println("in method book near..!");
        TrainClassSeatStatus tcss = tcsdo.get(trianClassId, near, box);
        if (tcss == null) {
            tcss = tcsdo.get(trianClassId, near, 0);
        }
        if (tcss == null) {
            System.out.println("No berth available");
            return false;
        } else {
            tcss.availability = false;
            tcsdo.update(tcss);
            undo.add(tcss);
            if (tcss.typeId == 1) {
                this.bookedLower++;
            }
            p.seat_no = tcss.seatNo;
            this.near = tcss.seatNo;
            this.box = tcss.box;
            if (racVal > 1) {
                if (racVal <= 4) {
                    p.initialSeatNo = racVal++;
                    p.initialStatusId = 2;
                } else {
                    p.initialSeatNo = initial_wait++;
                    p.initialStatusId = 3;
                }
            } else {
                p.initialSeatNo = tcss.seatNo;
                p.initialStatusId = 1;
            }
            p.sno = sno++;
            p.pnr = pnr;
            p.statusId = 1;
            queue.add(p);
        }
        return true;
    }

    public boolean bookRAC(Passenger p) throws SQLException {
        TrainClassRacStatus rac = racDo.getAvail(trianClassId);
        System.out.println("in RAC");
        this.notConfirm = true;
        if (rac != null) {
            if (initial_wait == 1) {
                if (racVal <= max_rac) {
                    rac.availability = false;
                    racDo.update(rac);
                    p.statusId = 2;
                    p.initialSeatNo = racVal++;
                    p.seat_no = (int) rac.racNo;
                    p.pnr = pnr;
                    p.sno = sno++;
                    queue.add(p);
                    bookedRac++;
                } else {
                    rac.availability = false;
                    racDo.update(rac);

                    p.initialStatusId = 3;
                    p.initialSeatNo = initial_wait++;
                    lastSeat
                            = p.statusId = 2;
                    p.seat_no = (int) rac.racNo;
                    p.pnr = pnr;
                    p.sno = sno++;
                    queue.add(p);
                    bookedRac++;
                }
            } else {
                rac.availability = false;
                racDo.update(rac);

                p.initialStatusId = 3;
                p.statusId = 2;
                p.initialSeatNo = initial_wait++;
                p.seat_no = (int) rac.racNo;
                p.pnr = pnr;
                p.sno = sno++;
                queue.add(p);
                bookedRac++;
            }
        } else {
            System.out.println("Going to waiting..!");
            p.initialSeatNo = initial_wait++;
            p.seat_no = wait++;
            p.statusId = 3;
            p.pnr = pnr;
            p.sno = sno++;
            queue.add(p);
        }
        return true;
    }

    public boolean isBookingOpen() throws SQLException {
        TrainClassSeatStatus tcss = tcsdo.get(trianClassId, near, 0);
        return (tcss != null);
    }

    public boolean finalise() throws SQLException {
        if (onlyConfirm && notConfirm) {
            message = "Confirmed tickets are not available.";
            this.UndoJobs(message);
            return false;
        } else if (this.requiredLower > this.preferredLower) {
            message = "Perfer atlest " + this.requiredLower + " lower berth.!";
            this.UndoJobs(message);
            return false;
        } else if (this.requiredLower > this.bookedLower) {
            message = "Required number of lower is not available..!";
            this.UndoJobs(message);
            return false;
        } else {
            PassengerTicket pt = new PassengerTicket();
            pt.pnr = pnr;
            pt.Adult = this.totalPassenger;
            pt.fromStationId = 1L;
            pt.toStationId = 1L;
            pt.Children = this.totalChild;
            pt.trainClassStatusId = this.trianClassId;
            /* Ticket fare calculation   ****/
            double oneTicketFare = 0, reservationFare = 0;
            try {
                double distance = new StationDistanceDO().getDistance(from_id, to_id);
                double farePerKM = new ClassDistanceFare().getFare(class_id);
                oneTicketFare = util.FareCalculater.CalculateFare(distance, farePerKM);
                reservationFare = util.FareCalculater.calculateReservationFare(distance);
            } catch (SQLException ex) {
                System.err.println(ex);
            }
            int halfFare = (int) (oneTicketFare * (0.5));
            double SeniorFare = senior * halfFare;
            double halffare = half * halfFare;
            double otherAdult = (this.totalPassenger - (senior + half)) * oneTicketFare;
            double serviceCharg = 10;
            if (this.totalPassenger > 1) {
                serviceCharg = (this.totalPassenger * 5) + 5;
            }
            double fare = util.FareCalculater.roundOff(otherAdult + halffare + SeniorFare);
            pt.basicfare = (int) fare;
            pt.reservationFare = (int) reservationFare * totalPassenger;
            pt.serviceCharge = serviceCharg;
            pt.totalFare = (int) (pt.basicfare + pt.reservationFare + pt.serviceCharge);
            PassengerTicketDO ptdo = new PassengerTicketDO();
            ptdo.add(pt);

            Reservation res = new Reservation();
            res.pnr = pnr;
            res.journeyID = journey_id;
            res.ReservationStatus = 1;
            ReservationDO rsdo = new ReservationDO();
            rsdo.add(res);

            Collections.sort(queue, new Comparator<Passenger>() {
                @Override
                public int compare(Passenger t, Passenger t1) {
                    if (t.no > t1.no) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            
            for(Passenger p:queue)
                System.out.println(p.no);
            

            for (Passenger pss : queue) {
                if (pss.age >= 60 || pss.age <= 12) {
                    pss.fare = (int) ((oneTicketFare * (0.5)) + reservationFare);
                } else {
                    pss.fare = (int) (oneTicketFare + reservationFare);
                }
                pss.coach = coach;
                pdo.add(pss);
                if (pss.initialStatusId != 0) {
                    lastSeat = pss.initialSeatNo;
                    lastStatus = pss.initialStatusId;
                }
                TrainClassSeatStatusDO trainSeatDO = new TrainClassSeatStatusDO();
                TrainClassSeatStatus tcss = trainSeatDO.getTrainClassSeat(trianClassId, pss.seat_no);

                if (pss.initialStatusId == 1) {
                    SeatPassenger seatPassenger = new SeatPassenger();
                    seatPassenger.pnr = this.pnr;
                    seatPassenger.seat_id = tcss.trainClassSeatStatusId;
                    seatPDo.add(seatPassenger);
                }
            }

            TrainClassStatus classSatatus = trainClassStatusDO.get(1);
            classSatatus.available -= this.totalPassenger;
            classSatatus.sno = this.sno;
            classSatatus.waiting = this.wait;
            classSatatus.initialWaiting = this.initial_wait;
            classSatatus.rac = racVal;
            classSatatus.last_seat_no = lastSeat;
            classSatatus.last_status_id = lastStatus;
            trainClassStatusDO.update(classSatatus);

            UnderPassengerDO cdo = new UnderPassengerDO();
            for (UnderPassenger c : child) {
                c.pnr = pnr;
                cdo.add(c);
            }

            StringBuilder sb = new StringBuilder();
            sb.append(new Date()).append("\n");
            sb.append("PNR:").append(pt.pnr).append("<br>");
            for (Passenger p : queue) {
                sb.append("Name:").append(p.name).append(" seat:").append(p.seat_no).append("<br>");
            }
            System.out.println(sb);
            return true;
        }
    }

    public void UndoJobs(String message) throws SQLException {
        for (TrainClassSeatStatus tcss : undo) {
            tcss.availability = true;
            tcsdo.update(tcss);
        }
        System.out.println(message);
        System.out.println("-----------Status-------");
        System.out.println("Booked lower:" + this.bookedLower);
        System.out.println("Pequired lower:" + this.requiredLower);
        System.out.println("Preferred lower:" + this.preferredLower);
        System.out.println("Undo jobs exit..!");
        System.out.println("---------------------------");
    }
}
