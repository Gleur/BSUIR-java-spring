package com.example.mathservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "repos")
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MathserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MathserviceApplication.class, args);
	}

}
