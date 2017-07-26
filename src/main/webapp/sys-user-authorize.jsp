<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>SYS Role Authorize</title>
<jsp:include page="layout/resource-css.jsp" />
</head>
<body class="gray-bg">

<div class="p10 animated fadeInRight">
	<div class="row">
		<div class="col-sm-12">
			<div class="panel panel-default mb10">
				<div class="panel-body">
					<span id="usernames" class="text-16 text-bold"></span>
					<button type="button" id="authorize" class="btn btn-info btn-xs btn-outline" style="float:right;">授权</button>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-4">
			<div class="panel panel-default mb0">
				<div class="panel-body">
					<div class="pb10 mb10 border-b1" style="border-color: rgb(221,221,221) !important;">
						<div class="pb10">角色</div>
						<button type="button" class="btn btn-xs btn-outline btn-info checkAll">全选</button>
						<button type="button" class="btn btn-xs btn-outline btn-info inverseAll">反选</button>
						<button type="button" class="btn btn-xs btn-outline btn-info uncheckAll">不选</button>
					</div>
					<div id="roleTree" class="ztree" style="height:calc(100vh - 188px);overflow:auto;"></div>
				</div>
			</div>
		</div>
		<div class="col-sm-4 pl0">
			<div class="panel panel-default mb0">
				<div class="panel-body">
					<div class="pb10 mb10 border-b1" style="border-color: rgb(221,221,221) !important;">
						<div class="pb10">额外包含的资源</div>
						<button type="button" class="btn btn-xs btn-outline btn-info checkAll">全选</button>
						<button type="button" class="btn btn-xs btn-outline btn-info inverseAll">反选</button>
						<button type="button" class="btn btn-xs btn-outline btn-info uncheckAll">不选</button>
						<button type="button" class="btn btn-xs btn-outline btn-info collapseAll">折叠</button>
						<button type="button" class="btn btn-xs btn-outline btn-info expandAll">展开</button>
					</div>
					<div id="includeTree" class="ztree" style="height:calc(100vh - 188px);overflow:auto;"></div>
				</div>
			</div>
		</div>
		<div class="col-sm-4 pl0">
			<div class="panel panel-default mb0">
				<div class="panel-body">
					<div class="pb10 mb10 border-b1" style="border-color: rgb(221,221,221) !important;">
						<div class="pb10">绝不包含的资源</div>
						<button type="button" class="btn btn-xs btn-outline btn-info checkAll">全选</button>
						<button type="button" class="btn btn-xs btn-outline btn-info inverseAll">反选</button>
						<button type="button" class="btn btn-xs btn-outline btn-info uncheckAll">不选</button>
						<button type="button" class="btn btn-xs btn-outline btn-info collapseAll">折叠</button>
						<button type="button" class="btn btn-xs btn-outline btn-info expandAll">展开</button>
					</div>
					<div id="excludeTree" class="ztree" style="height:calc(100vh - 188px);overflow:auto;"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="layout/resource-js.jsp" />
<script>
var zSetting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "parentId",
			rootPId: 0
		},
		key: {
			url: "null"
		}
	},
	check: {
		enable: true
	}
};
$(document).ready(function() {
	var object = $.getObjectFromUrl(frameElement.src);
	if (object.ids.length > 1) {
		$.msg.warning("多用户授权时不展示其原有的角色与资源权限");
	}
	var content = "";
	$.each(object.usernames, function(index, username) {
		content += ", " + username;
	});
	$("#usernames").text("授权用户: [ "+content.substring(2)+" ]");
	$(document).on("click", "#authorize", function() {
		var roleNodes = $.fn.zTree.getZTreeObj("roleTree").getCheckedNodes(true);
		var roleIds = "";
		$.each(roleNodes, function(index, node) {
			if (!node.getCheckStatus().half) {
				roleIds += "," + node.id;
			}
		});
		var includeNodes = $.fn.zTree.getZTreeObj("includeTree").getCheckedNodes(true);
		var includeIds = "";
		$.each(includeNodes, function(index, node) {
			if (!node.getCheckStatus().half) {
				includeIds += "," + node.id;
			}
		});
		var excludeNodes = $.fn.zTree.getZTreeObj("excludeTree").getCheckedNodes(true);
		var excludeIds = "";
		$.each(excludeNodes, function(index, node) {
			if (!node.getCheckStatus().half) {
				excludeIds += "," + node.id;
			}
		});
		var data = {
			userIds: object.ids,
			roleIds: roleIds.substring(1),
			includeIds: includeIds.substring(1),
			excludeIds: excludeIds.substring(1)
		};
		$.postJson("user/authorize", data, false, function(response) {
			if (response.status === 1) {
				$.msg.success("操作成功");
			} else if (response.status === 0) {
				$.msg.info("操作失败");
			} else if (response.status === -1) {
				$.msg.warning(response.message);
			}
		}, function() {$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");});
	});
	$(document).on("click", ".checkAll", function() {
		var id = $(this).parent().next().attr("id");
		$.fn.zTree.getZTreeObj(id).checkAllNodes(true);
	});
	$(document).on("click", ".inverseAll", function() {
		var id = $(this).parent().next().attr("id");
		var zTree = $.fn.zTree.getZTreeObj(id);
		var nodes = zTree.transformToArray(zTree.getNodes());
		$.each(nodes, function(index, node) {
			if (!node.getCheckStatus().half) {
				$.log(node.name);
				node.checked = !node.checked;
				zTree.updateNode(node);
			}
		});
	});
	$(document).on("click", ".uncheckAll", function() {
		var id = $(this).parent().next().attr("id");
		$.fn.zTree.getZTreeObj(id).checkAllNodes(false);
	});
	$(document).on("click", ".expandAll", function() {
		var id = $(this).parent().next().attr("id");
		$.fn.zTree.getZTreeObj(id).expandAll(true);
	});
	$(document).on("click", ".collapseAll", function() {
		var id = $(this).parent().next().attr("id");
		$.fn.zTree.getZTreeObj(id).expandAll(false);
	});
	// 初始化tree数据
	$.postJson("role/avaliable", null, false, function(response) {
		if (response.status == 1) {
			$.fn.zTree.init($("#roleTree"), zSetting, response.data.roles).expandAll(true);
		} else if (response.status == 0) {
			$.msg.info("初始化数据失败");
		} else if (response.status == -1) {
			$.msg.warning(response.message);
		}
	}, function() {
		$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");
	});
	$.postJson("resource/avaliable", null, false, function(response) {
		if (response.status == 1) {
			$.fn.zTree.init($("#includeTree"), zSetting, response.data.resources).expandAll(true);
			$.fn.zTree.init($("#excludeTree"), zSetting, response.data.resources).expandAll(true);
		} else if (response.status == 0) {
			$.msg.info("初始化数据失败");
		} else if (response.status == -1) {
			$.msg.warning(response.message);
		}
	}, function() {
		$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");
	});
	// 加载用户的角色与权限
	if (object.ids.length === 1) {
		$.postJson("user/"+object.ids[0], null, false, function(response) {
			if (response.status == 1) {
				var roleIds = response.data.user.roleIds
				if (!$.isNone(roleIds)) {
					var ids = (roleIds + "").split(",");
					var zTree = $.fn.zTree.getZTreeObj("roleTree");
					var nodes = zTree.transformToArray(zTree.getNodes());
					$.each(nodes, function(index, node) {
						if (ids.indexOf(node.id + "") !== -1) {
							zTree.checkNode(node, true, false, false);
						}
					});
				}
				var includeIds = response.data.user.includeIds
				if (!$.isNone(includeIds)) {
					var ids = (includeIds + "").split(",");
					var zTree = $.fn.zTree.getZTreeObj("includeTree");
					var nodes = zTree.transformToArray(zTree.getNodes());
					$.each(nodes, function(index, node) {
						if (ids.indexOf(node.id + "") !== -1) {
							zTree.checkNode(node, true, false, false);
						}
					});
				}
				var excludeIds = response.data.user.excludeIds
				if (!$.isNone(excludeIds)) {
					var ids = (excludeIds + "").split(",");
					var zTree = $.fn.zTree.getZTreeObj("excludeTree");
					var nodes = zTree.transformToArray(zTree.getNodes());
					$.each(nodes, function(index, node) {
						if (ids.indexOf(node.id + "") !== -1) {
							zTree.checkNode(node, true, false, false);
						}
					});
				}
			} else if (response.status == 0) {
				$.msg.info("初始化数据失败");
			} else if (response.status == -1) {
				$.msg.warning(response.message);
			}
		}, function() {
			$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");
		});
	}
});
</script>
</body>
</html>