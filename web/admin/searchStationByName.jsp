<%@page import="Domain.Station"%>
<%@page import="java.util.List"%>
<%@page import="Do.StationDO"%>
<%
    String input = request.getParameter("k");
    StationDO udo = new StationDO();
    List<Station> searchResult = udo.search(input);
    if (searchResult.isEmpty()) {
        out.println("No result found.!");
    } else {
%>
<table class="table table-bordered">
    <%
            out.println("<thead><tr><th class=\"col-md-2\">S.No</th><th class=\"col-md-3\">Name</th><tr/></thead>");
            int i = 1;
            for (Station u : searchResult) {
                out.println("<tr><td>" + i++ + "</td><td>" + u.name + "</td></tr>");
            }
            out.println("</table>");
        }

    %>