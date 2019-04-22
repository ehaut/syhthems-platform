<!DOCTYPE html>
<html>
<header>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css" type="text/css"/>
    <title>Home</title>
</header>
<body class="bg-dark" style="padding-top: 60px;">
<nav class="navbar fixed-top navbar-dark navbar-expand">
    <#--<a class="navbar-brand" href="/">首页</a>-->
    <ul class="nav navbar-nav">
        <li class="nav-item active">
            <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/user/">用户 </a>
        </li>
    </ul>
</nav>
<div class="container text-center text-white">
    <h1>这里是首页 ${message!"Sunrise"}</h1>
    <div class="row">
        <div class="col-10"></div>
        <div class="col-2">
            <#if _csrf?? >
            <#-- 如果开启了 CSRF  -->
                <div>
                    <form action="/logout" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input class="btn btn-outline-warning" type="submit" value="注销"/>
                    </form>
                </div>
            <#else >
                <a class="btn btn-outline-warning" href="/logout">注销</a>
            </#if>
        </div>
    </div>
</div>
<!-- /.container -->
</body>
</html>