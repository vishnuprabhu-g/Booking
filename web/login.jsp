<%
    if (session.getAttribute("user_id") != null) {
        Integer role_id = (Integer) session.getAttribute("role_id");
        if (role_id == 1) {
            response.sendRedirect("admin.jsp");
        } else {
            response.sendRedirect("user.jsp");
        }
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to online ticket reservation system</title>
    </head>
    <body>
        <div>
            <form action="Login" method="post">
                <table>
                    <tr>
                        <td>Username</td><td><input type="text" name="username"></td>
                    </tr>
                    <tr>
                        <td>Password</td><td><input type="password" name="password"></td>
                    </tr>
                    <tr>
                        <td></td><td><input type="submit" value="Login"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
