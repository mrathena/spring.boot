package com.mrathena.spring.boot.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBootKit implements ApplicationContextAware {

	Logger log = LoggerFactory.getLogger(getClass());
	
	// 非@import显式注入，@Component是必须的，且该类必须与main同包或子包
	// 若非同包或子包，则需手动import 注入，有没有@Component都一样
	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringBootKit.applicationContext == null) {
			SpringBootKit.applicationContext = applicationContext;
		}
		log.info("Spring Boot ApplicationContext has been set into tool SpringBootKit");
	}

	// 获取applicationContext
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	// 通过name获取 Bean.
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);

	}

	// 通过class获取Bean.
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	// 通过name,以及Clazz返回指定的Bean
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

}