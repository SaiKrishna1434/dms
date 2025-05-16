package com.hcl.diagnosticManagementSystem.dto;

public class CustomerRegistrationResponseDTO {
	
	private Long id;
	private String userId;
	private String emailId;
	
	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
