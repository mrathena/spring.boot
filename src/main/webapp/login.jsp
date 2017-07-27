<script>	
// 判断当前窗口是否有顶级窗口, 如果有就让当前的窗口的地址栏发生变化
if (window.top!=null && window.top.document.URL!=document.URL){
    window.top.location= document.URL; // 这样就可以让登录窗口显示在整个窗口
}
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>Login</title>
<!-- Icon -->
<link rel="shortcut icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- CSS -->
<link rel="stylesheet" href="assets/hplus/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/hplus/css/font-awesome.css" />
<link rel="stylesheet" href="assets/hplus/css/animate.css" />
<link rel="stylesheet" href="assets/hplus/css/style.css" />
<link rel="stylesheet" href="assets/hplus/css/login.css" />
<link rel="stylesheet" href="assets/hplus/css/plugins/iCheck/custom.css" >
<link rel="stylesheet" href="${path}${URLProvider.getForLookupPath('/assets/mrathena.css')}" />
</head>
<body class="signin">

	<div class="signinpanel">
		<div class="row">
			<div class="col-sm-6">
				<div class="signin-info">
					<div class="logopanel m-b">
						<h1>[ Template.Shiro : H+ ]</h1>
					</div>
					<div class="m-b"></div>
					<h4>
						欢迎使用 <strong>H+ 后台主题UI框架</strong>
					</h4>
					<ul class="m-b">
						<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>优势一: 你觉得呢</li>
						<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>优势二: 我也不知道哦</li>
						<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>优势三: 谁知道, 我保证不打死他</li>
						<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>优势四: 快点说出来啊, 着急死人了</li>
						<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>优势五: 我爱你</li>
					</ul> <strong>账号由系统管理员管理</strong>
				</div>
			</div>
			<div class="col-sm-6">
				<shiro:user>
					<div class="signin-info">
						<h2 class="mt25">
							您好, [ <shiro:principal /> ]
						</h2>
						<h3 class="mt20">
							您已经登入了系统, 可以选择如下操作
						</h3>
						<ul>
							<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i><a href="index">访问系统</a></li>
							<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i><a href="logout">退出系统</a></li>
						</ul>
					</div>
				</shiro:user>
				<shiro:guest>
					<form id="form" method="post" action="login">
						<h4 class="no-margins">登录：</h4>
						<c:if test="${error != null}">
							<div class="alert alert-danger alert-dismissable mt20">
								<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
								${error}
							</div>
						</c:if>
						<input type="text" class="form-control uname" name="username" placeholder="用户名" required value="mrathena">
						<input type="password" class="form-control pword" name="password" placeholder="密码" required value="1">
						<!-- RememberMe功能在后端管理页面并不适用,因为它并不会验证Cookie保存的账号密码信息是否正确,直接就算登录了,并不算安全 -->
						<!-- <div class="mtb20">
							<input type="checkbox" id="rememberMe" name="rememberMe" value="true">
							<label for="rememberMe" class="pointer">自动登录(1天内)</label>
						</div> -->
						<div class="mt20">
							<input type="submit" value="Login" class="btn btn btn-outline btn-primary btn-block" />
						</div>
					</form>
				</shiro:guest>
			</div>
		</div>
		<div class="signup-footer">
			<div class="pull-left">&copy; 2017 All Rights Reserved.</div>
		</div>
	</div>

	<!-- JS -->
	<script src="assets/hplus/js/jquery.min.js"></script>
	<script src="assets/hplus/js/bootstrap.min.js"></script>
	<script src="assets/lib/jquery/jquery.validate/1.16.0/jquery.validate.min.js"></script>
	<script src="assets/lib/jquery/jquery.validate/1.16.0/localization/messages_zh.min.js"></script>
	<script src="assets/lib/jquery/jquery.serialize/2.5.0/jquery.serialize-object.min.js"></script>
	<script src="assets/hplus/js/plugins/iCheck/icheck.min.js"></script>
	<script src="${path}${URLProvider.getForLookupPath('/assets/mrathena.js')}"></script>
	<script>
		/* $.validator.setDefaults({
			submitHandler : function(form) {
				console.log($("#form").serializeObject());
				form.submit();
			}
		}); */
		$(document).ready(function() {
			$("#rememberMe").iCheck({
				checkboxClass : 'icheckbox_square-green',
				radioClass : 'iradio_square-green',
				increaseArea : '20%'
			});
			// 参数验证
			$("#form").validate({
				messages : {
					username : {
						required : "请填写用户名"
					},
					password : {
						required : "请填写密码"
					}
				}
			});
		});
	</script>
</body>
</html>