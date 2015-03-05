var j = jQuery.noConflict();
var dMain = j("#main").html();
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

function resetP()
{
    var url = "reset.jsp";
    j.get(url, function (data) {
        j("#side").html(data);
    });
}

function addUser()
{
    var url = "signup_1.jsp";
    j.get(url, function (data) {
        j("#dia2").html(data);
        //alert(data);
        j(function () {
            j("#dia2").dialog({
                width: 400
            }, {
                height: 270
            });
        });
    });
}

function addUserFinalClick()
{
    //alert("REG method");
    //alert(j("#regForm").serialize());
    var obj = j("#regForm2");
    if (obj[0].checkValidity() !== false)
    {
        j.get("Register", j("#regForm2").serialize(), function (data) {
            //alert("After get the data" + data);
            if (data == 0)
            {
                j("#dia2").append("Username already taken. Try a different one.");
            }
            else
            {
                j("#dia2").html(data.substr(0, 32));
            }
            //j("#dia").dialog();
        });
    }
    else
    {
        j("#regForm2").append("Check the input data.");
    }
}


function fn2()
{
    j.get("signup.jsp", function (data) {
        j("#side").html(data);
    });
}


function reg()
{
    //alert("REG method");
    //alert(j("#regForm").serialize());
    //document.getElementById("regForm").validate();
    var i;
    var obj = j("#regForm");
    if (obj[0].checkValidity() !== false)
    {
        j.get("Register", j("#regForm").serialize(), function (data) {
            //alert("After get the data" + data);
            if (data == 0)
            {
                j("#side").append("Username already taken. Try a different one.");
            }
            else
            {
                j("#side").html(data);
            }
        });
    }
    else
    {
        j("#side").append("Invalid submit.");
    }
}
function fn(val)
{
    j.get("valid.jsp", function (data)
    {
        //alert(data);
        if (data == 0)
        {
            //alert("In zero");
            document.location = "login.jsp?next="
            document.reload();
        }
    });

    var url;
    if (val == 1)
    {
        url = "user/index.jsp";
    }
    else if (val == 2)
    {
        url = "cancel/"
    }
    else if (val == 3)
    {
        url = "admin/AllTicket.jsp"
    }
    else if (val == 4)
    {
        url = "admin/reset.jsp"
    }
    else if (val == 5)
    {
        url = "Chart";
    }
    else if (val == 6)
    {
        url = "admin/ViewFinalSeat.jsp";
    }
    else if (val == 0)
    {
        url = "admin/ViewCurrentStatus.jsp?mode=view";
    }
    else if (val == -1)
    {
        url = "admin/admin.jsp";
    }

    if (val < 5)
    {
        j.get(url, function (data) {
            j("#main").html(data);
        });
    }
    else if (val == 5) {
        j.get(url, function (data) {
            alert("Chart prepared successfully.Now you can view the chart!");
        });
    }
    else if (val == 6)
    {
        j.get(url, function (data) {
            data = data.trim();
            if (data.substr(0, 3) == 900)
            {
                alert("chart not prepared yet.!");
            }
            else
            {
                j("#main").html(data);
            }
        });
    }
}

function cancel(pnr) {
    if (pnr !== undefined) {
        var url = "cancel/cancel.jsp?pnr=" + pnr;
        j.get(url, function (data) {
            j("#main").html(data);
        });
    }
}

function showBook(trainClassStatusId) {
    var url = "user/book.jsp?tcsID=" + trainClassStatusId;
    j.get(url, function (data) {
        j("#main").html(data);
    });
}

function getBooked() {
    var url = "Book";
    var Frmdata = j("#form1").serialize();
    j.get(url, Frmdata, function doP(data) {
        var reply = data.substr(0, 3);
        var mess = data.substr(3, 60);
        if (reply == 101)
        {
            var userOpt = confirm("Your requested berth is not avaiable " + mess + ".\nBut there are some other berths avaviable.\nDo you want to book?");
            if (!userOpt)
            {
                alert("Thank you for using the system.. Your ticket is not booked");
                window.setTimeout(2000);
                window.location = "Booking/";
                location.reload();
            }
            else
            {
                j.get(url, Frmdata + "&ignore=1", function (dataIn) {
                    j("#main").html(dataIn);
                });

            }
        }
        else if (reply == 102)
        {
            /* var userOpt = confirm("No confirmed berth is available.\nDo you want to continue");
             if (!userOpt)
             {
             alert("Thank you for using the system.. Your ticket is not booked");
             window.setTimeout(2000);
             window.location = "Booking/";
             location.reload();
             }
             else*/
            {
                j.get(url, Frmdata + "&ignore=1", function (dataIn) {
                    //j("#main").html(data);
                    doP(dataIn);
                });

            }
        }
        else if (reply == 104)
        {
            alert(mess);
        }
        else if (reply == 001)
            alert("Tickets cannot be booked for kids only..!");
        else if (reply == 002)
            alert("Name invalid..!");
        else if (reply == 003)
            alert("Age invalid..!");
        else if (reply == 004)
            alert("Empty list..!");
        else if (reply == 005)
            alert("Age required (only number)..!");
        else if (reply == 006)
            alert("Exception while processing your request..!");
        else
            j("#main").html(data);
    });
}

j(function ()
{
    //j(document).tooltip();
});

function viewTic(pnr)
{
    var url = "user/ViewBookedTicket.jsp?pnr=" + pnr;
    j.get(url, function (data) {
        j("#dia").html(data);
        j(function () {
            j("#dia").dialog({
                width: 700
            }, {
                height: 500
            });
        });
    });
}

function cancelLast() {
    var url = "Cancel";
    var data = j("#form2").serialize();
    j.get(url, data, function (data) {
        j("#dia").html(data);
        j(function () {
            j("#dia").dialog({
                modal: true,
                buttons: {
                    Ok: function () {
                        j(this).dialog("close");
                        fn(3);
                    }
                }
            });
        });
    });
}

function price()
{
    var url = "user/policy.txt";
    j.get(url, function (data) {
        j("#dia").html(data);
        j(function () {
            j("#dia").dialog({
                modal: true,
                title: 'Policy',
                height: 470,
                width: 700,
                buttons: {
                    Ok: function () {
                        j(this).dialog("close");
                    }
                }
            });
        });
    });
}
function ProcessCancel()
{
    var data = j("#form3").serialize();
    var pnr = data.split("=");
    cancel(pnr[1]);
}

function PrintElem(val)
{
    if (val == 1)
        elem = j("#ticket");
    else
        elem = j("#to_print");
    Popup(j(elem).html());
}

function Popup(data)
{
    var mywindow = window.open('', 'my div', 'height=400,width=600');
    mywindow.document.write('<html><head><title>Chart of CBE MAS train</title>');
    mywindow.document.write('<link rel="stylesheet" href="css/bootstrap.css" type="text/css" />');
    mywindow.document.write('</head><body >');
    mywindow.document.write(data);
    mywindow.document.write('</body></html>');
    mywindow.print();
    mywindow.close();
    return true;
}

function selectAll()
{
    //console.log(j(".cb").size());
    j(".cb").each(function ()
    {
        this.checked = true;
    });
}

function getTrainClass(trainID, classID)
{
    data = "trainID=" + trainID + "&classID=" + classID;
    j.get("user/getTrainDetailAvail.jsp", data, function (reply) {
        j("#classAvailable").html(reply);
    });
}

function showDefault()
{
    j("#main").html = dMain;
}
