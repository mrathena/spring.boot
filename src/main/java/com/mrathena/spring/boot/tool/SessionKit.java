package com.mrathena.spring.boot.tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mrathena.spring.boot.constant.Constants;
import com.mrathena.spring.boot.entity.SYSUser;

public class SessionKit {

	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	public static HttpSession getSession() {
		HttpServletRequest request = getRequest();
		return request.getSession();
	}

	public static void setSessionEntity(String key, Object entity) {
		HttpSession session = getSession();
		session.setAttribute(key, entity);
	}

	public static Object getSessionEntity(String key) {
		HttpSession session = getSession();
		return session.getAttribute(key);
	}
	
	public static void setCurrentLoginedSYSUser(SYSUser user) {
		setSessionEntity(Constants.currentLoginedSYSUser, user);
	}
	
	public static SYSUser getCurrentLoginedSYSUser() {
		return (SYSUser) getSessionEntity(Constants.currentLoginedSYSUser);
	}
	
	public static Long getUserIdOfCurrentSYSUser() {
		return getCurrentLoginedSYSUser().getId();
	}
	
	public static String getUsernameOfCurrentSYSUser() {
		return getCurrentLoginedSYSUser().getUsername();
	}

}