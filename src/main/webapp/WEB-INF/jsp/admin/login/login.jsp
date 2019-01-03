<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m" %>
<%@ taglib uri="com.core.AuthorizeTag" prefix="px" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<m:ContentPage materPageId="master">
    <m:Content contentPlaceHolderId="css">
        <link rel="stylesheet" type="text/css" href="/static/manage/login/css/public.css"/>
        <link rel="stylesheet" type="text/css" href="/static/manage/login/css/style.css"/>
        <style>
            input {
                font-size: 18px;
            }
        </style>
    </m:Content>
    <m:Content contentPlaceHolderId="content">
        <!-- 内容主体区域 -->
        <div class="admin_login_box cboth">
            <!-- <div class="admin_login_left">
                <img src="/static/manage/login/img/login-2.png"/>
            </div> -->
            <div class="admin_login_right">
                <form id="login">
                    <div class="input_form cboth">
                        <label class="fleft">用户名：</label>
                        <div class="inputlabel fright">
                            <input type="text" name="userName" id="userName" placeholder="请输入用户名"/>
                        </div>
                    </div>
                    <div class="input_form cboth">
                        <label class="fleft">用户密码：</label>
                        <div class="inputlabel fright">
                            <input type="password" name="userPwd" id="userPwd" placeholder="请输入密码"/>
                        </div>
                    </div>
                    <div class="input_form cboth">
                        <label class="fleft">验证码：</label>
                        <div class="yzms_img fright">
                            <img id="IMG_Code" src="/code" alt="点击刷新验证码"
                                 onclick="javascript:this.src='/code?r='+Math.random();" class="yanzheng"
                                 style="height: 46px;width: 85px;cursor:pointer;"/>
                        </div>
                        <div class="inputlabel fright yzms">
                            <input type="text" name="code" id="code" placeholder="请输入验证码"/>
                        </div>
                    </div>
                    <%--<p class="wjmm"><span><a href="#">忘记密码？</a></span></p>--%>
                    <div class="input_form">
                        <label class="fleft"></label>
                        <div class="inputlabel submit_input fright">
                            <input type="button" value="登录" onclick="btnClick(this)"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </m:Content>
    <m:Content contentPlaceHolderId="js">
        <script type="text/javascript" src="/static/manage/public/layui/layui.js"></script>
        <script type="text/javascript">
            var layer;
            layui.use('layer', function () {
                layer = layui.layer;
            });
            $(function () {
                var index;
                $("#IMG_Code").hover(function () {
                    index = layer.tips("点击刷新验证码", this, {
                        time: 60 * 1000 * 60,
                        tips: [2, '#76c57f']
                    });
                }, function () {
                    layer.close(index);
                });

                //判断是否是在iframe中
                if (top.location.href != location.href) {
                    top.location.href = location.href;
                }
                $("#userName").focus();

            })

            $("body").keydown(function (event) {
                var code = event.keyCode;
                if (code == 13) {//回车
                    btnClick($(".dl_an")); //调用btnClick函数
                }
            });

            function btnClick(o) {

                var userName = $("#userName").val();
                if (userName == "") {
                    tipinfo("请输入用户名！", "#userName");
                    $("#userName").focus();
                    return false;
                }
                var userPwd = $("#userPwd").val();
                if (userPwd == "") {
                    tipinfo("请输入密码！", "#userPwd");
                    $("#userPwd").focus();
                    return false;
                }
                var code = $("#code").val();
                if (code == "") {
                    tipinfo("请输入验证码！", "#code");
                    $("#code").focus();
                    return false;
                }

                $(o).val("正在登录...").attr("disabled", true);

                $.ajax({
                    type: "post",
                    data: $("#login").serialize(),
                    url: "/${applicationScope.adminprefix }/login/userLogin",
                    success: function (data) {
                        if (data.isLogin) {
                            var redirect = '${redirectUrl}';
                            if (redirect != '') {
                                location.href = redirect;
                                return;
                            }
                            location.href = '/${applicationScope.adminprefix }/index';
                        } else {
                            var txt = data.message;
                            tipinfo(txt);
                            $("#code").val("");
                            $("#IMG_Code").click();
                            if (txt.indexOf("验证码") == -1) {
                                $("#userName").focus();
                                $("#login input").val("");
                            } else {
                                $("#code").focus();
                            }
                            $(o).val("登录").removeAttr("disabled");
                        }
                    },
                    error: function (data) {
                        tipinfo("网络异常,请稍后再试！");
                        $(o).val("登录").removeAttr("disabled");
                    }
                })
            }
            function tipinfo(msg, obj) {
                layer.closeAll();
                if (obj == null || typeof obj == "undefined") {
                    layer.msg('<font style="color:#fff" width="100%">' + msg + '</font>', {
                        time: 3000,
                        offset: 80,
                    });
                } else {
                    layer.tips(msg, obj, {
                        time: 2000,
                        tips: [2, '#f75c4c']
                    });
                }
            }
        </script>

    </m:Content>
</m:ContentPage>
