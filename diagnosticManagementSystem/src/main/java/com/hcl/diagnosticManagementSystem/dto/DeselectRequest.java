package com.hcl.diagnosticManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;

public class DeselectRequest {

	@NotBlank(message = "User Id cannot be blank")
	private String userId;
	@NotBlank(message ="Service Id cannot be blank")
	private String serviceId;
	
	public DeselectRequest() {
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	
}
