package com.hcl.diagnosticManagementSystem.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.diagnosticManagementSystem.dao.CustomerRepository;
import com.hcl.diagnosticManagementSystem.dao.RoleRepository;
import com.hcl.diagnosticManagementSystem.dto.CustomerRegistrationRequestDTO;
import com.hcl.diagnosticManagementSystem.dto.CustomerRegistrationResponseDTO;
import com.hcl.diagnosticManagementSystem.entity.Customer;
import com.hcl.diagnosticManagementSystem.entity.Role;
import com.hcl.diagnosticManagementSystem.exception.UserAlreadyExistsException;



@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	// Registering customer
	@Transactional
	public CustomerRegistrationResponseDTO registerCustomer(CustomerRegistrationRequestDTO requestDTO) {
		
		// Check if the user ID is already exists
		Optional<Customer> existingCustomer = customerRepository.findByUserId(requestDTO.getUserId());
		if(existingCustomer.isPresent()) {
			throw new UserAlreadyExistsException("User ID already exists");
		}
		
		// Create a new customer object
		Customer customer = new Customer();
		customer.setUserId(requestDTO.getUserId());
		customer.setPassword(passwordEncoder.encode(requestDTO.getPassword()));		// With encoded password
		customer.setDateOfBirth(requestDTO.getDateOfBirth());
		customer.setEmailId(requestDTO.getEmailId());
		
		// Assign default role 
		Role defaultRole = roleRepository.findByName("ROLE_CUSTOMER").orElseGet(()-> {
			Role newRole = new Role();
			newRole.setName("ROLE_CUSTOMER");
			return roleRepository.save(newRole);
		});
		customer.setRoles(Collections.singletonList(defaultRole));
		
		//save customer to database
		Customer savedCustomer = customerRepository.save(customer);
		
		
		// Convert the saved customer to a DTO
		CustomerRegistrationResponseDTO responseDTO = new CustomerRegistrationResponseDTO();
		responseDTO.setId(savedCustomer.getId());
		responseDTO.setUserId(savedCustomer.getUserId());
		responseDTO.setEmailId(savedCustomer.getEmailId());
		
		return responseDTO;
	}
	
	
	public Customer getCustomerByUserId(String userId) {
		return customerRepository.findByUserId(userId).orElse(null);
	}
}
