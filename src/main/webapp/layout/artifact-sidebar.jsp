<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<nav class="navbar-default navbar-static-side" role="navigation">
	<div class="nav-close">
		<i class="fa fa-times-circle"></i>
	</div>
	<div class="sidebar-collapse">
		<ul class="nav" id="side-menu">
			<li class="nav-header">
				<div class="text-20" style="color: white;">Hi，[ <shiro:principal /> ]</div>
			</li>
			<li>
				<a class="J_menuItem" href="welcome" data-index="0">
					<i class="fa fa-home"></i>
					<span class="nav-label">首页</span>
					<!-- 默认主页需在对应的菜单a元素上添加data-index="0" -->
				</a>
			</li>
			<!-- 
			<li>
				<a href="#">
					<i class="glyphicon glyphicon-cog"></i> 
					<span class="nav-label">系统管理</span> 
					<span class="fa arrow"></span>
				</a>
				<ul class="nav nav-second-level">
					<li>
						<a class="J_menuItem" href="sys-resource.jsp">
							<span class="nav-label">资源管理</span>
						</a>
					</li>
					<li><a class="J_menuItem" href="sys-role.jsp">
						<span class="nav-label">角色管理</span>
					</a></li>
					<li><a class="J_menuItem" href="sys-user.jsp">
						<span class="nav-label">用户管理</span>
					</a></li>
				</ul>
			</li>
			 -->
		</ul>
	</div>
</nav>