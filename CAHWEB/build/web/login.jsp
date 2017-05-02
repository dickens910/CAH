<%-- 
    Document   : login
    Created on : 2012-10-23, 20:17:01
    Author     : moumene
--%>
<%
    if (session.getAttribute("connecte")!=null)  //déjà connecté
    {
%>
        <jsp:forward page="index.jsp" />
<%
    }
%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Exemple de MVC</title>
        <style type="text/css">
            .errorMessage {color : red;}
        </style>
    </head>
    <body>

<%
        System.out.println(" login " );
        if (request.getAttribute("message")!=null)
        {
            out.println("<span class=\"errorMessage\">"+request.getAttribute("message")+"</span>");
        }
        String  us1 = request.getParameter("username");
        if (us1==null) us1="";
        else us1 = us1.trim();
        %>
        <form action="login.do" method="post">
            Username : <input type="text" name="username" value="<%=us1%>" /><br />
            Password : <input type="password" name="password" />
            <input type="hidden" name="action" value="login"  /><br />
            <input type="submit" value=" Connexion " />
            <a href="SignUp.jsp">S'inscrirre</a>
        </form> 
            <br>
            <br>
        <form action="login.do" method="post">
            <input type="hidden" name="action" value="regarder" /><br />
            <input type="submit" value=" Visionner " />  
        </form> 
         
    </body>
</html>
