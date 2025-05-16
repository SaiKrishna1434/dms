package com.hcl.diagnosticManagementSystem.dto;

import java.time.LocalDate;

public class HealthCheckupResponseDTO {
	
	private Long id;
	private Long customerId;
	private String checkupPlanName;
	private LocalDate applicationDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCheckupPlanName() {
		return checkupPlanName;
	}
	public void setCheckupPlanName(String checkupPlanName) {
		this.checkupPlanName = checkupPlanName;
	}
	public LocalDate getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(LocalDate applicationDate) {
		this.applicationDate = applicationDate;
	}	
}
