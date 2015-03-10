<div id="fromToDiv">
    <form id="getTrain">
        <table>
            <tr class="spaceUnder">
                <td class="spaceLeft"><label>From</label></td>
                <td class="spaceLeft"><input name="from" id="from" required/></td>
            </tr>
            <tr class="spaceUnder">
                <td class="spaceLeft"><label>To</label></td>
                <td class="spaceLeft"><input name="to" id="to" required/></td>
            </tr>
            <tr>
                <td></td><td class="spaceLeft"><button>Get trains</button></td>
            </tr>
        </table>
    </form>
</div>
<div id="fromToError"></div>
<div id="showTrains"></div>
<script>
    j("#getTrain").submit(function (event) {
        //alert("You will get train");
        j.get("user/validStation.jsp", j("#getTrain").serialize(), function (data) {
            if (data.trim() == 0)
            {
                document.getElementById("fromToError").innerHTML = "<label>Invalid selection.<label>";
            }
            else
            {
                j("#fromToError").hide();
                j("#fromToDiv").hide(1000);
                j.get("user/index2.jsp", j("#getTrain").serialize(), function (data) {
                    j("#showTrains").html(data);
                });
            }
        });
        event.preventDefault();
    });
    j.get("user/allStations.jsp", function (data) {
        data = data.trim();
        var allStationData = data.split(",");
        //console.log(allStationData[0]);
        j("#from").autocomplete({
            source: allStationData
        });
        j("#to").autocomplete({
            source: allStationData
        });
    });
</script>
