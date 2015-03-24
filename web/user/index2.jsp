<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="Do.*"%>
<%@page import="Domain.*"%>
<%
    TrainStatusDO tsdo = new Do.TrainStatusDO();
    TrainStatus ts = tsdo.get(1);
    TrainDO trainDO = new TrainDO();
    long statusId = ts.statusId;
    long from, to, classId = 1;
    double fare, distance;
    String fromStr = request.getParameter("from");
    String toStr = request.getParameter("to");
    StationDO stationDO = new StationDO();
    Station f = stationDO.getByName(fromStr.trim());
    Station t = stationDO.getByName(toStr.trim());
    from = f.id;
    to = t.id;
    List<Long> lList2 = new TrainStationDO().getTrainFromTo(from, to);
    try {
        distance = new StationDistanceDO().getDistance(from, to);
        if (lList2.isEmpty()) {
            out.println("<label>No trains available on this route now</label>");
            return;
        }
        session.setAttribute("from", from);
        session.setAttribute("to", to);
    } catch (SQLException ex) {
        out.println("Error in fetching the distance..!");
        System.err.println(ex);
        return;
    }
    TrainClassDO trainClassDO = new TrainClassDO();
    ClassDO cdo = new ClassDO();
    StationDO sdo = new StationDO();
    session.setAttribute("journey", 1);
%>

<h4>Available trains</h4>
<table class="table table-bordered table-hover">
    <thead>
        <tr><th>Train Number</th><th>Name</th><th>From</th><th>To</th><th>Distance(KM)</th><th>Available classes</th></tr>
    </thead>
    <tbody>
        <%
            List<Long> lList = new TrainStationDO().getTrainFromTo(from, to);
            System.out.println("Total trains:" + lList.size());
            System.out.println("From,to:" + from + "," + to);
            for (Long trainID : lList) {
                Train train = trainDO.get(trainID);
        %>
        <tr>
            <td><%=train.trainId%></td><td><%= train.name%></td><td><%=sdo.get(from).name%></td><td><%=sdo.get(to).name%></td><td><%=(int) distance%> </td>
            <td>
                <%
                    for (TrainClass tc : trainClassDO.getAll(trainID)) {
                        String clasCode = cdo.get(tc.classID).code;
                        out.println("<button class=\"btn btn-default\" onclick=\"getTrainClass(" + trainID + "," + tc.classID + ")\">" + clasCode + "</button>");
                    }
                %>
            </td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<div id="classAvailable"></div>
<br><br>
<div>
    Click<a href="javascript:void(0)" onclick="price()" > here </a>to know the pricing policy.
</div>