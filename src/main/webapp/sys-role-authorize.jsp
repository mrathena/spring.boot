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
					<span id="rolename" class="text-16 text-bold"></span>
					<button type="button" id="authorize" class="btn btn-info btn-xs btn-outline" style="float:right;">授权</button>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<div class="panel panel-default mb0">
				<div class="panel-body">
					<div class="pb10 mb10 border-b1" style="border-color: rgb(221,221,221) !important;">
						<button type="button" id="checkAll" class="btn btn-xs btn-outline btn-info">全部选中</button>
						<button type="button" id="inverseAll" class="btn btn-xs btn-outline btn-info">全部反选</button>
						<button type="button" id="uncheckAll" class="btn btn-xs btn-outline btn-info">全不选中</button>
						<button type="button" id="collapseAll" class="btn btn-xs btn-outline btn-info">折叠所有</button>
						<button type="button" id="expandAll" class="btn btn-xs btn-outline btn-info">展开所有</button>
					</div>
					<div id="zTree" class="ztree" style="height:calc(100vh - 159px);overflow:auto;"></div>
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
	$("#rolename").text("授权角色: [ "+object.rolename+" ]");
	$(document).on("click", "#authorize", function() {
		var nodes = $.fn.zTree.getZTreeObj("zTree").getCheckedNodes(true);
		var ids = "";
		$.each(nodes, function(index, node) {
			ids += "," + node.id;
		});
		$.postJson("role/update", {id:object.id, resourceIds:ids.substring(1)}, false, function(response) {
			if (response.status === 1) {
				$.msg.success("操作成功");
			} else if (response.status === 0) {
				$.msg.info("操作失败");
			} else if (response.status === -1) {
				$.msg.warning(response.message);
			}
		}, function() {$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");});
	});
	$(document).on("click", "#checkAll", function() {
		$.fn.zTree.getZTreeObj("zTree").checkAllNodes(true);
	});
	$(document).on("click", "#inverseAll", function() {
		var zTree = $.fn.zTree.getZTreeObj("zTree");
		var nodes = zTree.transformToArray(zTree.getNodes());
		$.each(nodes, function(index, node) {
			if (!node.getCheckStatus().half) {
				$.log(node.name);
				node.checked = !node.checked;
				zTree.updateNode(node);
			}
		});
	});
	$(document).on("click", "#uncheckAll", function() {
		$.fn.zTree.getZTreeObj("zTree").checkAllNodes(false);
	});
	$(document).on("click", "#expandAll", function() {
		$.fn.zTree.getZTreeObj("zTree").expandAll(true);
	});
	$(document).on("click", "#collapseAll", function() {
		$.fn.zTree.getZTreeObj("zTree").expandAll(false);
	});
	// 初始化tree数据
	$.postJson("resource/avaliable", null, false, function(response) {
		if (response.status == 1) {
			$.fn.zTree.init($("#zTree"), zSetting, response.data.resources).expandAll(true);
		} else if (response.status == 0) {
			$.msg.info("初始化数据失败");
		} else if (response.status == -1) {
			$.msg.warning(response.message);
		}
	}, function() {
		$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");
	});
	// 加载它的权限
	$.postJson("role/"+object.id, null, false, function(response) {
		if (response.status == 1) {
			var resourceIds = response.data.role.resourceIds;
			if (!$.isNone(resourceIds)) {
				var ids = (resourceIds + "").split(",");
				var zTree = $.fn.zTree.getZTreeObj("zTree");
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
});
</script>
</body>
</html>