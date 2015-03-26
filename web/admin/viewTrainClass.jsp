<%@page import="java.sql.SQLException"%>
<%@page import="Do.ClassDO"%>
<%@page import="Do.TrainClassDO"%>
<%@page import="Do.StationDO"%>
<%@page import="Do.TrainDO"%>
<%@page import="Domain.Train"%>
<%@page import="java.util.List"%>
<%
    List<Train> trainList = new TrainDO().getAll();
    TrainClassDO tcdo = new TrainClassDO();
    List<Domain.Class> classes = null;
    ClassDO cdo = null;
    try {
        cdo = new ClassDO();
        classes = cdo.getAll();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
%>
<table class="table table-bordered">
    <thead>
        <tr>
            <th>Train Number</th><th>Train Name</th>
                <%
                    for (Domain.Class cls : classes) {
                %>
            <th><%= cls.name%></th>
                <% }
                %>

            <th>Update</th>
        </tr>
    </thead>
    <tbody>
        <%            for (Train t : trainList) {

        %>
        <tr>
            <td><%=t.trainId%></td><td><%=t.name%></td>
            <%
                for (Domain.Class cls : classes) {
            %>
            <td><%=tcdo.getCountOfCompartment(t.trainId, cls.id)%></td>
            <% }
            %>
            <td><button class="btn btn-default" onclick="updateClass(<%=t.trainId%>)">Update</button></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
<script>
</script>