package com.hcl.diagnosticManagementSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.diagnosticManagementSystem.dto.CustomerRegistrationRequestDTO;
import com.hcl.diagnosticManagementSystem.entity.Customer;
import com.hcl.diagnosticManagementSystem.exception.DuplicateResourceException;
import com.hcl.diagnosticManagementSystem.service.CustomerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@Valid @RequestBody CustomerRegistrationRequestDTO reuqestDTO) {
		try {
			Customer registeredCustomer = customerService.registerCustomer(reuqestDTO);
			return new ResponseEntity<>("Registration successful for user ID: " + registeredCustomer.getUserId(), HttpStatus.CREATED);
		} catch (DuplicateResourceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
