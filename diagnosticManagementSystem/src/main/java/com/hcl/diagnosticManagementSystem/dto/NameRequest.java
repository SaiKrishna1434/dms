package com.hcl.diagnosticManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;

public class NameRequest {

	@NotBlank(message = "Agent name cannot be blank")
	private String name;
	
	public NameRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
