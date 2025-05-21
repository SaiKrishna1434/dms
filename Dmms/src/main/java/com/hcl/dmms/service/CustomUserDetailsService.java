package com.hcl.dmms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hcl.dmms.entity.Admin;
import com.hcl.dmms.entity.Customer;
import com.hcl.dmms.repository.AdminRepository;
import com.hcl.dmms.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// First, check Admin
		Optional<Admin> adminOpt = adminRepository.findByAdminId(username);
		if (adminOpt.isPresent()) {
			Admin admin = adminOpt.get();
			return new User(admin.getAdminId(), admin.getPassword(),
					List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
		}

		// Then, check Member
		Optional<Customer> memberOpt = userRepository.findByUserId(username);
		System.out.println(">>>>>>>>>>>>>>"+memberOpt);
		if (memberOpt.isPresent()) {
			Customer member = memberOpt.get();
			System.out.println(">>>>>>>>>>>>>>"+member);
			return new User(member.getUserId(), member.getPassword(),
					List.of(new SimpleGrantedAuthority("ROLE_MEMBER")));
		}

		throw new UsernameNotFoundException("User not found: " + username);
	}
}