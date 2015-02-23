/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import Do.*;
import Domain.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author vishnu-pt517
 */
class BookUtil {

    TrainClassSeatStatusDO tcssdo = new TrainClassSeatStatusDO();
    long tcsID = 1;
    BookingClass booking;

    public int getANewBox(int size) throws SQLException {
        int box = tcssdo.getBoxFreeforPassengers(tcsID, size);
        System.out.println("On searching for a new box for passengers of size:" + size + " found box:" + box);
        return box;
    }

    public boolean arrangeABox(int box, List<Passenger> passList) throws SQLException {
        Collections.sort(passList, new Comparator<Passenger>() {
            @Override
            public int compare(Passenger a, Passenger b) {
                if (a.age > b.age) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        booking.box = box;
        for (Passenger p : passList) {
            if (p.seat_no != 0) {
                boolean st;
                if (p.seat_no != 0) {
                    st = booking.BookPrefrredTicket(p, p.seat_no);
                } else {
                    st = booking.bookNear(p);
                }
                if (!st) {
                    st = booking.BookPrefrredTicketNew(p, p.seat_no);
                    if (!st) {
                        booking.bookNear(p);
                    }
                    booking.box = box;
                }
            }
        }
        booking.box = box;
        for (Passenger p : passList) {
            if (p.seat_no == 0) {
                boolean st;
                if (p.seat_no != 0) {
                    st = booking.BookPrefrredTicketNew(p, p.seat_no);
                } else {
                    st = booking.bookNear(p);
                }
                if (!st) {
                    booking.bookNear(p);
                }
            }
        }
        if (booking.finalise()) {
            util.CommitUtil.commit();
        } else {
            util.CommitUtil.rollBack();
        }
        return true;
    }

    public int getHalfFree(int size) throws SQLException {
        int box = tcssdo.getBoxFreeforPassengers(tcsID, size);
        return box;
    }

    public boolean arrangeHalf(int box, List<Passenger> passList) throws SQLException {
        Collections.sort(passList, new Comparator<Passenger>() {
            @Override
            public int compare(Passenger a, Passenger b) {
                if (a.age > b.age) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        booking.box = box;
        for (Passenger p : passList) {
            boolean st = booking.BookPrefrredTicket(p, p.seat_no);
            if (!st) {
                booking.bookNear(p);
            }
        }
        if (booking.finalise()) {
            util.CommitUtil.commit();
        } else {
            util.CommitUtil.rollBack();
        }
        return true;
    }

    public int getFew(List<Passenger> passList) throws SQLException {
        int size = passList.size(), lastBox = 0, lastMatch = 0;
        List<Integer> boxs = tcssdo.getBoxsFreeforPassengers(tcsID, size);
        if (boxs == null || boxs.isEmpty()) {
            return 0;
        }
        Collections.sort(boxs);
        for (Integer box : boxs) {
            int rt = this.isFit(passList, box);
            System.out.println("On Box" + box + "Rt val" + rt);
            if (rt == 0) {
                return box;
            } else if (rt > 0) {
                if (rt > lastMatch) {
                    lastBox = box;
                    lastMatch = rt;
                }
            }
        }
        System.out.println("The found box for " + size + " passengers is " + lastBox);
        return lastBox;
    }

    public int isFit(List<Passenger> list, int box) throws SQLException {
        int fitCount = 0;//List<TrainClassSeatStatus> undo=new ArrayList<TrainClassSeatStatus>();
        System.out.println("Finding the fit-->");
        for (Passenger p : list) {
            System.out.println(p.toString());
            if (p.seat_no != 0) {
                TrainClassSeatStatus tcss = tcssdo.getPref(tcsID, p.seat_no, 0, box);
                if (tcss != null) {
                    //System.out.println("SeatNo"+tcss.seatNo);
                    tcss.availability = false;
                    tcssdo.update(tcss);
                    //undo.add(tcss);
                    fitCount++;
                }
            } else {
                fitCount++;
            }
        }
        if (list.size() == fitCount) {
            util.CommitUtil.rollBack();
            return 0;
        } else if (fitCount == 0) {
            util.CommitUtil.rollBack();
            return -1;
        } else {
            util.CommitUtil.rollBack();
            return fitCount;
        }
    }

    public boolean ArrangeFew(int box, List<Passenger> passList) throws SQLException {
        for (Passenger p : passList) {
            boolean st = booking.BookPrefrredTicket(p, p.seat_no);
            if (!st) {
                booking.bookNear(p);
            }
        }
        if (booking.finalise()) {
            util.CommitUtil.commit();
        } else {
            util.CommitUtil.rollBack();
        }
        return true;
    }

    public void openBook(List<Passenger> passList) throws SQLException {
        System.out.println("---on open ticket booking---");
        int minMaxBox[] = tcssdo.getCloserOfSize(passList.size());
        if (minMaxBox.length == 2) {
            int minBox = minMaxBox[0];
            int maxBox = minMaxBox[1];
            System.out.println("System found closer box b/w" + minBox + "to" + maxBox);
            for (Passenger p : passList) {
                System.out.println(p);
                boolean booked = false;
                for (int x = minBox; x <= maxBox; x++) {
                    boolean isAvil = booking.searchInBox(x, p);
                    if (isAvil && !booked) {
                        booking.bookInBox(x, p);
                        booked = true;
                        break;
                    }
                }
                if (!booked)//Book without preference in the boxs
                {
                    System.out.println("went to openBook->closer->if Not" + p);
                    for (int x = minBox; x <= maxBox; x++) {
                        if (booking.isAvailInBox(x) && !booked) {
                            //p.seat_no = 0;
                            booking.bookInBox(x, p);
                            booked = true;
                        }
                    }
                }
            }
            booking.finalise();
        } else {
            System.out.println("Since no fit,going to book relaxed");
            booking.box = tcssdo.getBoxFreeforPassengersRelaxed(tcsID, passList.size());
            boolean bookingOpn;
            for (Passenger p : passList) {
                bookingOpn = booking.isBookingOpen();
                if (bookingOpn) {
                    if (p.seat_no != 0) {
                        boolean isBooked = booking.BookPrefrredTicket(p, p.seat_no);
                        if (!isBooked) {
                            booking.bookNear(p);
                        }
                    } else {
                        booking.bookNear(p);
                    }
                } else {
                    booking.bookRAC(p);
                }
            }
            booking.finalise();
        }
    }

    public void bookInCoach(List<Passenger> passenger, String coach) {
        System.out.println("Found the coach->" + coach);
        List<Passenger> Later = new ArrayList<>();
        for (Passenger p : passenger) {
            if (p.seat_no != 0) {

            } else {
                Later.add(p);
            }
        }

    }

    public void openBookInCoach(List<Passenger> passList, String coach) throws SQLException {
        System.out.println("---on open ticket booking---");
        int minMaxBox[] = tcssdo.getCloserOfSizeInCoach(passList.size(), coach);
        if (minMaxBox.length == 2) {
            int minBox = minMaxBox[0];
            int maxBox = minMaxBox[1];
            System.out.println("System found closer box b/w" + minBox + "to" + maxBox);
            for (Passenger p : passList) {
                System.out.println(p);
                boolean booked = false;
                for (int x = minBox; x <= maxBox; x++) {
                    boolean isAvil = booking.searchInBoxInCoach(x, p, coach);
                    if (isAvil && !booked) {
                        booking.bookInBox(x, p);
                        booked = true;
                        break;
                    }
                }
                if (!booked)//Book without preference in the boxs
                {
                    System.out.println("went to openBook->closer->if Not" + p);
                    for (int x = minBox; x <= maxBox; x++) {
                        if (booking.isAvailInBoxInCoach(x, coach) && !booked) {
                            //p.seat_no = 0;
                            booking.bookInBox(x, p);
                            booked = true;
                        }
                    }
                }
            }
            booking.finalise();
        } else {
            System.out.println("Since no fit,going to book relaxed");
            booking.box = tcssdo.getBoxFreeforPassengersRelaxed(tcsID, passList.size());
            boolean bookingOpn;
            for (Passenger p : passList) {
                bookingOpn = booking.isBookingOpen();
                if (bookingOpn) {
                    if (p.seat_no != 0) {
                        boolean isBooked = booking.BookPrefrredTicket(p, p.seat_no);
                        if (!isBooked) {
                            booking.bookNear(p);
                        }
                    } else {
                        booking.bookNear(p);
                    }
                } else {
                    booking.bookRAC(p);
                }
            }
            booking.finalise();
        }
    }

}
