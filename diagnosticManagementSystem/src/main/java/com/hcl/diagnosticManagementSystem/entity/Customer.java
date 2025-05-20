package com.hcl.diagnosticManagementSystem.entity;


import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table (name = "customers")
public class Customer {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	@NotBlank(message = "USER ID is required")
	private String userId;
	
	@Column(nullable = false)
	@NotBlank(message = "Password is required")
	@JsonIgnore
	private String password;
	
	@Column(nullable = false)
	@NotNull(message = "Date of Birth is required")
	private LocalDate dateOfBirth;
	
	@Column(unique = true, nullable = false)
    @NotBlank(message = "Email ID is required")
    @Email(message = "Invalid email format")
    private String emailId;
	
	
	// A Customer can have multiple roles - Agent, Doctor, Customer, Manager, Admin
	@ManyToMany
	@JoinTable(
			name = "customer_roles",
			joinColumns = @JoinColumn(name = "customer_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles;
	
	
	@OneToMany (mappedBy = "customer")
	private List<CustomerHealthCheckupApplication> customerHealthCheckupApplications;

	public Customer() {}	
	public Customer(String userId, String password, LocalDate dateOfBirth, String emailId) {
        this.userId = userId;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.emailId = emailId;
    }
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public LocalDate getDateOfBirth() { return dateOfBirth; }
	public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
	public String getEmailId() { return emailId; }
	public void setEmailId(String emailId) { this.emailId = emailId; }
	public List<Role> getRoles() { return roles; }
	public void setRoles(List<Role> roles) { this.roles = roles; }
	public List<CustomerHealthCheckupApplication> geCustomertHealthCheckupApplications() { return customerHealthCheckupApplications; }
	public void setHealthCheckups(List<CustomerHealthCheckupApplication> customerHealthCheckupApplications) { this.customerHealthCheckupApplications = customerHealthCheckupApplications; }
}
