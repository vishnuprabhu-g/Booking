<%@page import="java.util.List"%>
<%@page import="Do.*"%>
<%@page import="Domain.*" %>
<%
    long trainClassId=1;
    PassengerTicketDO ptdo = new PassengerTicketDO();
    PassengerDO pdo = new PassengerDO();
    long pnr = Long.parseLong(request.getParameter("pnr"));
    
    TrainClassStatusDO tcsdo=new TrainClassStatusDO();
    TrainClassStatus tcs=tcsdo.get(trainClassId);
    if(tcs.chart)
    {
        out.println("Chart prepared..No cancellaction allowed");
        return;
    }

    PassengerTicket pt = ptdo.get(pnr);
    List<Passenger> pass = pdo.getAll(pnr);

    UnderPassengerDO cdo = new UnderPassengerDO();
    List<UnderPassenger> child = cdo.getAll(pnr);

    StatusDO sd = new StatusDO();
    session.setAttribute("pnr", pnr);
%>

<center>
    Ticket details:
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>PNR</th><th>Total adult</th><th>Total children</th><th>Total Fare</th>
            </tr>
        </thead>
        <tr>
            <td><%=pnr%></td>
            <td><%= pt.Adult%></td>
            <td><%= pt.Children%></td>
            <td><%= pt.totalFare%></td>
        </tr>
    </table>
    <form action="Cancel" id="form2">
        Passengers details:
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Select</th><th>Name</th><th>Age</th><th>Status</th><th>Seat No</th>
                </tr>
            </thead>

            <% int i = 1;
                for (Passenger p : pass) {
                    String dis = "";
                    if (p.statusId == 4) {
                        dis = "disabled";
                    }
                    out.println("<tr><td> <input class=\"cb\" type=\"checkbox\" name=\"sno" + (i++) + "\" value=\"" + p.sno + " \" " + dis + " >");
                    out.println("</td><td>" + p.name + "</td><td>" + p.age + "</td>");
                    int status = p.statusId;
                    String clr = "";
                    if (status == 1) {
                        clr = "lightgreen";
                    } else if (status == 2) {
                        clr = "yellow";
                    } else if (status == 3) {
                        clr = "#FFC2B3";
                    } else {
                        clr = "gray";
                    }

                    out.println("<td style=\"background-color:" + clr + " \" >" + sd.get(p.statusId).name + "</td>");
                    String val = "";
                    if (status == 1) {
                        val = ""+p.coach +"-"+ p.seat_no;
                    }
                    if (status == 2) {
                        val = "RAC" + p.seat_no;
                    } else if (status == 3) {
                        val = "WL" + p.seat_no;
                    } else if (status == 4) {
                        val = "Cancelled";
                    }
                    out.println("<td>" + val + "</td></tr>");
                }
            %>
        </table>
        <%
            if (child.isEmpty()) {
                out.println("<!--");
            }
        %>
        Children details:
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Select</th><th>Name</th><th>Age</th>
                </tr>
            </thead>
            <tbody>
                <%
                    i = 1;
                    for (UnderPassenger up : child) {
                        out.println("<tr><td> <input type=\"checkbox\" name=\"child" + (i++) + "\" value=\"" + up.name + "-" + up.age + "\" " + " >");
                        out.println("</td><td>" + up.name + "</td><td>" + up.age + "</td></tr>");
                    }
                %>
            </tbody>
        </table>
        <%
            if (child.isEmpty()) {
                out.println("-->");
            }
        %>
    </form>
    <button href="javascript:void(0)" onclick="selectAll()" >Select all</button> 
    <button href="javascript:void(0)" onclick="cancelLast()" >Cancel selected tickets</button>   
</center>