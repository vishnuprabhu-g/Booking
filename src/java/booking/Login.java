/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import Do.UserDO;
import Domain.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vishnu-pt517
 */
public class Login extends HttpServlet {

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
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            UserDO userDO = new UserDO();
            User u = userDO.getUser(username, password);
            if (u == null) {
                response.sendRedirect("login.jsp?login=error");
            } else {

                HttpSession ses = request.getSession();
                java.util.Locale locale = Locale.ENGLISH;
                ses.setAttribute("java.util.Locale", locale);
                if (ses.getAttribute("user_id") != null) {
                    int role = (Integer) ses.getAttribute("role_id");
                    if (role == 1) {
                        response.sendRedirect("admin.jsp?Message=LogOutFirst");
                    } else {
                        response.sendRedirect("user.jsp?Message=LogOutFirst");
                    }
                    return;
                }

                if (u.role == 1) {
                    ses.setAttribute("role_id", 1);
                    ses.setAttribute("user_id", u.id);
                    ses.setAttribute("username", username);
                    response.sendRedirect("admin.jsp");
                } else {
                    ses.setAttribute("role_id", 2);
                    ses.setAttribute("user_id", u.id);
                    ses.setAttribute("username", username);
                    response.sendRedirect("user.jsp");
                }

            }
        } catch (SQLException e) {
            System.out.println(e);
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
