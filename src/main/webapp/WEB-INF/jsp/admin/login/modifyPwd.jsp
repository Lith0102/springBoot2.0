<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m" %>
<%@ taglib uri="com.core.AuthorizeTag" prefix="px" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<m:ContentPage materPageId="master">
    <m:Content contentPlaceHolderId="css">

    </m:Content>
    <m:Content contentPlaceHolderId="content">
        <!-- 内容主体区域 -->
        <div style="padding: 0 30px" class="layui-anim layui-anim-upbit">
            <div class="layui-field-box"
                 style="border-color: #666; border-radius: 3px; padding: 10px;">
                <form class="layui-form" id="savePost">
                        <%--用户Id--%>
                    <input type="hidden" name="userId" value="${userId}"/>
                    <div class="layui-form-item">
                        <label class="layui-form-label">原密码：</label>
                        <div class="layui-input-block">
                            <input type="password" name="oldPwd" lay-verify="oldPwd"
                                   autocomplete="off" placeholder="请输入原密码" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">新密码：</label>
                        <div class="layui-input-block">
                            <input type="password" name="newPwd" id="newPwd" lay-verify="newPwd"
                                   autocomplete="off" placeholder="请输入新密码" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">确认密码：</label>
                        <div class="layui-input-block">
                            <input type="password" lay-verify="confirmPwd" id="confirmPwd" name="confirmPwd"
                                   autocomplete="off" placeholder="请输入新密码" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn" lay-submit="" lay-filter="subPwd">修改</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </m:Content>
    <m:Content contentPlaceHolderId="js">
        <script>
            //JavaScript代码区域
            layui.use(['element', 'layer', 'form', 'laydate'], function () {
                var element = layui.element;
                var layer = layui.layer;
                var form = layui.form;
                //自定义验证规则
                form.verify({
                    oldPwd: function (value) {
                        if (value.length = 0) {
                            return '请输入原密码！';
                        }
                    },
                    newPwd: [/(.+){6,12}$/, '密码必须6到12位！'],
                    confirmPwd: function (value) {
                        if (value != $('input[name="newPwd"]').val()) {
                            return '两次新密码不相同！';
                        }
                    }
                });

                form.on('submit(subPwd)', function (data) {
                    var success = function (response) {
                        var result = response
                        layer.msg('<font style="color:#fff" width="100%">' + data.msg + '</font>', {
                            time: 3000,
                            offset: 80,
                        });
                        if (result.success) {
                            window.location.href = "/${applicationScope.adminprefix }/login/loginOut";
                        }

                    }
                    var data = $("#savePost").serialize();
                    ajax("/${applicationScope.adminprefix }/login/updatePwd", data, success);
                    return false;
                });
            });
        </script>
    </m:Content>
</m:ContentPage>