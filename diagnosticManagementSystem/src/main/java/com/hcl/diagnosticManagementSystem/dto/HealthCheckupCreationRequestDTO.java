package com.hcl.diagnosticManagementSystem.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HealthCheckupCreationRequestDTO {
	
	@NotBlank(message = "Health checkup plan name is required")
	private String name;
	@NotBlank(message = "Health checkup description is required")
	private String description;
	@NotNull(message = "Health checkup price is required")
	private double price;
	private List<Long> medicareServiceIds;
	
	public HealthCheckupCreationRequestDTO() {}
	public HealthCheckupCreationRequestDTO(String name, String description, double price, List<Long> medicareServiceIds) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.medicareServiceIds = medicareServiceIds;
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getDescription() { return description;}
	public void setDescription(String description) { this.description = description; }
	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }
	public List<Long> getMedicareServiceIds() { return medicareServiceIds; }
	public void setMedicareServiceIds(List<Long> medicareServiceIds) { this.medicareServiceIds = medicareServiceIds; }
}
