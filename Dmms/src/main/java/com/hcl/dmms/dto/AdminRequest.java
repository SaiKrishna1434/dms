package com.hcl.dmms.dto;

import jakarta.validation.constraints.NotBlank;

public class AdminRequest {
	@NotBlank
	private String adminId;
	
	@NotBlank
	private String password;

	public AdminRequest(@NotBlank String adminId, @NotBlank String password) {
		super();
		this.adminId = adminId;
		this.password = password;
	}

	public AdminRequest() {
		super();
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
