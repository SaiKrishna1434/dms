package com.hcl.diagnosticManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.hcl.diagnosticManagementSystem")
@EntityScan (basePackages = "com.hcl.diagnosticManagementSystem.entity")
@EnableJpaRepositories(basePackages = "com.hcl.diagnosticManagementSystem.dao")
public class DiagnosticManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiagnosticManagementSystemApplication.class, args);
		System.out.println("server started");
	}

}
