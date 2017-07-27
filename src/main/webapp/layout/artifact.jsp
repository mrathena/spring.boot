<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>Template Shiro</title>
<!-- Icon -->
<link rel="shortcut icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- 全局CSS -->
<link href="assets/hplus/css/bootstrap.min.css" rel="stylesheet">
<link href="assets/hplus/css/font-awesome.css" rel="stylesheet">
<link href="assets/hplus/css/animate.css" rel="stylesheet">
<!-- 自定义CSS -->
<link href="assets/hplus/css/style.css" rel="stylesheet">
<!-- 我的CSS -->
<link rel="stylesheet" href="${path}${URLProvider.getForLookupPath('/assets/mrathena.css')}" />
<style>
/* 解决百度翻译插件导致页面下方多出来空白的问题 */
#trans-tooltip, #tip-arrow-bottom, #tip-arrow-top {
	display: none;
}
</style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg">
	<div id="wrapper">
		<!--左侧导航开始-->
		<jsp:include page="artifact-sidebar.jsp" />
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row content-tabs">
				<!-- 去掉了没用的Navbar,添加了折叠/打开Sidebar的按钮 -->
				<button class="roll-nav roll-left navbar-minimalize">
					<i class="fa fa-bars"></i>
				</button>
				<button class="roll-nav roll-left J_tabLeft" style="margin-left: 40px;">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs" style="margin-left: 80px;">
					<div class="page-tabs-content">
						<a href="javascript:;" class="active J_menuTab" data-id="welcome">首页</a>
						<!-- 默认主页需在对应的选项卡a元素上添加data-id="默认主页的url" -->
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">标签操作</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right" style="min-width: 79px;">
						<li class="J_tabShowActive">
							<a style="padding: 5px; text-align: center;">定位当前</a>
						</li>
						<li class="J_tabRefreshActive">
							<a style="padding: 5px; text-align: center;">刷新当前</a>
						</li>
						<li class="divider" style="margin: 0;"></li>
						<li class="J_tabCloseOther">
							<a style="padding: 5px; text-align: center;">关闭其他</a>
						</li>
						<li class="J_tabCloseAll">
							<a style="padding: 5px; text-align: center;">关闭全部</a>
						</li>
					</ul>
				</div>
				<a href="logout" class="roll-nav roll-right J_tabExit">退出</a>
			</div>
			<div class="row J_mainContent" id="content-main" style="height: calc(100% - 44px);">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="welcome" frameborder="0" data-id="welcome" seamless></iframe>
				<!--默认主页需在对应的页面显示iframe元素上添加name="iframe0"和data-id="默认主页的url"-->
			</div>
			<!-- Fotter目前没什么用,先屏蔽掉 -->
			<!-- <div class="footer">
                <div class="pull-right">© 2014-2015 
                	<a href="http://www.zi-han.net/" target="_blank">zihan's blog</a>
                </div>
            </div> -->
		</div>
		<!--右侧部分结束-->
	</div>

<!-- 全局JS -->
<script src="assets/hplus/js/jquery.min.js"></script>
<script src="assets/hplus/js/bootstrap.min.js"></script>
<script src="assets/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="assets/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/hplus/js/plugins/layer/layer.min.js"></script>
<!-- 自定义js -->
<script src="assets/hplus/js/hplus.js"></script>
<script src="assets/hplus/js/contabs.js"></script>
<!-- 第三方JS -->
<script src="assets/hplus/js/plugins/pace/pace.min.js"></script>
<!-- 我的JS -->
<script src="${path}${URLProvider.getForLookupPath('/assets/mrathena.js')}"></script>
<script>
$.postJson("user/resource", null, false, function(response) {
	var navigations = response.data.resources;
	$.each(navigations, function(index, nav) {
		if (nav.type === "navigation") {
			// 是导航
			var isFirstLevelNav = $.isNone(nav.parentId) || nav.parentId === 0;// 是第一级导航
			var isParentNav = nav.url === "#";// 是父级导航,有子级导航
			var hasIcon = !$.isNone(nav.ico);// 有图标
			var html = "";
			html += '<li>';
			html += '<a '+(isParentNav?'':'class="J_menuItem" ')+'href="'+nav.url+'" '+(isParentNav?'':'data-index="'+(index+1)+'"')+'>';
			html += hasIcon?'<i class="'+nav.ico+'"></i>':'';
			html += '<span class="nav-label">'+nav.name+'</span>';
			html += isParentNav?'<span class="fa arrow"></span>':'';
			html += '</a>';
			html += isParentNav?'<ul class="nav nav-second-level collapse" id="nav-'+nav.id+'"></ul>':'';
			html += '</li>';
			if (isFirstLevelNav) {
				$("#side-menu").append(html);
			} else {
				$("#nav-"+nav.parentId).append(html);
			}
		}
	});
}, function() {$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");});
</script>
</body>
</html>