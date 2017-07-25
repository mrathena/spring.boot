<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
<jsp:include page="layout/resource-css.jsp" />
</head>
<body>
	<div class="p20">
		<h1>Welcome to Shiro Template</h1>
		<h2>
			<shiro:user>
				<shiro:authenticated>通过用户名密码登录</shiro:authenticated>
				<shiro:notAuthenticated>通过记住我登录</shiro:notAuthenticated>
			</shiro:user>
		</h2>
	</div>
</body>
</html>