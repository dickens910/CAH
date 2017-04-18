/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atoudeft.web.mvc;

import com.atoudeft.web.implementation.ClientDao;
import com.atoudeft.web.modele.Client;
import com.atoudeft.web.controleur.*;
import java.io.IOException;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Moumene
 */
public class Login extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            
            throws ServletException, IOException {

        String  u = request.getParameter("username"),
                p = request.getParameter("password");
        String nextView = this.valider(request);
        if (!"".equals(nextView)) {
            RequestDispatcher r = this.getServletContext().getRequestDispatcher(nextView);
            r.forward(request, response); 
            return;
        }
        try {
            //Chargement du pilote :
            Class.forName(this.getServletContext().getInitParameter("piloteJdbc"));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //Obtention de la connexion :
       
            Connection cnx = DriverManager.getConnection(this.getServletContext().getInitParameter("urlBd"));
                           request.setAttribute("Driver", DriverManager.getConnection(this.getServletContext().getInitParameter("urlBd")));
            Statement stm = cnx.createStatement();
                               
            ResultSet res = stm.executeQuery("SELECT * FROM usager WHERE usager = '"+u+"'");
                             
            if (res.next())
            {

                if (res.getString("mdp").equals(p))
                {
                    //connexion OK
                     ClientDao cDao = new ClientDao(cnx) ;
                    List<Client> listeJoueurs = new ArrayList<>();
                    listeJoueurs =  cDao.findAll();
                    request.setAttribute("liste", listeJoueurs);   
                    HttpSession session = request.getSession(true);
                    session.setAttribute("connecte", u);
                    RequestDispatcher r = this.getServletContext().getRequestDispatcher("/index.jsp");
                    r.forward(request, response);
                    return;
                }
                else
                {
                    //Mot de passe incorrect
                    request.setAttribute("message", "Mot de passe incorrect.");
                    RequestDispatcher r = this.getServletContext().getRequestDispatcher("/login.jsp");
                    r.forward(request, response);
                    return;
                }
            }
            else
            {
                //Utilisateur inexistant
                request.setAttribute("message", "Utilisateur "+u+" inexistant.");
                RequestDispatcher r = this.getServletContext().getRequestDispatcher("/login.jsp");
                r.forward(request, response);
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String valider(HttpServletRequest request) {
        String s = "";
        String  u = request.getParameter("username"),
                p = request.getParameter("password");
        if (u==null || u.trim().equalsIgnoreCase("")) {
            request.setAttribute("message", "Username obligatoire");
            s = "/login.jsp";
        }
        if (p.trim().equalsIgnoreCase(""))
        {
            //Utilisateur non saisi :
            request.setAttribute("message", "Mot de passe obligatoire");
            s = "/login.jsp";
        }        
        return s;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
