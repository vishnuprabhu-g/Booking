<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%
    String t_number = (String) session.getAttribute("trainNumber");
    String t_name = (String) session.getAttribute("trainName");
    String from = request.getParameter("from");
    String to = request.getParameter("to");
    String endKM = request.getParameter("endKM");
    List<String> stations = new ArrayList<String>();
    List<Integer> Kms = new ArrayList<Integer>();
    try {
        for (int i = 1; i < 100; i++) {
            String Station = request.getParameter("station" + i);
            if (Station == null) {
                break;
            }
            Integer km = Integer.parseInt(request.getParameter("km" + i));
            stations.add(Station);
            Kms.add(km);
        }
        session.setAttribute("stations", stations);
        session.setAttribute("kms", Kms);
        session.setAttribute("from", from);
        session.setAttribute("to", to);
        session.setAttribute("endKM", endKM);
    } catch (Exception e) {
        out.println("Invalid data.Please close this and try to readd with correct data");
    }
%>
<table class="table-bordered">
    <tbody>
        <tr>
            <td>Train Number</td><td><%= t_number%></td>
        </tr>
        <tr>
            <td>Train Name</td><td><%= t_name%></td>
        </tr>
        <tr>
            <td>From</td><td><%= from%></td>
        </tr>
        <tr>
            <td>To</td><td><%= to%></td>
        </tr>
        <tr>
            <td>Total Distance</td><td><%= endKM%></td>
        </tr>
</table>
<hr>
Stations:
<table class="table-bordered">
    <%
        for (int i = 0; i < stations.size(); i++) {
            int km = Kms.get(i);
    %>
    <tr><td style="width: 130px">Station</td><td style="width: 40px"><%=km%></td></tr>
    <%
        }
    %>
</tbody>
</table>
<hr>
<button>Add Train</button>