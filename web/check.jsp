<%
    if (session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp?login=noLogin");
    }
%>