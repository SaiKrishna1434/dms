package com.example.dmcmsproject.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.dmcmsproject.controller.InsuranceAgentController;
import com.example.dmcmsproject.customexception.CustomerNotFoundException;
import com.example.dmcmsproject.customexception.CustomerServiceNotFoundException;
import com.example.dmcmsproject.customexception.InsuranceAgentException;
import com.example.dmcmsproject.customexception.LocationNotFoundException;
import com.example.dmcmsproject.dto.DeselectRequest;
import com.example.dmcmsproject.dto.IDRequest;
import com.example.dmcmsproject.dto.LocationRequest;
import com.example.dmcmsproject.dto.NameRequest;
import com.example.dmcmsproject.dto.SelectServiceRequest;
import com.example.dmcmsproject.model.Customer;
import com.example.dmcmsproject.model.CustomerServiceRecord;
import com.example.dmcmsproject.model.InsuranceAgent;
import com.example.dmcmsproject.model.Services;
import com.example.dmcmsproject.response.CustomerResponse;
import com.example.dmcmsproject.response.InsuranceAgentResponse;
import com.example.dmcmsproject.response.NameResponse;
import com.example.dmcmsproject.response.ServicesResponse;
import com.example.dmcmsproject.service.InsuranceAgentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(InsuranceAgentController.class)
public class InsuranceAgentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private InsuranceAgentService insuranceAgentService;
	
	private final ObjectMapper objectMapper=new ObjectMapper();
	
	
	@Test
	public void testFindAgentByName_Success() throws Exception
	{
		NameRequest request=new NameRequest();
		request.setName("Rajiv Gupta");
		
		InsuranceAgent agent1=new InsuranceAgent();
		agent1.setId(2L);
		agent1.setName("Rajiv Gupta");
		
		InsuranceAgent agent2=new InsuranceAgent();
		agent1.setId(5L);
		agent1.setName("Rajiv Gupta");
		
		NameResponse response1 = new NameResponse();
		response1.setServiceId(1L);
		response1.setService("Dental Care");
		response1.setAgentId(agent1.getId());
		response1.setAgentName(agent1.getName());
		
		NameResponse response2 = new NameResponse();
		response2.setServiceId(6L);
		response2.setService("Body Checkup");
		response2.setAgentId(agent2.getId());
		response2.setAgentName(agent2.getName());
		
		List<NameResponse> responseList=List.of(response1,response2);
		
		when(insuranceAgentService.getAgentByName("Rajiv Gupta")).thenReturn(responseList);
		
		mockMvc.perform(post("/api/insurance/byAgentName")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Rajiv Gupta\"}"))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.status").value("Success"))
		        .andExpect(jsonPath("$.message").value("Insurance agents services fetched successfully..."))
		        .andExpect(jsonPath("$.data[0].service").value("Dental Care"))
		        .andExpect(jsonPath("$.data[1].service").value("Body Checkup"));	
	}
	
	@Test
	public void testFindAgentByName_NotFound() throws Exception
	{
		when(insuranceAgentService.getAgentByName("Wrong Name")).thenThrow(new InsuranceAgentException("Agent Not Found"));
		
		mockMvc.perform(post("/api/insurance/byAgentName")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Wrong Name\"}"))
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath("$.status").value("Failed"))
		        .andExpect(jsonPath("$.message").value("Failed to fetch agents: Agent Not Found"))
		        .andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testFindAgentByName_Exception() throws Exception
	{
		when(insuranceAgentService.getAgentByName(anyString())).thenThrow(new RuntimeException("DB Error"));
		
		mockMvc.perform(post("/api/insurance/byAgentName")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Rajiv Gupta\"}"))
		        .andExpect(status().isInternalServerError())
		        .andExpect(jsonPath("$.status").value("Failed"))
		        .andExpect(jsonPath("$.message").value("Failed to fetch agents: DB Error"))
		        .andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	public void testFindAgents_Success() throws JsonProcessingException, Exception
	{
		LocationRequest request=new LocationRequest();
		request.setLocation("Nagpur");
		
		InsuranceAgentResponse response=new InsuranceAgentResponse();
		response.setAgentName("Rajiv Gupta");
		response.setLocation("Nagpur");
		
		List<InsuranceAgentResponse> responseList=List.of(response);
		
		when(insuranceAgentService.getAgentsUsingLocation(any(LocationRequest.class))).thenReturn(responseList);
		
		mockMvc.perform(post("/api/insurance/getInsuranceAgent")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.status").value("Success"))
		        .andExpect(jsonPath("$.message").value("Insurance agents fetched successfully..."))
		        .andExpect(jsonPath("$.data[0].agentName").value("Rajiv Gupta"));
	}

	@Test
	public void testFindAgents_LocationNotFound() throws JsonProcessingException, Exception
	{
		LocationRequest request=new LocationRequest();
		request.setLocation("Nagpur");
		
		when(insuranceAgentService.getAgentsUsingLocation(any(LocationRequest.class)))
		.thenThrow(new LocationNotFoundException("Location Not Found"));
		
		mockMvc.perform(post("/api/insurance/getInsuranceAgent")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		        .andExpect(status().isBadRequest())
		        .andExpect(jsonPath("$.status").value("Failed"))
		        .andExpect(jsonPath("$.message").value("Failed to fetch agents: Location Not Found"));
	}
	
	@Test
	public void testFindAgents_Exception() throws JsonProcessingException, Exception
	{
		LocationRequest request=new LocationRequest();
		request.setLocation("Nagpur");
		
		when(insuranceAgentService.getAgentsUsingLocation(any(LocationRequest.class)))
		.thenThrow(new Exception());
		
		mockMvc.perform(post("/api/insurance/getInsuranceAgent")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		        .andExpect(status().isInternalServerError())
		        .andExpect(jsonPath("$.status").value("Failed"));	
	}
	
	@Test
	public void testDeselectServices_Success() throws JsonProcessingException, Exception
	{
		IDRequest request=new IDRequest();
		request.setUserId("1");
		
		CustomerResponse response= new CustomerResponse();
		response.setCustomerId(1L);
		response.setCustomerName("Ramesh Chandak");
		
		when(insuranceAgentService.deleteAllServices(any(String.class)))
		         .thenReturn(response);
		
		mockMvc.perform(delete("/api/insurance/deselectAllService")
			   .contentType(MediaType.APPLICATION_JSON)
			   .content(objectMapper.writeValueAsString(request)))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.status").value("Success"))
			   .andExpect(jsonPath("$.message").value("Customer deselct all services successfully..."))
			   .andExpect(jsonPath("$.data.customerId").value(1L))
			   .andExpect(jsonPath("$.data.customerName").value("Ramesh Chandak"));
	}
	
	@Test
	public void testDeselectServices_CustomerNotFound() throws JsonProcessingException, Exception
	{
		IDRequest request=new IDRequest();
		request.setUserId("2");
		
		when(insuranceAgentService.deleteAllServices(any(String.class)))
        .thenThrow(new CustomerNotFoundException("Customer not found"));
		
		mockMvc.perform(delete("/api/insurance/deselectAllService")
				   .contentType(MediaType.APPLICATION_JSON)
				   .content(objectMapper.writeValueAsString(request)))
				   .andExpect(status().isBadRequest())
				   .andExpect(jsonPath("$.status").value("Failed"))
				   .andExpect(jsonPath("$.message").value("Failed to delete customer services: Customer not found"));
	}

	@Test
	public void testDeselectServices_ServiceNotExist() throws JsonProcessingException, Exception
	{
		IDRequest request=new IDRequest();
		request.setUserId("2");
		
		when(insuranceAgentService.deleteAllServices(any(String.class)))
        .thenThrow(new CustomerServiceNotFoundException("Customer has no services"));
		
		mockMvc.perform(delete("/api/insurance/deselectAllService")
				   .contentType(MediaType.APPLICATION_JSON)
				   .content(objectMapper.writeValueAsString(request)))
				   .andExpect(status().isBadRequest())
				   .andExpect(jsonPath("$.status").value("Failed"))
				   .andExpect(jsonPath("$.message").value("Failed to delete customer services: Customer has no services"));
	}
	
	@Test
	public void testDeselectServices_Exception() throws JsonProcessingException, Exception
	{
		IDRequest request=new IDRequest();
		request.setUserId("2");
		
		when(insuranceAgentService.deleteAllServices(any(String.class)))
        .thenThrow(new Exception("Something went wrong"));
		
		mockMvc.perform(delete("/api/insurance/deselectAllService")
				   .contentType(MediaType.APPLICATION_JSON)
				   .content(objectMapper.writeValueAsString(request)))
				   .andExpect(status().isInternalServerError())
				   .andExpect(jsonPath("$.status").value("Failed"))
				   .andExpect(jsonPath("$.message").value("Failed to delete customer services: Something went wrong"));
	}
	
	@Test
	public void testOnlySelectedServiceDeleted_Success() throws JsonProcessingException, Exception
	{
		DeselectRequest request=new DeselectRequest();
		request.setUserId("1");
		request.setServiceId("2");
		
		CustomerResponse response = new CustomerResponse();
		response.setCustomerId(1L);
		response.setCustomerName("Ramesh Chandak");
		
		when(insuranceAgentService.deleteSelectedService(any(DeselectRequest.class)))
					.thenReturn(response);
		
		mockMvc.perform(delete("/api/insurance/deleteSelectService")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("Success"))
				.andExpect(jsonPath("$.message").value("Customer deleted selected services successfully..."));
	}
	
	@Test
	public void testOnlySelectedServiceDeleted_CustomerNotFound() throws JsonProcessingException, Exception
	{
		DeselectRequest request=new DeselectRequest();
		request.setUserId("1");
		request.setServiceId("2");
		
		when(insuranceAgentService.deleteSelectedService(any(DeselectRequest.class)))
        .thenThrow(new CustomerNotFoundException("Customer not found"));
		
		mockMvc.perform(delete("/api/insurance/deleteSelectService")
				   .contentType(MediaType.APPLICATION_JSON)
				   .content(objectMapper.writeValueAsString(request)))
				   .andExpect(status().isBadRequest())
				   .andExpect(jsonPath("$.status").value("Failed"))
				   .andExpect(jsonPath("$.message").value("Failed to delete customer services: Customer not found"));
	
	}
	
	@Test
	public void testOnlySelectedServiceDeleted_ServiceNotExist() throws JsonProcessingException, Exception
	{
		IDRequest request=new IDRequest();
		request.setUserId("2");
		
		when(insuranceAgentService.deleteSelectedService(any(DeselectRequest.class)))
        .thenThrow(new CustomerServiceNotFoundException("Customer has no services"));
		
		mockMvc.perform(delete("/api/insurance/deleteSelectService")
				   .contentType(MediaType.APPLICATION_JSON)
				   .content(objectMapper.writeValueAsString(request)))
				   .andExpect(status().isBadRequest())
				   .andExpect(jsonPath("$.status").value("Failed"))
				   .andExpect(jsonPath("$.message").value("Failed to delete customer services: Customer has no services"));
	}
	
	@Test
	public void testOnlySelectedServiceDeleted_Exception() throws JsonProcessingException, Exception
	{
		IDRequest request=new IDRequest();
		request.setUserId("2");
		
		when(insuranceAgentService.deleteSelectedService(any(DeselectRequest.class)))
        .thenThrow(new Exception("Something went wrong"));
		
		mockMvc.perform(delete("/api/insurance/deleteSelectService")
				   .contentType(MediaType.APPLICATION_JSON)
				   .content(objectMapper.writeValueAsString(request)))
				   .andExpect(status().isInternalServerError())
				   .andExpect(jsonPath("$.status").value("Failed"))
				   .andExpect(jsonPath("$.message").value("Failed to delete customer services: Something went wrong"));
	}
	
	@Test
	public void testCustomerSelectService_Success() throws JsonProcessingException, Exception
	{
		
		SelectServiceRequest request=new SelectServiceRequest();
		request.setUserId("1");
		request.setAgentId("1");
		request.setServiceNames(List.of("ODP","Body checkup"));
		
		Customer customer=new Customer();
		customer.setUserId(1L);
		
		InsuranceAgent agent=new InsuranceAgent();
		agent.setId(1L);
		agent.setName("Rajiv Gupta");
		agent.setServices(List.of(new Services(1L,"ODP"),new Services(5L,"Body Checkup"),new Services(8L,"Dental Health")));
		
		CustomerServiceRecord record=new CustomerServiceRecord();
		record.setCustomer(customer);
		record.setInsuranceAgent(agent);
		record.setService(new Services(1L,"ODP"));
		record.setService(new Services(5L,"Body Checkup"));
		
		Map<String, List<String>> serviceMap=new HashMap<>();
    	serviceMap.put(agent.getName(), List.of("ODP","Body checkup"));
		
		CustomerResponse response=new CustomerResponse();
		response.setCustomerId(1L);
		response.setServicesList(serviceMap);
		
		when(insuranceAgentService.selectService(any(SelectServiceRequest.class)))
						.thenReturn(response);
		
		mockMvc.perform(post("/api/insurance/selectedService")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
		    	.andExpect(jsonPath("$.message").value("Customer selected services successfully..."));
	}
	
	@Test
	public void testCustomerSelectService_CustomerNotFound() throws JsonProcessingException, Exception
	{
		SelectServiceRequest request=new SelectServiceRequest();
		request.setUserId("1");
		request.setAgentId("1");
		request.setServiceNames(List.of("ODP","Body checkup"));
		
		when(insuranceAgentService.selectService(any(SelectServiceRequest.class)))
								.thenThrow(new CustomerNotFoundException("Customer Not Found"));
		
		mockMvc.perform(post("/api/insurance/selectedService")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").value("Failed to fetch customer services: Customer Not Found"));
	}
	
	@Test
	public void testCustomerSelectService_AgentException() throws JsonProcessingException, Exception
	{
		SelectServiceRequest request=new SelectServiceRequest();
		request.setUserId("1");
		request.setAgentId("1");
		request.setServiceNames(List.of("ODP","Body checkup"));
		
		when(insuranceAgentService.selectService(any(SelectServiceRequest.class)))
								.thenThrow(new InsuranceAgentException("Agent Not Available"));
		
		mockMvc.perform(post("/api/insurance/selectedService")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").value("Failed to fetch customer services: Agent Not Available"));
	}

	@Test
	public void testCustomerSelectService_GenericException() throws JsonProcessingException, Exception
	{
		SelectServiceRequest request=new SelectServiceRequest();
		request.setUserId("1");
		request.setServiceNames(List.of("ODP","Body checkup"));
		
		when(insuranceAgentService.selectService(any(SelectServiceRequest.class)))
								.thenThrow(new Exception("Something went wrong"));
		
		mockMvc.perform(post("/api/insurance/selectedService")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andExpect(status().isInternalServerError())
		.andExpect(jsonPath("$.message").value("Failed to fetch customer services: Something went wrong"));
	}
	
	@Test
	public void testCustomerDetails_Success() throws JsonProcessingException, Exception
	{
		IDRequest request=new IDRequest();
		request.setUserId("1");
		
		CustomerResponse response=new CustomerResponse();
		response.setCustomerId(1L);
		response.setCustomerName("Ramesh Chandak");
		response.setCustomerLocation("Nagpur");
		
		Map<String,List<String>> serviceMap=new HashMap<String, List<String>>();
		serviceMap.put("Rajiv Gupta", List.of("Dental Health","Vision and Eye Care"));
		
		
		response.setServicesList(serviceMap);
		
		when(insuranceAgentService.getCustomerAllDetails(any(IDRequest.class)))
		         .thenReturn(response);
		
		mockMvc.perform(post("/api/insurance/viewCustomer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.message").value("Customer services fetched successfully..."))
		.andExpect(jsonPath("$.data.customerName").value("Ramesh Chandak"));
	}
	
	@Test
	public void testCustomerDetails_CustomerNotFound() throws JsonProcessingException, Exception
	{
		IDRequest request=new IDRequest();
		request.setUserId("1");
		
		when(insuranceAgentService.getCustomerAllDetails(any(IDRequest.class)))
					.thenThrow(new CustomerNotFoundException("Customer Not Found"));
		
		mockMvc.perform(post("/api/insurance/viewCustomer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").value("Failed to fetch customer services: Customer Not Found"));
	}
	
	@Test
	public void testCustomerDetails_Exception() throws JsonProcessingException, Exception
	{
		IDRequest request=new IDRequest();
		request.setUserId("1");
		
		when(insuranceAgentService.getCustomerAllDetails(any(IDRequest.class)))
					.thenThrow(new Exception("Something went wrong"));
		
		mockMvc.perform(post("/api/insurance/viewCustomer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andExpect(status().isInternalServerError())
		.andExpect(jsonPath("$.message").value("Failed to fetch customer services: Something went wrong"));
	}
}
