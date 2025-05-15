package com.example.dmcmsproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.dmcmsproject.customexception.CustomerNotFoundException;
import com.example.dmcmsproject.customexception.CustomerServiceNotFoundException;
import com.example.dmcmsproject.customexception.InsuranceAgentException;
import com.example.dmcmsproject.customexception.LocationNotFoundException;
import com.example.dmcmsproject.dao.CustomerRepository;
import com.example.dmcmsproject.dao.CustomerServiceRecordsRepository;
import com.example.dmcmsproject.dao.InsuranceAgentRepository;
import com.example.dmcmsproject.dao.ServicesRepository;
import com.example.dmcmsproject.dto.DeselectRequest;
import com.example.dmcmsproject.dto.IDRequest;
import com.example.dmcmsproject.dto.LocationRequest;
import com.example.dmcmsproject.dto.SelectServiceRequest;
import com.example.dmcmsproject.model.Customer;
import com.example.dmcmsproject.model.CustomerServiceRecord;
import com.example.dmcmsproject.model.InsuranceAgent;
import com.example.dmcmsproject.model.Services;
import com.example.dmcmsproject.response.CustomerResponse;
import com.example.dmcmsproject.response.InsuranceAgentResponse;
import com.example.dmcmsproject.response.NameResponse;
import com.example.dmcmsproject.response.ServicesResponse;

@Service
public class InsuranceAgentService {

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
	
	public List<NameResponse> getAgentByName(String name) throws InsuranceAgentException {
		
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
	
	public CustomerResponse deleteAllServices(String userId) throws NumberFormatException, CustomerNotFoundException, CustomerServiceNotFoundException,Exception {
		
		Customer customer=customerRepository.findById(Long.parseLong(userId.trim()))
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
		
		List<CustomerServiceRecord> records=customer.getCustomerRecords();
		
		if(records.isEmpty())
		{
			throw new CustomerServiceNotFoundException("Customer not has services");
		}
		
		Map<String,List<String>> deletedServicesMap=new HashMap<>();
		
		for(CustomerServiceRecord s : records)
		{
			InsuranceAgent agent=s.getInsuranceAgent();
			Services service=s.getService();
			
			String agentName= agent!=null ? agent.getName() : "Unknown Agent";
			String serviceName= service!=null ? service.getService() : "Unknown Service"; 
			
			if(!deletedServicesMap.containsKey(agentName))
			{
				deletedServicesMap.put(agentName, new ArrayList<>());
			}
					 
			deletedServicesMap.get(agentName).add(serviceName);
		 }
			
		customerServiceRepository.deleteAll(records);
		customer.getCustomerRecords().clear();
		customerRepository.save(customer);
		
		
		CustomerResponse response=new CustomerResponse();
		response.setCustomerId(customer.getUserId());
		response.setCustomerName(customer.getCname());
		response.setCustomerLocation(customer.getLocation());
		response.setServicesList(deletedServicesMap);
		
		return response;
		
	}
	
	public CustomerResponse deleteSelectedService(DeselectRequest request) throws CustomerServiceNotFoundException, NumberFormatException, CustomerNotFoundException, Exception{
		
		Customer customer=customerRepository.findById(Long.parseLong(request.getUserId()))
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
		
		Map<String,List<String>> remainingServices=new HashMap<>();
		
		for(CustomerServiceRecord record : customer.getCustomerRecords())
		{
			String agentName = record.getInsuranceAgent().getName();
			String serviceName = record.getService().getService();
			
			if(!remainingServices.containsKey(agentName))
			{
				remainingServices.put(agentName, new ArrayList<>());
			}
			
			remainingServices.get(agentName).add(serviceName);
		}
		
		CustomerResponse response = new CustomerResponse();
		response.setCustomerId(customer.getUserId());
		response.setCustomerName(customer.getCname());
		response.setCustomerLocation(customer.getLocation());
		response.setServicesList(remainingServices);
		
		return response;
	}
	
	public CustomerResponse getCustomerAllDetails(IDRequest request) throws NumberFormatException, CustomerNotFoundException,Exception {
		
		Customer optionalCustomer=customerRepository.findById(Long.parseLong(request.getUserId().trim()))
				.orElseThrow(()-> new CustomerNotFoundException("Customer Not found"));

			Map<String,List<String>> servicesMap=new HashMap<>();
		
			for(CustomerServiceRecord record : optionalCustomer.getCustomerRecords())
			{
				InsuranceAgent agent= record.getInsuranceAgent();
			
				Services service=record.getService();
			
				String agentName= agent!=null ? agent.getName() : "Unknown Agent";
				String servicename= service!=null ? service.getService() :"Unknown Service";
 			
				if(!servicesMap.containsKey(agentName))
				{
					servicesMap.put(agentName, new ArrayList<>());
				}
			
			servicesMap.get(agentName).add(servicename);
			
		}
		
		CustomerResponse response=new CustomerResponse();
		
		response.setCustomerId(optionalCustomer.getUserId());
		response.setCustomerName(optionalCustomer.getCname());
		response.setCustomerLocation(optionalCustomer.getLocation());
		response.setServicesList(servicesMap);
		
		return response;
	}

	public List<InsuranceAgentResponse> getAgentsUsingLocation(LocationRequest locationRequest) throws LocationNotFoundException,Exception {
		
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
		
		Customer customer=customerRepository.findById(Long.parseLong(request.getUserId().trim()))
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
    	response.setCustomerName(customer.getCname());
    	response.setCustomerLocation(customer.getLocation());
    	List<String> serviceNames = new ArrayList<String>();
    	for(Services s : matchedServices)
    	{
    	   serviceNames.add(s.getService());
    	}
    	    
    	Map<String, List<String>> serviceMap=new HashMap<>();
    	serviceMap.put(agent.getName(), serviceNames);
    	    
    	response.setServicesList(serviceMap);
    	    
    	return response;
      }
}

