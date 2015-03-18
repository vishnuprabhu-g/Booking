<%@include file="check.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Railway ticket online reservation</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/jquery-ui.css">
        <link rel="stylesheet" href="css/ticket.css">
        <link rel="icon" href="css/logo.png">
        <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0">
        <style>
            body {
                zoom: 0.98;
                -moz-transform: scale(0.98);
                -moz-transform-origin: 0 0;
            }
        </style>
    </head>
    <body>
        <script src="js/jquery.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/jquery.validate.min.js"></script>
        <script src="js/index.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/admin.js"></script>
        <!-- <div id="alertDiv">
            <div class="alert alert-success">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <div id="alertDivMessage">
                </div>
            </div>
        </div> -->
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
                            <a href="javascript:void(0)" onclick="fn(-1)" >Admin console</a>
                            <br><br>
                            <a href="logout.jsp">Logout</a>
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
        <div id="dia2" style="display: none;padding-left: 1px" title="Add user" >
        </div>
        <div id="dia3" style="display: none;padding-left: 1px" title="Update user" >
        </div>

    </body>
</html>