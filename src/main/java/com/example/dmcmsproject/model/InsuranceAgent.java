package com.example.dmcmsproject.model;

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

@Entity
public class InsuranceAgent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String location;
	
	@ManyToMany
	@JoinTable(name="agent_services", joinColumns = @JoinColumn(name="agent_id"),
	inverseJoinColumns = @JoinColumn(name="service_id"))
	@JsonManagedReference
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
}
