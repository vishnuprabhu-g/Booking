function editU(userID)
{
    var url = "admin/editUser.jsp?userID=" + userID;
    j.get(url, function (data) {
        j("#dia").html(data);
        j("#dia").dialog();
    });
}

function resetU(userId)
{
    var rt = confirm("Do you want to reset password for the user?");
    if (rt === true)
    {

    }
}

function deleteU(userId)
{
    var rt = confirm("Do you want to delete the user?");
    if (rt === true)
    {
        var url = "admin/deleteUser.jsp?userID=" + userId;
        j.get(url, function (data) {
            j("#user").html(data);
        });
    }
}

function searchUser()
{
    var key = prompt("Enter the username to search");
    if (key !== null)
    {
        var url = "admin/searchUserByUsername.jsp?k=" + key;
        j.get(url, function (data) {
            j("#user").html(data);
        });
    }
}