package com.mrathena.spring.boot.controller.sys;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrathena.spring.boot.entity.SYSRole;
import com.mrathena.spring.boot.service.SYSRoleService;
import com.mrathena.toolkit.data.Json;

@Controller
@RequestMapping("role")
public class RoleController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SYSRoleService roleService;
	
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public Object getRole(@PathVariable Long id) {
		Json json = null;
		try {
			SYSRole role = roleService.getRoleByRoleId(id);
			json = Json.success().put("role", role);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "all", method = RequestMethod.POST)
	public Object getAllRoles() {
		Json json = null;
		try {
			List<SYSRole> roles = roleService.getAllRoles();
			json = Json.success().put("roles", roles);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "avaliable", method = RequestMethod.POST)
	public Object getAllAvaliableRoles() {
		Json json = null;
		try {
			List<SYSRole> roles = roleService.getAvaliableRoles();
			json = Json.success().put("roles", roles);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public Object addRole(SYSRole role) {
		Json json = null;
		try {
			boolean flag = roleService.insertRole(role);
			json = flag ? Json.success().put("role", role) : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "disable", method = RequestMethod.POST)
	public Object lockRole(@RequestParam("roleIds[]") Long... roleIds) {
		Json json = null;
		try {
			boolean flag = roleService.disableRolesByRoleIds(roleIds);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "enable", method = RequestMethod.POST)
	public Object unlockRole(@RequestParam("roleIds[]") Long... roleIds) {
		Json json = null;
		try {
			boolean flag = roleService.enableRolesByRoleIds(roleIds);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Object deleteRole(@RequestParam("roleIds[]") Long... roleIds) {
		Json json = null;
		try {
			boolean flag = roleService.deleteRolesByRoleIds(roleIds);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Object updateRole(SYSRole role) {
		Json json = null;
		try {
			boolean flag = roleService.updateRoleByRoleId(role);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}

}