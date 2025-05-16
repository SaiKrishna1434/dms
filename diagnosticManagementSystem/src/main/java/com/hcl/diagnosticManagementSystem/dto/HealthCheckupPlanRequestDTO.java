package com.hcl.diagnosticManagementSystem.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HealthCheckupPlanRequestDTO {
	
	@NotBlank(message = "Health Checkup Plan name is required")
	private String name;
	
	@NotBlank(message =  "Health Checkup Description is required")
	private String description;
	
	@NotNull(message =  "Health Checkup Price is required")
	private BigDecimal price;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
