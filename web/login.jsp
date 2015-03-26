<%
    if (session.getAttribute("user_id") != null) {
        Integer role_id = (Integer) session.getAttribute("role_id");
        if (role_id == 1) {
            response.sendRedirect("admin.jsp");
        } else {
            response.sendRedirect("user.jsp");
        }
    }
%>
<html>
    <head>
        <title>Railway ticket online reservation</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/jquery-ui.css">
        <link rel="icon" href="css/logo.png">
        <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0">
        <style>
            body {
                zoom: 0.98;
                -moz-transform: scale(0.98);
                -moz-transform-origin: 0 0;
            }
            tr.spaceUnder > td
            {
                padding-bottom: 0.7em;
            }
        </style>
    </head>
    <body>
        <script src="js/jquery.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/index.js" type="text/javascript"></script>
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
                        <a href="PNR"><font color="white">PNR status</font></a>
                    </div>
                </div>
            </div>
            <!--body row -->
            <div class="row" style="height: 78vh">
                <center>
                    <div class="col-lg-3" style="background-color: #ffffcc;height:78vh;min-height: 78vh ">
                        <div id='side'>

                            <form id="regForm" action="Login" method="post">

                                <table> <h3>
                                        <tr class="spaceUnder"> 
                                            <td>Username:</td><td><input type="text" name="username" required autofocus></td>
                                        </tr><br>
                                        <tr class="spaceUnder">
                                            <td>Password:</td><td><input type="password" name="password" required></td>
                                        </tr>
                                        <tr>
                                            <td></td><td><input type="submit" value="Login"/></td>
                                        </tr></h3>
                                </table>
                            </form>
                            <br>
                            <p>Don't have account,Sign up <a href="javascript:void(0)" onclick="fn2()">here</a></p>
                            <p>Forgot your password,Reset it <a href="javascript:void(0)" onclick="resetP()">here</a></p>
                            <p id="messageP"></p>
                        </div>
                    </div>
                </center>
                <div class="col-lg-9" style="height: 78vh;max-height: 78vh;min-height: 78vh;padding-left:3%;padding-top:3%  ;overflow: auto ;background-color:ghostwhite ">
                    <div id="main">
                        <div class="row">
                            <div class="col-lg-5">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4><i class="fa fa-fw fa-check">Detailed_Availability</i></h4>
                                    </div>
                                    <div class="panel-body">
                                        The system gives the detailed availability of the available berth.Which you can't get in any other system. 
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-5">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4><i class="fa fa-fw fa-check">Easy_Book</i></h4>
                                    </div>
                                    <div class="panel-body">
                                        The system gives you the seamless booking experience.
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-5">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4><i class="fa fa-fw fa-check">Cancel_Effectively</i></h4>
                                    </div>
                                    <div class="panel-body">
                                        You can cancel the ticket anytime before the chart preparation of the travel.
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-5">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4><i class="fa fa-fw fa-check">One_Click_Print</i></h4>
                                    </div>
                                    <div class="panel-body">
                                        Print your printer friendly version of the ticket with single click.Export the ticket as pdf too.
                                    </div>
                                </div>
                            </div>
                        </div>
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
            var message;
            loginParam = getParameterByName("login");
            if (loginParam == "logout")
            {
                message = "<font color=\"Blue\"> Logged out successfully.</font>";
            }
            else if (loginParam == "error")
            {
                message = "<font color=\"Red\">Invalid login.Please check your username and password.</font>";
            }
            if (message != undefined)
            {
                document.getElementById("messageP").innerHTML = message;
            }

            function getParameterByName(name) {
                name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
                var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                        results = regex.exec(location.search);
                return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
            }
        </script>
    </body>
</html>