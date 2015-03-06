<%@page import="Do.UserDO"%>
<body>
    <div style="margin: 50px 200px;background-color: #ffffcc;height: 200px">
        <%
            try
            {
            String pass = request.getParameter("password");
            String uid = (String) session.getAttribute("uid");
            Long userID = Long.parseLong(uid);
            new UserDO().updatePassByUid(userID, pass);
            out.println("Password updated..");
            out.println("<br>Login <a href=\"login.jsp\">here</a>");
            session.invalidate();
            }
            catch(Exception e)
            {
                out.println("Cannot process your request now.!");
            }
        %>
        <br><a href="login.jsp">Home</a>
    </div>    
</body>