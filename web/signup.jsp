<%-- 
    Document   : signup
    Created on : 27 Feb, 2015, 2:15:40 PM
    Author     : vishnu-pt517
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register to book tickets.</title>
    </head>
    <body>
        <form id="regForm" method="post">
            <table>
                <br>
                Register a new account:
                <tr class="spaceUnder"><td>Name:</td><td><input name="name" required></td></tr>
                <tr class="spaceUnder"><td>Email:</td><td><input name="email" required></td></tr>
                <tr class="spaceUnder"><td>Username:</td><td><input name="username" required></td></tr>
                <tr class="spaceUnder"><td>Password:</td><td><input type="password" name="password" required></td></tr>
            </table>
        </form>
    <center><button onclick="reg()">Register</button></center>
</body>
</html>
