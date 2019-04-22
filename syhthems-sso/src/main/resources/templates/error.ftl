<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Error</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css" type="text/css"/>
</head>
<body class="bg-dark">
<div class="container text-white my-3">
    <div class="row">
        <div class="col-md-6 mx-auto">
            <div class="card text-white bg-danger">
                <div class="card-body">
                    <#if title??><h3 class="card-title">${title}</h3></#if>
                    <p class="card-text">${status!"500"} : ${error!"error"}</p>
                    <p class="card-text">${value!"error"}</p>
                </div>
            </div>
        </div>
    </div><!--/row-->
</div><!--container-->
</body>
</html>
