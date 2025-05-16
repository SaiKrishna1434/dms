package com.hcl.diagnosticManagementSystem.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "health_checkup")
public class HealthCheckup {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "healthcheckup_customer",
			joinColumns = @JoinColumn(name = "healthcheckup_id"),
			inverseJoinColumns = @JoinColumn(name = "customer_id")
	)
	@NotNull(message = "Customers are required")
	private List<Customer> customers;
	
	@Column(name = "checkup_plan_name", nullable = false)
	@NotBlank(message = "Checkup Plan Name is required")
	private String checkupPlanName;		// Storing the name for simplicity
	
	
	@Column(nullable = false)
	@NotNull(message = "Application Date is required")
	private LocalDate applicationDate;

	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
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


