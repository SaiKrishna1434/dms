package com.hcl.dmms.controller;

import org.springframework.beans.factory.annotation.Autowired;

//controller/MemberController.java

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dmms.dto.MemberLoginResponse;
import com.hcl.dmms.dto.UserLoginRequest;
import com.hcl.dmms.entity.Customer;
import com.hcl.dmms.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class UserController {
	
	@Autowired
	private UserService memberService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody Customer request) {
		String message = memberService.registerMember(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}
	@PostMapping("/login")
	public ResponseEntity<MemberLoginResponse> login(@RequestBody UserLoginRequest request) {
	    String token = memberService.login(request);
	    return ResponseEntity.ok(new MemberLoginResponse(token));
	}
}
