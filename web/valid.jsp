<%
    if (session.getAttribute("user_id") == null) {
        out.println(0);
    } else {
        out.println(1);
    }
%>