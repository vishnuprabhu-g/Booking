<%@page import="Domain.Passenger"%>
<%@page import="Domain.TrainClassStatus"%>
<%@page import="Do.TrainClassStatusDO"%>
<%@page import="Do.StatusDO"%>
<%@page import="Domain.UnderPassenger"%>
<%@page import="Do.UnderPassengerDO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="Domain.PassengerTicket"%>
<%@page import="Do.StationDO"%>
<%@page import="Domain.Reservation"%>
<%@page import="Do.ReservationDO"%>
<%@page import="Do.PassengerDO"%>
<%@page import="Do.PassengerTicketDO"%>
<%
    String pnrStr = request.getParameter("pnr");
    if (pnrStr == null || pnrStr.trim().equalsIgnoreCase("")) {
        return;
    }
%>
<%
    PassengerTicketDO ptdo = new PassengerTicketDO();
    PassengerDO pdo = new PassengerDO();
    long pnr = Long.parseLong(pnrStr);
    //session.setAttribute("pnr", pnr);

    ReservationDO rdo = new ReservationDO();
    Reservation r = rdo.get(pnr);
    if (r == null) {
        out.println("Invalid/Flushed PNR");
        return;
    }
    StationDO sdo = new StationDO();

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
<div style="margin-top: 2%">
    <table class="table table-bordered tab"> 
        <tr>
            <td>PNR</td><td><%=pnrStr%></td>
            <td>Date of booking</td><td><%=r.timestamp.getDate() + "-" + (r.timestamp.getMonth() + 1) + "-15"%></td>
        </tr>
        <tr>
            <td>From</td><td><%=sdo.get(pt.fromStationId).name%></td><td>To</td><td><%=sdo.get(pt.toStationId).name%></td>
        </tr>    
    </table>
    Ticket details:
    <div id="ticket">
        <table class="table table-bordered tab">
            <thead class="tabh">
                <tr>
                    <th class="tabth">PNR</th><th class="tabth">Total adult</th><th class="tabth">Total children</th><th class="tabth">Basic Fare(&#8377)</th><th class="tabth">Reservation Charge(&#8377)</th><th class="tabth">Service Charge(&#8377)</th><th class="tabth">Total Fare(&#8377)</th>
                </tr>
            </thead>
            <tr>
                <td class="tabth"><%=pnr%></td>
                <td class="tabth"><%= pt.Adult%></td>
                <td class="tabth"><%= pt.Children%></td>
                <td class="tabth"><%= pt.basicfare%></td>
                <td class="tabth"><%= pt.reservationFare%></td>
                <td class="tabth"><%= pt.serviceCharge%></td>
                <td class="tabth"><%= pt.totalFare%></td>
            </tr>
        </table>
        Passengers details:
        <table  class="table table-bordered tab" >
            <thead class="tabh">
                <tr>
                    <th class="tabth">Name</th><th class="tabth">Age</th><th class="tabth">Status</th><th class="tabth">Seat No</th>
                </tr>
            </thead>
            <% for (Passenger p : pass) {
                    out.println("<tr><td class=\"tabth\">" + p.name + "</td><td class=\"tabth\">" + p.age + "</td>");
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
                    out.println("<td class=\"tabth\" >" + sd.get(p.statusId).name + "</td>");
                    out.println("<td class=\"tabth\">" + val + "</td></tr>");
                }
            %>
        </table>
        <%
            if (child.isEmpty()) {
                out.println("<!--");
            }
        %>
        Children:
        <table class="table table-bordered tab">
            <thead class="tabh">
                <tr>
                    <th class="tabth">Name</th><th class="tabth">Age</th><th class="tabth">Status</th>
                </tr>
            </thead>

            <% for (UnderPassenger p : child) {
                    String bStatus = "";
                    if (p.status_id == 1) {
                        bStatus = "booked";
                    } else {
                        bStatus = "cancelled";
                    }
                    out.println("<tr><td class=\"tabth\">" + p.name + "</td><td class=\"tabth\">" + p.age + "</td><td class=\"tabth\">" + bStatus + "</td></tr>");
                }

            %>
        </table>
        <%             if (child.isEmpty()) {
                out.println("-->");
            }
        %>
    </div>
</div>