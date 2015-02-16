<%@page import="java.util.Date"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>
<%@page import="Do.*"%>
<%@page import="Domain.*"%>
<%! int x=10;%>
<%
    List<Reservation> resList = null;
    PassengerTicketDO ptdo = new PassengerTicketDO();
    ReservationStatusDO rsdo = new ReservationStatusDO();

    try {
        resList = new ReservationDO().getAll();
        if (resList.isEmpty()) {
            out.println("<br><br><h3><label class=\"label label-info\">No Tickets Now..! </label></h3>");
            out.close();
            return;
        }
    } catch (SQLException ex) {
        System.out.println(ex);
    }
%>

View booked tickets:
<br>
<form id="form3">
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th>Select</th><th>S.No</th> <th>PNR</th><th>Status</th><th>No of passengers</th><th>Date of booking</th><th>View Ticket</th>
            </tr>
        </thead>
        <tbody>
            <%
                int i = 1;
                for (Reservation res : resList) {
                    PassengerTicket pt = ptdo.get(res.pnr);
                    String dis = "";
                    if (res.ReservationStatus == 3) {
                        dis = "disabled";
                    }
                    out.println("<tr><td><input type=\"radio\" name=\"pnr\" value=\"" + res.pnr + "\" " + dis + " > " + "</td><td>" + (i++) + "</td><td>" + res.pnr + "</td><td>" + rsdo.get(res.ReservationStatus).status + "<td>" + pt.Adult + "</td><td>" + res.timestamp + "</td><td><a href=\"javascript:void(0)\" onClick=\"viewTic(" + pt.pnr + ") \" > <img height=\"20\" src=\"css/view.jpg \"> </a></td></tr>");
                }
            %>
        </tbody>
    </table>
</form>
<button onclick="ProcessCancel()">Cancel selected ticket</button>
