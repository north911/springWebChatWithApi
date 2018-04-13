<!DOCTYPE html>
<html lang="en">

<head>


    <title>SB Admin 2 - Bootstrap Admin Theme</title>

    <!-- Bootstrap Core CSS -->
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../resources/css/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../resources/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../resources/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        function init() {
            $('#user_add').ajaxForm({
                dataType: 'json',
                success: function (data) {
                    if (data.errorMsg != null)
                        alert(data.errorMsg);
                },
            });
        }
    </script>

</head>

<body onload="init()">

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Register new account</h3>
                </div>
                <div class="panel-body">
                    <form action="/createUser" id="user_add" method="post" role="form">
                        <fieldset>

                            <div class="form-group">
                                <input class="form-control" placeholder="name" id="username" name="username" type="text"
                                       autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" id="password" name="password"
                                       type="password"
                                       value="">
                            </div>

                            <label>choose your role</label>
                            <select class="form-control" id="role" name="role">
                                <option value="agent">AGENT</option>
                                <option value="client">CLIENT</option>
                            </select>
                            <!-- Change this to a button or input when using this as a form -->
                            <div class="form-group">
                                <input class="btn btn-lg btn-success btn-block" type="submit"
                                       value="Register new client">
                            </div>

                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="../resources/js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../resources/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="../resources/js/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="../resources/js/sb-admin-2.js"></script>

</body>

</html>
