<%@page import="Domain.User"%>
<%@page import="java.util.List"%>
<%@page import="Do.UserDO"%>
<%
    String input = request.getParameter("k");
    UserDO udo = new UserDO();
    List<User> searchResult = udo.search(input);
    if (searchResult.isEmpty()) {
        out.println("No result found.!");
    } else {
%>
<table class="table table-bordered">
    <%
            out.println("<thead><tr><th class=\"col-md-2\">S.No</th><th class=\"col-md-3\">Username</th><th class=\"col-md-4\">Name</th><th class=\"col-md-3\">Actions</th><tr/></thead>");
            int i = 1;
            for (User u : searchResult) {
                out.println("<tr><td>" + i++ + "</td><td>" + u.username + "</td><td>" + u.username + "</td><td><img src=\"img/update profile.png \" height=30 width=30 title=\"Update\" onclick=\"editU(" + u.id + ")\"></img><img style=\"margin-left:3%\" src=\"img/reset password.png \" height=30 width=30 title=\"Reset password\" onclick=\"resetU(" + u.id + ")\"></img><img style=\"margin-left:3%\" src=\"img/remove user.png \" height=30 width=30 title=\"Delete user\" onclick=\"deleteU(" + u.id + ")\"></img></td></tr>");
            }
            out.println("</table>");
        }

    %>