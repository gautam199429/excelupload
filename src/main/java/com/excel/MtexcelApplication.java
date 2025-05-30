package com.excel.upload;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@OpenAPIDefinition
@EnableAsync
@EnableTransactionManagement
public class MtexcelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtexcelApplication.class, args);
	}

}
