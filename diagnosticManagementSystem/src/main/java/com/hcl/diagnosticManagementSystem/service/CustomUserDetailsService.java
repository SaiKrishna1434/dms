package com.hcl.diagnosticManagementSystem.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hcl.diagnosticManagementSystem.entity.Customer;
import com.hcl.diagnosticManagementSystem.entity.Role;
import com.hcl.diagnosticManagementSystem.repository.CustomerRepository;

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
		
		Role customerRole = customer.getRole();
		if(customerRole == null || customerRole.getName() == null) {
			throw new UsernameNotFoundException("User " + userId + " has no assigned role.");
		}
		
		List<SimpleGrantedAuthority> authorities =  Collections.singletonList(
				new SimpleGrantedAuthority(customerRole.getName().name())
		);
		return new User(customer.getUserId(), customer.getPassword(), authorities);
	}
	
}
