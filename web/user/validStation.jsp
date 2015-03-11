<%@page import="Do.StationDO"%>
<%
    String from = request.getParameter("from");
    String to = request.getParameter("to");
    if (from == null || to == null || from.equals(to)) {
        out.println(0);
    } else {
        StationDO sdo = new StationDO();
        if (sdo.getByName(from.trim()) == null || sdo.getByName(to.trim()) == null) {
            out.println(0);
        } else {
            out.println(1);
        }
    }
%>
