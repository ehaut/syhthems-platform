<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Login</title>
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/static/css/bootstrap.min.css" type="text/css"/>
</head>
<body class="bg-dark">
<div class="container py-5">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-6 mx-auto">
                    <#if RequestParameters.error?? >
                        <div class="card text-white bg-danger my-1">
                            <div class="card-body">
                                <h3 class="card-title">用户名或密码错误</h3>
                            </div>
                        </div>
                    </#if>
                    <#if RequestParameters.logout?? >
                        <div class="card text-white bg-danger my-1">
                            <div class="card-body">
                                <h3 class="card-title">请重新登录</h3>
                            </div>
                        </div>
                    </#if>
                    <#if isAuthenticated == true >
                        <div class="card text-white bg-success my-1">
                            <div class="card-body">
                                <h3 class="card-title"><#if userDetails?? && userDetails.getUsername()?? >${userDetails.getUsername()}, </#if>
                                    您已登录</h3>
                            </div>
                        </div>
                    <#else >
                        <!-- form card login -->
                        <div class="card rounded-0">
                            <div class="card-header">
                                <h3 class="mb-0">Login</h3>
                            </div>
                            <div class="card-body">

                                <form class="form" role="form" autocomplete="on" id="formLogin" novalidate=""
                                      action="${springMacroRequestContext.contextPath}/login" method="POST">
                                    <div class="form-group">
                                        <label for="username">Username</label>
                                        <input type="text" class="form-control form-control-lg rounded-0"
                                               name="username"
                                               id="username" required="true">
                                    </div>
                                    <div class="form-group">
                                        <label>Password</label>
                                        <input type="password" class="form-control form-control-lg rounded-0"
                                               name="password" id="password" required="true">
                                    </div>
                                    <#if _csrf?? >
                                        <div class="form-group">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        </div>
                                    </#if>
                                    <button type="submit" class="btn btn-success btn-lg float-right" id="login">Login
                                    </button>
                                </form>
                            </div>
                            <!--/card-block-->
                        </div>
                        <!-- /form card login -->
                    </#if>

                </div>


            </div>
            <!--/row-->

        </div>
        <!--/col-->
    </div>
    <!--/row-->
</div>
<!--/container-->
</body>
</html>