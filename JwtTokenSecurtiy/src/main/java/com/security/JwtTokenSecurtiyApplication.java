package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.security,com.security.util")
public class JwtTokenSecurtiyApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtTokenSecurtiyApplication.class, args);
		System.out.println("system started");
	}

}
