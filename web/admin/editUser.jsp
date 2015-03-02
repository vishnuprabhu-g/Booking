<%@page import="Do.UserProfileDO"%>
<%@page import="Domain.UserProfile"%>
<%
    String userID = request.getParameter("userID");
    long userId = Long.parseLong(userID);

    UserProfileDO updo = new UserProfileDO();
    UserProfile up = updo.get(userId);

%>
<form id="editUser">
    <table>
        <tr>
            <td>Username:</td><td><input disabled value="<%=up.username%>"/></td>
        </tr>
        <tr>
            <td>Name:</td><td><input name="name" value="<%=up.name%>" required/></td>
        </tr>
        <tr>
            <td>Email:</td><td><input value="<%=up.email%>" required>
                <input style="display: none" name="userId" value="<%= up.userID%>">
            </td>

        </tr>
    </table>
</form>
<button onClick="processUpdateUser">Update</button>