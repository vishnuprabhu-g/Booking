<%-- 
    Document   : unAuth
    Created on : 25 Feb, 2015, 3:47:03 PM
    Author     : vishnu-pt517
--%>
<%
    session.invalidate();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error in your request</title>
    </head>
    <body>
        <h1>You don't have permission to view this page..Hence you are logged out.</h1>
    </body>
</html>
