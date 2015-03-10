<%@page import="java.util.Iterator"%>
<%@page import="Do.StationDO"%>
<%@page import="Domain.Station"%>
<%@page import="java.util.List"%>
<%
    List<Station> allStation = new StationDO().getAll();
    Iterator<Station> i = allStation.iterator();
    while (i.hasNext()) {
        Station s = i.next();
        out.print(s.name);
        if (i.hasNext()) {
            out.print(",");
        }
    }
%>