/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import Do.ResetDO;
import Do.UserDO;
import Domain.ResetKeyVal;
import Domain.User;
import Domain.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vishnu-pt517
 */
public class ResetPassword extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String username = request.getParameter("username");
            UserDO udo = new UserDO();
            UserProfile up = udo.getUserByUsername(username);
            out.println("<body>");
            out.println("<div style=\"margin:50px 200px;background-color:#FFFFCC;height:200px \">");
            out.println("<div style=\"margin-top=50px\" >");
            if (up != null) {
                String email = up.email;
                String sub = "Password reset";
                long keyV = System.currentTimeMillis();
                long uid = up.userID;
                ResetDO rdo = new ResetDO();
                ResetKeyVal r = new ResetKeyVal();
                r.key = keyV;
                r.uid = uid;
                rdo.add(r);
                util.CommitUtil.commit();

                String key = "" + keyV;
                String link = "http://vishnu-pt517:8080/Booking/Reset?key=" + key + "&uid=" + up.userID;
                String message = "Hello " + up.name + ",\n";
                message += "\tIf you want to reset your password,please use the follwing link.\n";
                message += link;
                message += "\n";
                message += "\n(use the most recent link to reset your password if you have requested more than once)";
                message += "\nThanks for using the system.";

                new util.MailUtil().SendMail(email, message, sub);
                out.println("Successfull..Check your mail!");

            } else {
                out.println("No user found for the username:" + username);
            }
            out.println("<br><a href=\"login.jsp\">Home</a>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");

        } catch (SQLException ex) {
            Logger.getLogger(ResetPassword.class.getName()).log(Level.SEVERE, null, ex);
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
