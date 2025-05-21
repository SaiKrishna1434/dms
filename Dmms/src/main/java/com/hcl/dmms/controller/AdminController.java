package com.hcl.dmms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dmms.dto.AdminRequest;
import com.hcl.dmms.dto.JwtResponse;
import com.hcl.dmms.service.AdminService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody AdminRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(adminService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody AdminRequest request) {
		return ResponseEntity.ok(adminService.login(request));
	}

}
