<%-- 
    Document   : SignUp
    Created on : 2017-04-11, 19:43:16
    Author     : usager
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="login.do" method="post">  
                Name: <input type="text" name="usager"/><br/><br/>  
                Password: <input type="password" name="mdp"/><br/><br/>  
                Courriel: <input type="text" name="courriel"/><br/><br/>  
                <input type="hidden" name="action" value="inscription" /><br />
            <input type="submit" value=" Inscription " />
        </form>  
    </body>
</html>
