package com.hcl.diagnosticManagementSystem.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class InsuranceAgent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message="Agent Name cannot be blank")
	private String name;
	@NotBlank(message="Agent location cannot be blank")
	private String location;
	@Email(message = "Invalid email format")
	private String email;
	@NotBlank(message="Agent Contact number cannot be blank")
	private String contactNo;
	
	@ManyToMany
	@JoinTable(name="agent_services", joinColumns = @JoinColumn(name="agent_id"),
	inverseJoinColumns = @JoinColumn(name="service_id"))
	@JsonManagedReference
	@NotNull(message = "Agent Services cannot be null")
	@NotEmpty(message = "Insurance agent must have at least one services")
	private List<Services> services=new ArrayList<Services>();
	
	@OneToMany(mappedBy="insuranceAgent")
	private List<CustomerServiceRecord> agentRecords=new ArrayList<>();

	public InsuranceAgent() {
		// TODO Auto-generated constructor stub
		
	}
	
	public InsuranceAgent(Long id, String name, String location, List<Services> services) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.services = services;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
	}

	public List<CustomerServiceRecord> getAgentRecords() {
		return agentRecords;
	}

	public void setAgentRecords(List<CustomerServiceRecord> agentRecords) {
		this.agentRecords = agentRecords;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
}
