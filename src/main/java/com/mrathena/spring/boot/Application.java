package com.mrathena.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mrathena.spring.boot.tool.SpringBootKit;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public SpringBootKit getSpringBootKit() {
		return new SpringBootKit();
	}

}