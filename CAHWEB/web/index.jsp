<%-- 
    Document   : index
    Created on : 2012-10-23, 20:17:01
    Author     : moumene
--%>

<%@page import="java.util.Random"%>
<%@page import="com.atoudeft.web.modele.cartesNoire"%>
<%@page import="com.atoudeft.web.modele.cartesBlanches"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.atoudeft.web.modele.Client"%>
<%@page import="java.util.List"%>
<%@page import="com.atoudeft.web.implementation.ClientDao"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script src="websocket.js"></script>
<link rel="stylesheet" type="text/css" href="css.css"> 

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
            List<cartesBlanches> listewCards =  (List<cartesBlanches>)getServletContext().getAttribute("Wcards"); 
            List<cartesNoire> listeBCards =  (List<cartesNoire>)getServletContext().getAttribute("Bcards"); 
            
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
                    Client client = listeClients.get(i);
                    request.setAttribute("client", client);  

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
        
           
           for (int i = 0; i < 3; i++) {
                 Random generator = new Random();
                int rnd = generator.nextInt(listewCards.size()) + 1;
                    out.println("<div class='play'><div class='whiteCard' ><div class='inner'>"+ listewCards.get(rnd).getTexte() +"</div></div></div>" );                
                    listewCards.remove(rnd);
                    
           }
           if (listeBCards.size() == 114 || "0" == (String)getServletContext().getAttribute("condition")){
                   Random generator = new Random(); 
                   int rnd = generator.nextInt(listeBCards.size()) + 1;
                    getServletContext().setAttribute("blackCardPicked",listewCards.get(rnd).getTexte());
                       getServletContext().setAttribute("condition",1);
                       out.println("<div class='play'><div  class='blackCard'><div class='inner'>"+ listeBCards.get(rnd).getTexte() +"</div></div></div>" );                
                       listeBCards.remove(rnd);
        }
        else {
            out.println("<div class='play'><div  class='blackCard' ><div class='inner'>"+ getServletContext().getAttribute("blackCardPicked") +"</div></div></div>" ); 
            String a = (String) request.getParameter("PickCardValue");
            getServletContext().setAttribute("condition",a);
        }

        %>
            
             <div id="wrapper">
            <div id="butonsDiv">
                <input type="button" class="button" id="ToggleGame" value="Request Game" onclick='requestGame()'>
                <input type="button"  id="pButton" class="button play" value="Pick new Black Card" onclick='PickCard()'> 
            </div>    
            <div id="addDevice">
                <div class="button"> <a  href="#" OnClick="showForm()">Add a client</a> </div>
                 <form id="addClientForm">
                     <input type="button"  id="pButton" class="button play" value="Pick new Black Card" onclick='PickCard()'> 
                    <input type="hidden" id="PickCard" name="PickCardValue">  
                    <h3>Add a new client</h3>
                    <span>Name: <input type="text" value="<%=nom%>" name="client_name" id="client_name"></span>
                    <span>Description:<br />
                        <textarea name="description" id="client_description" rows="2" cols="50"></textarea>
                    </span>

                    <input type="button" class="button" value="Add" onclick='formSubmit()'>
                    <input type="reset" class="button" value="Cancel" onclick='hideForm()'>
                 </form>     
            </div>
            <br />
            <h3>Currently connected clients:</h3>
            <div id="content">
            </div>
            <style>
                #message {
                    position : fixed; bottom:0; right:0;  
                }
                textarea {display:block; border-radius: 10px; overflow:auto; height:150px; width:200px;}
            </style>
            <div id="message">
                <form id="messageForm" >
                    <textarea  > </textarea>
                    <input type="text" name="client_msg" id="client_msg" >
                    <input type="button" class="button" value="Envoyer" onclick='sendMsg()'>
                </form>
            </div>    
        </div>
                    