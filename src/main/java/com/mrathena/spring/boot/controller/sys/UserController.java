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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrathena.spring.boot.entity.SYSResource;
import com.mrathena.spring.boot.entity.SYSUser;
import com.mrathena.spring.boot.service.SYSUserService;
import com.mrathena.spring.boot.tool.SessionKit;
import com.mrathena.toolkit.Json;

@Controller
@RequestMapping("user")
public class UserController {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SYSUserService userService;
	@Autowired
	private CacheManager cacheManager;

	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
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
	@RequestMapping(value = "all", method = RequestMethod.POST)
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
	@RequestMapping(value = "resource", method = RequestMethod.POST)
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
	@RequestMapping(value = "insert", method = RequestMethod.POST)
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
	@RequestMapping(value = "disable", method = RequestMethod.POST)
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
	@RequestMapping(value = "enable", method = RequestMethod.POST)
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
	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
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
	@RequestMapping(value = "delete", method = RequestMethod.POST)
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
	@RequestMapping(value = "authorize", method = RequestMethod.POST)
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
	@RequestMapping(value = "unlock", method = RequestMethod.POST)
	public Object unlock(@RequestParam("usernames[]") String[] usernames) {
		Cache<String, AtomicInteger> passwordRetryCache = cacheManager.getCache("passwordRetryCache");
		for (String username : usernames) {
			passwordRetryCache.remove(username);
		}
		return Json.success();
	}

}