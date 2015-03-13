<%
    System.out.println(request.getParameter("trainNumber"));
    session.setAttribute("trainNumber", request.getParameter("trainNumber"));
    session.setAttribute("trainName", request.getParameter("trainName"));
%>
<h4>Please enter station details in order:</h4>
<form id="trainForm2">
    <table id="trainTab2">
        <tr>
            <td><label>Start</label></td>
            <td><input name="from"/></td>
            <td></td><td></td>
        </tr>
        <tr>
            <td><label>End</label></td>
            <td><input name="to"/>
            <td><label>KM</label></td><td><input name="endKM" maxlength="4"/></td>
        </tr>
    </table>
</form>
<a href="javascript:addFormField()" class="pull-right"><button id="addButton">+</button></a><br>
<hr>
<button onclick="train3()">Next</button>
<script type="text/javascript">
    var i = 1;
    function addFormField()
    {
        var ele = i++;
        j("<tr><td><label>Station" + ele + "</label></td><td><input type='text' name='station" + ele + "' /></td><td><label>KM</label></td><td><input type='text' name='km" + ele + "' maxlength='4' /></td></tr> ")
                .appendTo("#trainTab2");
    }
</script>