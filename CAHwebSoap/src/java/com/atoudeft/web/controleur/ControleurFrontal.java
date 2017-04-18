/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atoudeft.web.controleur;

import com.atoudeft.web.mvc.*;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author moumene
 */
public class ControleurFrontal extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action !=null)
        {     
            if ("login".equals(action))
            {
                RequestDispatcher r = this.getServletContext().getRequestDispatcher("/signin");  //redirection vers la servlet Login
                r.forward(request, response);     
                return;
            } 
            if ("regarder".equals(action))
            {
                System.out.println("inside regarder CF  ");
                RequestDispatcher r = this.getServletContext().getRequestDispatcher("/Regarder");  //redirection vers la servlet Login
                r.forward(request, response);
                return;
            }
            if ("logout".equals(action))
            {
                RequestDispatcher r = this.getServletContext().getRequestDispatcher("/logout");  //redirection vers la servlet Logout
                r.forward(request, response);                
            }  
            if ("inscription".equals(action))
            {
                RequestDispatcher r = this.getServletContext().getRequestDispatcher("/signup");  //redirection vers la servlet Login
                r.forward(request, response);     
                return;
            }   
            return;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */

    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
