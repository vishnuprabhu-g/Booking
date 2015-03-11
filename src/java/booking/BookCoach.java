package booking;

import Do.CoachDO;
import Domain.Passenger;
import Domain.UnderPassenger;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BookCoach extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            List<Passenger> passengerList = new ArrayList<>();
            List<UnderPassenger> childList = new ArrayList<>();
            Pattern pattern = Pattern.compile("^[a-zA-Z\\ ]*\\.?[a-zA-Z]*\\ ?[a-zA-Z]*$");
            int prefCount = 0, halfTicket = 0, seniorCount = 0;
            for (int index = 0; index < 6; index++) {
                String name = request.getParameter("name" + index);
                if (name == null || name.trim().equals("")) {
                    break;
                }
                Matcher matcher = pattern.matcher(name);
                if (!matcher.matches()) {
                    out.println("002");
                    out.flush();
                    out.close();
                    return;
                }
                int age = Integer.parseInt(request.getParameter("age" + index));
                if (age > 150 || age <= 0) {
                    out.println("003");/*Invalid age*/

                    out.flush();
                    out.close();
                    return;
                }
                int pref = Integer.parseInt(request.getParameter("berth" + index));
                int gender = Integer.parseInt(request.getParameter("gender" + index));
                if (age > 5) {
                    if (pref != 0) {
                        prefCount++;
                    }
                    Passenger p = new Passenger();
                    p.age = age;
                    p.gender = gender;
                    p.name = name;
                    p.seat_no = pref;
                    p.no = (index + 1);
                    if (pref == 0) {
                        passengerList.add(p);
                    } else {
                        passengerList.add(prefCount - 1, p);
                    }
                    if (age <= 12 && age > 5) {
                        halfTicket++;
                    } else if (age >= 60) {
                        seniorCount++;
                    }
                } else {
                    UnderPassenger p = new UnderPassenger();
                    p.age = age;
                    p.gender = gender;
                    p.name = name;
                    childList.add(p);
                }
            }

            System.out.println("---------Parsed the input given by user----------");
            System.out.println(passengerList.size());
            System.out.println("--------------------------------------------------");

            if (passengerList.isEmpty() && childList.isEmpty()) {
                out.println("004");
                /*Empty set*/
                out.flush();
                return;
            } else if (passengerList.isEmpty() && childList.size() > 0) {
                out.println("001");
                /*Only kids*/
                out.flush();
                return;
            }

            HttpSession session = request.getSession();
            long userId = (Long) session.getAttribute("user_id");
            long tcsID = (Long) session.getAttribute("tcsID");
            long classID = (Long) session.getAttribute("classID");
            System.out.println("Debug info:" + "user-" + userId + " tcs-" + tcsID + " classID-" + classID);
            CoachDO cdo = new CoachDO();
            CoachBookUtil coachBookUtil = new CoachBookUtil();
            String coach = cdo.getCoachesForPassengers(passengerList, tcsID);

            BookCoachClass booking = new BookCoachClass();
            booking.trianClassId = tcsID;
            booking.t_train_class_id = tcsID;
            booking.class_id = classID;
            booking.userId = (int) userId;
            booking.totalPassenger = passengerList.size();
            booking.totalChild = childList.size();
            booking.half = halfTicket;
            booking.senior = seniorCount;
            booking.adult = passengerList.size() - halfTicket;
            booking.child = childList;
            booking.from_id = (Long) session.getAttribute("from");
            booking.to_id = (Long) session.getAttribute("to");
            booking.afterInit();
            coachBookUtil.bookCoachClass = booking;

            if (!coach.equals("NO")) {
                coachBookUtil.CoachBook(coach, passengerList);
            } else {
                System.out.println("Else block in main servlet");
                coachBookUtil.bookLikeOpen(passengerList);
            }
            util.CommitUtil.commit();
            response.sendRedirect("user/ViewBookedTicket.jsp?pnr=" + booking.pnr);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            out.println("006");
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            out.println("005");
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
