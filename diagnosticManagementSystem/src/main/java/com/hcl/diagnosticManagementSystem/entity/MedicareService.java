package com.hcl.diagnosticManagementSystem.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
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
	
	@NotBlank(message = "Medicare category is required")
	@Size(max = 100)
	private String category;
	
	@NotBlank(message = "Medicare service name is required")
	@Size(max = 100)
	private String location;
	
	private double price;
	
	// @JsonBackReference is crucial here to break the infinite loop
	@JsonBackReference
	@ManyToMany(mappedBy = "medicareService")
	private List<HealthCheckup> healthCheckups;
	
	// Constructors
	public MedicareService() {}
	public MedicareService(Long id,
			@NotBlank(message = "Medicare service name is required") @Size(max = 255) String name,
			@NotBlank(message = "Medicare descriptio is required") @Size(max = 500) String description,
			@NotBlank(message = "Medicare category is required") @Size(max = 100) String category,
			@NotBlank(message = "Medicare service name is required") @Size(max = 100) String location, 
			double price,
			List<HealthCheckup> healthCheckups) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.location = location;
		this.price = price;
		this.healthCheckups = healthCheckups;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }

	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }

	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }

	public List<HealthCheckup> getHealthCheckups() { return healthCheckups; }
	public void setHealthCheckups(List<HealthCheckup> healthCheckups) { this.healthCheckups = healthCheckups; }
}
