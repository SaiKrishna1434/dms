package com.hcl.diagnosticManagementSystem.service;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.diagnosticManagementSystem.dao.CustomerRepository;
import com.hcl.diagnosticManagementSystem.dao.RoleRepository;
import com.hcl.diagnosticManagementSystem.dto.CustomerRegistrationRequestDTO;
import com.hcl.diagnosticManagementSystem.entity.Customer;
import com.hcl.diagnosticManagementSystem.entity.Role;
import com.hcl.diagnosticManagementSystem.exception.DuplicateResourceException;


@Service
public class CustomerService {	
	
	private final CustomerRepository customerRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public CustomerService(CustomerRepository customerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.customerRepository = customerRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public Customer registerCustomer(CustomerRegistrationRequestDTO requestDTO) {
			
			Optional<Customer> existingCustomerByUserId = customerRepository.findByUserId(requestDTO.getUserId());
			if(existingCustomerByUserId.isPresent()) {
				throw new DuplicateResourceException("User ID already exists");
			}
			
			Optional<Customer> existingCustomerByEmailId = customerRepository.findByEmailId(requestDTO.getEmailId());
			if(existingCustomerByEmailId.isPresent()) {
				throw new DuplicateResourceException("Email ID already exists");
			}
			
			Customer customer = new Customer();
			customer.setUserId(requestDTO.getUserId());
			customer.setPassword(passwordEncoder.encode(requestDTO.getPassword()));		// With encoded password
			customer.setDateOfBirth(requestDTO.getDateOfBirth());
			customer.setEmailId(requestDTO.getEmailId());
			
			// Assign default role 
			Role customerRole = roleRepository.findByName(Role.RoleName.ROLE_CUSTOMER)
				.orElseGet(()-> {
					Role newRole = new Role(Role.RoleName.ROLE_CUSTOMER);
					return roleRepository.save(newRole);
				});
			// customer.setRoles(List.of(customerRole));  // -> when customer-role included
			
			//save customer to database
			Customer savedCustomer = customerRepository.save(customer);
			return savedCustomer;
	}
	
	public Optional<Customer> getCustomerByUserId(String userId) {
		return customerRepository.findByUserId(userId);
	}
	
	public Customer getCurrentCustomer() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return getCustomerByUserId(userId).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
