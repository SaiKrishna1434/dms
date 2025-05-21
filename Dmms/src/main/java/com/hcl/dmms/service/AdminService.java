package com.hcl.dmms.service;

import com.hcl.dmms.dto.AdminRequest;
import com.hcl.dmms.dto.JwtResponse;

public interface AdminService {
	String register(AdminRequest reuest);
	JwtResponse login(AdminRequest request);
}
