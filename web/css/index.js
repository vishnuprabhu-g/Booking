function fn(val)
{
    var url;
    if (val == 1)
    {
        url = "user/";
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
        url = "admin/ViewFinalSeat.jsp?mode=view";
    }

    if (val < 5)
    {
        $.get(url, function (data) {
            $("#main").html(data);
        });
    }
    else if (val == 5) {
        $.get(url, function (data) {
            alert("Chart prepared successfully.Now you can view the chart!");
        });
    }
    else if (val == 6)
    {
        $.get(url, function (data) {
            data = data.trim();
            if (data.substr(0, 3) == 900)
            {
                alert("chart not prepared yet.!");
            }
            else
            {
                $("#main").html(data);
            }
        });
    }
}

function cancel(pnr) {
    if (pnr !== undefined) {
        var url = "cancel/cancel.jsp?pnr=" + pnr;
        $.get(url, function (data) {
            $("#main").html(data);
        });
    }
}

function showBook() {
    var url = "user/book.jsp";
    $.get(url, function (data) {
        $("#main").html(data);
    });
}

function getBooked() {
    var url = "Book";
    var Frmdata = $("#form1").serialize();
    $.get(url, Frmdata, function doP(data) {
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
                $.get(url, Frmdata + "&ignore=1", function (dataIn) {
                    $("#main").html(dataIn);
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
                $.get(url, Frmdata + "&ignore=1", function (dataIn) {
                    //$("#main").html(data);
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
            $("#main").html(data);
    });
}

$(function ()
{
    $(document).tooltip();
});

function viewTic(pnr)
{
    var url = "user/ViewBookedTicket.jsp?pnr=" + pnr;
    $.get(url, function (data) {
        $("#dia").html(data);
        $(function () {
            $("#dia").dialog({
                width: 700
            }, {
                height: 500
            });
        });
    });
}

function cancelLast() {
    var url = "Cancel";
    var data = $("#form2").serialize();
    $.get(url, data, function (data) {
        $("#dia").html(data);
        $(function () {
            $("#dia").dialog({
                modal: true,
                buttons: {
                    Ok: function () {
                        $(this).dialog("close");
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
    $.get(url, function (data) {
        $("#dia").html(data);
        $(function () {
            $("#dia").dialog({
                modal: true,
                title: 'Policy',
                height: 470,
                width: 700,
                buttons: {
                    Ok: function () {
                        $(this).dialog("close");
                    }
                }
            });
        });
    });
}
function ProcessCancel()
{
    var data = $("#form3").serialize();
    var pnr = data.split("=");
    cancel(pnr[1]);
}

function PrintElem(val)
{
    if(val==1)
        elem=$("#ticket");
    else
       elem=$("#to_print");
    Popup($(elem).html());
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
    //console.log($(".cb").size());
    $(".cb").each(function ()
    {
        this.checked = true;
    });
}