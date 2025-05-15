package com.example.dmcmsproject.dto;

import java.util.List;

public class SelectServiceRequest {

	private String userId;
	private String agentId;
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
