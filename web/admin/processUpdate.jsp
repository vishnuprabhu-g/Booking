<%@page import="Do.ProfileDO"%>
<%@page import="Domain.Profile"%>
<%
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    long userid = Long.parseLong(request.getParameter("user_id"));
    Profile p = new Profile();
    p.email = email;
    p.name = name;
    p.userID =userid;
    new ProfileDO().update(p);
    util.CommitUtil.commit();
%>