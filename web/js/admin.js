function editU(userID)
{
    var url = "admin/editUser.jsp?userID=" + userID;
    j.get(url, function (data) {
        j("#dia3").html(data);
        j("#dia3").dialog();
    });
}
function processUpdateUser()
{
    var obj = j("#editUser");
    if (obj[0].checkValidity() !== false)
    {
        j.get("admin/processUpdate.jsp", j("#editUser").serialize(), function (data) {
            alert("Update success");
        });
    }
    else
    {
        j("#editUser").append("Check the values");
    }
}
function resetU(username)
{
    var rt = confirm("Do you want to reset password for the user?");
    if (rt === true)
    {
        var url = "ResetPassword?username=" + username;
        j.get(url, function (data) {
            alert("Reset success..!");
        });

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

function listUser()
{
    var url = "admin/listUser.jsp";
    j.get(url, function (data) {
        j("#user").html(data);
    });
}

function listTrain()
{
    var url = "admin/listTrain.jsp";
    j.get(url, function (data) {
        j("#train").html(data);
    });
}
function listStation()
{
    var url = "admin/listStation.jsp";
    j.get(url, function (data) {
        j("#station").html(data);
    });
}

function addTrain()
//~~Train1()
{
    var url = "admin/addTrain1.jsp";
    j.get(url, function (data) {
        j("#diaAdmin").html(data);
        j("#diaAdmin").dialog({
            title: "Train details"
        });
    });
}

function train2()
{
    alert(j("#trainForm1").validate());
    var url = "admin/addTrain2.jsp";
    j.get(url,j("#trainForm1").serialize(), function (data) {
        j("#diaAdmin").html(data);
        j("#diaAdmin").dialog({
            title: "Station Details",
            height: 400,
            width: 550
        });
    });
}

function train3()
{
    j.get("admin/showAddTrain.jsp", j("#trainForm2").serialize(), function (data) {
        j("#diaAdmin").html(data);
        j("#diaAdmin").dialog({
            title: "Verify details",
            height: 400,
            width: 550
        });
    });

}