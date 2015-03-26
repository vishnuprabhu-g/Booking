<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>
<%@page import="Do.ClassDO"%>
<%@page import="Domain.Train"%>
<%@page import="Do.TrainDO"%>
<%@page import="Do.TrainClassDO"%>
<%
    List<Domain.Class> classes = null;
    ClassDO cdo = null;
    Train t = null;
    TrainClassDO tcdo = null;
    try {
        tcdo = new TrainClassDO();
        TrainDO tdo = new TrainDO();
        String trainIDStr = request.getParameter("trainID");
        long trainID = Long.parseLong(trainIDStr);
        session.setAttribute("trainID", trainID);
        t = tdo.get(trainID);
        cdo = new ClassDO();
        classes = cdo.getAll();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
%>
<table class="table table-bordered">
    <tr>
        <td>Train Number</td><td><%=t.trainId%></td>
        <td>Train Name</td><td><%=t.name%></td>
    </tr>
</table>
<form id="updateClass">
    <table class="table table-bordered">
        <thead>
        <th>Class Name</th><th>Current Compartments</th>
        </thead>
        <tbody>
            <% for (Domain.Class cls : classes) {
                    int value = tcdo.getCountOfCompartment(t.trainId, cls.id);
            %>
            <tr><td><%=cls.name%></td><td><input type="number" name="<%=cls.id%>" value="<%=value%>" required /></td></tr> 
                    <%
                        }%>
        </tbody>
    </table>
    <center><button>Update</button></center>
</form>
<script>
    j('#updateClass').submit(function (event) {
        event.preventDefault();
        j.post("admin/processUpdateClass.jsp", j('#updateClass').serialize(), function (data) {
            j('#class').html(data);
        });
    });
</script>
