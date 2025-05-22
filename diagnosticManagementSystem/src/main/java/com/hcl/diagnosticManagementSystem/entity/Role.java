package com.hcl.diagnosticManagementSystem.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<Customer> customers;
	
	@Column(nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private RoleName name;
	
	public enum RoleName {
		ROLE_INSURANCE_AGENT,
		ROLE_CUSTOMER,
		ROLE_DOCTOR,
		ROLE_ADMIN
	}
	
	public Role() {}
	public Role(RoleName name) {
		this.name = name;
	}
	
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
	public RoleName getName() {
		return name;
	}
	public void setName(RoleName name) {
		this.name = name;
	}
}
