<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="m"%>
<%@ taglib uri="com.core.AuthorizeTag" prefix="px" %>
<m:ContentPage materPageId="master">
<m:Content contentPlaceHolderId="css">
<style>


</style>
</m:Content>
<m:Content contentPlaceHolderId="content">
		<!-- 内容主体区域 -->
		<div style="padding:0 30px" class="layui-anim layui-anim-upbit">
			
			<div class="layui-field-box" style=" border-color:#666; border-radius:3px; padding:10px;">
					<form class="layui-form" action="">
					<input type="hidden" name="userId" id="userId" value="${userMap.userId }" />
					<input type="hidden" id="userType" value="${userType }" />
						<div class="layui-form-item">
							<label class="layui-form-label">用户名：</label>
							<div class="layui-input-block">
								<input type="text" name="userName" value="${userMap.userName }" lay-verify="required" autocomplete="off" placeholder="" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">密码：</label>
							<div class="layui-input-block">
								<input type="password" name="password" value="" autocomplete="off" placeholder="" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">手机号：</label>
							<div class="layui-input-block">
								<input type="text" name="telenumber" value="${userMap.telenumber }" lay-verify="phone" autocomplete="off" placeholder="" class="layui-input">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">所属角色：</label>
							<div class="layui-input-block" >
								 <c:forEach items="${role }" var="item">
								 	<input type="checkbox" name="roleId" value="${item.roleid }" title="${item.roleName }" data-role="${item.identify }">
									<%-- <input type="checkbox" class="roleId" value="${item.roleid }" name="roleId" data-role="${item.identify }"/>
									<span>${item.roleName }</span> --%>
								</c:forEach>
							</div>
						</div>
						<input type="hidden" value="${userMap.roleId }" id="roleHidden" />
						<div class="layui-form-item">
							<label class="layui-form-label">真实姓名：</label>
							<div class="layui-input-block">
								<input type="text" name="realname" value="${userMap.realname }" lay-verify="required" autocomplete="off" placeholder="" class="layui-input">
							</div>
						</div>
						<c:if test="${_op=='add' }">
							<div class="layui-form-item">
								<div class="layui-input-block">
									<button class="layui-btn" lay-submit="" lay-filter="addRole">新增</button>
								</div>
							</div>
						</c:if>
						<c:if test="${_op=='update' }">
							<div class="layui-form-item">
								<div class="layui-input-block">
									<button class="layui-btn" lay-submit="" lay-filter="updateRole">修改</button>
								</div>
							</div>
						</c:if>
					</form>
				</div>
            
		</div>
	</m:Content>
<m:Content contentPlaceHolderId="js">
	<script>
	$(function(){	
		var userRoleList = ${userRole};
		window.onload = function() {
			for(var i = 0;i<userRoleList.length;i++){
				var userRoleId = userRoleList[i].roleId;
				$("input[type=checkbox][name='roleId']").each(function() {
					if ($(this).val() == userRoleId) {
						$(this).attr("checked", true);
						$(this).next().addClass("layui-form-checked");
					}
				});
			}
		};
		
	});
		//JavaScript代码区域
		layui.use(['element', 'layer', 'form', 'laydate'], function() {
			var element = layui.element;
			var layer = layui.layer;
			var form = layui.form;
			var laydate = layui.laydate;

			//自定义验证规则
			form.verify({
				title: function(value) {
					if(value.length < 3 && value.length>0) {
						return '标题至少得3个字符';
					}
				},
				pass: [/(.+){6,12}$/, '密码必须6到12位']
			});
			
			//监听提交
			if(${_op=='update' }){
				//修改
				form.on('submit(updateRole)', function(data) {
					layer.load(2, {
						shade: [0.8, '#393D49']
					});
					var success = function(response) {
						var result = response
						if(result.success) {
							layer.alert(result.msg, {
								icon: 1
							}, function() {
								var index = parent.layer.getFrameIndex(window.name);
								 parent.layer.close(index)
							});
						} else {
							layer.alert(result.msg, {
								icon: 2
							}, function() {
								layer.closeAll();
							});
						}

					}
					//获取多选框值
					var val = "";
					$("input:checkbox[name='roleId']:checked").each(function() { // 遍历name=standard的多选框
						val += $(this).val() + ',';
					});
					var data = {
						userId: data.field.userId,
						userName: data.field.userName,
						userPwd: data.field.password,
						telenumber: data.field.telenumber,
						roleId: val,
						realname: data.field.realname,
						r:Math.random()
					}
					ajax("/${applicationScope.adminprefix }/system/user/editAdminUser", data, success);
					return false;
				});
			}else if(${_op=='add' }){
				//新增
				form.on('submit(addRole)', function(data) {
					layer.load(2, {
						shade: [0.8, '#393D49']
					});
					var success = function(response) {
						var result = response
						if(result.success) {
							layer.alert(result.msg, {
								icon: 1
							}, function() {
								var index = parent.layer.getFrameIndex(window.name);
								 parent.layer.close(index)
							});
						} else {
							layer.alert(result.msg, {
								icon: 2
							}, function() {
								layer.closeAll();
							});
						}

					}
					//获取多选框值
					var val = "";
					$("input:checkbox[name='roleId']:checked").each(function() {
						val += $(this).val() + ',';
                        var role = $(this).data('role');
                        console.log(role);
                        $("#userType").val(role);
					});
                    var userType = $("#userType").val();
					var data = {
							userName: data.field.userName,
							userPwd: data.field.password,
							telenumber: data.field.telenumber,
							roleId: val,
							realname: data.field.realname,
							userType: userType,
							r:Math.random()
					}
					ajax("/${applicationScope.adminprefix }/system/user/addAdmin", data, success,'post', 'json');
					return false;
				});
			}
			
		});
		
	</script>
</m:Content>
</m:ContentPage>