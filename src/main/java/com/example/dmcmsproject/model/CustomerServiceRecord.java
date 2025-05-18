package com.example.dmcmsproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class CustomerServiceRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cust_id;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="agent_id")
	private InsuranceAgent insuranceAgent;
	
	@ManyToOne
	@JoinColumn(name="service_id")
	private Services service;
	
	public CustomerServiceRecord() {
		// TODO Auto-generated constructor stub
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public InsuranceAgent getInsuranceAgent() {
		return insuranceAgent;
	}

	public void setInsuranceAgent(InsuranceAgent insuranceAgent) {
		this.insuranceAgent = insuranceAgent;
	}

	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}
}
