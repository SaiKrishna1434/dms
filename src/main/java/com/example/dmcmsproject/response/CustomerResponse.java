package com.example.dmcmsproject.response;

import java.util.List;
import java.util.Map;

import com.example.dmcmsproject.model.Services;

public class CustomerResponse {

	private Long customerId;
	private String customerName;
	private String customerLocation;
	private Map<String,List<String>> servicesList;
	
	public CustomerResponse() {
		// TODO Auto-generated constructor stub
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Map<String, List<String>> getServicesList() {
		return servicesList;
	}

	public void setServicesList(Map<String, List<String>> servicesList) {
		this.servicesList = servicesList;
	}

	public String getCustomerLocation() {
		return customerLocation;
	}

	public void setCustomerLocation(String customerLocation) {
		this.customerLocation = customerLocation;
	}
	
}
