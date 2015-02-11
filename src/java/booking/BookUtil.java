/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import Do.*;
import Domain.*;
import java.sql.SQLException;
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
            boolean st;
            if (p.seat_no != 0) {
                st = booking.BookPrefrredTicket(p, p.seat_no);
            } else {
                st = booking.bookNear(p);
            }
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
        int fitCount = 0;
        for (Passenger p : list) {

            if (p.seat_no != 0) {
                TrainClassSeatStatus tcss = tcssdo.getPref(tcsID, p.seat_no, 0, box);
                if (tcss != null) {
                    fitCount++;
                }
            } else {
                fitCount++;
            }
        }
        if (list.size() == fitCount) {
            return 0;
        } else if (fitCount == 0) {
            return -1;
        } else {
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
        booking.box=tcssdo.getBoxFreeforPassengersRelaxed(tcsID, passList.size());
        boolean bookingOpn;
        for (Passenger p : passList) {
            bookingOpn=booking.isBookingOpen();
                if(bookingOpn)
                {
                    if(p.seat_no!=0)
                    {
                        booking.BookPrefrredTicket(p, p.seat_no);
                    }
                    else
                        booking.bookNear(p);
                }
                else
                    booking.bookRAC(p);
        }
        booking.finalise();
    }
}
