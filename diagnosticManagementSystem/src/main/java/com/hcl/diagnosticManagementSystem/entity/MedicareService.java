package com.hcl.diagnosticManagementSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class MedicareService {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Medicare service name is required")
	@Size(max = 255)
	private String name;
	
	@NotBlank(message = "Medicare description is required")
	@Size(max = 500)
	private String description;
	
	@NotBlank(message = "Eligibility cannot be blank")
	@Size(max = 50, message = "Character limit exceeded from 25 characters")
	private String eligibility;
	
	@NotBlank(message = "Frequency cannot be blank")
	@Size(max = 20, message = "Character limit exceeded from 25 characters")
	private String frequency;
	
	@NotBlank(message = "Medicare service name is required")
	@Size(max = 100)
	private String location;
	
	@Positive(message = "Cost should be a positive value")
    private double cost;
	
	
	public MedicareService() {}
	
	public MedicareService(Long id,
			@NotBlank(message = "Medicare service name is required") @Size(max = 255) String name,
			@NotBlank(message = "Medicare description is required") @Size(max = 500) String description,
			@NotBlank(message = "Eligibility cannot be blank") @Size(max = 50, message = "Character limit exceeded from 25 characters") String eligibility,
			@NotBlank(message = "Frequency cannot be blank") @Size(max = 20, message = "Character limit exceeded from 25 characters") String frequency,
			@NotBlank(message = "Medicare service name is required") @Size(max = 100) String location,
			@Positive(message = "Cost should be a positive value") double cost) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.eligibility = eligibility;
		this.frequency = frequency;
		this.location = location;
		this.cost = cost;
	}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getEligibility() { return eligibility; }
	public void setEligibility(String eligibility) { this.eligibility = eligibility; }
	
	public String getFrequency() { return frequency; }
	public void setFrequency(String frequency) { this.frequency = frequency; }

	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }

	public double getCost() { return cost; }
	public void setCost(double cost) { this.cost = cost; }
}
