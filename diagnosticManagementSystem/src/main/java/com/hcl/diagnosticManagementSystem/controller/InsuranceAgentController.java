package com.hcl.diagnosticManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.diagnosticManagementSystem.apiResponseModel.CustomerViewResponseModel;
import com.hcl.diagnosticManagementSystem.dto.CustomerResponse;
import com.hcl.diagnosticManagementSystem.dto.DeselectRequest;
import com.hcl.diagnosticManagementSystem.dto.IDRequest;
import com.hcl.diagnosticManagementSystem.dto.InsuranceAgentResponse;
import com.hcl.diagnosticManagementSystem.dto.LocationRequest;
import com.hcl.diagnosticManagementSystem.dto.NameRequest;
import com.hcl.diagnosticManagementSystem.dto.NameResponse;
import com.hcl.diagnosticManagementSystem.dto.SelectServiceRequest;
import com.hcl.diagnosticManagementSystem.exception.CustomerNotFoundException;
import com.hcl.diagnosticManagementSystem.exception.CustomerServiceNotFoundException;
import com.hcl.diagnosticManagementSystem.exception.InsuranceAgentException;
import com.hcl.diagnosticManagementSystem.exception.LocationNotFoundException;
import com.hcl.diagnosticManagementSystem.exception.RequestBodyException;
import com.hcl.diagnosticManagementSystem.service.InsuranceAgentService;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceAgentController {

	@Autowired
	private InsuranceAgentService insuranceAgentService;
	
	@PostMapping("/byAgentName")
	public ResponseEntity<CustomerViewResponseModel<List<NameResponse>>> findAgentsByName(@RequestBody NameRequest name)
	{
		CustomerViewResponseModel<List<NameResponse>> response=new CustomerViewResponseModel<>();
		try
		{
			List<NameResponse> insuranceAgent= insuranceAgentService.getAgentByName(name.getName());
				response.setStatus("Success");
				response.setMessage("Insurance agents services fetched successfully...");
				response.setData(insuranceAgent);
				return ResponseEntity.ok(response);	
		}
		catch(InsuranceAgentException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch agents: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(RequestBodyException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch agents: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(Exception e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch agents: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PostMapping("/getInsuranceAgent")
	public ResponseEntity<CustomerViewResponseModel<List<InsuranceAgentResponse>>> findAgentsByLocation(@RequestBody LocationRequest locationRequest)
	{
		CustomerViewResponseModel<List<InsuranceAgentResponse>> response=new CustomerViewResponseModel<>();
		try
		{
			List<InsuranceAgentResponse> insuranceAgent= insuranceAgentService.getAgentsUsingLocation(locationRequest);
				response.setStatus("Success");
				response.setMessage("Insurance agents fetched successfully...");
				response.setData(insuranceAgent);
				return ResponseEntity.ok(response);	

		}
		catch(LocationNotFoundException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch agents: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);	
		}
		catch(RequestBodyException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch agents: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(Exception e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch agents: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@DeleteMapping("/deselectAllService")
	public ResponseEntity<CustomerViewResponseModel<CustomerResponse>> deleteAllServices(@RequestBody IDRequest request)
	{
	
		CustomerViewResponseModel<CustomerResponse> response=new CustomerViewResponseModel<CustomerResponse>();
		try
		{
			CustomerResponse customer=insuranceAgentService.deleteAllServices(request.getUserId());
			response.setStatus("Success");
			response.setMessage("Customer delete all services successfully...");
			response.setData(customer);
			return ResponseEntity.ok(response);	
			
		}
		catch(CustomerNotFoundException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to delete customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(CustomerServiceNotFoundException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to delete customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(RequestBodyException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to delete customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(Exception e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to delete customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@DeleteMapping("/deleteSelectService")
	public ResponseEntity<CustomerViewResponseModel<CustomerResponse>> onlySelectedServiceDeleted(@RequestBody DeselectRequest request)
	{
		CustomerViewResponseModel<CustomerResponse> response=new CustomerViewResponseModel<>();
		try
		{
			CustomerResponse customer=insuranceAgentService.deleteSelectedService(request);
			response.setStatus("Success");
			response.setMessage("Customer deleted selected services successfully...");
			response.setData(customer);
			return ResponseEntity.ok(response);	
			
		}
		catch(CustomerNotFoundException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to delete customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(CustomerServiceNotFoundException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to delete customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(RequestBodyException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to delete customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(NumberFormatException e)
		{
			response.setStatus("Failed");
			response.setMessage("Invalid ServiceId: Must be a numeric value.");
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(Exception e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to delete customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	
	@PostMapping("/selectedService")
	public ResponseEntity<CustomerViewResponseModel<CustomerResponse>> customerSelectedService(@RequestBody SelectServiceRequest request)
	{
		CustomerViewResponseModel<CustomerResponse> response=new CustomerViewResponseModel<>();
		try
		{
			CustomerResponse customer=insuranceAgentService.selectService(request);
				response.setStatus("Success");
				response.setMessage("Customer selected services added successfully...");
				response.setData(customer);
				return ResponseEntity.ok(response);	
		}
		catch(CustomerNotFoundException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to add customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(InsuranceAgentException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to add customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(NumberFormatException e)
		{
			response.setStatus("Failed");
			response.setMessage("Invalid agentId: Must be numeric value");
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(RequestBodyException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to add customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(Exception e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to add customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PostMapping("/viewCustomer")
	public ResponseEntity<CustomerViewResponseModel<CustomerResponse>> customerDetails(@RequestBody IDRequest request)
	{
		CustomerViewResponseModel<CustomerResponse> response = new CustomerViewResponseModel<CustomerResponse>();
		
		try {
			
			CustomerResponse customer=insuranceAgentService.getCustomerAllDetails(request);
				
			response.setStatus("Success");
			response.setMessage("Customer details fetched successfully...");
			response.setData(customer);
			return ResponseEntity.ok(response);	
		}
		catch(CustomerNotFoundException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch customer details: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(RequestBodyException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch customer details: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	   catch(Exception e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch customer details: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
