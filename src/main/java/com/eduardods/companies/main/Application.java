package com.eduardods.companies.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.eduardods.companies")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
