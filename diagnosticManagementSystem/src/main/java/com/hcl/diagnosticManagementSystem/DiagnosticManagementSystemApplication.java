package com.hcl.diagnosticManagementSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiagnosticManagementSystemApplication {
	
	private static final Logger log=LoggerFactory.getLogger(DiagnosticManagementSystemApplication.class);

	private static final Logger logger = LoggerFactory.getLogger(DiagnosticManagementSystemApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(DiagnosticManagementSystemApplication.class, args);
<<<<<<< HEAD
		logger.info("server started successfully");
=======
		log.info("Application Started");
>>>>>>> 3db9b5a36d976dfa8e568db99248ac3875de5fa5
	}
}


