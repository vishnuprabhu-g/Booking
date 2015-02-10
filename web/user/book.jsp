<div class="row">

    <!--   <div style="height:23px;background-color: #357ebd">
           <h4 style="color: #ffffff">
               Journey Details
           </h4></div>
       <div class="row">
           <div class="col-lg-3">
               <table>
                   <tr>
                       <td>Train No:</td><td>1</td><td>
                   </tr>
                   <tr>
                       <td>Train Name:</td><td>Chennai CBE Express</td>
                   </tr>
                   <tr>
                       <td> Boarding Station:<span class="red-star">*</span></td><td><select><option></option></select></td>
                   </tr>
               </table>
           </div>
           <div class="col-lg-1"></div>
           <div class="col-lg-3">
               <table>
                   <tr>
                       <td>From:</td><td>Chennai<td>
                   </tr>
                   <tr>
                       <td>To:</td><td>CBE</td>
                   </tr>
                   <tr>
                       <td>Journey date: </td><td>12/12/12</td>
                   </tr>
               </table>
           </div>
           <div class="col-lg-1"></div>
           <div class="col-lg-3">
               <table>
                   <tr>
                       <td>Class:</td><td><td>
                   </tr>
                   <tr>
                       <td>Time of journey:</td><td></td>
                   </tr>
                   <tr>
                       <td>Journey date: </td><td>12/12/12</td>
                   </tr>
               </table>
           </div>
       </div> -->
    <!-- End of first group -->
    <div style="height:23px;background-color: #357ebd">
        <h4 style="color: #ffffff">
            Passenger Details
        </h4></div>
    <form action="Book" id="form1" >
        <table class="table table-bordered" id="tab">
            <thead>
                <tr  >
                    <th class="col-md-1">S.No</th><th class="col-md-4">Name(Only character without space)<span class="red-star">*</span></th><th class="col-md-2">Age<span class="red-star">*</span></th><th class="col-md-2">Gender<span class="red-star">*</span></th><th class="col-md-3">Berth preference</th>
                </tr>
            </thead>
            <tbody>               
                <%
                    for (int i = 0; i < 6; i++) {
                        out.println("<tr id=\"td+1\"><td>" + (i + 1) + "</td><td><input size=\"30\" type=\"text\" name=\"name" + i + "\"></td><td><input type=\"text\"  size=\"3\" name=\"age" + i + "\" ></td><td><select name=\"gender" + i + "\"><option value=\"1\">Male</option><option value=\"2\">Female</option> </select></td><td><select name=\"berth" + i + "\"><option value=\"0\">No preference</option><option value=\"1\">Lower Berth</option><option value=\"2\">Middle Berth</option><option value=\"3\">Upper Berth</option><option value=\"4\">Side Upper</option></select></td></tr>");
                    }
                %>               
            </tbody>
        </table>

        <!-- End of first group -->
        <!--
        <div style="height:23px;background-color: #357ebd">
            <h4 style="color: #ffffff;">
                Children Below 5 years(Ticket will not be issued)
            </h4>
        </div>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th class="col-md-1">S.No</th><th class="col-md-4">Name</th><th class="col-md-2">Age</th><th class="col-md-2">Gender</th>
                </tr>
            </thead>
            <tbody>
                /*<%
                    for (int i = 0; i < 2; i++) {
                        out.println("<tr><td>" + (i + 1) + "</td><td><input type=\"text\" name=\"Cname" + i + "\"></td><td><input type=\"number\" name=\"Cage" + i + "\" ></td><td><select name=\"Cgender" + i + "\"><option value=\"1\">Boy</option><option value=\"2\">Girl</option> </select></td></tr>");
                    }
        %>*/
    </tbody>
</table>
        -->

        <div class="row">
            <div class="col-lg-6">
                <div style="background-color: #dff0d8;color: #6161d5;margin: 2%">
                    <h4>Conditions</h4>
                    <input type="checkbox" name="confirmBerth" value="1"> Book only if confirm berth are alloted<br>
                    <input type="radio" name="berth" value="0" checked > None<br>
                    <input type="radio" name="berth" value="1" > Book only if at least 1 lower berth is alloted<br>
                    <input type="radio" name="berth" value="2"> Book only if at least 2 lower berth is alloted

                </div>
            </div>
            <div class="col-lg-6">
                <div style="font-size: 120%;background-color: #dff0d8;color: #ff9999;margin: 2%">
                    <p> 1. Please check your name and age before booking ticket.<br>
                        2. Booked ticket cannot be edited.<br> 
                        3. Choice is subject to availability</p>
                </div>
            </div>
        </div>
    </form>
    <center> <button onclick="getBooked()">Book</button></center>

</div>
</body>
</html>