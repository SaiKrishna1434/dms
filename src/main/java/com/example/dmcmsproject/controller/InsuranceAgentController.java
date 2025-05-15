package com.example.dmcmsproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dmcmsproject.customexception.CustomerNotFoundException;
import com.example.dmcmsproject.customexception.CustomerServiceNotFoundException;
import com.example.dmcmsproject.customexception.InsuranceAgentException;
import com.example.dmcmsproject.customexception.LocationNotFoundException;
import com.example.dmcmsproject.dto.DeselectRequest;
import com.example.dmcmsproject.dto.IDRequest;
import com.example.dmcmsproject.dto.LocationRequest;
import com.example.dmcmsproject.dto.NameRequest;
import com.example.dmcmsproject.dto.SelectServiceRequest;
import com.example.dmcmsproject.response.CustomerResponse;
import com.example.dmcmsproject.response.InsuranceAgentResponse;
import com.example.dmcmsproject.response.NameResponse;
import com.example.dmcmsproject.response.ServicesResponse;
import com.example.dmcmsproject.responsemodel.CustomerViewResponseModel;
import com.example.dmcmsproject.service.InsuranceAgentService;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceAgentController {

	@Autowired
	private InsuranceAgentService insuranceAgentService;
	
	@GetMapping("/api/insuarance/home")
	public ResponseEntity<String> home()
	{
		return ResponseEntity.ok("Welcome to the Home Page");
	}
	
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
			response.setMessage("Customer deselct all services successfully...");
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
				response.setMessage("Customer selected services successfully...");
				response.setData(customer);
				return ResponseEntity.ok(response);	
		}
		catch(CustomerNotFoundException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(InsuranceAgentException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		catch(Exception e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch customer services: "+e.getMessage());
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
			response.setMessage("Customer services fetched successfully...");
			response.setData(customer);
			return ResponseEntity.ok(response);	
		}
		catch(CustomerNotFoundException e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	   catch(Exception e)
		{
			response.setStatus("Failed");
			response.setMessage("Failed to fetch customer services: "+e.getMessage());
			response.setData(null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
