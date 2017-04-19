<%-- 
    Document   : index
    Created on : 2012-10-23, 20:17:01
    Author     : moumene
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.atoudeft.web.modele.Client"%>
<%@page import="java.util.List"%>
<%@page import="com.atoudeft.web.implementation.ClientDao"%>
<%
    if (session.getAttribute("regarderName")!=null)  //non connecté
    {
         //rester sur la page pour regarder
    }
     else if (session.getAttribute("connecte")==null)
    {
       
%>
        <jsp:forward page="login.jsp" />
<%
    }
%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<style>
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}
td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}
tr:nth-child(even) {
    background-color: #dddddd;
}
</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Exemple de MVC</title>
        <style type="text/css">
            .errorMessage {color : red;}
            .resultat {font-weight: bold;color:blue;}
        </style>
    </head>
<%        
        if (session.getAttribute("connecte")!=null)
        {
            out.println("<p class=\"resultat\">"+session.getAttribute("connecte")+", vous êtes connectés. "+
                        "<a href=\"logout.do?action=logout  \">   déconnexion</a></p>");
        }        
      else if (session.getAttribute("regarderName")!=null){
          out.println("<p class=\"resultat\"> vous pouvez seulement suivre les parties. "+
                        "<a href=\"logout.do?action=logout  \">   Sortir</a></p>");
      }
%>        
        <h1>MVC</h1>
              <table>
               <tr>
                <th>Nom</th>
                <th>Points</th>
                <th>Position</th>
                </tr>
             
        <%         
            List<Client> listeJoueurs =  (List<Client>)request.getAttribute("liste");  
            int pts ;  
            String nom;
            if(session.getAttribute("regarderName")=="true")
                 {  nom = "visiteur";}
            else {
                 nom = (String)session.getAttribute("connecte");
            }
            for (int i = 0; i < listeJoueurs.size(); i++) {
                 pts = i+1;
                if (nom.equals(listeJoueurs.get(i).getNom()))
                {
                    out.println("<tr><td><b>"+listeJoueurs.get(i).getNom()+"</b></td><td><b>"+listeJoueurs.get(i).getPts()+"</b></td><td><b>"+pts+"</b></td></tr>");
                }
                else
                {    
                    out.println("<tr><td>"+listeJoueurs.get(i).getNom()+"</td><td>"+listeJoueurs.get(i).getPts()+"</td><td>"+pts+"</td></tr>");
                }
            }
        %>
            </table>
        <%
        if (request.getAttribute("resultat")!=null)
        {
            out.println("<span class=\"resultat\">"+request.getAttribute("resultat")+"</span>");
        }
        %>

    </body>
</html>
