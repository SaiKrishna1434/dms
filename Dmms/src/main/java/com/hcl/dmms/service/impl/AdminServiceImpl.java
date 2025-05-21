package com.hcl.dmms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcl.dmms.dto.AdminRequest;
import com.hcl.dmms.dto.JwtResponse;
import com.hcl.dmms.entity.Admin;
import com.hcl.dmms.exeption.ResourceAlreadyExistsException;
import com.hcl.dmms.exeption.ResourceNotFoundException;
import com.hcl.dmms.repository.AdminRepository;
import com.hcl.dmms.security.JwtUtil;
import com.hcl.dmms.service.AdminService;

@Service

public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


	@Override
	public String register(AdminRequest reuest) {
		if (adminRepository.findByAdminId(reuest.getAdminId()).isPresent()) {
			throw new ResourceAlreadyExistsException("Admin already registered");
		}

		Admin admin = new Admin();
		admin.setAdminId(reuest.getAdminId());
		admin.setPassword(encoder.encode(reuest.getPassword()));;

		adminRepository.save(admin);
		return "Admin registered successfully";
	}

	@Override
	public JwtResponse login(AdminRequest request) {
		Admin admin = adminRepository.findByAdminId(request.getAdminId())
				.orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

		if (!encoder.matches(request.getPassword(), admin.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		String token = jwtUtil.generateToken(admin.getAdminId(), "ROLE_ADMIN");

		return new JwtResponse(token);
	}
	
	


}