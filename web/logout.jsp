<%
    session.invalidate();
    response.sendRedirect("login.jsp?login=logout");
%>
