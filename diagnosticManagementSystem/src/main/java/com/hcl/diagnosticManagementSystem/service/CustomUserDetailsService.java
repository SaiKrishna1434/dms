package com.hcl.diagnosticManagementSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hcl.diagnosticManagementSystem.dao.CustomerRepository;
import com.hcl.diagnosticManagementSystem.entity.Customer;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final CustomerRepository customerRepository;
	
	public CustomUserDetailsService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByUserId(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with user ID: " + userId));
		
		List<String> roles = customer.getRoles().stream()
					.map(role -> role.getName().name())
					.toList();
		return new User(customer.getUserId(), customer.getPassword(), new ArrayList<>());
	}
	
}
