<%@page import="Do.TrainDO"%>
<%@page import="Domain.Train"%>
<%@page import="java.util.List"%>
<%
    List<Train> trainList = new TrainDO().getAll();
%>
<table class="table table-bordered">
    <tbody>
        <tr>
            <th>Train ID</th><th>Train Name</th><th>Actions</th>
        </tr>
    </tbody>
    <%
        for (Train t : trainList) {
            out.println("<tr><td>" + t.trainId + "</td><td>" + t.name + "</td><td><button class=\"btn\" onclick=\"popupTrain(" + t.trainId + ")\">Update train</button></td></tr>");
        }
    %>
</table>
<div id="trainPopup">
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                </div>
                <div class="modal-body">
                    ...
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function popupTrain(trainID)
    {
        $("#myModal").model();
    }
</script>