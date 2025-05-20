package com.hcl.diagnosticManagementSystem.dto;

import java.util.List;
import java.util.Map;

public class CustomerResponse {

	private String customerId;
	private String customerName;
	private String customerLocation;
	private Map<InsuranceAgentDetails,List<ServicesResponse>> servicesList;
	
	public CustomerResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Map<InsuranceAgentDetails, List<ServicesResponse>> getServicesList() {
		return servicesList;
	}

	public void setServicesList(Map<InsuranceAgentDetails, List<ServicesResponse>> servicesList) {
		this.servicesList = servicesList;
	}

	public String getCustomerLocation() {
		return customerLocation;
	}

	public void setCustomerLocation(String customerLocation) {
		this.customerLocation = customerLocation;
	}
	
}
