<%@page import="java.sql.SQLException"%>
<%@page import="Do.UserDO"%>
<%@page import="Do.ProfileDO"%>
<%
    long userID = 0;
    try {
        userID = Long.parseLong(request.getParameter("userID"));
    } catch (Exception e) {
        out.println("Exception in processing your request.!");
    }
    if (userID != 0) {
        try {
            UserDO udo = new UserDO();
            ProfileDO pdo = new ProfileDO();
            udo.delete(userID);
            pdo.delete(userID);
            util.CommitUtil.commit();
            out.println("user deleted successfully.");
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
        }
    }
%>