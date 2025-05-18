package com.example.dmcmsproject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Services {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Service cannot be blank")
	private String service;
	
	@ManyToMany(mappedBy="services")
	@JsonBackReference
	private List<InsuranceAgent> insuranceAgents=new ArrayList<InsuranceAgent>();
	
	@OneToMany(mappedBy="service")
	private List<CustomerServiceRecord> serviceRecords=new ArrayList<>();
	
	public Services() {
	// TODO Auto-generated constructor stub
	}
	
	public Services(long l, String string) {
		this.id=l;
		this.service=string;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public List<InsuranceAgent> getInsuranceAgents() {
		return insuranceAgents;
	}

	public void setInsuranceAgents(List<InsuranceAgent> insuranceAgents) {
		this.insuranceAgents = insuranceAgents;
	}

	public List<CustomerServiceRecord> getRecords() {
		return serviceRecords;
	}

	public void setRecords(List<CustomerServiceRecord> records) {
		this.serviceRecords = records;
	}

	public List<CustomerServiceRecord> getServiceRecords() {
		return serviceRecords;
	}

	public void setServiceRecords(List<CustomerServiceRecord> serviceRecords) {
		this.serviceRecords = serviceRecords;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Services services = (Services) o;
        return Objects.equals(id, services.id) && Objects.equals(service, services.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, service);
    }
}
