package com.mrathena.spring.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	
	// 类上的@RestController相当于 类上的@Controller+方法上的@ResponseBody, 所有方法直接返回response内容而不是页面
	// 可以直接把SpringBootDemoApolication类做Controller

	@RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
	public String index() {
		return "hello world from Spring Boot";
	}
	
}
