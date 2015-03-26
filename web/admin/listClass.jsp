<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>
<%@page import="Do.ClassDO"%>
<%
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
            <th>S.No</th><th>Name</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (Domain.Class cls : classes) {
        %>
        <tr><td><%=cls.id%></td><td><%=cls.name%></td></tr>
        <%
            }
        %>
    </tbody>

</table>