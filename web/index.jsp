<!DOCTYPE html>
<html>
    <head>
        <title>Railway ticket online reservation</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/jquery-ui.css">
        <link rel="icon" href="css/logo.png">
        <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0">
    </head>
    <body>
        <script src="css/jquery.min.js" type="text/javascript"></script>
        <script src="css/jquery-ui.js"></script>
        <div style="width:94%;height: 100%;margin-left: 3%;margin-top: 0.2%;">
            <!--Top bar row -->
            <div class="row" style="background-color: #e6e6e6">
                <div class="col-lg-3"><a href="/Booking"><img src="css/logo.png" alt="logo" height="100"></a></div>
                <div class="col-lg-6"><h2>Online railway ticket reservation system</h2>
                </div>
            </div>
            <!--second small bar row -->
            <div class="row">
                <div class="col-lg-12" style="background-color:#3276b1 ">
                    <div align="right">
                        <a style="margin-left: 3px" href="javascript:void(0)" onclick="fn(0)" title="currect status"><font color="white"> View current tickets</font></a> 
                        <a style="margin-left: 3px" href="javascript:void(0)" onclick="fn(4)" title="Reset system"><font color="white"> Reset</font></a>
                        <a style="margin-left: 3px" href="javascript:void(0)" onclick="fn(5)" title="Prepare chart" ><font color="white">Prepare chart</font></a>
                        <a style="margin-left: 3px" href="javascript:void(0)" onclick="fn(6)" title="View chart" ><font color="white">View chart</font></a> 
                    </div>
                </div>
            </div>
            <!--body row -->
            <div class="row" style="height: 78vh">
                <center>
                    <div class="col-lg-3" style="background-color: #ffffcc;height:78vh;min-height: 78vh ">
                        <h3>
                            <a href="javascript:void(0)" onclick="fn(1)" >Book tickets</a>
                            <br><br>
                            <a href="javascript:void(0)" onclick="fn(3)" >View tickets</a>
                            <br><br>
                        </h3>
                    </div>
                </center>
                <div class="col-lg-9" style="height: 78vh;max-height: 78vh;min-height: 78vh;padding-left:3%;padding-top:3%  ;overflow: auto ;background-color:ghostwhite ">
                    <div id="main">
                        <h4>
                            <br>
                            A warm welcome to the online ticket reservation system.
                            <br> </h4>
                        <p>An advanced CRS(Computer reservation system) that gives you the best booking experience..!</p>
                    </div>
                </div>               
            </div>
            <div class="row">
                <div class="col-lg-12"><div style="background-color: #3276b1"><center><p>Online ticket reservation system</p></center></div></div>
            </div>
        </div>
        <div id="dia" style="display: none;padding-left: 1px" title="Ticket Details" >
        </div>
        <script type="text/javascript">
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
                var url = "cancel/cancel.jsp?pnr=" + pnr;
                $.get(url, function (data) {
                    $("#main").html(data);
                });
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

            function PrintElem()
            {
                elem = $("#to_print");
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
        </script>
    </body>
</html>