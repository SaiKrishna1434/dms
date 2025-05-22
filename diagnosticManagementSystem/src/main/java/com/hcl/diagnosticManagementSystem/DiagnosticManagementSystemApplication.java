package com.hcl.diagnosticManagementSystem;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class DiagnosticManagementSystemApplication {
	
	private static final Logger log=LoggerFactory.getLogger(DiagnosticManagementSystemApplication.class);

	private static final Logger logger = LoggerFactory.getLogger(DiagnosticManagementSystemApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(DiagnosticManagementSystemApplication.class, args);
		log.info("Application Started");

	}
}


