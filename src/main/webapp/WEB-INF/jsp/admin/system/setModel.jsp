<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/tlds/jsp-masterpage.tld" prefix="px"%>
<%@ taglib uri="/WEB-INF/tlds/authorizetag.tld" prefix="pxkj"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<px:ContentPage materPageId="master">
	<px:Content contentPlaceHolderId="css">
	<style>
	
	.layui-form-label{
		width:100px!important;
	}
	.layui-input-block{
	    margin-left: 140px!important;
	}
	</style>
	</px:Content>
	<px:Content contentPlaceHolderId="content">
		<div style="padding: 15px;" class="layui-anim layui-anim-upbit">
			<div class="yw_cx">
				<blockquote class="layui-elem-quote  layui-bg-blue">基本信息</blockquote>
			</div>
			<div class="layui-tab">
				<ul class="layui-tab-title">
					<li class="layui-this">网站设置</li>
					<li>后台地址前缀配置</li>
					<li>微信配置</li>
					<li>短信配置</li>
				</ul>
				<div class="layui-tab-content">
					<!-- 网站设置 -->
					<div class="layui-tab-item layui-show">
						<form class="layui-form" action="" id="WebSetForm">
			    			<input type="hidden" value="${siteInfo.imgUrl }" id="oldImgUrl"/>
							<div class="layui-form-item">
								<label class="layui-form-label">网站域名：</label>
								<div class="layui-input-block">
									<input type="text" name="siteUrl" value="${siteInfo.siteUrl }" autocomplete="off" placeholder="网站域名" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">网站名称：</label>
								<div class="layui-input-block">
									<input type="text" name="siteName" value="${siteInfo.siteName }" autocomplete="off" placeholder="网站名称" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">网站标题：</label>
								<div class="layui-input-block">
									<input type="text" name="siteTitle" value="${siteInfo.siteTitle }" autocomplete="off" placeholder="网站标题" class="layui-input">
								</div>
							</div>
							<%--<div class="layui-form-item">
								<label class="layui-form-label">网站LOGO：</label>
								<div class="layui-input-block">
									<button type="button" class="layui-btn" id="test1">
										<i class="layui-icon">&#xe67c;</i>上传图片
									</button>
									<div style="margin-top: 7px;padding: 0;height: 100px;">
		                                <img src="${siteInfo.imgUrl }" id="imgShow" width="100px"/>
		                                <input type="button" value="删除图片" onclick="deleteImg()" id="delImg" style="display: none;" class="layui-btn layui-btn-warm"/>
		                            </div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">服务热线：</label>
								<div class="layui-input-block">
									<input type="text" name="siteTel" value="${siteInfo.siteTel }" autocomplete="off" placeholder="服务热线" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">电子邮件：</label>
								<div class="layui-input-block">
									<input type="text" name="email" value="${siteInfo.email }" autocomplete="off" placeholder="电子邮件" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">ICP证书号：</label>
								<div class="layui-input-block">
									<input type="text" name="icpNO" value="${siteInfo.icpNO }" autocomplete="off" placeholder="ICP证书号，若为空显示备案信息" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">网站版权信息：</label>
								<div class="layui-input-block">					
									<textarea placeholder="网站版权信息" name="copyRight" class="layui-textarea">${siteInfo.copyRight}</textarea>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">网站关键字：</label>
								<div class="layui-input-block">
								 	<textarea placeholder="网站关键字" name="metaKeywords" class="layui-textarea">${siteInfo.metaKeywords}</textarea>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">网站描述：</label>
								<div class="layui-input-block">
									<textarea placeholder="网站描述" name="metaDescription" class="layui-textarea">${siteInfo.metaDescription}</textarea>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label" >站点状态：</label>
								<div class="layui-input-block">
									<input type="checkbox" name="siteStatus" lay-skin="switch" lay-text="开启|关闭" ${siteInfo.siteStatus==true?'checked="checked"':'' }>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">关闭提示：</label>
								<div class="layui-input-block">
									<textarea placeholder="站点关闭时提示信息" name="closeTip" class="layui-textarea">${siteInfo.closeTip}</textarea>
								</div>
							</div>--%>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<button class="layui-btn" lay-submit="" lay-filter="WebSave">提交</button>
								</div>
							</div>
						</form>
					</div>

					<!-- 后台地址前缀配置 -->
					<div class="layui-tab-item">
						<form class="layui-form" action="" id="adminPreFixSetForm">
							<div class="layui-form-item">
								<label class="layui-form-label" >后台地址前缀：</label>
								<div class="layui-input-block">
									<input type="text" name="adminUrl" lay-verify="required" value="${adminPreFixSetting.adminUrl }" autocomplete="off" placeholder="后台地址前缀" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<button class="layui-btn" lay-submit="" lay-filter="adminPreFixSetSave">提交</button>
								</div>
							</div>
						</form>
					</div>
					<%--微信配置--%>
					<div class="layui-tab-item">
						<form class="layui-form" action="" id="wechat">
							<div class="layui-form-item">
								<label class="layui-form-label" >appid：</label>
								<div class="layui-input-block">
									<input type="text" name="appid" lay-verify="required" value="${wechat.appid }" autocomplete="off" placeholder="填写appid" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label" >appsecret：</label>
								<div class="layui-input-block">
									<input type="text" name="appsecret" lay-verify="required" value="${wechat.appsecret }" autocomplete="off" placeholder="填写appsecret" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<button class="layui-btn" lay-submit="" lay-filter="WechatSave">提交</button>
								</div>
							</div>
						</form>
					</div>
					<%--短信配置--%>
					<div class="layui-tab-item">
						<form class="layui-form" action="" id="sms">
							<div class="layui-form-item">
								<label class="layui-form-label" >mid：</label>
								<div class="layui-input-block">
									<input type="text" name="mid" lay-verify="required" value="${sms.mid }" autocomplete="off" placeholder="填写mid" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label" >accessKeyId：</label>
								<div class="layui-input-block">
									<input type="text" name="accessKeyId" lay-verify="required" value="${sms.accessKeyId }" autocomplete="off" placeholder="填写accessKeyId" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label" >accessKeySecret：</label>
								<div class="layui-input-block">
									<input type="text" name="accessKeySecret" lay-verify="required" value="${sms.accessKeySecret }" autocomplete="off" placeholder="填写accessKeySecret" class="layui-input">
								</div>
							</div>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<button class="layui-btn" lay-submit="" lay-filter="SmsSave">提交</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</px:Content>
	<px:Content contentPlaceHolderId="js">
		<script>
		layui.use(['element', 'layer', 'form', 'laydate', 'upload'], function() {
			var element = layui.element;
			var layer = layui.layer;
			var form = layui.form;
			var laydate = layui.laydate;
			var upload = layui.upload;
			//网站设置
			form.on('submit(WebSave)', function(data) {
				layer.load(2, {
					shade: [0.8, '#393D49']
				});
				var success = function(response) {
					var result = response;
					if(result.success) {
						layer.alert(result.msg, {
							icon: 1
						}, function() {
							location.reload();
						});
					} else {
						layer.alert(result.msg, {
							icon: 2
						}, function() {
							location.reload();
						});
					}

				}
				var imgUrl = $('#oldImgUrl').val();
				var data = {
					siteName: data.field.siteName,
					siteTitle: data.field.siteTitle,
					siteUrl: data.field.siteUrl,
					siteTel: data.field.siteTel,
					metaKeywords: data.field.metaKeywords,
					metaDescription: data.field.metaDescription,
					imgUrl: imgUrl,
					email: data.field.email,
					icpNO: data.field.icpNO,
					siteStatus: data.field.siteStatus,
					closeTip: data.field.closeTip,
					copyRight: data.field.copyRight,
					r:Math.random()
				}
				ajax("/${applicationScope.adminprefix }/system/set/saveSetting", data, success);
				return false;
			});
			//后台地址前缀
			form.on('submit(adminPreFixSetSave)', function(data) {
				layer.load(2, {
					shade: [0.8, '#393D49']
				});
				var success = function(response) {
					var result = response;
					if(result.success) {
						layer.alert(result.msg, {
							icon: 1
						}, function() {
                            location.reload();
						});
					} else {
						layer.alert(result.msg, {
							icon: 2
						}, function() {
							location.reload();
						});
					}

				}
			
				var data = {
					adminUrl: data.field.adminUrl,
                    r:Math.random()
				}
				ajax("/${applicationScope.adminprefix }/system/set/saveAdminPreFixSetting", data, success);
				return false;
			});
			//微信设置
			form.on('submit(WechatSave)',function (data) {
				layer.load(2,{shade:[0.8,'#393D49']});
                var success = function(response) {
                    var result = response;
                    if(result.success) {
                        layer.alert(result.msg, {
                            icon: 1
                        }, function() {
                            location.reload();
                        });
                    } else {
                        layer.alert(result.msg, {
                            icon: 2
                        }, function() {
                            location.reload();
                        });
                    }
                }
                var data = {
                    appid: data.field.appid,
                    appsecret: data.field.appsecret,
                    r:Math.random()
                }
                ajax("/${applicationScope.adminprefix }/system/set/saveWechat", data, success);
                return false;
            })
			//加盟费
            form.on('submit(SmsSave)',function (data) {
                layer.load(2,{shade:[0.8,'#393D49']});
                var success = function(response) {
                    var result = response;
                    if(result.success) {
                        layer.alert(result.msg, {
                            icon: 1
                        }, function() {
                            location.reload();
                        });
                    } else {
                        layer.alert(result.msg, {
                            icon: 2
                        }, function() {
                            location.reload();
                        });
                    }
                }
                var data = {
                    mid: data.field.mid,
                    accessKeyId: data.field.accessKeyId,
                    accessKeySecret: data.field.accessKeySecret,
                    r:Math.random()
                }
                ajax("/${applicationScope.adminprefix }/system/set/saveSms", data, success);
                return false;
            })
		});
        </script>
	</px:Content>
</px:ContentPage>
