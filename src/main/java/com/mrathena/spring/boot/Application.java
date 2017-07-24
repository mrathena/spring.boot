package com.mrathena.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	// thymeleaf-extras-data-attribute, 给Thymeleaf添加Dialect(DataAttributeDialect)
	// data:foo="${'bar'}"会转换成data-foo="bar", 否则需要th:attr="data-foo=${'bar'}"
//	@Bean
//	public DataAttributeDialect getDataAttributeDialect() {
//		return new DataAttributeDialect();
//	}

}