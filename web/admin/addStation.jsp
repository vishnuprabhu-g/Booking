<form id="addStation">
    <table>
        <tr>
            <td>Station Name</td><td><input type="text" name="name" required/></td>
        </tr>
        <tr>
            <td></td><td><input type="submit" value="Add"/></td>
        </tr>
    </table>
</form>
<script>
    j("#addStation").submit(function (event) {
        event.preventDefault();
        var data = j("#addStation").serialize();
        j.get("admin/processAddStation.jsp", data, function (data) {
            alert(data);
            j("#dia2").dialog('close');
            //j("#dia2").html(data);
            //j("#dia2").alert();
        });
    });
</script>