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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table (name = "customer")
public class Customer {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	@NotBlank(message = "USER ID is required")
	private String userId;
	
	@Column(nullable = false)
	@NotBlank(message = "Password is required")
	private String password;
	
	@Column(nullable = false)
	@NotNull(message = "Date of Birth is required")
	private LocalDate dateOfBirth;
	
	
	@Column(nullable = false)
    @NotBlank(message = "Email ID is required")
    @Email(message = "Invalid email format")
    private String emailId;

	
	@ManyToMany(fetch = FetchType.EAGER)		// defines that data must be eagerly fetchted
	@JoinTable(
			name = "customer_role",
			joinColumns = @JoinColumn(name = "customer_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles;
	
	@ManyToMany (mappedBy = "customers", cascade = CascadeType.ALL)		// Added this to map back to HealthCheckup
	private List<HealthCheckup> healthCheckups;

	// Non-parameterized constructor
	public Customer() {
	}
	
	public Customer(Long id, @NotBlank(message = "USER ID is required") String userId,
			@NotBlank(message = "Password is required") String password,
			@NotNull(message = "Date of Birth is required") LocalDate dateOfBirth,
			@NotBlank(message = "Email ID is required") @Email(message = "Invalid email format") String emailId,
			List<Role> roles, List<HealthCheckup> healthCheckups) {
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.emailId = emailId;
		this.roles = roles;
		this.healthCheckups = healthCheckups;
	}


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

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<HealthCheckup> getHealthCheckups() {
		return healthCheckups;
	}
	public void setHealthCheckups(List<HealthCheckup> healthCheckups) {
		this.healthCheckups = healthCheckups;
	}
}
