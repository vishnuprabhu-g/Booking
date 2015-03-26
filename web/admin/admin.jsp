<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingOne">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                    User Management
                </a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
            <div class="panel-body">

                <div class="row">
                    <button type="button" class="btn btn-default" onclick="addUser()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add User
                    </button>
                    <button type="button" class="btn btn-default" onclick="listUser()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-list" aria-hidden="true"></span> List User
                    </button>
                    <button type="button" class="btn btn-default" onclick="searchUser()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span> Search User
                    </button>
                </div>
                <br>
                <div id="user" class="row" style="margin: 1%">

                </div>

            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingTwo">
            <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                    Train Management
                </a>
            </h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse " role="tabpanel" aria-labelledby="headingTwo">
            <div class="panel-body">
                <div class="row">
                    <button type="button" class="btn btn-default" onclick="addTrain()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Train
                    </button>
                    <button type="button" class="btn btn-default" onclick="listTrain()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-list" aria-hidden="true"></span> List Train
                    </button>
                    <button type="button" class="btn btn-default" onclick="searchTrain()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span> Search Train
                    </button>
                </div>
                <div id="train" class="row" style="margin: 1%">

                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingThree">
            <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                    Station Management
                </a>
            </h4>
        </div>
        <div id="collapseThree" class="panel-collapse collapse " role="tabpanel" aria-labelledby="headingThree">
            <div class="panel-body">
                <div class="row">
                    <button type="button" class="btn btn-default" onclick="addStation()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Station
                    </button>
                    <button type="button" class="btn btn-default" onclick="listStation()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-list" aria-hidden="true"></span> List Station
                    </button>
                    <button type="button" class="btn btn-default" onclick="searchStation()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span> Search Station
                    </button>
                </div>
                <div id="station" class="row" style="margin: 1%">

                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingFive">
            <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseThree">
                   Class Management
                </a>
            </h4>
        </div>
        <div id="collapseFive" class="panel-collapse collapse " role="tabpanel" aria-labelledby="headingFive">
            <div class="panel-body">
                <div class="row">
                    <button type="button" class="btn btn-default" onclick="addClass()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Class
                    </button>
                    <button type="button" class="btn btn-default" onclick="listClass()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-list" aria-hidden="true"></span> List Class
                    </button>
                </div>
                <div id="classUpdate" class="row" style="margin: 1%">
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingFour">
            <h4 class="panel-title">
                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                    Train-Class Management
                </a>
            </h4>
        </div>
        <div id="collapseFour" class="panel-collapse collapse " role="tabpanel" aria-labelledby="headingFour">
            <div class="panel-body">
                <div class="row">
                    <button type="button" class="btn btn-default" onclick="viewTrainClass()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> View Train Class Details
                    </button>
                    <button type="button" class="btn btn-default" onclick="searchTrainClass()" style="margin-left: 3%">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span> Search Train
                    </button>
                </div>
                <div id="class" class="row" style="margin: 1%">

                </div>
            </div>
        </div>
    </div>
</div>
<div id="diaAdmin"></div>