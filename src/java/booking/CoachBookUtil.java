package booking;

import Do.*;
import Domain.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachBookUtil {

    CoachDO cdo = new CoachDO();
    TrainClassSeatStatusDO tcssdo = new TrainClassSeatStatusDO();

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
                System.out.println("This is the box");
                this.BookInTheCoachAndBox(coach, b.boxNo, pass);
                return;
            }
        }
        System.out.println("Out of first for loop");
        Box[] boxArray = (Box[]) selectedCoach.boxs.toArray();
        System.out.println(boxArray.length);
        for (int i = 0; i < boxArray.length - 1; i++) {
            for (int j = i + 1; j < boxArray.length; j++) {
                int aTotal = boxArray[i].total + boxArray[j].total;
                int aLower = boxArray[i].Lower + boxArray[j].Lower;
                int aMiddle = boxArray[i].Middle + boxArray[j].Middle;
                int aUpper = boxArray[i].Upper + boxArray[j].Upper;
                int aSide = boxArray[i].Side + boxArray[j].Side;
                System.out.println("The values in combined box:"+aTotal+aLower+aMiddle+aUpper+aSide);
                if (aTotal >= required && aLower >= rLower && aMiddle >= rMiddle && aUpper >= rUpper && aSide >= rSide) {
                    System.out.println("Book combined in the box " + i + j);
                }
            }
        }
    }

    private void BookInTheCoachAndBox(String coach, int box, List<Passenger> passList) throws SQLException {
        List<Passenger> later = new ArrayList<>();
        TrainClassSeatNewDO tcssNewDo = new TrainClassSeatNewDO();
        for (Passenger p : passList) {
            if (p.seat_no == 0) {
                later.add(p);
            } else {
                TrainClassSeatStatus tcss = tcssNewDo.getInCoachInBoxWithPref(coach, box, p.seat_no);
                tcss.availability = false;
                tcssdo.update(tcss);
            }
        }
        for (Passenger p : later) {
            TrainClassSeatStatus tcss = tcssNewDo.getInCoachInBoxWithOutPref(coach, box);
            tcss.availability = false;
            tcssdo.update(tcss);
        }
        util.CommitUtil.commit();
    }

}
