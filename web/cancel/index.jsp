<%@page import="java.util.List"%>
<%@page import="Do.*"%>
<%@page import="Domain.*"%>

<%
    PassengerTicketDO ptdo = new PassengerTicketDO();
    List<PassengerTicket> tickets = ptdo.getAll();
    
    long trainClassId=1;    
    TrainClassStatusDO tcsdo=new TrainClassStatusDO();
    TrainClassStatus tcs=tcsdo.get(trainClassId);
    if(tcs.chart)
    {
        out.println("<br><br><h3><label class=\"label label-danger\">Chart prepared..No cancellaction allowed</label></h3>");
        return;
    }

    if (tickets.isEmpty()) {
        out.println("<br><br><h3><label class=\"label label-info\">No Tickets Now..! </label></h3>");
        out.close();
        return;
    }
%>


<center>
    <div id="message" style="background-color: #a6e1ec; margin:2% 40%"></div>
    <table class="table table-responsive">
        <thead>
            <tr>
                <th>PNR</th><th>Passengers</th><th>View</th><th>Cancel</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (PassengerTicket pt : tickets) {
                    out.println("<tr><td>" + pt.pnr + "</td><td>" + pt.Adult + "</td><td><a href=\"javascript:void(0)\" onClick=\"viewTic(" + pt.pnr + ") \"\" >View ticket</a></td><td><a href=\"javascript:void(0)\" onclick=\"cancel(" + pt.pnr + ") \">Cancel ticket</a> </td></tr>");
                }
            %>
        </tbody>
    </table>
</center>
<script type="text/javascript">
    function get(name) {
        if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search))
            return decodeURIComponent(name[1]);
    }
    var param = get("cancel");
    if (param == 1)
    {
        document.getElementById("message").innerHTML = "Cancelled successfully..!";
    }
    else if (param == 0)
    {
        document.getElementById("message").innerHTML = "Cancelation failed..!";
    }
</script>
