/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import Do.ProfileDO;
import Do.UserDO;
import Domain.Profile;
import Domain.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vishnu-pt517
 */
public class Register extends HttpServlet {

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
            PrintWriter out = response.getWriter();
            String name = request.getParameter("name");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");

            UserDO userDO = new UserDO();
            ProfileDO profileDO = new ProfileDO();
            boolean isAlreadyExist = userDO.isExist(username);
            if (isAlreadyExist) {
                out.println(0);
            } else {
                User u = new User();
                u.username = username;
                u.password = password;
                u.role = 2;
                userDO.add(u);

                u = userDO.getUser(username, password);
                Profile p = new Profile();
                p.email = email;
                p.name = name;
                p.userID = u.id;
                profileDO.addProfile(p);
                util.CommitUtil.commit();
                String message = "Hello " + p.name + "\n\n";
                message += "\tYour account is created for the online ticket reservation system.\n\n";
                message += "\t\tUsername:" + username;
                message += "\n\t\tPassword :" + password;
                message += "\n\tYou can login to the system at http://vishnu-pt517:8080";
                message += "\n\nThank you for using the system.";
                new util.MailUtil().SendMail(email, message, "Your account created.");
                out.println("<br><br>Registered successfully.Login <a href=\"/\"> here </a> ");
            }

        } catch (SQLException SQLEx) {
            System.out.println(SQLEx.getMessage());
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
