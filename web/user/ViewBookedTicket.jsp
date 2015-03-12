<%@page import="java.util.List"%>
<%@page import="Do.*"%>
<%@page import="Domain.*" %>
<html>
    <head>
        <link href="css/bootstrap.min.css" rel="stylesheet"/>
        <style>
            .tab { margin: 1em; border-collapse: collapse; }
            .tabtd, .tabth { padding: .3em; border: 1px #ccc solid; }
            .tabh{ background: #fc9; } 
        </style>
    </head>
    <body>
        <%
            PassengerTicketDO ptdo = new PassengerTicketDO();
            PassengerDO pdo = new PassengerDO();
            long pnr = Long.parseLong(request.getParameter("pnr"));
            session.setAttribute("pnr", pnr);

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
        <button onclick="PrintT()">Print Ticket</button><br>
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
                        out.println("<td class=\"tabth\" style=\"background-color:" + clr + "\">" + sd.get(p.statusId).name + "</td>");
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
        <a href="user/downloadTicket.jsp"><button>Save to PDF</button> </a>
        <script>
            function PrintT()
            {
                elem = j("#ticket");
                Popup(j(elem).html());
            }
        </script>
    </body>
</html>