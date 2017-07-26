package com.mrathena.spring.boot.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mrathena.spring.boot.shiro.RetryLimitHashedCredentialsMatcher;
import com.mrathena.spring.boot.shiro.SYSUserRealm;

@Configuration
public class Shiro {
	
	@Bean("CacheManager")
	public EhCacheManager getCacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return cacheManager;
	}
	
	@Bean("SYSUserRealm")
	public SYSUserRealm getSYSUserRealm(@Qualifier("CacheManager") CacheManager cacheManager) {
		SYSUserRealm realm = new SYSUserRealm();
		realm.setCachingEnabled(false);
		RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		realm.setCredentialsMatcher(credentialsMatcher);
		return realm;
	}

	@Bean("SecurityManager")
	public DefaultWebSecurityManager getSecurityManager(@Qualifier("SYSUserRealm") SYSUserRealm realm, @Qualifier("CacheManager") CacheManager cacheManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		securityManager.setCacheManager(cacheManager);
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(3600000);// 1小时:3600000, 10分钟:600000, 1分钟:60000
		securityManager.setSessionManager(sessionManager);
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		rememberMeManager.setCipherKey("#{T(org.apache.shiro.codec.Base64).decode('4AvVhmFLUs0KTA3Kprsdag==')}".getBytes());
		rememberMeManager.getCookie().setMaxAge(86400);// 30天:2592000, 7天:604800, 1天:86400
		securityManager.setRememberMeManager(rememberMeManager);
		return securityManager;
	}

	@Bean("ShiroFilter")
	public ShiroFilterFactoryBean getShiroFilter(@Qualifier("SecurityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		bean.setLoginUrl("/login");
		bean.setSuccessUrl("/index");
		bean.setUnauthorizedUrl("/unauthorize");
		Map<String, Filter> filters = new HashMap<>();
		filters.put("authc", new FormAuthenticationFilter());
		bean.setFilters(filters);
		Map<String, String> FilterChainDefinitions = new LinkedHashMap<>();
		FilterChainDefinitions.put("/assets/**", "anon");
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

	@Bean("LifecycleBeanPostProcessor")
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
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("SecurityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }
    
}