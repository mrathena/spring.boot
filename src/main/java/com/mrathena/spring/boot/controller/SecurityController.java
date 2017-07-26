package com.mrathena.spring.boot.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mrathena.spring.boot.entity.SYSUser;
import com.mrathena.spring.boot.service.SYSUserService;
import com.mrathena.spring.boot.tool.SessionKit;

@Controller
public class SecurityController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SYSUserService userService;
	@Autowired
	private CacheManager cacheManager;

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "login.jsp";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(SYSUser user, Boolean rememberMe, Model model) {
		Subject subject = SecurityUtils.getSubject();
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), rememberMe != null);
			subject.login(token);
			// 能执行到这一步, 说明没有报错, 把当前登录用户保存到session中
			SYSUser currentUser = userService.getUserByUsername(user.getUsername());
			SessionKit.setCurrentLoginedSYSUser(currentUser);
		} catch (UnknownAccountException e) {
			// 账号不存在, SYSUserRealm.java
			model.addAttribute("error", "账号与密码组合不正确");
			return "login.jsp";
		} catch (IncorrectCredentialsException e) {
			// 密码不正确. SYSUserRealm.java
			String message = "账号与密码组合不正确";
			Cache<String, AtomicInteger> passwordRetryCache = cacheManager.getCache("passwordRetryCache");
			AtomicInteger count = passwordRetryCache.get(user.getUsername());
			if (count != null) {
				if (count.get() == 5) {
					message += "<br />由于您累计输入错误的账号与密码组合已达 5 次, 系统已锁定您的账号, 请于60分钟后再尝试登录";
				} else {
					message += "<br />您还可以尝试 " + (5 - count.get()) + " 次";
				}
			}
			model.addAttribute("error", message);
			return "login.jsp";
		} catch (ExcessiveAttemptsException e) {
			// 登录失败次数过多, RetryLimitHashedCredentialsMatcher.java
			model.addAttribute("error", "由于您累计输入错误的账号与密码组合已达 5 次, 系统已锁定您的账号, 请于60分钟后再尝试登录 <br />注意:锁定期间您的每一次登录尝试都会将锁定等待时间重置为60分钟.");
			return "login.jsp";
		} catch (LockedAccountException e) {
			// 账号已被禁用, SYSUserRealm.java
			model.addAttribute("error", "账号已被禁用, 不允许登录");
			return "login.jsp";
		} catch (ExpiredCredentialsException e) {
			model.addAttribute("error", "账号已过期, 不允许登录");
			return "login.jsp";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			model.addAttribute("error", "系统异常");
			return "login.jsp";
		}
		return "redirect:index";
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String artifect() {
		return "layout/artifact.jsp";
	}
	
	@RequestMapping(value = "unauthorize", method = RequestMethod.GET)
	public String unauthorize() {
		return "unauthorize.jsp";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		return "redirect:login";
	}

}