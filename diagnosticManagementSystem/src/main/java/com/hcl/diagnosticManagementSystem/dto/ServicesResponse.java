package com.hcl.diagnosticManagementSystem.dto;

public class ServicesResponse {

	private Long id;
	private String service;
	
	public ServicesResponse() {
		// TODO Auto-generated constructor stub
	}

	public ServicesResponse(Long id, String service) {
		this.id = id;
		this.service = service;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
}
