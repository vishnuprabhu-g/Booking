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

    public int getFew(int size) throws SQLException {
        int box = tcssdo.getBoxFreeforPassengers(tcsID, size);
        System.out.println("The found box for " + size + " passengers is " + box);
        return box;
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
    
    public void openBook(List<Passenger> passList)
    {
        
    }
}
