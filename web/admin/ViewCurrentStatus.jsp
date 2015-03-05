<%@page import="Do.ClassDO"%>
<%@page import="Do.TrainClassDO"%>
<%@page import="Domain.TrainClass"%>
<%
    TrainClassDO trainClassDO = new TrainClassDO();
    ClassDO cdo = new ClassDO();
    long trainID = 1;
%>
<div>
    <h4>View current ticket details:</h4>
    <p>please select a class to view the current ticket detail:<p>
        <% for (TrainClass tc : trainClassDO.getAll(trainID)) {
                String clasCode = cdo.get(tc.classID).code;
                out.println("<button class=\"btn btn-default\" onclick=\"showCurrentClass(" + trainID + "," + tc.classID + ")\">" + clasCode + "</button>");
            }
        %>
</div>
<script type="text/javascript">
    function showCurrentClass(trainID, classID)
    {
        j.get("admin/ViewFinalSeat.jsp?mode=view&classID=" + classID, function (data) {
            j("#main").html(data);
        });
    }
</script>
