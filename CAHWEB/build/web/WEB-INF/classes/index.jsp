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
            List<Client> listeClients =  (List<Client>)request.getAttribute("liste");  
            int pts ;  
            String nom;
            if(session.getAttribute("regarderName")=="true")
                 {  nom = "visiteur";}
            else {
                 nom = (String)session.getAttribute("connecte");
            }
            for (int i = 0; i < listeClients.size(); i++) {
                 pts = i+1;
                if (nom.equals(listeClients.get(i).getNom()))
                {
                    out.println("<tr><td><b>"+listeClients.get(i).getNom()+"</b></td><td><b>"+listeClients.get(i).getPts()+"</b></td><td><b>"+pts+"</b></td></tr>");
                }
                else
                {    
                    out.println("<tr><td>"+listeClients.get(i).getNom()+"</td><td>"+listeClients.get(i).getPts()+"</td><td>"+pts+"</td></tr>");
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
        <script src="Resources/websocket.js"></script>
          <link rel="stylesheet" type="text/css" href="Resources/css.css">
    </head>
    <body>

        <div id="wrapper">
            <h1>Java Websocket Home</h1>
            <p>Welcome to the Java WebSocket Home. Click the Add a client button to start adding clients.</p>
            <br />
            <div id="addDevice">
                <div class="button"> <a href="#" OnClick="showForm()">Add a client</a> </div>
                <form id="addDeviceForm">
                    <h3>Add a new client</h3>
                    <span>Name: <input type="text" name="client_name" id="client_name"></span>
                    <span>Type: 
                        <select id="client_type">
                            <option name="type" value="Appliance">Appliance</option>
                            <option name="type" value="Electronics">Electronics</option>
                            <option name="type" value="Lights">Lights</option>
                            <option name="type" value="Other">Other</option>
                        </select></span>

                    <span>Description:<br />
                        <textarea name="description" id="client_description" rows="2" cols="50"></textarea>
                    </span>

                    <input type="button" class="button" value="Add" onclick=formSubmit()>
                    <input type="reset" class="button" value="Cancel" onclick=hideForm()>
                </form>
            </div>
            <br />
            <h3>Currently connected clients:</h3>
            <div id="content">
            </div>
        </div>

    </body>
</html>
