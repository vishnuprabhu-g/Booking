/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booking;

import Do.ResetDO;
import Domain.ResetKeyVal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vishnu-pt517
 */
public class Reset extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            Long key = Long.parseLong(request.getParameter("key"));
            Long uid = Long.parseLong(request.getParameter("uid"));
            ResetDO rdo = new ResetDO();
            ResetKeyVal reset = new ResetKeyVal();
            reset.key = key;
            reset.uid = uid;
            boolean rt = rdo.isValid(reset);
            util.CommitUtil.commit();
            out.println("<body>");
            out.println("<div style=\"margin:50px 200px;background-color:#FFFFCC;height:200px \">");
            if (!rt) {
                out.println("Invalid request..!");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("uid", new Long(uid).toString());
                out.println("<form action=\" setPass.jsp\">");
                out.println("New password:<input typr=\"password\" name=\"password\" />");
                out.println("<br><button>Reset</button>");
                out.println("</form>");
            }
            out.println("</div>");
            out.println("</body>");
        } catch (Exception e) {
            out.println("Cannot process your request now.");
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
