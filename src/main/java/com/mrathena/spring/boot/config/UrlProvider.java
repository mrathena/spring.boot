package com.mrathena.spring.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@ControllerAdvice
public class UrlProvider {

	@Autowired
	private ResourceUrlProvider resourceUrlProvider;

	// 用于页面替换静态资源url(MD5)
	@ModelAttribute("URLProvider")
	public ResourceUrlProvider getURLProvider() {
		return this.resourceUrlProvider;
	}

}