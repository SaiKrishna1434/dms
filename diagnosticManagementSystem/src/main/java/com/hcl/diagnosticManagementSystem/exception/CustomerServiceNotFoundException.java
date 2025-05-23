package com.hcl.diagnosticManagementSystem.exception;

public class CustomerServiceNotFoundException extends Exception{

	public String serviceNotFoundException()
	{
		return "Service Id not found for this customer.";
	}
	
	public CustomerServiceNotFoundException(String message) {
		super(message);
	}
}
