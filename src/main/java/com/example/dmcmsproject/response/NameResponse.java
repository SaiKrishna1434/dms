package com.example.dmcmsproject.response;

import java.util.List;

public class NameResponse {

	private Long serviceId;
	private String service;
	private Long agentId;
	private String agentName;
	private String location;
	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
