/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import Do.ClassDistanceFare;
import Do.PassengerDO;
import Do.PassengerTicketDO;
import Do.ReservationDO;
import Do.SeatPassengerDO;
import Do.StationDistanceDO;
import Do.TrainClassRacStatusDO;
import Do.TrainClassSeatStatusDO;
import Do.TrainClassStatusDO;
import Do.UnderPassengerDO;
import Do.UserProfileDO;
import Domain.Passenger;
import Domain.PassengerTicket;
import Domain.Reservation;
import Domain.SeatPassenger;
import Domain.TrainClassRacStatus;
import Domain.TrainClassSeatStatus;
import Domain.TrainClassStatus;
import Domain.UnderPassenger;
import Domain.UserProfile;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author vishnu-pt517
 */
public class BookCoachClass {

    public int userId;
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
    List<Passenger> queue = new ArrayList<>();
    List<UnderPassenger> child = new ArrayList<>();
    List<TrainClassSeatStatus> undo = new ArrayList<>();
    public int sno;
    long pnr;
    public long trianClassId, journey_id = 1, t_train_class_id = -1;
    int near = 1, adult = 0, half = 0, senior, box = 0;
    int bookedRac = 0;
    public int wait, initial_wait, racVal, max_rac = 8, max_waiting = 50;//fetch it from db
    public int preferredLower;// How many selected lower
    public long class_id, from_id, to_id;
    public String message, coach = "";
    TrainClassStatus trainClassStatus;
    int lastStatus, lastSeat;
    SeatPassengerDO seatPDo = new SeatPassengerDO();

    public BookCoachClass() {
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

    }

    public void afterInit() {
        try {
            trainClassStatus = trainClassStatusDO.get(t_train_class_id);
            sno = trainClassStatus.sno;
            wait = trainClassStatus.waiting;
            initial_wait = trainClassStatus.initialWaiting;
            racVal = trainClassStatus.rac;
        } catch (SQLException ex) {
            System.out.println("Exception in getting the sno\n" + ex);
        }
        pnr = System.currentTimeMillis();
        //class_id = 1;
        //from_id = 1;
        //to_id = 2;
    }

    public void assignPassAndTCSS(Passenger p, TrainClassSeatStatus tcss) {
        if (tcss == null) {
            System.out.println("--!Getting null in assignment..!");
            return;
        } else {
//            tcss.availability = false;
//            tcsdo.update(tcss);
            undo.add(tcss);
            if (tcss.typeId == 1) {
                this.bookedLower++;
            }
            if (racVal > 1) {
                if (racVal <= max_rac) {
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
//            this.near = tcss.seatNo;
//            this.box = tcss.box;
            p.coach = tcss.compartment;
            p.sno = sno++;
            p.pnr = pnr;
            p.statusId = 1;
            p.seat_status_id = tcss.trainClassSeatStatusId;
            queue.add(p);
        }
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

    public boolean finalise() throws SQLException {
        System.out.println("Called finalise");
        System.out.println("Queue Size-->" + queue.size() + "Undo size-->" + undo.size());

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
            pt.fromStationId = from_id;
            pt.toStationId = to_id;
            PassengerTicketDO ptdo = new PassengerTicketDO();
            ptdo.add(pt);

            Reservation res = new Reservation();
            res.userId = userId;
            res.pnr = pnr;
            res.journeyID = journey_id;
            res.classId = class_id;
            res.ReservationStatus = 1;
            res.trainClassStausID = t_train_class_id;
            ReservationDO rsdo = new ReservationDO();
            rsdo.add(res);
            //*This is added to send email for every succssfull ticket booking*//
            if (userId != 1) {
                UserProfile up = new UserProfileDO().get(userId);
                String messageEmail = "Hello " + up.name + ",\n";
                messageEmail += "\tYour ticket is booked,Please find the details of the ticket below.\n";
                messageEmail += "\nPNR\t:" + pnr;
                messageEmail += "\nTotal Passengers\t:" + totalPassenger;
                messageEmail += "\nCheck your pnr status at http://vishnu-pt517:8080/Booking/PNR";
                messageEmail += "\n\nThank you fo using the system";

                new util.MailUtil().SendMail(up.email, messageEmail, "Ticket Book Confirmation");
            }
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

            for (Passenger p : queue) {
                //System.out.println(p.no);
            }

            for (Passenger pss : queue) {
                if (pss.age >= 60 || pss.age <= 12) {
                    pss.fare = (int) ((oneTicketFare * (0.5)) + reservationFare);
                } else {
                    pss.fare = (int) (oneTicketFare + reservationFare);
                }
                pdo.add(pss);
                if (pss.initialStatusId != 0) {
                    lastSeat = pss.initialSeatNo;
                    lastStatus = pss.initialStatusId;
                }
                // TrainClassSeatStatusDO trainSeatDO = new TrainClassSeatStatusDO();
                // TrainClassSeatStatus tcss = trainSeatDO.getTrainClassSeat(trianClassId, pss.seat_no);

                if (pss.initialStatusId == 1) {
                    SeatPassenger seatPassenger = new SeatPassenger();
                    seatPassenger.pnr = this.pnr;
                    seatPassenger.seat_id = pss.seat_status_id;
                    seatPDo.add(seatPassenger);
                }
            }

            TrainClassStatus classSatatus = trainClassStatusDO.get(trianClassId);
            classSatatus.available -= this.totalPassenger;
            classSatatus.sno = this.sno;
            classSatatus.waiting = this.wait;
            classSatatus.initialWaiting = this.initial_wait;
            classSatatus.rac = racVal;
            classSatatus.last_seat_no = lastSeat;
            classSatatus.last_status_id = lastStatus;
            trainClassStatusDO.update(classSatatus);

            UnderPassengerDO cdo = new UnderPassengerDO();
            int no = 1;
            for (UnderPassenger c : child) {
                c.pnr = pnr;
                c.status_id = 1;
                c.no = no++;
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
