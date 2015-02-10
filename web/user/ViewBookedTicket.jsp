<%@page import="java.util.List"%>
<%@page import="Do.*"%>
<%@page import="Domain.*" %>
<%
    PassengerTicketDO ptdo = new PassengerTicketDO();
    PassengerDO pdo = new PassengerDO();
    long pnr = Long.parseLong(request.getParameter("pnr"));

    PassengerTicket pt = ptdo.get(pnr);
    List<Passenger> pass = pdo.getAll(pnr);

    UnderPassengerDO cdo = new UnderPassengerDO();
    List<UnderPassenger> child = cdo.getAll(pnr);

    StatusDO sd = new StatusDO();

    long trainClassId = 1;
    TrainClassStatusDO tcsdo = new TrainClassStatusDO();
    TrainClassStatus tcs = tcsdo.get(trainClassId);
    boolean afterChart = tcs.chart;
%>

Ticket details:
<table class="table table-bordered">
    <thead>
        <tr>
            <th>PNR</th><th>Total adult</th><th>Total children</th><th>Basic Fare(&#8377)</th><th>Reservation Charge(&#8377)</th><th>Service Charge(&#8377)</th><th>Total Fare(&#8377)</th>
        </tr>
    </thead>
    <tr>
        <td><%=pnr%></td>
        <td><%= pt.Adult%></td>
        <td><%= pt.Children%></td>
        <td><%= pt.basicfare%></td>
        <td><%= pt.reservationFare%></td>
        <td><%= pt.serviceCharge%></td>
        <td><%= pt.totalFare%></td>
    </tr>
</table>
Passengers details:
<table  class="table table-bordered">
    <thead>
        <tr>
            <th>Name</th><th>Age</th><th>Status</th><th>Seat No</th>
        </tr>
    </thead>
    <% for (Passenger p : pass) {
            out.println("<tr><td>" + p.name + "</td><td>" + p.age + "</td>");
            int status = p.statusId;
            String clr = "";
            String val = "";

            if (status == 1) {
                clr = "lightgreen";
            } else if (status == 2) {
                clr = "yellow";
            } else if (status == 3) {
                clr = "#FFC2B3";
            } else {
                clr = "gray";
            }
            if (afterChart) {
                val = sd.get(p.initialStatusId).name + "" + p.initialSeatNo + " / ";
                if (p.statusId != 4) {
                    val += sd.get(p.statusId).name + "" + p.seat_no;
                } else {
                    val += "cancelled";
                }
            } else {
                if (p.initialStatusId == 1) {
                    val = "CNF" + " / ";
                } else {
                    val = sd.get(p.initialStatusId).name + "" + p.initialSeatNo + " / ";
                }
                if (status == 1 && p.initialStatusId == 1) {
                    val += p.coach + "-" + p.initialSeatNo;
                } else if (status == 1) {
                    val += "CNF";
                } else if (status == 2) {
                    val += "RAC" + p.seat_no;
                } else if (status == 3) {
                    val += "WL" + p.seat_no;
                } else if (status == 4) {
                    val += "Cancelled";
                }
            }
            out.println("<td style=\"background-color:" + clr + "\">" + sd.get(p.statusId).name + "</td>");
            out.println("<td>" + val + "</td></tr>");
        }
    %>
</table>
<%
    if (child.isEmpty()) {
        out.println("<!--");
    }
%>
Children:
<table class="table table-bordered">
    <thead>
        <tr>
            <th>Name</th><th>Age</th>
        </tr>
    </thead>

    <% for (UnderPassenger p : child) {
            out.println("<tr><td>" + p.name + "</td><td>" + p.age + "</td></tr>");
        }

    %>
</table>
<%             if (child.isEmpty()) {
        out.println("-->");
    }
%>
