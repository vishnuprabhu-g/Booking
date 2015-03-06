package booking;

import Do.*;
import Domain.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachBookUtil {

    CoachDO cdo = new CoachDO();
    TrainClassSeatStatusDO tcssdo = new TrainClassSeatStatusDO();
    BookCoachClass bookCoachClass;
    long trainClassSatatusId;

    void CoachBook(String coach, List<Passenger> pass) throws SQLException {
        System.out.println("---CoachBook----(coach=" + coach + ")");
        Coach selectedCoach = cdo.loadCoach(coach);
        /*This is the coach i'm going to book in no other changes*/
        int required = pass.size();
        int noPref = 0;
        int pref[] = {0, 0, 0, 0};
        for (Passenger p : pass) {
            if (p.seat_no == 0) {
                noPref++;
            } else {
                pref[p.seat_no - 1]++;
            }
        }
        int rLower = pref[0], rMiddle = pref[1], rUpper = pref[2], rSide = pref[3];
        for (Box b : selectedCoach.boxs) {
            if (b.total >= required && b.Lower >= rLower && b.Middle >= rMiddle && b.Upper >= rUpper && b.Side >= rSide) {
                System.out.println("This is the box:" + coach + b.boxNo);
                this.BookInTheCoachAndBox(coach, b.boxNo, pass);
                bookCoachClass.finalise();
                return;
            }
        }
        System.out.println("Out of first for loop");
        Box[] boxArray = new Box[2];
        boxArray = selectedCoach.boxs.toArray(boxArray);
        for (int i = 0; i < boxArray.length - 1; i++) {
            for (int j = i + 1; j < boxArray.length; j++) {
                int aTotal = boxArray[i].total + boxArray[j].total;
                int aLower = boxArray[i].Lower + boxArray[j].Lower;
                int aMiddle = boxArray[i].Middle + boxArray[j].Middle;
                int aUpper = boxArray[i].Upper + boxArray[j].Upper;
                int aSide = boxArray[i].Side + boxArray[j].Side;
                if (aTotal >= required && aLower >= rLower && aMiddle >= rMiddle && aUpper >= rUpper && aSide >= rSide) {
                    System.out.println("Book combined in the box " + boxArray[i].boxNo + boxArray[j].boxNo);
                    int boxs[] = new int[]{boxArray[i].boxNo, boxArray[j].boxNo};
                    this.BookInTheCoachAndBoxs(coach, boxs, pass);
                    bookCoachClass.finalise();
                    return;
                }
            }
        }
        System.out.println("Out of second for loop");
        this.BookInTheCoachAndBoxsV2(coach, new int[]{1, 2}, pass);

        bookCoachClass.finalise();
    }

    private void BookInTheCoachAndBox(String coach, int box, List<Passenger> passList) throws SQLException {
        List<Passenger> later = new ArrayList<>();
        TrainClassSeatNewDO tcssNewDo = new TrainClassSeatNewDO();
        try {
            for (Passenger p : passList) {
                if (p.seat_no == 0) {
                    later.add(p);
                } else {
                    TrainClassSeatStatus tcss = tcssNewDo.getInCoachInBoxWithPref(coach, box, p.seat_no);
                    if (tcss == null) {
                        System.out.println("Fetching error:");
                    }
                    tcss.availability = false;
                    tcssdo.update(tcss);
                    bookCoachClass.assignPassAndTCSS(p, tcss);
                }
            }
            for (Passenger p : later) {
                TrainClassSeatStatus tcss = tcssNewDo.getInCoachInBoxWithOutPref(coach, box);
                tcss.availability = false;
                tcssdo.update(tcss);
                bookCoachClass.assignPassAndTCSS(p, tcss);
            }
            util.CommitUtil.commit();
        } catch (NullPointerException e) {
            System.out.println("Null P E in book in coach and box:" + e.getMessage());
            throw new NullPointerException();
        }
    }

    private void BookInTheCoachAndBoxs(String coach, int[] boxs, List<Passenger> passList) throws SQLException {
        List<Passenger> later = new ArrayList<>();
        TrainClassSeatNewDO tcssNewDo = new TrainClassSeatNewDO();
        for (Passenger p : passList) {
            if (p.seat_no == 0) {
                later.add(p);
            } else {
                TrainClassSeatStatus tcss = tcssNewDo.getInCoachInBoxWithPref(coach, boxs[0], p.seat_no);
                if (tcss == null) {
                    tcss = tcssNewDo.getInCoachInBoxWithPref(coach, boxs[1], p.seat_no);
                }
                tcss.availability = false;
                tcssdo.update(tcss);
                bookCoachClass.assignPassAndTCSS(p, tcss);
            }
        }
        for (Passenger p : later) {
            TrainClassSeatStatus tcss = tcssNewDo.getInCoachInBoxWithOutPref(coach, boxs[0]);
            if (tcss == null) {
                tcss = tcssNewDo.getInCoachInBoxWithOutPref(coach, boxs[1]);
            }
            tcss.availability = false;
            tcssdo.update(tcss);
            bookCoachClass.assignPassAndTCSS(p, tcss);
        }
        util.CommitUtil.commit();
    }

    private void BookInTheCoachAndBoxsV2(String coach, int[] boxs, List<Passenger> passList) throws SQLException {
        List<Passenger> later = new ArrayList<>();
        TrainClassSeatNewDO tcssNewDo = new TrainClassSeatNewDO();
        for (Passenger p : passList) {
            if (p.seat_no == 0) {
                later.add(p);
            } else {
                TrainClassSeatStatus tcss = tcssNewDo.getInCoachInBoxWithPref(coach, boxs[0], p.seat_no);
                if (tcss == null) {
                    tcss = tcssNewDo.getInCoachInBoxWithPref(coach, boxs[1], p.seat_no);
                }
                if (tcss == null) {
                    later.add(p);
                } else {
                    tcss.availability = false;
                    tcssdo.update(tcss);
                    bookCoachClass.assignPassAndTCSS(p, tcss);
                }
            }
        }
        for (Passenger p : later) {
            TrainClassSeatStatus tcss = tcssNewDo.getInCoachInBoxWithOutPref(coach, boxs[0]);
            if (tcss == null) {
                tcss = tcssNewDo.getInCoachInBoxWithOutPref(coach, boxs[1]);
            }
            tcss.availability = false;
            tcssdo.update(tcss);
            bookCoachClass.assignPassAndTCSS(p, tcss);
        }
        util.CommitUtil.commit();
    }

    public void bookLikeOpen(List<Passenger> pass) throws SQLException {
        List<Passenger> later = new ArrayList<>();
        for (Passenger p : pass) {
            if (p.seat_no == 0) {
                later.add(p);
            } else {
                TrainClassSeatStatus tcss = cdo.getPrefInAllCoach(bookCoachClass.trianClassId, p.seat_no);
                if (tcss == null) {
                    later.add(p);
                } else {
                    tcss.availability = false;
                    tcssdo.update(tcss);
                    bookCoachClass.assignPassAndTCSS(p, tcss);
                }
            }
        }
        for (Passenger p : later) {
            TrainClassSeatStatus tcss = cdo.getInAllCoach(bookCoachClass.trianClassId);
            if (tcss != null) {
                tcss.availability = false;
                tcssdo.update(tcss);
                bookCoachClass.assignPassAndTCSS(p, tcss);
            } else {
                bookCoachClass.bookRAC(p);
            }
        }
        bookCoachClass.finalise();
    }
}
