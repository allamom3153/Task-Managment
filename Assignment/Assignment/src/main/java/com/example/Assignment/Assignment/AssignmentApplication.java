package com.example.Assignment.Assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "com.example.Assignment.Assignment.repository")
public class AssignmentApplication {

	public static void main(String[] args) {

		SpringApplication.run(AssignmentApplication.class, args);
		System.out.println("Hello");
	}

}
