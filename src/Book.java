package booking;

import Do.TrainClassStatusDO;
import Domain.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Book extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            util.CommitUtil.commit();
            int gender, halfTicket = 0, seniorCount = 0, pref, prefCount = 0, age;
            Boolean isIgnoreWarning;
            Pattern pattern = Pattern.compile("^[a-zA-Z\\ ]*\\.?[a-zA-Z]*\\ ?[a-zA-Z]*$");
            Matcher matcher;
            String name, onlyif = request.getParameter("confirmBerth");
            int requiredBerth = Integer.parseInt(request.getParameter("berth"));
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
                    out.println("002");/* Name invalid*/

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

            if (passengerList.isEmpty() && childList.isEmpty()) {
                out.println("004");/*Empty set*/

            } else if (passengerList.isEmpty() && childList.size() > 0) {
                out.println("001");/*Only kids*/

            } else {
                BookingClass booking = new BookingClass();
                booking.totalPassenger = passengerList.size();
                booking.requiredLower = requiredBerth;
                booking.preferredLower = prefchoiceLower;
                booking.totalChild = childList.size();
                booking.half = halfTicket;
                booking.senior = seniorCount;
                booking.adult = passengerList.size() - halfTicket;
                if (onlyif == null || onlyif.equals("")) {
                    booking.onlyConfirm = false;
                } else {
                    booking.onlyConfirm = true;
                }
                System.out.println("--------------------------");
                System.out.println("passengers:" + passengerList.size());
                System.out.println("Children:" + childList.size());
                System.out.println("RequiredBerth:" + requiredBerth);
                System.out.println("OnlfBookRequired:" + booking.BookOnlyPreferred);
                System.out.println("--------------------------");
                if (passengerList.size() == 1 && isAnyChild) {
                    Passenger p = passengerList.get(0);
                    if (p.seat_no == 0) {
                        p.seat_no = 1;
                    }
                    passengerList.remove(p);
                    passengerList.add(p);
                }
                boolean BookingOpen = true;

                /*This will find best compartment for the passengers*/
                booking.boxSelect();
                /*Actual boooking begins*/
                List<Passenger> processLater = new ArrayList<Passenger>();
                int[] boxs = new int[passengerList.size()];
                int i;

                for (i = 0; i < booking.totalPassenger; i++) {
                    Passenger p = passengerList.get(i);
                    boxs[i] = booking.checkInBox(p);
                }
                int maxPassInBox = booking.getPopularElement(boxs);
                int lastBox = boxs[i - 1];
                for (i = 0; i < booking.totalPassenger; i++) {
                    if (boxs[i] != maxPassInBox) {
                        Passenger p = passengerList.get(i);
                        boolean isAvailInBoxX = booking.checkInBoxX(p, maxPassInBox);
                        if (isAvailInBoxX) {
                            boxs[i] = maxPassInBox;
                        }
                    }
                }
                int oldSameFactor = booking.getCountBox(boxs, maxPassInBox);
                boolean isSameBox = booking.isSameBox(boxs);
                if (!isSameBox) {
                    for (i = 0; i < booking.totalPassenger; i++) {
                        if (boxs[i] != lastBox) {
                            Passenger p = passengerList.get(i);
                            boolean isAvailInBoxX = booking.checkInBoxX(p, lastBox);
                            if (isAvailInBoxX) {
                                boxs[i] = lastBox;
                            }
                        }
                    }
                    int newSameFactor = booking.getCountBox(boxs, lastBox);
                    if (newSameFactor > oldSameFactor) {
                        maxPassInBox = lastBox;
                    }
                }
                for (Passenger p : passengerList) {
                    booking.box = maxPassInBox;
                    if (BookingOpen) {
                        if (p.seat_no != 0) {
                            boolean status = booking.BookPrefrredTicket(p, p.seat_no);
                            if (!status) {
                                BookingOpen = booking.isBookingOpen();
                                processLater.add(p);
                            }
                            if (!status && !BookingOpen) {
                                booking.prefViolated = true;
                                if (!isIgnoreWarning) {
                                    booking.UndoJobs("User prompt..");
                                    out.println("102" + "for passenger(s) from " + p.name);
                                    out.flush();
                                    out.close();
                                    return;
                                }
                            }
                            if (!status && !seniorCorrection) {
                                booking.prefViolated = true;
                                if (!isIgnoreWarning) {
                                    booking.UndoJobs("User prompt..");
                                    out.println("101" + "for passenger(s) from " + p.name);
                                    out.flush();
                                    out.close();
                                    return;
                                }
                            }
                            if (seniorCorrection) {
                                seniorCorrection = false;
                            }
                        } else {
                            BookingOpen = booking.bookNear(p);
                            if (!BookingOpen) {
                                if (!isIgnoreWarning) {
                                    booking.UndoJobs("User prompt..");
                                    out.println("102" + "for passenger(s) from " + p.name);
                                    out.flush();
                                    out.close();
                                    return;
                                }
                            }
                        }
                    }
                    if (!BookingOpen) {
                        System.out.println("-----Booking RAC or WL---");
                        booking.bookRAC(p);
                    }
                }
                for (Passenger p : processLater) {
                    BookingOpen = booking.bookNear(p);
                    if (!BookingOpen) {
                        if (!isIgnoreWarning) {
                            booking.UndoJobs("User prompt..");
                            out.println("102" + "for passenger(s) from " + p.name);
                            out.flush();
                            out.close();
                            return;
                        }
                    }
                }

                booking.child = childList;
                boolean finalStatus = booking.finalise();
                if (finalStatus) {
                    long pnr = booking.pnr;
                    util.CommitUtil.commit();
                    response.sendRedirect("user/ViewBookedTicket.jsp?pnr=" + pnr);
                } else {
                    out.println("104" + booking.message);
                }
            }
        } catch (SQLException e) {
            try {
                util.CommitUtil.rollBack();
            } catch (SQLException ex) {
                Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(e);
            out.println("006");
        } catch (NumberFormatException e) {
            try {
                util.CommitUtil.rollBack();
            } catch (SQLException ex) {
                Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("005");
            System.out.println(e);
        } catch (IllegalStateException e) {
            System.out.println(e);
        } finally {
            out.close();
        }
    }    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

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
