package com.hcl.diagnosticManagementSystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.diagnosticManagementSystem.controller.InsuranceAgentController;
import com.hcl.diagnosticManagementSystem.dto.CustomerResponse;
import com.hcl.diagnosticManagementSystem.dto.DeselectRequest;
import com.hcl.diagnosticManagementSystem.dto.IDRequest;
import com.hcl.diagnosticManagementSystem.dto.InsuranceAgentDetails;
import com.hcl.diagnosticManagementSystem.dto.InsuranceAgentResponse;
import com.hcl.diagnosticManagementSystem.dto.LocationRequest;
import com.hcl.diagnosticManagementSystem.dto.NameResponse;
import com.hcl.diagnosticManagementSystem.dto.SelectServiceRequest;
import com.hcl.diagnosticManagementSystem.dto.ServicesResponse;
import com.hcl.diagnosticManagementSystem.entity.Customer;
import com.hcl.diagnosticManagementSystem.entity.CustomerServiceRecord;
import com.hcl.diagnosticManagementSystem.entity.InsuranceAgent;
import com.hcl.diagnosticManagementSystem.entity.Services;
import com.hcl.diagnosticManagementSystem.exception.CustomerNotFoundException;
import com.hcl.diagnosticManagementSystem.exception.CustomerServiceNotFoundException;
import com.hcl.diagnosticManagementSystem.exception.InsuranceAgentException;
import com.hcl.diagnosticManagementSystem.exception.LocationNotFoundException;
import com.hcl.diagnosticManagementSystem.exception.RequestBodyException;
import com.hcl.diagnosticManagementSystem.repository.CustomerRepository;
import com.hcl.diagnosticManagementSystem.repository.CustomerServiceRecordsRepository;
import com.hcl.diagnosticManagementSystem.repository.InsuranceAgentRepository;
import com.hcl.diagnosticManagementSystem.repository.ServicesRepository;

@Service
public class InsuranceAgentService {

	private static final Logger log=LoggerFactory.getLogger(InsuranceAgentService.class);
	
	@Autowired
	private InsuranceAgentRepository insuranceAgentRepository;
	@Autowired
	private ServicesRepository servicesRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerServiceRecordsRepository customerServiceRepository;
	
	
	public InsuranceAgentRepository getInsuranceAgentRepository() {
		return insuranceAgentRepository;
	}
	
	public void setInsuranceAgentRepository(InsuranceAgentRepository insuranceAgentRepository) {
		this.insuranceAgentRepository = insuranceAgentRepository;
	}
	
	public ServicesRepository getServicesRepository() {
		return servicesRepository;
	}
	
	public void setServicesRepository(ServicesRepository servicesRepository) {
		this.servicesRepository = servicesRepository;
	}
	
	public List<NameResponse> getAgentByName(String name) throws InsuranceAgentException, RequestBodyException {
		
		log.info("Get Agent Details by name method is been invocked");
		
		if(name.trim().isEmpty())
		{
			throw new RequestBodyException("Agent name cannot be blank");
		}
		
		List<InsuranceAgent> agents=insuranceAgentRepository.findAgentByName(name);
	
		if(agents.isEmpty())
			throw new InsuranceAgentException("Agent Not Found");
		
		List<NameResponse> responseList=new ArrayList<>();
		
		for(InsuranceAgent agent : agents)
		{
			for(Services service:agent.getServices())
			{
			    NameResponse response=new NameResponse();
				response.setServiceId(service.getId());
				response.setService(service.getService());
				response.setAgentId(agent.getId());
				response.setAgentName(agent.getName());
				response.setLocation(agent.getLocation());
				responseList.add(response);
			}
		}
		return responseList;
	}
	
	public CustomerResponse deleteAllServices(String userId) throws CustomerNotFoundException, CustomerServiceNotFoundException,Exception {
	
		log.info("Delete all services method is been invocked");
		
		if(userId.trim().isEmpty())
		{
			throw new RequestBodyException("userId cannot be blank");
		}
		
		Customer customer=customerRepository.findById(userId.trim())
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
		
		List<CustomerServiceRecord> records=customer.getCustomerRecords();
		
		if(records.isEmpty())
		{
			throw new CustomerServiceNotFoundException("Customer not has services");
		}
		
		Map<InsuranceAgentDetails,List<ServicesResponse>> deletedServicesMap=new HashMap<>();
		
		for(CustomerServiceRecord s : records)
		{
			InsuranceAgent agent=s.getInsuranceAgent();
			InsuranceAgentDetails agents=new InsuranceAgentDetails();
			agents.setAgentId(String.valueOf(s.getInsuranceAgent().getId()));
			agents.setAgentName(s.getInsuranceAgent().getName());
			agents.setLocation(s.getInsuranceAgent().getLocation());
			agents.setEmail(s.getInsuranceAgent().getEmail());
			agents.setContactInfo(s.getInsuranceAgent().getContactNo());
			
			Services service=s.getService();
			ServicesResponse response=new ServicesResponse();
			response.setId(service.getId());
			response.setService(service.getService());
			
			String agentName= agent!=null ? agent.getName() : "Unknown Agent";
			String serviceName= service!=null ? service.getService() : "Unknown Service"; 
			
			if(!deletedServicesMap.containsKey(agents))
			{
				deletedServicesMap.put(agents, new ArrayList<>());
			}
					 
			deletedServicesMap.get(agents).add(response);
		 }
			
		customerServiceRepository.deleteAll(records);
		customer.getCustomerRecords().clear();
		customerRepository.save(customer);
		
		
		CustomerResponse response=new CustomerResponse();
		response.setCustomerId(customer.getUserId());
		response.setCustomerName(customer.getCustomerName());
		response.setCustomerLocation(customer.getLocation());
		response.setServicesList(deletedServicesMap);
		
		return response;
		
	}
	
	public CustomerResponse deleteSelectedService(DeselectRequest request) throws CustomerServiceNotFoundException, NumberFormatException, CustomerNotFoundException, Exception{
		
		log.info("Delete selected services method is been invocked");
		
		if(request.getUserId().trim().isEmpty())
		{
			throw new RequestBodyException("userId cannot be blank");
		}
		
		if(request.getServiceId().trim().isEmpty())
		{
			throw new RequestBodyException("serviceId cannot be blank");
		}
		
		Customer customer=customerRepository.findById(request.getUserId().trim())
				.orElseThrow(()-> new CustomerNotFoundException("Customer not Found"));
		Services service=servicesRepository.findById(Long.parseLong(request.getServiceId()))
				.orElseThrow(()-> new CustomerServiceNotFoundException("Service not found"));
		
		List<CustomerServiceRecord> records = customer.getCustomerRecords();
		boolean deleted=false;
		
		for(CustomerServiceRecord record : records)
		{
			if(record.getService().getId().equals(service.getId()))
			{
				customerServiceRepository.delete(record);
				customer.getCustomerRecords().remove(record);
				deleted=true;
				break;
			}
		}
		
		if(!deleted)
		{
			throw new CustomerServiceNotFoundException("Customer has insert wrong services");
		}
		
		customerRepository.save(customer);
		
		Map<InsuranceAgentDetails,List<ServicesResponse>> remainingServices=new HashMap<>();
		
		for(CustomerServiceRecord record : customer.getCustomerRecords())
		{
			InsuranceAgent agent=record.getInsuranceAgent();
			InsuranceAgentDetails insuranceAgent=new InsuranceAgentDetails();
			insuranceAgent.setAgentId(String.valueOf(agent.getId()));
			insuranceAgent.setAgentName(agent.getName());
			insuranceAgent.setLocation(agent.getLocation());
			insuranceAgent.setEmail(agent.getEmail());
			insuranceAgent.setContactInfo(agent.getContactNo());
			
			Services s=record.getService();
			ServicesResponse response=new ServicesResponse();
			response.setId(s.getId());
			response.setService(s.getService());
			
			if(!remainingServices.containsKey(insuranceAgent))
			{
				remainingServices.put(insuranceAgent, new ArrayList<>());
			}
			
			remainingServices.get(insuranceAgent).add(response);
		}
		
		CustomerResponse response = new CustomerResponse();
		response.setCustomerId(customer.getUserId());
		response.setCustomerName(customer.getCustomerName());
		response.setCustomerLocation(customer.getLocation());
		response.setServicesList(remainingServices);
		
		return response;
	}
	
	public CustomerResponse getCustomerAllDetails(IDRequest request) throws RequestBodyException, CustomerNotFoundException,Exception {
		
		log.info("Get customer details method is been invocked");
		
		if(request.getUserId().trim().isEmpty())
		{
			throw new RequestBodyException("UserId should not be blank");
		}
		
		Customer optionalCustomer=customerRepository.findById(request.getUserId().trim())
				.orElseThrow(()-> new CustomerNotFoundException("Customer Not found"));

			Map<InsuranceAgentDetails,List<ServicesResponse>> servicesMap=new HashMap<>();
		
			for(CustomerServiceRecord record : optionalCustomer.getCustomerRecords())
			{
				InsuranceAgent agent= record.getInsuranceAgent();
				InsuranceAgentDetails insuranceAgent=new InsuranceAgentDetails();
				insuranceAgent.setAgentId(String.valueOf(agent.getId()));
				insuranceAgent.setAgentName(agent.getName());
				insuranceAgent.setLocation(agent.getLocation());
				insuranceAgent.setEmail(agent.getEmail());
				insuranceAgent.setContactInfo(agent.getContactNo());
				
				Services service=record.getService();
				ServicesResponse response=new ServicesResponse();
				response.setId(service.getId());
				response.setService(service.getService());
 			
				if(!servicesMap.containsKey(insuranceAgent))
				{
					servicesMap.put(insuranceAgent, new ArrayList<>());
				}
			
			servicesMap.get(insuranceAgent).add(response);
			
		}
		
		CustomerResponse response=new CustomerResponse();
		
		response.setCustomerId(optionalCustomer.getUserId());
		response.setCustomerName(optionalCustomer.getCustomerName());
		response.setCustomerLocation(optionalCustomer.getLocation());
		response.setServicesList(servicesMap);
		
		return response;
	}

	public List<InsuranceAgentResponse> getAgentsUsingLocation(LocationRequest locationRequest) throws LocationNotFoundException,Exception {
		
		log.info("Get Agent Details by location method is been invocked");
		
		if(locationRequest.getLocation().trim().isEmpty())
		{
			throw new RequestBodyException("Location cannot be blank");
		}
		
		List<InsuranceAgent> agents=insuranceAgentRepository.findByLocation(locationRequest.getLocation());
		
		if(agents.isEmpty())
			throw new LocationNotFoundException("Agents is not availaable in this location");
	
		List<InsuranceAgentResponse> responses=new ArrayList<>();
	
		for(InsuranceAgent agent : agents)
		{
			InsuranceAgentResponse response=new InsuranceAgentResponse();
			response.setId(agent.getId());
			response.setAgentName(agent.getName());
			response.setLocation(agent.getLocation());
		
			List<ServicesResponse> serviceList=new ArrayList<>();
			for(Services service:agent.getServices())
			{
				ServicesResponse servicesResponse=new ServicesResponse();
				servicesResponse.setId(service.getId());
				servicesResponse.setService(service.getService());
				serviceList.add(servicesResponse);
			}
			response.setServiceList(serviceList);
			responses.add(response);
		}
	
		return responses;
	}

	public CustomerResponse selectService(SelectServiceRequest request) throws NumberFormatException, CustomerNotFoundException, InsuranceAgentException,Exception {
		
		log.info("Select service method is been invocked");
		
		if(request.getUserId().trim().isEmpty())
		{
			throw new RequestBodyException("userId cannot be blank");
		}
		
		if(request.getAgentId().trim().isEmpty())
		{
			throw new RequestBodyException("agentId cannot be blank");
		}
		
		Customer customer=customerRepository.findById(request.getUserId().trim())
				.orElseThrow(()-> new CustomerNotFoundException("Customer Not found"));
		
		InsuranceAgent agent=insuranceAgentRepository.findById(Long.parseLong(request.getAgentId()))
				.orElseThrow(()-> new InsuranceAgentException("Agent Not Found"));
    		
    	List<Services> agentServices = agent.getServices();
    	List<Services> matchedServices=servicesRepository.findByServiceIn(request.getServiceNames());
    	    
    	if(matchedServices.isEmpty())
    	{
    	   throw new InsuranceAgentException("Customer requested services not available");
    	}
    	    
    	for(Services s: matchedServices)
    	{
    	    if(!agentServices.contains(s))
    	    {
    	      throw new InsuranceAgentException("Agent does not provide this services");
    	    }
    	}
    	    
    	for(Services s : matchedServices)
    	{
    	    CustomerServiceRecord record = new CustomerServiceRecord();
    	    record.setCustomer(customer);
    	    record.setInsuranceAgent(agent);
    	    record.setService(s);
    	    customerServiceRepository.save(record);
    	}         
    	    
    	CustomerResponse response = new CustomerResponse();
    	response.setCustomerId(customer.getUserId());
    	response.setCustomerName(customer.getCustomerName());
    	response.setCustomerLocation(customer.getLocation());
    	
    	List<ServicesResponse> serviceNames = new ArrayList<>();
    	for(Services s : matchedServices)
    	{
    		ServicesResponse services=new ServicesResponse();
    		services.setId(s.getId());
    		services.setService(s.getService());
    		
    		serviceNames.add(services);
    	}
    	
    	InsuranceAgentDetails insuranceAgent=new InsuranceAgentDetails();
		insuranceAgent.setAgentId(String.valueOf(agent.getId()));
		insuranceAgent.setAgentName(agent.getName());
		insuranceAgent.setLocation(agent.getLocation());
		insuranceAgent.setEmail(agent.getEmail());
		insuranceAgent.setContactInfo(agent.getContactNo());
    	    
    	Map<InsuranceAgentDetails, List<ServicesResponse>> serviceMap=new HashMap<>();
    	serviceMap.put(insuranceAgent, serviceNames);
    	    
    	response.setServicesList(serviceMap);
    	    
    	return response;
      }
}

