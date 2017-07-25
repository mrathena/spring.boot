package com.mrathena.spring.boot.tool;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.mrathena.spring.boot.constant.Constants;
import com.mrathena.spring.boot.entity.SYSUser;

public class SessionKit {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static void setSessionEntity(String key, Object entity) {
		Session session = getSession();
		session.setAttribute(key, entity);
	}

	public static Object getSessionEntity(String key) {
		Session session = getSession();
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