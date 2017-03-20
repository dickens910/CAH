<%-- 
    Document   : index
    Created on : Mar 18, 2017, 11:46:29 AM
    Author     : gaby
--%>

<%@page import="java.io.InputStream"%>
<%@page import="controleur.MainClient"%>
<%@page import="modele.*"%>
<%@page import="vue.*"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" /> 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="${pageContext.request.contextPath}/Resources/script/cardsScript.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resources/css/myStyle.css">
     <% 
        new MainClient("Labo1 ver 2.0 ").setVisible(true);
    %>       
<body>
<div id="wCard"></div>
<div id="bCard"></div>

<button onclick="giveCards()">white card</button>
<button onclick="giveBlackCard()">Black card </button>
</html>
