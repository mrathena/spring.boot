package com.mrathena.spring.boot.controller.sys;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrathena.spring.boot.entity.SYSResource;
import com.mrathena.spring.boot.entity.SYSUser;
import com.mrathena.spring.boot.service.SYSUserService;
import com.mrathena.spring.boot.tool.SessionKit;
import com.mrathena.toolkit.data.Json;

@Controller
@RequestMapping("user")
public class UserController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SYSUserService userService;
	@Autowired
	private CacheManager cacheManager;

	@ResponseBody
	@RequestMapping("{id}")
	public Object getUser(@PathVariable Long id) {
		Json json = null;
		try {
			SYSUser user = userService.getUserByUserId(id);
			json = Json.success().put("user", user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("all")
	public Object getAllUsers() {
		Json json = null;
		try {
			List<SYSUser> users = userService.getAllUsers();
			json = Json.success().put("users", users);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("resource")
	public Object getResourcesByUsername() {
		Json json = null;
		try {
			String username = SessionKit.getUsernameOfCurrentSYSUser();
			List<SYSResource> resources = userService.getResourcesByUsername(username);
			json = Json.success().put("resources", resources);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("insert")
	public Object addUser(SYSUser user) {
		Json json = null;
		try {
			boolean flag = userService.insertUser(user);
			json = flag ? Json.success().put("user", user) : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("disable")
	public Object lockUser(@RequestParam("userIds[]") Long... userIds) {
		Json json = null;
		try {
			boolean flag = userService.disableUsersByUserIds(userIds);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("enable")
	public Object unlockUser(@RequestParam("userIds[]") Long... userIds) {
		Json json = null;
		try {
			boolean flag = userService.enableUsersByUserIds(userIds);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("resetPassword")
	public Object resetPassword(SYSUser user) {
		Json json = null;
		try {
			boolean flag = userService.updatePasswordByUserId(user.getId(), user.getPassword());
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("delete")
	public Object deleteUser(@RequestParam("userIds[]") Long... userIds) {
		Json json = null;
		try {
			boolean flag = userService.deleteUsersByUserIds(userIds);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("authorize")
	public Object authorizeUsers(@RequestParam("userIds[]") Long[] userIds, String roleIds, String includeIds, String excludeIds) {
		Json json = null;
		try {
			boolean flag = userService.authorizeUsersByUserIds(userIds, roleIds, includeIds, excludeIds);
			json = flag ? Json.success() : Json.failure();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json = Json.error(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("unlock")
	public Object unlock(@RequestParam("usernames[]") String[] usernames) {
		Cache<String, AtomicInteger> passwordRetryCache = cacheManager.getCache("passwordRetryCache");
		for (String username : usernames) {
			passwordRetryCache.remove(username);
		}
		return Json.success();
	}

}