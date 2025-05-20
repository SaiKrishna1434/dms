package com.hcl.diagnosticManagementSystem.exception;

public class InsuranceAgentException extends Exception {

	public String insuranceAgentNotFound()
	{
		return "Insurance agent not available in this location...";
	}
	public InsuranceAgentException(String message) {
		super(message);
	}
	public InsuranceAgentException() {
		// TODO Auto-generated constructor stub
	}
}
