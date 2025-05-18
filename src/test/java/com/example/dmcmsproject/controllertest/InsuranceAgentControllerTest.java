package com.example.dmcmsproject.controllertest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
import com.example.dmcmsproject.response.CustomerResponse;
import com.example.dmcmsproject.response.InsuranceAgentDetails;
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
    public void findAgentsByName_Success() throws Exception {
        
        NameRequest nameRequest = new NameRequest();
        nameRequest.setName("Rajiv Gupta");
        
        List<NameResponse> expectedAgents = Arrays.asList(
                new NameResponse(),
                new NameResponse()
        );

        expectedAgents.get(0).setServiceId(101L);
        expectedAgents.get(0).setService("Service A");
        expectedAgents.get(0).setAgentId(1L);
        expectedAgents.get(0).setAgentName("Rajiv Gupta");
        expectedAgents.get(0).setLocation("Mumbai");

        expectedAgents.get(1).setServiceId(202L);
        expectedAgents.get(1).setService("Service B");
        expectedAgents.get(1).setAgentId(2L);
        expectedAgents.get(1).setAgentName("Sai Shrivastav");
        expectedAgents.get(1).setLocation("Pune");

        when(insuranceAgentService.getAgentByName(nameRequest.getName())).thenReturn(expectedAgents);

        mockMvc.perform(post("/api/insurance/byAgentName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nameRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Success")))
                .andExpect(jsonPath("$.message", is("Insurance agents services fetched successfully...")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].serviceId", is(101)))
                .andExpect(jsonPath("$.data[0].service", is("Service A")))
                .andExpect(jsonPath("$.data[0].agentId", is(1)))
                .andExpect(jsonPath("$.data[0].agentName", is("Rajiv Gupta")))
                .andExpect(jsonPath("$.data[0].location", is("Mumbai")))
                .andExpect(jsonPath("$.data[1].serviceId", is(202)))
                .andExpect(jsonPath("$.data[1].service", is("Service B")))
                .andExpect(jsonPath("$.data[1].agentId", is(2)))
                .andExpect(jsonPath("$.data[1].agentName", is("Sai Shrivastav")))
                .andExpect(jsonPath("$.data[1].location", is("Pune")));
    }


    @Test
    public void findAgentsByName_InsuranceAgentException() throws Exception {
        
        NameRequest nameRequest = new NameRequest();
        nameRequest.setName("Invalid Name");
       
        when(insuranceAgentService.getAgentByName(nameRequest.getName())).thenThrow(new InsuranceAgentException("Agent not found with this name."));

        
        mockMvc.perform(post("/api/insurance/byAgentName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nameRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Failed to fetch agents: Agent not found with this name.")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void findAgentsByName_GenericException() throws Exception {
        
        NameRequest nameRequest = new NameRequest();
        nameRequest.setName("Problematic Name");
      
        when(insuranceAgentService.getAgentByName(nameRequest.getName())).thenThrow(new RuntimeException("Something went wrong"));

        
        mockMvc.perform(post("/api/insurance/byAgentName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nameRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Failed to fetch agents: Something went wrong")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
    
    @Test
    public void findAgentsByLocation_Success() throws Exception {
        
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setLocation("Mumbai");

        List<ServicesResponse> servicesAgentA = Arrays.asList(
                new ServicesResponse(101L, "Service A"),
                new ServicesResponse(102L, "Service B")
        );

        List<ServicesResponse> servicesAgentB = Collections.singletonList(
                new ServicesResponse(201L, "Service C")
        );

        List<InsuranceAgentResponse> expectedAgents = Arrays.asList(
                new InsuranceAgentResponse(),
                new InsuranceAgentResponse()
        );

        expectedAgents.get(0).setId(1L);
        expectedAgents.get(0).setAgentName("Agent A");
        expectedAgents.get(0).setLocation("Mumbai");
        expectedAgents.get(0).setServiceList(servicesAgentA);

        expectedAgents.get(1).setId(2L);
        expectedAgents.get(1).setAgentName("Agent B");
        expectedAgents.get(1).setLocation("Mumbai");
        expectedAgents.get(1).setServiceList(servicesAgentB);

        when(insuranceAgentService.getAgentsUsingLocation(locationRequest)).thenReturn(expectedAgents);

       
        mockMvc.perform(post("/api/insurance/getInsuranceAgent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Success")))
                .andExpect(jsonPath("$.message", is("Insurance agents fetched successfully...")));
    }

    @Test
    public void findAgentsByLocation_LocationNotFoundException() throws Exception {
        
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setLocation("NonExistentLocation");

        when(insuranceAgentService.getAgentsUsingLocation(any(LocationRequest.class))).thenThrow(new LocationNotFoundException("No agents found in this location."));

       
        mockMvc.perform(post("/api/insurance/getInsuranceAgent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Failed to fetch agents: No agents found in this location.")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void findAgentsByLocation_GenericException() throws Exception {
       
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setLocation("ProblematicLocation");
       
        when(insuranceAgentService.getAgentsUsingLocation(any(LocationRequest.class))).thenThrow(new Exception("Something went wrong"));

       
        mockMvc.perform(post("/api/insurance/getInsuranceAgent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Failed to fetch agents: Something went wrong")))
                .andExpect(jsonPath("$.data").doesNotExist());
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
    public void customerSelectedService_Success() throws Exception {
       
        SelectServiceRequest request = new SelectServiceRequest();
        request.setUserId("123");
        request.setAgentId("456");
        request.setServiceNames(Arrays.asList("1", "2"));

        InsuranceAgentDetails agentDetails = new InsuranceAgentDetails();
        agentDetails.setAgentId("456");
        agentDetails.setAgentName("Agent A");
        
        List<ServicesResponse> selectedServices = Arrays.asList(
                new ServicesResponse(1L, "Service A"),
                new ServicesResponse(2L, "Service B")
        );
        
        Map<InsuranceAgentDetails, List<ServicesResponse>> servicesMap = new HashMap<>();
        servicesMap.put(agentDetails, selectedServices);
        
        CustomerResponse expectedResponse = new CustomerResponse();
        expectedResponse.setCustomerId(123L);
        expectedResponse.setCustomerName("Customer A");
        expectedResponse.setCustomerLocation("Mumbai");
        expectedResponse.setServicesList(servicesMap);
        
        when(insuranceAgentService.selectService(any(SelectServiceRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/insurance/selectedService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Success")))
                .andExpect(jsonPath("$.message", is("Customer selected services successfully...")))
                .andExpect(jsonPath("$.data.customerId", is(123)));
//                .andExpect(jsonPath("$.data.selectedServices", is(Arrays.asList("Service A", "Service B"))));
    }

    @Test
    public void customerSelectedService_CustomerNotFoundException() throws Exception {
     
        SelectServiceRequest request = new SelectServiceRequest();
        request.setUserId("999");
        request.setAgentId("111");
        request.setServiceNames(Collections.singletonList("3"));

     
        when(insuranceAgentService.selectService(any(SelectServiceRequest.class))).thenThrow(new CustomerNotFoundException("Customer not found."));

       
        mockMvc.perform(post("/api/insurance/selectedService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Failed to fetch customer services: Customer not found.")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void customerSelectedService_InsuranceAgentException() throws Exception {
       
        SelectServiceRequest request = new SelectServiceRequest();
        request.setUserId("222");
        request.setAgentId("888");
        request.setServiceNames(Arrays.asList("4", "5"));

        when(insuranceAgentService.selectService(any(SelectServiceRequest.class))).thenThrow(new InsuranceAgentException("Insurance agent not found."));

        
        mockMvc.perform(post("/api/insurance/selectedService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Failed to fetch customer services: Insurance agent not found.")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void customerSelectedService_NumberFormatException() throws Exception {
        
        SelectServiceRequest request = new SelectServiceRequest();
        request.setUserId(("abc")); 
        request.setAgentId("456");
        request.setServiceNames(Collections.singletonList("1"));

        when(insuranceAgentService.selectService(any(SelectServiceRequest.class))).thenThrow(new NumberFormatException());

       
        mockMvc.perform(post("/api/insurance/selectedService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Invalid userId and agentId: Must be a numeric value.")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void customerSelectedService_GenericException() throws Exception {
        
        SelectServiceRequest request = new SelectServiceRequest();
        request.setUserId("789");
        request.setAgentId("321");
        request.setServiceNames(Arrays.asList("6", "7", "8"));

        when(insuranceAgentService.selectService(any(SelectServiceRequest.class))).thenThrow(new Exception("Something went wrong"));

       
        mockMvc.perform(post("/api/insurance/selectedService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Failed to fetch customer services: Something went wrong")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
	
	@Test
    public void customerDetails_Success() throws Exception {
        
        IDRequest request = new IDRequest();
        request.setUserId("123");

        InsuranceAgentDetails agent1 = new InsuranceAgentDetails();
        agent1.setAgentId("456");
        agent1.setAgentName("Agent A");

        List<ServicesResponse> services1 = Arrays.asList(
                new ServicesResponse(1L, "Service A"),
                new ServicesResponse(2L, "Service B")
        );

        InsuranceAgentDetails agent2 = new InsuranceAgentDetails();
        agent2.setAgentId("789");
        agent2.setAgentName("Agent B");

        List<ServicesResponse> services2 = Collections.singletonList(
                new ServicesResponse(3L, "Service C")
        );

        Map<InsuranceAgentDetails, List<ServicesResponse>> servicesMap = new HashMap<>();
        servicesMap.put(agent1, services1);
        servicesMap.put(agent2, services2);

        CustomerResponse expectedResponse = new CustomerResponse();
        expectedResponse.setCustomerId(123L);
        expectedResponse.setCustomerName("Rajiv Gupta");
        expectedResponse.setCustomerLocation("Mumbai");
        expectedResponse.setServicesList(servicesMap);

        when(insuranceAgentService.getCustomerAllDetails(any(IDRequest.class))).thenReturn(expectedResponse);

        
        mockMvc.perform(post("/api/insurance/viewCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Success")))
                .andExpect(jsonPath("$.message", is("Customer services fetched successfully...")))
                .andExpect(jsonPath("$.data.customerId", is(123)))
                .andExpect(jsonPath("$.data.customerName", is("Rajiv Gupta")))
                .andExpect(jsonPath("$.data.customerLocation", is("Mumbai")));
    }

    @Test
    public void customerDetails_CustomerNotFoundException() throws Exception {
       
        IDRequest request = new IDRequest();
        request.setUserId("999");
        
        when(insuranceAgentService.getCustomerAllDetails(any(IDRequest.class))).thenThrow(new CustomerNotFoundException("Customer not found"));

       
        mockMvc.perform(post("/api/insurance/viewCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Failed to fetch customer services: Customer not found")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void customerDetails_NumberFormatException() throws Exception {
        
        IDRequest request = new IDRequest();
        request.setUserId("abc");
        
        
        when(insuranceAgentService.getCustomerAllDetails(any(IDRequest.class))).thenThrow(new NumberFormatException());

        
        mockMvc.perform(post("/api/insurance/viewCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("Failed")))
                .andExpect(jsonPath("$.message", is("Invalid userId: Must be a numeric value.")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void customerDetails_GenericException() throws Exception {


        IDRequest request = new IDRequest();
        request.setUserId("456");
       
        when(insuranceAgentService.getCustomerAllDetails(any(IDRequest.class))).thenThrow(new Exception("Something went wrong"));

        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/insurance/viewCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("Failed")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Failed to fetch customer services: Something went wrong")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist());
    }
}
