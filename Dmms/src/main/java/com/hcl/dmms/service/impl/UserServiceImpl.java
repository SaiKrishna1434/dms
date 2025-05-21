package com.hcl.dmms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcl.dmms.dto.UserLoginRequest;
import com.hcl.dmms.entity.Customer;
import com.hcl.dmms.exeption.ResourceAlreadyExistsException;
import com.hcl.dmms.exeption.ResourceNotFoundException;
import com.hcl.dmms.repository.UserRepository;
import com.hcl.dmms.security.JwtUtil;
import com.hcl.dmms.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private JwtUtil jwtUtil;



	@Override
	public String registerMember(Customer request) {
		if (userRepository.findByUserId(request.getUserId()).isPresent()) {
			throw new ResourceAlreadyExistsException("Member ID already registered");
		}

		Customer user = new Customer();
		user.setUserId(request.getUserId());
		user.setPassword(encoder.encode(request.getPassword()));// hash later
		user.setDateOfBirth(request.getDateOfBirth());
		user.setContactNumber(request.getContactNumber());
		user.setEmailId(request.getEmailId());

		userRepository.save(user);
		return "Your details are submitted successfully";
	}

	@Override
	public String login(UserLoginRequest request) {
		Customer user = userRepository.findByUserId(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (!encoder.matches(request.getPassword(), user.getPassword())) {
			System.out.println(request.getPassword());
			System.out.println(user.getPassword());
			throw new RuntimeException("Invalid credentials");
		}

		return jwtUtil.generateToken(user.getUserId(),"ROLE_USER");
	}

}