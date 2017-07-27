<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>SYS User</title>
<jsp:include page="layout/resource-css.jsp" />
</head>
<body class="gray-bg">

	<div class="p10 animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="panel panel-default mb10">
					<div class="panel-body">
						<button type="button" class="btn btn-sm btn-outline btn-info refresh">刷新页面</button>
						<button type="button" class="btn btn-sm btn-outline btn-info" data-toggle="modal" data-target="#addUserModal">添加用户</button>
						<button type="button" id="batchEnableUsers" class="btn btn-sm btn-outline btn-info">批量启用</button>
						<button type="button" id="batchDisableUsers" class="btn btn-sm btn-outline btn-info">批量禁用</button>
						<button type="button" id="batchUnlockUsers" class="btn btn-sm btn-outline btn-info">批量解锁</button>
						<button type="button" id="batchAuthorizeUsers" class="btn btn-sm btn-outline btn-info">批量授权</button>
						<button type="button" id="batchDeleteUsers" class="btn btn-sm btn-outline btn-info">批量删除</button>
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
	
<div class="modal inmodal fade" id="addUserModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加用户</h4>
			</div>
			<div class="modal-body">
				<form role="form" class="m0" id="addUserForm">
                    <div class="form-group">
                        <label>用户名</label>
                        <input type="text" name="username" class="form-control" required>
                    </div>
                    <div class="form-group m0">
                        <label>密码</label>
                        <input type="text" name="password" class="form-control" required>
                    </div>
                </form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default btn-outline" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-info btn-outline" id="addUserSubmitBtn">确认</button>
			</div>
		</div>
	</div>
</div>
	
<jsp:include page="layout/resource-js.jsp" />
<script>
function resetAddUserForm() {
	$("#addUserForm").validate().resetForm();
	document.getElementById("addUserForm").reset();
}
$(document).ready(function() {
	// 初始化datatables数据
	$.postJson("user/all", null, false, function(response) {
		if (response.status == 1) {
			$('#table').dataTable({
				language: datatables.language,
				data: response.data.users,
				columns: [
					{
						width: 18,
						orderable: false,
						title: '<div style="width:18px;height:18px;"><input type="checkbox" class="checkOrUncheckAllItems" /></div>',
						data: function(row, type, set, meta) {
							var html = '<div style="width:18px;height:18px;"><input type="checkbox" class="item" data-id="'+row.id+'" data-username="'+row.username+'" /></div>';
							return html;
						}
					},
		            {
						title: "用户名",
						data: 'username'
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
                        title: "创建时间",
                        data: "createTime"
                    },
		            {
		            	title: "操作",
		            	orderable: false,
		            	render: function(data, type, row, meta) {
		            		var html = '<input type="button" value="启用" data-id="'+row.id+'" data-username="'+row.username+'" class="enableCurrentUser btn btn-xs btn-outline btn-info mr4">';
		            		html += '<input type="button" value="禁用" data-id="'+row.id+'" data-username="'+row.username+'" class="disableCurrentUser btn btn-xs btn-outline btn-info mr4">';
		            		html += '<input type="button" value="解锁" data-id="'+row.id+'" data-username="'+row.username+'" class="unlockCurrentUser btn btn-xs btn-outline btn-info mr4">';
		            		html += '<input type="button" value="改密" data-id="'+row.id+'" data-username="'+row.username+'" class="resetPasswordForCurrentUser btn btn-xs btn-outline btn-info mr4">';
		            		html += '<input type="button" value="授权" data-id="'+row.id+'" data-username="'+row.username+'" class="authorizeCurrentUser btn btn-xs btn-outline btn-info mr4">';
		            		html += '<input type="button" value="删除" data-id="'+row.id+'" data-username="'+row.username+'" class="deleteCurrentUser btn btn-xs btn-outline btn-info">';
		            		return html;
		            	}
		            }
		        ],
		        order: [[ 3, 'asc' ]],// 覆盖默认的排序设定, 解决datatables第一列总是有排序图标的问题
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
	// 创建用户确认按钮点击事件
	$(document).on("click", "#addUserSubmitBtn", function() {
		if (!$("#addUserForm").valid()) {
	        return false;
		}
		var data = $("#addUserForm").serializeObject();
		$.postJson("user/insert", data, false, function(response) {
			if (response.status == 1) {
				$.msg.success("操作成功");
				$("#addUserModal").modal("hide");
				resetAddUserForm();
				// 添加新行, 初始化时用的是user数组, 添加新行用单个user
				var table = $('#table').DataTable();
				table.row.add(response.data.user).draw();
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
	$("#addUserModal").on("hide.bs.modal", function() {
		resetAddUserForm();
	});
	// 禁用用户
	$(document).on("click", "#batchDisableUsers, .disableCurrentUser", function() {
		var userIds = new Array();
		if ($(this).attr("id")) {
			$("input[type=checkbox].item:checked").each(function() {
				userIds.push($(this).data("id"));
			});
		} else {
			userIds.push($(this).data("id"));
		}
		if (userIds.length === 0) {
			$.msg.warning("需要选择至少一个用户才能执行此操作");
			return false;
		}
		$.postJson("user/disable", {userIds:userIds}, false, function(response) {
			if (response.status === 1) {
				$.msg.success("操作成功");
				$("input[type=checkbox].item").each(function() {
					if (userIds.indexOf($(this).data("id")) !== -1) {
						$(this).parents("tr").children().eq(2).html('<span class="badge badge-danger">已禁用</span>');
					}
				});
			} else if (response.status === 0) {
				$.msg.info("操作失败");
			} else if (response.status === -1) {
				$.msg.warning(response.message);
			}
		}, function() {$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");});
	});
	// 启用用户
	$(document).on("click", "#batchEnableUsers, .enableCurrentUser", function() {
		var userIds = new Array();
		if ($(this).attr("id")) {
			$("input[type=checkbox].item:checked").each(function() {
				userIds.push($(this).data("id"));
			});
		} else {
			userIds.push($(this).data("id"));
		}
		if (userIds.length === 0) {
			$.msg.warning("需要选择至少一个用户才能执行此操作");
			return false;
		}
		$.postJson("user/enable", {userIds:userIds}, false, function(response) {
			if (response.status === 1) {
				$.msg.success("操作成功");
				$("input[type=checkbox].item").each(function() {
					if (userIds.indexOf($(this).data("id")) !== -1) {
						$(this).parents("tr").children().eq(2).html('<span class="badge badge-primary">已启用</span>');
					}
				});
			} else if (response.status === 0) {
				$.msg.info("操作失败");
			} else if (response.status === -1) {
				$.msg.warning(response.message);
			}
		}, function() {$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");});
	});
	// 解锁用户
	$(document).on("click", "#batchUnlockUsers, .unlockCurrentUser", function() {
		var usernames = new Array();
		if ($(this).attr("id")) {
			$("input[type=checkbox].item:checked").each(function() {
				usernames.push($(this).data("username"));
			});
		} else {
			usernames.push($(this).data("username"));
		}
		if (usernames.length === 0) {
			$.msg.warning("需要选择至少一个用户才能执行此操作");
			return false;
		}
		$.postJson("user/unlock", {usernames:usernames}, false, function(response) {
			if (response.status === 1) {
				$.msg.success("操作成功");
			} else if (response.status === 0) {
				$.msg.info("操作失败");
			} else if (response.status === -1) {
				$.msg.warning(response.message);
			}
		}, function() {$.msg.error("请稍后重试或联系系统管理员解决", "系统异常");});
	});
	// 删除用户
	$(document).on("click", "#batchDeleteUsers, .deleteCurrentUser", function() {
		var userIds = new Array();
		if ($(this).attr("id")) {
			$("input[type=checkbox].item:checked").each(function() {
				userIds.push($(this).data("id"));
			});
		} else {
			userIds.push($(this).data("id"));
		}
		if (userIds.length === 0) {
			$.msg.warning("需要选择至少一个用户才能执行此操作");
			return false;
		}
		var index = layer.open({
			title: "删除用户",
			icon: 0,
			content: "确认要删除"+($(this).attr("id")?"所选中的":"该")+"用户吗?",
			btn: "确认",
			yes: function() {
				layer.close(index);
				$.postJson("user/delete", {userIds:userIds}, false, function(response) {
					if (response.status === 1) {
						$.msg.success("操作成功");
						var table = $('#table').DataTable();
						$("input[type=checkbox].item").each(function() {
							if (userIds.indexOf($(this).data("id")) !== -1) {
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
	// 重置密码
	$(document).on("click", ".resetPasswordForCurrentUser", function() {
		var element = $(this);
		var userId = $(this).data("id");
		var username = $(this).data("username");
		var content = '<form role="form" class="m0" id="resetPasswordForm">';
		content += '<div class="form-group m0"><label>[ '+username+' ] 的新密码</label><input type="text" id="newPassword" name="password" class="form-control" required></div>';
		content += '</form>';
		var index = layer.open({
			title: "重置密码",
			content: content,
			btn: "确认",
			yes: function() {
				if (!$("#resetPasswordForm").valid()) {
					return false;
				}
				var newPassword = $("#newPassword").val().trim();
				layer.close(index);
				$.postJson("user/resetPassword", {id:userId, password:newPassword}, false, function(response) {
					if (response.status === 1) {
						$.msg.success("操作成功");
						element.parents("tr").children().eq(5).text($.getDateTime());
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
	$(document).on("click", "#batchAuthorizeUsers, .authorizeCurrentUser", function() {
		var ids = new Array();
		var usernames = new Array();
		if ($(this).attr("id")) {
			$("input[type=checkbox].item:checked").each(function() {
				ids.push($(this).data("id"));
				usernames.push($(this).data("username"));
			});
		} else {
			ids.push($(this).data("id"));
			usernames.push($(this).data("username"));
		}
		if (ids.length === 0) {
			$.msg.warning("需要选择至少一个用户才能执行此操作");
			return false;
		}
		if (ids.length > 1) {
			$.msg.warning("多用户授权时不展示其原有的角色与资源权限");
		}
		var url = $.getUrlWithObject("sys-user-authorize", {ids:ids, usernames:usernames});
		var index = layer.open({
			type: 2,
			title: "用户授权",
			area: ['900px', '700px'],
			fixed: false, //不固定
			maxmin: true,
			content: url
		});
	});
});
</script>
</body>
</html>