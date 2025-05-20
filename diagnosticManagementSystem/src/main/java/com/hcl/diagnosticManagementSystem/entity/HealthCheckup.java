package com.hcl.diagnosticManagementSystem.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "health_checkup")
public class HealthCheckup {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name cannot be blank")
	@Size(max = 255, message = "Name cannot exceed 255 characters")
	private String name;
	
	@Size(max = 1000, message = "Description cannot exceed 1000 characters")
	private String description;
	
	@Positive(message = "Price must be a positive value")
    private double price;
	
//	@JsonManagedReference("healthCheckup-medicareService")
	@ManyToMany
	@JoinTable(
			name = "health_checkup_medicare_service",
			joinColumns = @JoinColumn(name = "health_checkup_id"),
			inverseJoinColumns = @JoinColumn(name = "medicare_service_id")
	)
	private List<MedicareService> medicareService;

	@JsonIgnore 
	@OneToOne(mappedBy = "healthCheckup", cascade = CascadeType.ALL )
    private CustomerHealthCheckupApplication customerHealthCheckupApplication;

	public HealthCheckup() {}
	public HealthCheckup(Long id, String name, String description, double price,
			List<MedicareService> medicareService, CustomerHealthCheckupApplication customerHealthCheckupApplication) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.medicareService = medicareService;
		this.customerHealthCheckupApplication = customerHealthCheckupApplication;
	}

	// Getters and Setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public double getPrice() {	return price; }
	public void setPrice(double price) { this.price = price; }
	public List<MedicareService> getMedicareService() { return medicareService; }
	public void setMedicareService(List<MedicareService> medicareService) { this.medicareService = medicareService; }
	public CustomerHealthCheckupApplication getCustomerHealthCheckupApplication() { return customerHealthCheckupApplication; }
	public void setCustomerHealthCheckupApplication(CustomerHealthCheckupApplication customerHealthCheckupApplication) { this.customerHealthCheckupApplication = customerHealthCheckupApplication; }
}


