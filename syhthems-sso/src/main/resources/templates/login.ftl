<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <meta charset="UTF-8"/>
    <title>Login</title>
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/static/css/bootstrap.min.css"
          type="text/css"/>
    <link rel="stylesheet" href="${springMacroRequestContext.contextPath}/static/css/all.min.css" type="text/css"/>
    <script src="${springMacroRequestContext.contextPath}/static/js/jquery-3.4.1.min.js"></script>
    <script src="${springMacroRequestContext.contextPath}/static/js/jquery.validate.min.js"></script>
    <script src="${springMacroRequestContext.contextPath}/static/js/additional-methods.min.js"></script>
    <script src="${springMacroRequestContext.contextPath}/static/js/bootstrap.min.js"></script>
    <style>
        body {
            background: url(${springMacroRequestContext.contextPath}/static/images/cloud_and_sunrise.jpeg) no-repeat;
            background-size: cover;
            font-size: 16px;
        }

        .form {
            background: rgba(255, 255, 255, 0.5);
            margin-top: 200px;
            margin-bottom: 200px;
        }

        #login_form {
            display: block;
        }

        #register_form {
            display: none;
        }

        #register_success, #register_failed {
            display: none;
        }

        .fas {
            display: inline-block;
            top: 29px;
            left: 6px;
            position: relative;
            color: #ccc;
        }

        input[type="text"], input[type="password"] {
            padding-left: 26px;
        }

        .form-title {
            text-align: center;
            margin-top: 20px;
        }

        label.error {
            color: red;
        }

        input.error {
            border: 2px solid red;
            background-color: #FFFFD5;
            color: red;
        }
    </style>
</head>
<body>
<#-- 新的页面 -->
<div class="container">
    <div class="row">
        <div class="col-md-12 col-sm-12">
            <div class="row">
                <form class="form col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-4" id="login_form" autocomplete="on"
                      action="${springMacroRequestContext.contextPath}/sso/login" method="POST">
                    <h3 class="form-title text-info">登录账号</h3>
                    <div>
                        <#if RequestParameters.error?? >
                            <div class="card text-white bg-danger my-1">
                                <div class="card-body py-2">
                                    <p class="card-title">用户名或密码错误</p>
                                </div>
                            </div>
                        </#if>
                        <#if RequestParameters.logout?? >
                            <div class="card text-white bg-danger my-1">
                                <div class="card-body py-2">
                                    <p class="card-title">请重新登录</p>
                                </div>
                            </div>
                        </#if>
                        <#if isAuthenticated == true >
                            <div class="card text-white bg-success my-1">
                                <div class="card-body py-2">
                                    <p class="card-title"><#if userDetails?? && userDetails.getUsername()?? >${userDetails.getUsername()}, </#if>
                                        您已登录</p>
                                </div>
                            </div>
                        <#else >
                            <div class="form-group">
                                <i class="fas fa-user"></i>
                                <input class="form-control required" type="text" placeholder="用户名" name="username"
                                       autofocus="autofocus" maxlength="20"/>
                            </div>
                            <div class="form-group">
                                <i class="fas fa-lock"></i>
                                <input class="form-control required" type="password" placeholder="密码" name="password"
                                       minlength="4"/>
                            </div>
                            <#if _csrf?? >
                                <div class="form-group">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </div>
                            </#if>
                            <div class="form-group">
                                <hr/>
                                <button type="button" id="register_btn" class="btn btn-info">注册?</button>
                                <button type="submit" class="btn btn-success pull-right" id="submit_login">登录</button>
                            </div>
                        </#if>
                    </div>
                </form>
            </div>
        </div>
        <div class="col-md-12 col-sm-12">
            <div class="row">
                <form class="form col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-4" id="register_form" autocomplete="on"
                      action="${springMacroRequestContext.contextPath}/sso/register" method="POST">
                    <h3 class="form-title text-info">注册账号</h3>
                    <div>
                        <div class="form-group">
                            <i class="fas fa-user"></i>
                            <input id="register_form_username" class="form-control required" type="text" placeholder="用户名"
                                   name="username" autofocus="autofocus"/>
                        </div>
                        <div class="form-group">
                            <i class="fas fa-lock"></i>
                            <input class="form-control required" type="password" placeholder="密码" id="register_password"
                                   name="password"/>
                        </div>
                        <div class="form-group">
                            <i class="fas fa-check"></i>
                            <input class="form-control required" type="password" placeholder="再次输入您的密码" name="retype_password"/>
                        </div>
                        <div class="form-group">
                            <i class="fas fa-envelope"></i>
                            <input class="form-control eamil" type="text" placeholder="电子邮箱" name="email"/>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-info" id="back_btn">返回</button>
                            <button type="submit" class="btn btn-success pull-right" id="submit_register"
                                    data-loading-text="注册中...">注册</button>
                        </div>
                        <div id="register_success" class="alert alert-success">
                            <a href="#" class="close" data-dismiss="alert">&times;</a>
                            <strong>注册成功</strong>
                        </div>
                        <div id="register_failed" class="alert alert-warning">
                            <a href="#" class="close" data-dismiss="alert">&times;</a>
                            <strong>注册失败</strong>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $().ready(function () {
        $("#login_form").validate({
            onkeyup: false,
            focusInvalid: false,
            debug: false,
            rules: {
                username: {
                    required: true,
                    pattern: '[\\d\\w]+'
                },
                password: {
                    required: true,
                    minlength: 4
                }
            },
            messages: {
                username: {
                    required: "请输入用户名",
                    pattern: "用户名必须是字母或数字"
                },
                password: {
                    required: "请输入密码",
                    minlength: $.validator.format("密码不能小于{0}个字符")
                }
            }
        });
        $("#register_form").validate({
            submitHandler: function(form) {
                $("#submit_register").button('loading');
                $.ajax({
                    data: $(form).serialize(),
                    dataType: 'json',
                    processData: false,
                    type: 'POST',
                    url: '${springMacroRequestContext.contextPath}/sso/register',
                    success: function (result, status) {
                        if (result.code === 0) {
                            $("#register_success").css("display", "block");
                            $("#register_success .close").click(function () {
                                $("#register_success").alert('close');
                                $("#submit_register").button('reset');
                                $("#back_btn").click();
                            })
                        }
                    },
                    error: function (xhr) {
                        console.log(xhr.status + " " + xhr.statusText);
                        $("#register_failed").css("display", "block");
                        $("#register_failed .close").click(function () {
                            $("#register_failed").alert('close');
                            $("#submit_register").button('reset');
                        })
                    }
                })
            },
            onkeyup: false,
            focusInvalid: false,
            debug: false,
            rules: {
                username: {
                    required: true,
                    pattern: '[\\d\\w]+',
                    remote: {
                        url: "${springMacroRequestContext.contextPath}/sso/user/check_user_name",     //后台处理程序
                        dataType: "json"            //接受数据格式
                    }
                },
                password: {
                    required: true,
                    minlength: 4
                },
                retype_password: {
                    equalTo: "#register_password"
                },
                email: {
                    required: true,
                    email: true,
                    remote: {
                        url: "${springMacroRequestContext.contextPath}/sso/user/check_user_email",
                        dataType: "json"
                    }
                }
            },
            messages: {
                username: {
                    required: "请输入用户名",
                    pattern: "用户名必须是字母或数字",
                    remote: $.validator.format("{0} 已经存在")
                },
                password: {
                    required: "请输入密码",
                    minlength: $.validator.format("密码不能小于{0}个字符")
                },
                retype_password: {
                    equalTo: "两次密码不一样"
                },
                email: {
                    required: "请输入邮箱",
                    email: "请输入有效的邮箱",
                    remote: $.validator.format("{0} 已经存在")
                }
            }
        });
        $("#register_btn").click(function () {
            $("#register_form").css("display", "block");
            $("#login_form").css("display", "none");
        });
        $("#back_btn").click(function () {
            $("#register_form").css("display", "none");
            $("#login_form").css("display", "block");
        });

    });
</script>
</body>
</html>