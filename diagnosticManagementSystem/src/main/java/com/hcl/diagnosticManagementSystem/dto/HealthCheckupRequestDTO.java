package com.hcl.diagnosticManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HealthCheckupRequestDTO {
	
	@NotBlank(message = "User ID is required to apply for a health checkup")
	private String user_id;
	
	@NotNull(message = "Health Checkup Plan ID is required")
	private Long healthCheckupPlanId;

	
	public HealthCheckupRequestDTO() {		// required for Jackson
	}

	// Getters and Setters
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Long getHealthCheckupPlanId() {
		return healthCheckupPlanId;
	}
	public void setHealthCheckupPlanId(Long healthCheckupPlanId) {
		this.healthCheckupPlanId = healthCheckupPlanId;
	}
}
