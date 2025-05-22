package com.hcl.diagnosticManagementSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiagnosticManagementSystemApplication {
	
	private static final Logger log=LoggerFactory.getLogger(DiagnosticManagementSystemApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DiagnosticManagementSystemApplication.class, args);
		log.info("Application Started");
	}
}


