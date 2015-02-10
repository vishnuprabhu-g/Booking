package booking;

import Do.*;
import Do.SeatPassengerDO;
import Do.TrainClassSeatStatusDO;
import Domain.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Chart extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            long trainClassStatusID = 1;
            PassengerDO pdo = new PassengerDO();
            TrainClassSeatStatusDO tcssdo = new TrainClassSeatStatusDO();
            SeatPassengerDO seatPassengerDo = new SeatPassengerDO();
            SeatPassengerDO seatPDo = new SeatPassengerDO();
            ChartDO chartDO = new ChartDO();
            List<Passenger> cnfList = chartDO.getAllNewCnf();

            for (Passenger p : cnfList) {
                System.out.println("In adding");
                TrainClassSeatStatus tcss = tcssdo.getTrainClassSeat(trainClassStatusID, p.seat_no);
                SeatPassenger sp = new SeatPassenger();
                sp.pnr = p.pnr;
                sp.seat_id = tcss.trainClassSeatStatusId;
                seatPDo.add(sp);
            }

            for (Passenger p : cnfList) {
                List<Passenger> pnrCnf = chartDO.getAllCNFofPNR(p.pnr);
                if (!pnrCnf.isEmpty())//Somebody already cnf
                {
                    System.out.println(p.name + p.age);
                    int thisPassengerSeat = p.seat_no;
                    int newPassengerSeat;
                    int mid = 0;
                    for (Passenger cnf : pnrCnf) {
                        mid += cnf.seat_no;
                    }
                    mid /= pnrCnf.size();
                    System.out.println("Find a seat near to-" + mid + " for " + p.name);
                    Passenger foundSeat = chartDO.getSeatNo(mid);
                    System.out.println("Found a passenger ticket:" + foundSeat.name + " " + foundSeat.seat_no);
                    newPassengerSeat = foundSeat.seat_no;

                    TrainClassSeatStatus foundtcss = tcssdo.getTrainClassSeat(trainClassStatusID, foundSeat.seat_no);

                    if (p.pnr == foundSeat.pnr) {
                        continue;
                    } else if (!foundtcss.availability) {
                        p.seat_no = newPassengerSeat;
                        foundSeat.seat_no = thisPassengerSeat;

                        pdo.update(p);
                        pdo.update(foundSeat);

                        ///////////////////////////////swap complete
                        TrainClassSeatStatusDO trainSeatDO = new TrainClassSeatStatusDO();
                        TrainClassSeatStatus oldTCSS = trainSeatDO.getTrainClassSeat(trainClassStatusID, thisPassengerSeat);

                        seatPDo.delete(oldTCSS.trainClassSeatStatusId);
                        SeatPassenger sp = new SeatPassenger();

                        TrainClassSeatStatus newTCSS = trainSeatDO.getTrainClassSeat(trainClassStatusID, newPassengerSeat);
                        seatPDo.delete(newTCSS.trainClassSeatStatusId);
                        sp.pnr = p.pnr;
                        sp.seat_id = newTCSS.trainClassSeatStatusId;
                        seatPDo.add(sp);

                        SeatPassenger sp2 = new SeatPassenger();
                        sp2.pnr = foundSeat.pnr;
                        sp2.seat_id = oldTCSS.trainClassSeatStatusId;
                        seatPDo.add(sp2);
                    } else {
                        TrainClassSeatStatus oldtcss = tcssdo.getTrainClassSeat(trainClassStatusID, thisPassengerSeat);
                        seatPDo.delete(oldtcss.trainClassSeatStatusId);
                        SeatPassenger sp = new SeatPassenger();
                        sp.pnr = p.pnr;
                        sp.seat_id = foundtcss.trainClassSeatStatusId;
                        seatPDo.add(sp);
                    }
                }
            }

            List< TrainClassSeatStatus> allSeat = tcssdo.getAll(trainClassStatusID);
            out.println("<table class=\"table table-bordered\"><thead><tr><th>Seat No</th><th>PNR</th> <th>Passenger name</th><th>Age</th></tr></thead>");
            out.println("<tbody>");
            for (TrainClassSeatStatus seat : allSeat) {
                if (seat.availability) {
                    out.println("<tr><td>" + seat.seatNo + "</td><td>  " + "Not booked" + "</td><td> " + "----" + "</td><td>" + "-" + "</td></tr> ");
                } else {
                    long pnr = seatPassengerDo.get(seat.trainClassSeatStatusId).pnr;
                    Passenger p = pdo.getBySeat(pnr, seat.seatNo);
                    out.println("<tr><td>" + seat.seatNo + "</td><td>  " + pnr + "</td><td> " + p.name + "</td><td>" + p.age + "</td></tr> ");
                }
            }
            out.println("</tbody></table>");

            out.println("Passengers who are in RAC and share the rac seats<br>");
            out.println("<table class=\"table table-bordered\"><thead><tr><th>PNR</th><th>Name</th><th>Age</th><th>Seat</th></tr></thead>");
            out.println("<tbody>");
            List<Passenger> racList = chartDO.getAllByStatus(2);
            int i = 0;
            boolean flg = false;
            for (Passenger p : racList) {
                String seat = "" + ((i * 8) + 7);
                if (!flg) {
                    seat += " A";
                    flg = true;
                } else {
                    seat += " B";
                    flg = false;
                    i++;
                }
                out.println("<tr><td>" + p.pnr + "</td><td> " + p.name + "</td><td>" + p.age + "</td><td>" + seat + "</tr>");
            }
            out.println("</tbody></table>");

            out.println("Passengers who are in WL and can't travel in the train<br>");
            out.println("<table class=\"table table-bordered\"><thead><tr><th>PNR</th><th>Name</th></tr></thead>");
            out.println("<tbody>");
            List<Passenger> wlList = chartDO.getAllByStatus(3);
            for (Passenger p : wlList) {
                out.println("<tr><td>" + p.pnr + "</td><td> " + p.name + " </td></tr>");
            }
            out.println("</tbody></table>");
            chartDO.close(trainClassStatusID);
            util.CommitUtil.commit();
        } catch (SQLException ex) {
            try {
                util.CommitUtil.rollBack();
            } catch (SQLException ex1) {
                Logger.getLogger(Chart.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println(ex);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
