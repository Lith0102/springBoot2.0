<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m" %>
<%@ taglib uri="com.core.AuthorizeTag" prefix="px" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<m:ContentPage materPageId="master">
    <m:Content contentPlaceHolderId="css">
    
    	<!--必要样式-->
	    <link href="/static/manage/login/css/styles.css" rel="stylesheet" type="text/css" />
	    <link href="/static/manage/login/css/demo.css" rel="stylesheet" type="text/css" />
	    <link href="/static/manage/login/css/loaders.css" rel="stylesheet" type="text/css" />
	    
    </m:Content>
    <m:Content contentPlaceHolderId="content">
     <!-- 内容主体区域 -->
    <form id="login">
	    <div class='login'>
		  <div class='login_title'>
		    <span>管理员登录</span>
		  </div>
		  <div class='login_fields'>
		    <div class='login_fields__user'>
		      <div class='icon'>
		        <img alt="" src='/static/manage/login/img/user_icon_copy.png'>
		      </div>
		      <input  name="userName" id="userName" placeholder='用户名' maxlength="16" type='text' autocomplete="off" value="admin"/>
		        <div class='validation'>
		          <img alt="" src='/static/manage/login/img/tick.png'>
		        </div>
		    </div>
		    <div class='login_fields__password'>
		      <div class='icon'>
		        <img alt="" src='/static/manage/login/img/lock_icon_copy.png'>
		      </div>
		      <input type="password" name="userPwd" id="userPwd" placeholder='密码' maxlength="16" type='text' autocomplete="off" value="a123123">
		      <div class='validation'>
		        <img alt="" src='/static/manage/login/img/tick.png'>
		      </div>
		    </div>
		    <div class='login_fields__password'>
		      <div class='icon'>
		        <img alt="" src='/static/manage/login/img/key.png'>
		      </div>
		      <input name="code" id="code" placeholder='验证码' maxlength="5" type='text' autocomplete="off" style="width: 50px;">
		      <div class="yzms_img fright">
	             <img id="IMG_Code" src="/code" alt="点击刷新验证码" onclick="javascript:this.src='/code?r='+Math.random();" class="yanzheng" style="height: 40px;width: 80px;cursor:pointer;float: right;margin-right:-1vw;margin-top: -3vw;"/>
	          </div>
		    </div>
		    <div class='login_fields__submit' style="width: 100%;">
		      <input type='button' value='登录' onclick="btnClick(this)">
		    </div>
		  </div>
		  <div class='success'>
		  </div>
		  <div class='disclaimer'>
		    <p>欢迎登陆后台管理系统</p>
		  </div>
		</div>
		<div class='authent'>
		  <div class="loader" style="height: 44px;width: 44px;margin-left: 28px;">
	        <div class="loader-inner ball-clip-rotate-multiple">
	            <div></div>
	            <div></div>
	            <div></div>
	        </div>
	        </div>
		  <p>认证中...</p>
		</div>
    </form>
    </m:Content>
    <m:Content contentPlaceHolderId="js">
	    <script type="text/javascript" src="/static/manage/public/layui/layui.js"></script>
    	<!-- <script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script> -->
		<!-- <script type="text/javascript" src="/static/manage/login/js/jquery-ui.min.js"></script> -->
		<!-- <script type="text/javascript" src='/static/manage/login/js/stopExecutionOnTimeout.js?t=1'></script> -->
	    <script src="/static/manage/login/js/Particleground.js" type="text/javascript"></script>
	    <!-- <script src="/static/manage/login/js/Treatment.js" type="text/javascript"></script> -->
	    <!-- <script src="/static/manage/login/js/jquery.mockjax.js" type="text/javascript"></script> -->
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
            
            $('body').particleground({
    	        dotColor: '#E8DFE8',
    	        lineColor: '#133b88'
    	    });
        </script>

    </m:Content>
</m:ContentPage>
