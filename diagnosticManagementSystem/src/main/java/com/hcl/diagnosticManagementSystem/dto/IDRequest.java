package com.hcl.diagnosticManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;

public class IDRequest {

	@NotBlank(message = "User Id cannot be blank")
	private String userId;
	
	public IDRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
