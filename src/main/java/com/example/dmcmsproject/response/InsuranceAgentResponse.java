package com.example.dmcmsproject.response;

import java.util.List;

import com.example.dmcmsproject.model.Services;

public class InsuranceAgentResponse {

	private Long id;
	private String agentName;
	private String location;
	private List<ServicesResponse> serviceList;
	
	public InsuranceAgentResponse() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<ServicesResponse> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ServicesResponse> serviceList) {
		this.serviceList = serviceList;
	}
}
