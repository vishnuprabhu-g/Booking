<%@page import="java.sql.SQLException"%>
<%@page import="Do.*"%>
<%@page import="Domain.*"%>
<%
    TrainStatusDO tsdo = new Do.TrainStatusDO();
    TrainStatus ts = tsdo.get(1);
    long statusId = ts.statusId;
    long from = 1, to = 2, classId = 1;
    double fare, distance;

    try {
        distance = new StationDistanceDO().getDistance(from, to);
        double farePerKM = new ClassDistanceFare().getFare(classId);
        fare = util.FareCalculater.CalculateFare(distance, farePerKM);
    } catch (SQLException ex) {
        out.println("Error in fetching the fare..!");
        System.err.println(ex);
        return;
    }

    StationDO sdo = new StationDO();
    TrainClassStatusDO tcsdo = new TrainClassStatusDO();
    TrainClassStatus classStatus = tcsdo.get(statusId);
    TrainClassRacStatusDO racdo = new TrainClassRacStatusDO();
    int rac = racdo.getCount(1L);

    long classStatusId = classStatus.trianClassStatusId;
    TrainClassSeatStatusDO tcssdo = new TrainClassSeatStatusDO();
    int lower = tcssdo.getCount(classStatusId, 1);
    int middle = tcssdo.getCount(classStatusId, 2);
    int upper = tcssdo.getCount(classStatusId, 3);
    int side = tcssdo.getCount(classStatusId, 4);
    //tcssdo.addSleeper(classStatusId);
    session.setAttribute("journey", 1);

    String detailAvl = "";
    String message = "";

    int available = classStatus.available;
    int maxRac = classStatus.maxRac;
    int maxWaiting = classStatus.maxWaiting;
    int waiting = classStatus.waiting;
    int initial_waiting = classStatus.initialWaiting;
    int racA = classStatus.rac;
    int availbleA = lower + middle + upper + side;

    if (classStatus.chart) {
        message = "Chart prepared..No more bookings..!";
    } else {
        if (available > 0 && racA == 1) {
            message = "Available " + available;
        } else if (available > 0 && racA > 1) {
            if (racA <= 4) {
                message = "RAC" + racA + "/ CNF";
            } else {
                message = "WL" + initial_waiting + "/ CNF";
            }
        } else if (available <= 0 && availbleA > 0) {
            if (initial_waiting == 1) {
                if (racA <= maxRac) {
                    message = "RAC " + racA + "/ CNF";
                } else {
                    message = "WL " + initial_waiting + "/ CNF";
                }
            } else {
                if (racA <= maxRac) {
                    message = "RAC " + racA + "/ CNF";
                } else {
                    message = "WL " + initial_waiting + "/ CNF";
                }
            }
        } else {
            if (rac > 0) {
                if (racA <= maxRac) {
                    message = "RAC " + racA + "/ RAC" + (maxRac - (rac - 1));
                    detailAvl = "Book in RAC(You can travel with RAC ticket.Ticket may be confirmed in future)";
                } else {
                    message = "WL " + initial_waiting + "/ RAC" + (maxRac - (rac - 1));
                    detailAvl = "Book in RAC(You can travel with RAC ticket.Ticket may be confirmed in future)";
                }
            } else {
                message = "WL " + initial_waiting + "/WL " + waiting;
                detailAvl = "Book in WaitingList(You can't travel with WL ticket.Fare will be refunded if ticket not confirmed.)";
            }
        }
    }

    if (availbleA > 0) {
        detailAvl = "Lower Berth:" + lower + " \nMiddle Berth:" + middle + " \nUpper Berth:" + upper + " \nSide Upper:" + side;
    }
%>
<h4>Available trains</h4>
<table class="table table-bordered table-hover">
    <thead>
        <tr><th>Train Number</th><th>Name</th><th>From</th><th>To</th><th>Distance(KM)</th> <th>Fare (&#8377) </th> <th>Current Status</th></tr>
    </thead>
    <tbody>
                                                                                                                                                                               <tr class="success"> <td>1001</td><td>CBE express</td><td><%=sdo.get(from).name%></td><td><%=sdo.get(to).name%></td><td><%=(int) distance%> </td> <td><%= fare%> </td> <td title="<% if (!classStatus.chart) {
                    out.print(detailAvl);
                }%>"><%= message%><br> <% if (classStatus.chart) {
                                                                                                                                                                                           out.print(" <!-- ");
                                                                                                                                                                                       } %><a href="javascript:void(0)" onclick="showBook()"  >Book now</a><% if (classStatus.chart) {
                        out.print(" --> ");
                    }%>
            </td></tr>
    </tbody>
</table>
<br>
<div>
    Click<a href="javascript:void(0)" onclick="price()" > here </a>to know the pricing policy.
</div>