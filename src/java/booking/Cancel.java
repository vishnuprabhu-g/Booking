package booking;

import Do.*;
import Domain.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Cancel extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            util.CommitUtil.commit();//DB commit
            HttpSession session = request.getSession();
            PassengerDO pdo = new PassengerDO();
            UnderPassengerDO udo = new UnderPassengerDO();
            PassengerTicketDO ptdo = new PassengerTicketDO();
            Long pnr = (Long) session.getAttribute("pnr");
            Long trainClassStatusId = 1L;
            int refund = 0;
            boolean childDeleted = false;
            if (pnr == null) {
                out.println("Invalid access..!");
            } else {
                List<Integer> cancel = new ArrayList<Integer>();
                for (int i = 1; i <= 6; i++) {
                    String val = request.getParameter("sno" + i);
                    if (val == null) {
                    } else {
                        cancel.add(Integer.parseInt(val.trim()));
                    }
                }
                for (int i = 1; i <= 5; i++) {
                    String val = request.getParameter("child" + i);
                    if (val == null) {
                    } else {
                        String[] nameAge = val.split("-");
                        String name = nameAge[0];
                        int age = Integer.parseInt(nameAge[1]);
                        UnderPassenger up = new UnderPassenger();
                        up.age = age;
                        up.name = name;
                        up.pnr = pnr;
                        udo.delete(up);
                        childDeleted = true;
                        PassengerTicket pt = ptdo.get(pnr);
                        pt.Children--;
                        ptdo.update(pt);
                    }
                }

                if (cancel.isEmpty() && !childDeleted) {
                    out.println("Nothing to cancel..!");
                    return;
                }
                if (cancel.isEmpty() && childDeleted) {
                    out.println("Children ticket cancelled");
                } else {
                    CancellingClass can = new CancellingClass();
                    can.pnr = pnr;
                    PassengerTicket pt = ptdo.get(pnr);
                    for (Integer sno : cancel) {
                        Passenger p = pdo.get(pnr, sno);
                        refund += p.fare;
                        if (p.statusId == 4) {
                            out.println("Cancelled ticket cannot be cancelled once again..!");
                            out.println("<br>Exiting cancellation..!");
                            break;
                        } else if (p.statusId == 1) {
                            can.cancelCNF(p);
                        } else if (p.statusId == 2) {
                            can.cancelRAC(p);
                        } else if (p.statusId == 3) {
                            can.cancelWL(p);
                        }
                        pt.Adult--;
                    }
                    ptdo.update(pt);
                    can.finalise();
                    out.println("You will get a refund of: " + (refund - 30));
                    out.println("<div style=\"backround-color:green\" >Cancelled successfully </div>");
                    util.CommitUtil.commit();
                }
            }
        } catch (SQLException e) {
            try {
                util.CommitUtil.rollBack();
            } catch (SQLException ex) {
                Logger.getLogger(Cancel.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("Cancellation Failed");
            System.out.println(e);
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
