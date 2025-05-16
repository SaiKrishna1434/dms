package com.hcl.diagnosticManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.diagnosticManagementSystem.dto.CustomerRegistrationRequestDTO;
import com.hcl.diagnosticManagementSystem.dto.CustomerRegistrationResponseDTO;
import com.hcl.diagnosticManagementSystem.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/register")
	public ResponseEntity<CustomerRegistrationResponseDTO> registerCustomer(@Valid @RequestBody CustomerRegistrationRequestDTO reuqestDTO) {
		CustomerRegistrationResponseDTO responseDTO = customerService.registerCustomer(reuqestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}
	
}
