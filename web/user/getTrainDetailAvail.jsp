<%@page import="java.sql.SQLException"%>
<%@page import="Do.ClassDistanceFare"%>
<%@page import="Do.StationDistanceDO"%>
<%@page import="Do.ClassDO"%>
<%
    long trainId = Long.parseLong(request.getParameter("trainID"));
    long classId = Long.parseLong(request.getParameter("classID"));
    String className = new ClassDO().get(classId).name;

    long from = 1, to = 2;
    double fare, distance;
    String fileN = "availability.jsp?classID=" + classId;
    try {
        distance = new StationDistanceDO().getDistance(from, to);
        double farePerKM = new ClassDistanceFare().getFare(classId);
        fare = util.FareCalculater.CalculateFare(distance, farePerKM);
    } catch (SQLException ex) {
        out.println("Error in fetching the fare..!");
        System.err.println(ex);
        return;
    }
%>
    
    <div class="row" >
        <div class="col-lg-3"/>
        <div class="col-lg-6">
            <h4 style="display: inline">Selected Class Availability:</h4>
            <div style="border: #269abc">
                <button class="btn btn-sm btn-info" style="margin-bottom: 1%" onclick="RefreshClass()">Refresh this</button>
                <button class="btn btn-sm pull-right btn-danger" style="display: inline;margin-bottom: 1%" onclick="hideClass()">Hide this</button>

                <table class="table table-bordered">
                    <tr>
                        <td>Train number:</td>
                        <td><%=trainId%></td>
                    </tr>
                    <tr>
                        <td>Selected class</td>
                        <td><%=className%></td>
                    </tr>
                    <tr>
                        <td>Basic fare(&#8377)</td>
                        <td><%=(int) fare%></td>
                    </tr>
                    <tr>
                        <td>Availability</td>
                        <jsp:include page="<%= fileN%>" flush="true"/>
                    </tr>
                </table>
            </div>
        </div>

    </div></center>
<script type="text/javascript">
    function hideClass() {
        j("#classAvailable").html("");
    }
    function RefreshClass()
    {

    }
</script>