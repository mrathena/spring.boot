package com.mrathena.spring.boot.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mrathena.spring.boot.shiro.RetryLimitHashedCredentialsMatcher;
import com.mrathena.spring.boot.shiro.SYSUserRealm;

@Configuration
public class Shiro {

	@Bean
	public EhCacheManager getCacheManager() {
		EhCacheManager manager = new EhCacheManager();
		manager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return manager;
	}

	@Bean
	public SYSUserRealm getRealm() {
		SYSUserRealm realm = new SYSUserRealm();
		RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(getCacheManager());
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		realm.setCredentialsMatcher(credentialsMatcher);
		realm.setCachingEnabled(true);
		realm.setAuthenticationCachingEnabled(true);
		realm.setAuthenticationCacheName("authenticationCache");
		realm.setAuthorizationCachingEnabled(true);
		realm.setAuthorizationCacheName("authorizationCache");
		return realm;
	}

	@Bean("sessionManager")
	public DefaultWebSessionManager getSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(3600000);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler();
		sessionValidationScheduler.setSessionValidationInterval(600000);
		sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
		sessionManager.setSessionIdCookieEnabled(true);
		SimpleCookie sessionIdCookie = new SimpleCookie("sid");
		sessionIdCookie.setHttpOnly(true);
		sessionIdCookie.setMaxAge(-1);
		sessionManager.setSessionIdCookie(sessionIdCookie);
		EnterpriseCacheSessionDAO sessionDao = new EnterpriseCacheSessionDAO();
		sessionDao.setActiveSessionsCacheName("shiro-activeSessionCache");
		sessionDao.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
		sessionManager.setSessionDAO(sessionDao);
		return sessionManager;
	}

	@Bean("rememberMeManager")
	public CookieRememberMeManager getRememberManager() {
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		rememberMeManager.setCipherKey("#{T(org.apache.shiro.codec.Base64).decode('4AvVhmFLUs0KTA3Kprsdag==')}".getBytes());
		SimpleCookie rememberMeCookie = new SimpleCookie("rememberMe");
		rememberMeCookie.setHttpOnly(true);
		rememberMeCookie.setMaxAge(86400);
		rememberMeManager.setCookie(rememberMeCookie);
		return rememberMeManager;
	}

	@Bean("securityManager")
	public DefaultWebSecurityManager getSecurityManager(SYSUserRealm realm, CacheManager cacheManager, SessionManager sessionManager, RememberMeManager rememberMeManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		securityManager.setCacheManager(cacheManager);
		securityManager.setSessionManager(sessionManager);
		securityManager.setRememberMeManager(rememberMeManager);
		return securityManager;
	}

	@Bean
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean(SecurityManager securityManager) {
		MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
		bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		bean.setArguments(new Object[] {securityManager});
		return bean;
	}

	@Bean
	public FormAuthenticationFilter getFormAuthenticationFilter() {
		FormAuthenticationFilter filter = new FormAuthenticationFilter();
		filter.setUsernameParam("username");
		filter.setPasswordParam("password");
		filter.setLoginUrl("/login");
		filter.setSuccessUrl("/index");
		filter.setRememberMeParam("rememberMe");
		return filter;
	}

	@Bean("shiro-filter")
	public ShiroFilterFactoryBean getShiroFilter(SecurityManager securityManager, FormAuthenticationFilter formAuthenticationFilter) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		bean.setLoginUrl("/login");
		bean.setSuccessUrl("/index");
		bean.setUnauthorizedUrl("/unauthorize");
		Map<String, Filter> filters = new HashMap<>();
		filters.put("authc", formAuthenticationFilter);
		bean.setFilters(filters);
		Map<String, String> FilterChainDefinitions = new LinkedHashMap<>();
		FilterChainDefinitions.put("/assets", "anon");
		FilterChainDefinitions.put("/404.html", "anon");
		FilterChainDefinitions.put("/500.html", "anon");
		FilterChainDefinitions.put("/login", "anon");
		FilterChainDefinitions.put("/register", "anon");
		FilterChainDefinitions.put("/unauthorize", "anon");
		FilterChainDefinitions.put("/logout", "logout");
		FilterChainDefinitions.put("/**", "user");
		bean.setFilterChainDefinitionMap(FilterChainDefinitions);
		return bean;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }
    
}