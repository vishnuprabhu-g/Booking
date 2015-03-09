<%@page import="Do.TrainClassRacStatusDO"%>
<%@page import="Do.TrainClassStatusDO"%>
<%@page import="Domain.TrainClassStatus"%>
<%@page import="Do.TrainClassSeatStatusDO"%>
<%
    //System.out.println("Availability:");

    long statusId = Long.parseLong(request.getParameter("classID"));
    session.setAttribute("classID", Long.parseLong(request.getParameter("classID")));
    TrainClassStatusDO tcsdo = new TrainClassStatusDO();
    TrainClassStatus classStatus = tcsdo.get(statusId);

    long classStatusId = classStatus.trianClassStatusId;
    TrainClassSeatStatusDO tcssdo = new TrainClassSeatStatusDO();
    int lower = tcssdo.getCount(classStatusId, 1);
    int middle = tcssdo.getCount(classStatusId, 2);
    int upper = tcssdo.getCount(classStatusId, 3);
    int side = tcssdo.getCount(classStatusId, 4);
    //tcssdo.addSleeper(classStatusId);
    session.setAttribute("journey", 1);
    TrainClassRacStatusDO racdo = new TrainClassRacStatusDO();
    int rac = racdo.getCount(classStatusId);

    String detailAvl = "";
    String message = "";

    int available = classStatus.available;
    int maxRac = classStatus.maxRac;
    int maxWaiting = classStatus.maxWaiting;
    int waiting = classStatus.waiting;
    int initial_waiting = classStatus.initialWaiting;
    int racA = classStatus.rac;
    int availbleA = lower + middle + upper + side;
    System.out.println(classStatus);
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
                    System.out.println("This condtion:(max:Aval)" + maxRac + ":" + racA);
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
<td title="<% if (!classStatus.chart) {
        out.print(detailAvl);
    }%>">

    <%= message%><br> 
    <% if (classStatus.chart) {
            out.print(" <!-- ");
        }%><a href="javascript:void(0)" onclick="showBook(<%=statusId%>)"  >Book now</a><% if (classStatus.chart) {
                out.print(" --> ");
            }%>
    <% //System.out.println("detailMsg=" + detailAvl);
    %>
</td>