<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>SYS Role</title>
<jsp:include page="layout/resource-css.jsp" />
</head>
<body class="gray-bg">

	<div class="p10 animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="panel panel-default mb10">
					<div class="panel-body">
						<button type="button" class="btn btn-sm btn-outline btn-info refresh">刷新页面</button>
						<button type="button" class="btn btn-sm btn-outline btn-info" data-toggle="modal" data-target="#addRoleModal">添加角色</button>
						<button type="button" id="batchEnableRoles" class="btn btn-sm btn-outline btn-info">批量启用</button>
						<button type="button" id="batchDisableRoles" class="btn btn-sm btn-outline btn-info">批量禁用</button>
						<button type="button" id="batchDeleteRoles" class="btn btn-sm btn-outline btn-info">批量删除</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<table id="table" class="table table-hover table-bordered text-12" style="width:100%;"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
<div class="modal inmodal fade" id="addRoleModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加角色</h4>
			</div>
			<div class="modal-body">
				<form role="form" class="m0" id="addRoleForm">
                    <div class="form-group">
                        <label>角色名</label>
                        <input type="text" name="name" class="form-control" required>
                    </div>
                    <div class="form-group m0">
                        <label>描述</label>
                        <input type="text" name="description" class="form-control">
                    </div>
                </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default btn-outline" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-info btn-outline" id="addRoleSubmitBtn">确认</button>
			</div>
		</div>
	</div>
</div>

<jsp:include page="layout/resource-js.jsp" />
<script>
function resetAddRoleForm() {
	$("#addRoleForm").validate().resetForm();
	document.getElementById("addRoleForm").reset();
}
$(document).ready(function() {
	// 初始化datatables数据
	$.postJson("role/all", null, false, function(response) {
		if (response.status == 1) {
			$('#table').dataTable({
				language: datatables.language,
				data: response.data.roles,
				columns: [
					{
						width: 18,
						orderable: false,
						title: '<div style="width:18px;height:18px;"><input type="checkbox" class="checkOrUncheckAllItems" /></div>',
						data: function(row, type, set, meta) {
							var html = '<div style="width:18px;height:18px;"><input type="checkbox" class="item" data-id="'+row.id+'" data-name="'+row.name+'" /></div>';
							return html;
						}
					},
		            {
						title: "ID",
						data: 'id'
					},
		            {
						title: "角色名",
						data: 'name'
					},
		            {
						title: "描述",
						data: 'description'
					},
	            	{	
						title: "状态",
						data: 'available',
		            	render: function(data, type, row, meta) {
		            		var html = '<span class="badge ';
		            		html += (data ? "badge-primary" : "badge-danger") + '">';
		            		html += (data ? "已启用" : "已禁用") + '</span>';
		            		return html;
		            	}
		            },
		            {
		            	title: "操作",
		            	orderable: false,
		            	render: function(data, type, row, meta) {
		            		var html = '<input type="button" value="启用" data-id="'+row.id+'" data-rolename="'+row.name+'" class="enableCurrentRole btn btn-xs btn-outline btn-info mr4">';
		            		html += '<input type="button" value="禁用" data-id="'+row.id+'" data-rolename="'+row.name+'" class="disableCurrentRole btn btn-xs btn-outline btn-info mr4">';
		            		html += '<input type="button" value="授权" data-id="'+row.id+'" data-rolename="'+row.name+'" class="authorizeCurrentRole btn btn-xs btn-outline btn-info mr4">';
		            		html += '<input type="button" value="删除" data-id="'+row.id+'" data-rolename="'+row.name+'" class="deleteCurrentRole btn btn-xs btn-outline btn-info">';
		            		return html;
		            	}
		            }
		        ],
		        order: [[ 1, 'asc' ]],// 覆盖默认的排序设定, 解决datatables第一列总是有排序图标的问题
				processing: true,
				fixedHeader: true,
		        scrollX: true,
				pagingType: "full_numbers",
				lengthMenu: [[10, 15, 20, 25, 50, -1], [10, 15, 20, 25, 50, "All"]],
				drawCallback: function() {
					$("input[type=checkbox]").iCheck("uncheck").iCheck({
						checkboxClass : 'icheckbox_square-green',
						radioClass : 'iradio_square-green'
					});
			    }
			});
		} else if (response.status == 0) {
			$.msg.info("初始化数据失败");
		} else if (response.status == -1) {
			$.msg.warning(response.message);
		}
	}, function() {
		$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");
	});
	// 表格全选操作
	$(document).on("ifChanged", ".checkOrUncheckAllItems", function() {
		var status = $(this).prop("checked") ? "check" : "uncheck";
		$("input[type=checkbox].item").iCheck(status);
	});
	// 创建角色确认按钮点击事件
	$(document).on("click", "#addRoleSubmitBtn", function() {
		if (!$("#addRoleForm").valid()) {
	        return false;
		}
		var data = $("#addRoleForm").serializeObject();
		$.postJson("role/insert", data, false, function(response) {
			if (response.status == 1) {
				$.msg.success("操作成功");
				$("#addRoleModal").modal("hide");
				resetAddRoleForm();
				var table = $('#table').DataTable();
				table.row.add(response.data.role).draw();
			} else if (response.status == 0) {
				$.msg.info("操作失败");
			} else if (response.status == -1) {
				$.msg.warning(response.message);
			}
		}, function() {
			$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");
		});
	});
	// Modal隐藏事件
	$("#addRoleModal").on("hide.bs.modal", function() {
		resetAddRoleForm();
	});
	// 禁用用户
	$(document).on("click", "#batchDisableRoles, .disableCurrentRole", function() {
		var roleIds = new Array();
		if ($(this).attr("id")) {
			$("input[type=checkbox].item:checked").each(function() {
				roleIds.push($(this).data("id"));
			});
		} else {
			roleIds.push($(this).data("id"));
		}
		if (roleIds.length === 0) {
			$.msg.warning("需要选择至少一个角色才能执行此操作");
			return false;
		}
		$.postJson("role/disable", {roleIds:roleIds}, false, function(response) {
			if (response.status === 1) {
				$.msg.success("操作成功");
				$("input[type=checkbox].item").each(function() {
					if (roleIds.indexOf($(this).data("id")) !== -1) {
						$(this).parents("tr").children().eq(4).html('<span class="badge badge-danger">已禁用</span>');
					}
				});
			} else if (response.status === 0) {
				$.msg.info("操作失败");
			} else if (response.status === -1) {
				$.msg.warning(response.message);
			}
		}, function() {$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");});
	});
	// 启用角色
	$(document).on("click", "#batchEnableRoles, .enableCurrentRole", function() {
		var roleIds = new Array();
		if ($(this).attr("id")) {
			$("input[type=checkbox].item:checked").each(function() {
				roleIds.push($(this).data("id"));
			});
		} else {
			roleIds.push($(this).data("id"));
		}
		if (roleIds.length === 0) {
			$.msg.warning("需要选择至少一个角色才能执行此操作");
			return false;
		}
		$.postJson("role/enable", {roleIds:roleIds}, false, function(response) {
			if (response.status === 1) {
				$.msg.success("操作成功");
				$("input[type=checkbox].item").each(function() {
					if (roleIds.indexOf($(this).data("id")) !== -1) {
						$(this).parents("tr").children().eq(4).html('<span class="badge badge-primary">已启用</span>');
					}
				});
			} else if (response.status === 0) {
				$.msg.info("操作失败");
			} else if (response.status === -1) {
				$.msg.warning(response.message);
			}
		}, function() {$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");});
	});
	// 删除角色
	$(document).on("click", "#batchDeleteRoles, .deleteCurrentRole", function() {
		var roleIds = new Array();
		if ($(this).attr("id")) {
			$("input[type=checkbox].item:checked").each(function() {
				roleIds.push($(this).data("id"));
			});
		} else {
			roleIds.push($(this).data("id"));
		}
		if (roleIds.length === 0) {
			$.msg.warning("需要选择至少一个角色才能执行此操作");
			return false;
		}
		var index = layer.open({
			title: "删除角色",
			icon: 0,
			content: "确认要删除"+($(this).attr("id")?"所选中的":"该")+"角色吗?",
			btn: "确认",
			yes: function() {
				layer.close(index);
				$.postJson("role/delete", {roleIds:roleIds}, false, function(response) {
					if (response.status === 1) {
						$.msg.success("操作成功");
						var table = $('#table').DataTable();
						$("input[type=checkbox].item").each(function() {
							if (roleIds.indexOf($(this).data("id")) !== -1) {
								table.row($(this).parents('tr')).remove().draw();
							}
						});
					} else if (response.status === 0) {
						$.msg.info("操作失败");
					} else if (response.status === -1) {
						$.msg.warning(response.message);
					}
				}, function() {$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");});
			}
		});
	});
	// 授权
	$(document).on("click", ".authorizeCurrentRole", function() {
		var id = $(this).data("id");
		var rolename = $(this).data("rolename");
		var url = $.getUrlWithObject("sys-role-authorize", {id:id, rolename:rolename});
		var index = layer.open({
			type: 2,
			title: "角色授权",
			area: ['400px', '700px'],
			fixed: false, //不固定
			maxmin: true,
			content: url
		});
	});
});
</script>
</body>
</html>