package com.example.dmcmsproject.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public class SelectServiceRequest {

	@NotBlank(message="User Id cannot be blank")
	private String userId;
	@NotBlank(message = "Agent Id cannot be blank")
	private String agentId;
	
	@NotEmpty(message = " Customer should have enter one services")
	private List<String> serviceNames;
	
	public SelectServiceRequest() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public List<String> getServiceNames() {
		return serviceNames;
	}

	public void setServiceNames(List<String> serviceNames) {
		this.serviceNames = serviceNames;
	}
}
