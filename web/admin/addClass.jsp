<form id="addClassForm">
    <table class="table">
        <tr>
            <td>Name</td><td><input type="text" name="name" required/></td>
        </tr>
        <tr>
            <td></td><td><button class="btn btn-default">Add Class</button></td>
        </tr>
    </table>
</form>
<script>
    j('#addClassForm').submit(function (event) {
        event.preventDefault();
        url = 'admin/preocessAddClass.jsp';
        j.get(url, j('#addClassForm').serialize(), function (data) {
            alert(data);
            j('#classUpdate').html("");
        });
    });
</script>
