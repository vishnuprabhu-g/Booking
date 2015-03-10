<%
    String from = request.getParameter("from");
    String to = request.getParameter("to");
    if (from == null || to == null || from.equals(to)) {
        out.println(0);
    } else {
        out.println(1);
    }
%>
