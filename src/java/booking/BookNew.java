package booking;

import Do.TrainClassStatusDO;
import Domain.Passenger;
import Domain.TrainClassStatus;
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

public class BookNew extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            util.CommitUtil.commit();
            int gender, halfTicket = 0, seniorCount = 0, pref, prefCount = 0, age;
            Boolean isIgnoreWarning;
            Pattern pattern = Pattern.compile("^[a-zA-Z\\ ]*\\.?[a-zA-Z]*\\ ?[a-zA-Z]*$");
            Matcher matcher;
            String name, onlyif = request.getParameter("confirmBerth");
            int requiredBerth;
            requiredBerth = Integer.parseInt(request.getParameter("berth"));
            String ignore = request.getParameter("ignore");
            if (ignore == null || ignore.trim().equals("")) {
                isIgnoreWarning = false;
            } else {
                isIgnoreWarning = true;
            }
            int index;
            int prefchoiceLower = 0;
            List<Passenger> passengerList = new ArrayList<Passenger>();
            List<UnderPassenger> childList = new ArrayList<UnderPassenger>();
            TrainClassStatusDO tcsdo = new TrainClassStatusDO();
            TrainClassStatus tcs = tcsdo.get(1);
            boolean isChild, isAnyChild = false, seniorCorrection = false;

            for (index = 0; index < 6; index++) {
                name = request.getParameter("name" + index);
                if (name == null || name.trim().equals("")) {
                    break;
                }
                matcher = pattern.matcher(name);
                if (!matcher.matches()) {
                    out.println("002");
                    out.flush();
                    out.close();
                    return;
                }
                age = Integer.parseInt(request.getParameter("age" + index));
                if (age <= 5) {
                    isChild = true;
                    isAnyChild = true;
                } else {
                    isChild = false;
                }

                if (age > 150 || age <= 0) {
                    out.println("003");/*Invalid age*/

                    out.flush();
                    out.close();
                    return;
                }
                pref = Integer.parseInt(request.getParameter("berth" + index));
                if (age >= 60 && pref == 0)//senior not made choice ==>make lower as prefered one
                {
                    pref = 1;
                    seniorCorrection = true;
                }
                gender = Integer.parseInt(request.getParameter("gender" + index));
                if (!isChild) {
                    if (pref == 1) {
                        prefchoiceLower++;
                    }
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
            System.out.println();

            if (passengerList.isEmpty() && childList.isEmpty()) {
                out.println("004");/*Empty set*/

            } else if (passengerList.isEmpty() && childList.size() > 0) {
                out.println("001");/*Only kids*/

            }
            BookUtil bookUtil = new BookUtil();
            BookingClass booking = new BookingClass();
            booking.totalPassenger = passengerList.size();
            booking.requiredLower = requiredBerth;
            booking.preferredLower = prefchoiceLower;
            booking.totalChild = childList.size();
            booking.half = halfTicket;
            booking.senior = seniorCount;
            booking.adult = passengerList.size() - halfTicket;
            booking.child = childList;
            bookUtil.booking = booking;

            int box;
            if (passengerList.size() >= 5) {
                box = bookUtil.getANewBox(passengerList.size());
                System.out.println("In the booking of >5 tics and box=" + box);
                if (box == 0) {
                    bookUtil.openBook(passengerList);
                } else {
                    booking.box = box;
                    bookUtil.booking = booking;
                    bookUtil.arrangeABox(box, passengerList);
                }
                response.sendRedirect("user/ViewBookedTicket.jsp?pnr=" + booking.pnr);

            } else if (passengerList.size() <= 2) {
                box = bookUtil.getFew(passengerList);
                System.out.println("In the booking of <2 tics and box=" + box);
                if (box == 0) {
                    bookUtil.openBook(passengerList);
                } else {
                    booking.box = box;
                    bookUtil.booking = booking;
                    bookUtil.ArrangeFew(box, passengerList);
                }
                response.sendRedirect("user/ViewBookedTicket.jsp?pnr=" + booking.pnr);

            } else {
                box = bookUtil.getFew(passengerList);
                System.out.println("In the booking of 3-4 tics and box=" + box);
                if (box == 0) {
                    bookUtil.openBook(passengerList);
                } else {
                    booking.box = box;
                    bookUtil.booking = booking;
                    bookUtil.arrangeHalf(box, passengerList);
                }
                response.sendRedirect("user/ViewBookedTicket.jsp?pnr=" + booking.pnr);

            }
            util.CommitUtil.commit();
        } catch (SQLException ex) {
            System.out.println("Exception in booknew\n" + ex);
            out.println("006");
            ex.printStackTrace();
        } catch (NumberFormatException numex) {
            System.out.println("numberformat exception in booknew\n");
            out.println("006Number format exception in the system..!");
            numex.printStackTrace();
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