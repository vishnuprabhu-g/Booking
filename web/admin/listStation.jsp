<%@page import="Do.StationDO"%>
<%@page import="Domain.Station"%>
<%@page import="Do.TrainDO"%>
<%@page import="Domain.Train"%>
<%@page import="java.util.List"%>
<%
    List<Station> trainList = new StationDO().getAll();
%>
<table class="table table-bordered">
    <tbody>
        <tr>
            <th>Station ID</th><th>Station Name</th><th>Actions</th>
        </tr>
    </tbody>
    <%
        for (Station t : trainList) {
            out.println("<tr><td>" + t.id + "</td><td>" + t.name + "</td><td><button class=\"btn btn-default\" onclick='popupStation(" + t.id + ")' >Update Station</button></td></tr>");
        }
    %>
</table>