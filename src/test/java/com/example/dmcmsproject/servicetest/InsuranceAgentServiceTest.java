package com.example.dmcmsproject.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.example.dmcmsproject.response.InsuranceAgentDetails;
import com.example.dmcmsproject.response.InsuranceAgentResponse;
import com.example.dmcmsproject.response.NameResponse;
import com.example.dmcmsproject.response.ServicesResponse;
import com.example.dmcmsproject.service.InsuranceAgentService;

@ExtendWith(MockitoExtension.class)
public class InsuranceAgentServiceTest {

	@InjectMocks
	private InsuranceAgentService insuranceAgentService;
	@Mock
	private InsuranceAgentRepository insuranceAgentRepository;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private CustomerServiceRecordsRepository customerServiceRecordsRepository;
	@Mock
	private ServicesRepository servicesRepository;
	
	@Test
	public void testAgentGetByName_WhenAgentExists_ReturnServiceList() throws InsuranceAgentException
	{
		Services service1=new Services();
		service1.setId(1L);
		service1.setService("Dental Care");
		
		Services service2=new Services();
		service2.setId(2L);
		service2.setService("Eye Checkup");
		
		InsuranceAgent agent1=new InsuranceAgent();
		agent1.setName("Rajiv Gupta");
		agent1.setServices(Arrays.asList(service1,service2));
	
		Services service3=new Services();
		service3.setId(4L);
		service3.setService("General Health Checkup");
		
		Services service4=new Services();
		service4.setId(6L);
		service4.setService("Heart Insurance");
		
		InsuranceAgent agent2=new InsuranceAgent();
		agent2.setName("Rajiv Gupta");
		agent2.setServices(Arrays.asList(service3,service4));
		
		NameResponse response1=new NameResponse();
		response1.setAgentId(agent1.getId());
		response1.setAgentName(agent1.getName());
		response1.setServiceId(agent1.getServices().get(0).getId());
		response1.setService(agent1.getServices().get(0).getService());
		
		NameResponse response2=new NameResponse();
		response2.setAgentId(agent2.getId());
		response2.setAgentName(agent2.getName());
		response2.setServiceId(agent2.getServices().get(1).getId());
		response2.setService(agent2.getServices().get(1).getService());
		
		List<InsuranceAgent> responseList=List.of(agent1,agent2);
		
		when(insuranceAgentRepository.findAgentByName("Rajiv Gupta")).thenReturn(responseList);
		
		List<NameResponse> nameResponseList = insuranceAgentService.getAgentByName("Rajiv Gupta");
		
		assertNotNull(nameResponseList);
		assertEquals(4, nameResponseList.size());
		assertEquals("Dental Care",nameResponseList.get(0).getService());
		assertEquals("General Health Checkup",nameResponseList.get(2).getService());
		
		verify(insuranceAgentRepository).findAgentByName("Rajiv Gupta");
	}

	
	
	@Test
	public void testGetByName_WhenAgentNotFound_ThrowsException() throws InsuranceAgentException
	{
		String name="Unknown Agent";
		
		when(insuranceAgentRepository.findAgentByName(name))
				.thenReturn(Collections.emptyList());
		
		assertThrows(InsuranceAgentException.class, () -> {insuranceAgentService.getAgentByName(name);});
	
	}

	
	@Test
	public void testGetAgentsByLocation_ReturnsAgentList() throws Exception
	{
		LocationRequest request=new LocationRequest();
		request.setLocation("Mumbai");
		
		InsuranceAgent agent = new InsuranceAgent();
		agent.setId(101L);
		agent.setName("Rajiv Gupta");
		agent.setLocation("Mumbai");
		
		Services service1=new Services();
		service1.setId(201L);
		service1.setService("Dental Care");
		
		agent.setServices(List.of(service1));
		
		when(insuranceAgentRepository.findByLocation("Mumbai")).thenReturn(List.of(agent));
		
		
		List<InsuranceAgentResponse> responses=insuranceAgentService.getAgentsUsingLocation(request);
		
		assertNotNull(responses);
		assertEquals(1, responses.size());
		InsuranceAgentResponse response=responses.get(0);
		assertEquals("Rajiv Gupta",response.getAgentName());
		assertEquals("Mumbai",response.getLocation());
		assertEquals(1,response.getServiceList().size());
		assertEquals("Dental Care",response.getServiceList().get(0).getService());
	}
	
	@Test
	public void testGetAgentsByLocation_NoAgentsFound_ThrowsException()
	{
		LocationRequest request=new LocationRequest();
		request.setLocation("Mumbai");

		when(insuranceAgentRepository.findByLocation("Mumbai")).thenReturn(Collections.emptyList());
		
		assertThrows(LocationNotFoundException.class, () -> insuranceAgentService.getAgentsUsingLocation(request));
	}

	
//	@Test
//	public void testDeleteAllServices_ShouldDeleteSuccessfully() throws Exception
//	{
//		String userId="2";
//		
//		Customer customer=new Customer();
//		customer.setUserId(2L);
//		customer.setCname("Ramesh Chandak");
//		
//		InsuranceAgent agent=new InsuranceAgent();
//		agent.setName("Rajiv Gupta");
//		
//		Services service=new Services();
//		service.setService("General Health Checkup");
//		
//		CustomerServiceRecord record=new CustomerServiceRecord();
//		record.setInsuranceAgent(agent);
//		record.setService(service);
//		
//		List<CustomerServiceRecord> records=new ArrayList<CustomerServiceRecord>();
//		records.add(record);
//		
//		customer.setCustomerRecords(records);
//		
//		when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));
//		
//		CustomerResponse response = insuranceAgentService.deleteAllServices(userId);
//		
//		assertEquals(2L, response.getCustomerId());
//		assertEquals("Ramesh Chandak", response.getCustomerName());
//		assertTrue(response.getServicesList().get("Rajiv Gupta").contains("General Health Checkup"));
//		
//		verify(customerServiceRecordsRepository).deleteAll(records);
//		verify(customerRepository).save(customer);
//	}
	
	 @Test
	  public void deleteAllServices_Success() throws Exception {
	        
	        String userId = "123";
	        long customerId = Long.parseLong(userId);

	       
	        Customer customer = new Customer();
	        customer.setUserId(customerId);
	        customer.setCname("John Doe");
	        customer.setLocation("Mumbai");
	        List<CustomerServiceRecord> records = new ArrayList<>();

	       
	        InsuranceAgent agent1 = new InsuranceAgent();
	        agent1.setId(101L);
	        agent1.setName("Agent One");
	        agent1.setLocation("Delhi");
	        agent1.setEmail("agent1@example.com");
	        agent1.setContactNo("9876543210");

	        InsuranceAgent agent2 = new InsuranceAgent();
	        agent2.setId(102L);
	        agent2.setName("Agent Two");
	        agent2.setLocation("Kolkata");
	        agent2.setEmail("agent2@example.com");
	        agent2.setContactNo("8765432109");

	      
	        Services service1 = new Services();
	        service1.setId(1L);
	        service1.setService("Life Insurance");

	        Services service2 = new Services();
	        service2.setId(2L);
	        service2.setService("Health Insurance");

	       
	        CustomerServiceRecord record1 = new CustomerServiceRecord();
	        record1.setCustomer(customer);
	        record1.setInsuranceAgent(agent1);
	        record1.setService(service1);
	        records.add(record1);

	        CustomerServiceRecord record2 = new CustomerServiceRecord();
	        record2.setCustomer(customer);
	        record2.setInsuranceAgent(agent2);
	        record2.setService(service2);
	        records.add(record2);

	        customer.setCustomerRecords(records);

	        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
	        
	        CustomerResponse response = insuranceAgentService.deleteAllServices(userId);

	        assertNotNull(response);
	        assertEquals(customerId, response.getCustomerId());
	        assertEquals("John Doe", response.getCustomerName());
	        assertEquals("Mumbai", response.getCustomerLocation());
	        assertNotNull(response.getServicesList());
	        assertEquals(2, response.getServicesList().size());

	  
	        Map<InsuranceAgentDetails, List<ServicesResponse>> deletedServicesMap = response.getServicesList();

	
	        InsuranceAgentDetails agentDetails1 = new InsuranceAgentDetails();
	        agentDetails1.setAgentId("101");
	        agentDetails1.setAgentName("Agent One");
	        agentDetails1.setLocation("Delhi");
	        agentDetails1.setEmail("agent1@example.com");
	        agentDetails1.setContactInfo("9876543210");

	        assertEquals(1, deletedServicesMap.get(agentDetails1).size());
	        assertEquals(1L, deletedServicesMap.get(agentDetails1).get(0).getId());
	        assertEquals("Life Insurance", deletedServicesMap.get(agentDetails1).get(0).getService());

	       
	        InsuranceAgentDetails agentDetails2 = new InsuranceAgentDetails();
	        agentDetails2.setAgentId("102");
	        agentDetails2.setAgentName("Agent Two");
	        agentDetails2.setLocation("Kolkata");
	        agentDetails2.setEmail("agent2@example.com");
	        agentDetails2.setContactInfo("8765432109");

	        assertEquals(1, deletedServicesMap.get(agentDetails2).size());
	        assertEquals(2L, deletedServicesMap.get(agentDetails2).get(0).getId());
	        assertEquals("Health Insurance", deletedServicesMap.get(agentDetails2).get(0).getService());

	        
	        verify(customerServiceRecordsRepository, times(1)).deleteAll(records);
	        verify(customerRepository, times(1)).save(customer);
	    }

	
	@Test
	public void testDeleteAllServices_CustomerNotFound()
	{
		String userId="999";
		
		when(customerRepository.findById(999L)).thenReturn(Optional.empty());
		
		assertThrows(CustomerNotFoundException.class, () -> {insuranceAgentService.deleteAllServices(userId);});
		
		verify(customerServiceRecordsRepository,never()).deleteAll(any());
		verify(customerRepository,never()).save(any());
	}
	
	@Test
	public void testDeleteAllServices_NoFoundServices() throws NumberFormatException, CustomerNotFoundException, CustomerServiceNotFoundException
	{
		String userId="2";
		
		Customer customer=new Customer();
		customer.setUserId(2L);
		customer.setCustomerRecords(Collections.emptyList());
		
		when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));
		
		assertThrows(CustomerServiceNotFoundException.class, () -> {insuranceAgentService.deleteAllServices(userId);});
		
	}
	
//	@Test
//	public void testDeleteSelectedService_Success() throws Exception
//	{
//		DeselectRequest request = new DeselectRequest();
//		request.setUserId("1");
//		request.setServiceId("101");
//		
//		Customer customer=new Customer();
//		customer.setUserId(1L);
//		customer.setCname("Ramesh Chandak");
//		
//		Services service=new Services();
//		service.setId(101L);
//		service.setService("Dental Care");
//		
//		InsuranceAgent agent=new InsuranceAgent();
//		agent.setName("Rajiv Gupta");
//		
//		InsuranceAgentDetails agentDetails=new InsuranceAgentDetails();
//		agentDetails.setAgentName(agent.getName());
//		
//		CustomerServiceRecord record=new CustomerServiceRecord();
//		record.setService(service);
//		record.setInsuranceAgent(agent);
//		
//		List<CustomerServiceRecord> records=new ArrayList<>();
//		records.add(record);
//		customer.setCustomerRecords(records);
//		
//		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
//		when(servicesRepository.findById(101L)).thenReturn(Optional.of(service));
//		
//		CustomerResponse response=insuranceAgentService.deleteSelectedService(request);
//		
//		assertEquals(1L,response.getCustomerId());
//		assertEquals("Ramesh Chandak",response.getCustomerName());
//		
//		Map<InsuranceAgentDetails, List<ServicesResponse>> servicesMap=response.getServicesList();
//		assertNotNull(servicesMap);
//		
//		if(servicesMap.containsKey(agentDetails))
//		{
//			List<ServicesResponse> services=new ArrayList<ServicesResponse>();
//			services.add(service);
//			assertFalse(services.contains("Dental Care"));
//		}
//		
//		verify(customerServiceRecordsRepository).delete(record);
//		verify(customerRepository).save(customer);	
//	}
	
	@Test
	public void testDeleteSelectedService_Success() throws Exception {
	    DeselectRequest request = new DeselectRequest();
	    request.setUserId("1");
	    request.setServiceId("101");

	    Customer customer = new Customer();
	    customer.setUserId(1L);
	    customer.setCname("Ramesh Chandak");

	    Services serviceToDelete = new Services();
	    serviceToDelete.setId(101L);
	    serviceToDelete.setService("Dental Care");

	    Services anotherService = new Services();
	    anotherService.setId(102L);
	    anotherService.setService("Eye Care");

	    InsuranceAgent agent1 = new InsuranceAgent();
	    agent1.setName("Rajiv Gupta");

	    InsuranceAgent agent2 = new InsuranceAgent();
	    agent2.setName("Sunita Verma");

	    CustomerServiceRecord recordToDelete = new CustomerServiceRecord();
	    recordToDelete.setService(serviceToDelete);
	    recordToDelete.setInsuranceAgent(agent1);

	    CustomerServiceRecord recordToKeep = new CustomerServiceRecord();
	    recordToKeep.setService(anotherService);
	    recordToKeep.setInsuranceAgent(agent2);

	    List<CustomerServiceRecord> records = new ArrayList<>();
	    records.add(recordToDelete);
	    records.add(recordToKeep);
	    customer.setCustomerRecords(records);

	    when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
	    when(servicesRepository.findById(101L)).thenReturn(Optional.of(serviceToDelete));

	    CustomerResponse response = insuranceAgentService.deleteSelectedService(request);

	    assertEquals(1L, response.getCustomerId());
	    assertEquals("Ramesh Chandak", response.getCustomerName());

	    Map<InsuranceAgentDetails, List<ServicesResponse>> servicesMap = response.getServicesList();
	    assertNotNull(servicesMap);
	    assertEquals(1, servicesMap.size()); 
	    
	    ServicesResponse servicesResponse=new ServicesResponse();
	    servicesResponse.setId(anotherService.getId());
	    servicesResponse.setService(anotherService.getService());

	    InsuranceAgentDetails agentDetails2 = new InsuranceAgentDetails();
	    agentDetails2.setAgentName("Sunita Verma");
	    
	    servicesMap.put(agentDetails2,List.of(servicesResponse));
	    

	    assertTrue(servicesMap.containsKey(agentDetails2));
	    assertEquals(1, servicesMap.get(agentDetails2).size());
	    assertEquals("Eye Care", servicesMap.get(agentDetails2).get(0).getService());

	    verify(customerServiceRecordsRepository).delete(recordToDelete);
	    verify(customerRepository).save(customer);
	}
	
	@Test
	public void testDeleteSelectedService_ServiceNotFoundInCustomer_ThrowsException()
	{
		DeselectRequest request = new DeselectRequest();
		request.setUserId("1");
		request.setServiceId("999");
		
		Customer customer=new Customer();
		customer.setUserId(1L);
		customer.setCustomerRecords(new ArrayList<>());
		
		Services service=new Services();
		service.setId(999L);
		
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(servicesRepository.findById(999L)).thenReturn(Optional.empty());
		
		assertThrows(CustomerServiceNotFoundException.class, () -> {
			insuranceAgentService.deleteSelectedService(request);
		});
		
		verify(customerServiceRecordsRepository,never()).delete(any());
		verify(customerRepository,never()).save(any());
	}

	@Test
	public void testDeleteSelectedService_CustomerNotFound()
	{
		DeselectRequest request = new DeselectRequest();
		request.setUserId("1");
		request.setServiceId("999");
		
		when(customerRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(CustomerNotFoundException.class,()->{
			insuranceAgentService.deleteSelectedService(request);
		});
		
		verify(servicesRepository,never()).findById(any());
	}
	
//	@Test
//	public void testSelectService_Success() throws NumberFormatException, Exception
//	{
//		
//		SelectServiceRequest request=new SelectServiceRequest();
//		request.setUserId("1");
//		request.setAgentId("2");
//		request.setServiceNames(List.of("Eye Checkup","Dental Care"));
//		
//		Customer customer =new Customer();
//		customer.setUserId(1L);
//		customer.setCname("Ramesh Chandak");
//		
//		Services service1=new Services();
//		service1.setId(101L);
//		service1.setService("Eye Checkup");
//		
//		Services service2=new Services();
//		service2.setId(102L);
//		service2.setService("Dental Care");
//		
//		InsuranceAgent agent=new InsuranceAgent();
//		agent.setId(2L);
//		agent.setName("Rajiv Gupta");
//		agent.setServices(Arrays.asList(service1,service2));
//		
//		List<Services> matchedServices=List.of(service1,service2);
//		
//		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
//		when(insuranceAgentRepository.findById(2L)).thenReturn(Optional.of(agent));
//		when(servicesRepository.findByServiceIn(request.getServiceNames())).thenReturn(matchedServices);
//		
//		CustomerResponse response = insuranceAgentService.selectService(request);
//		
//		assertNotNull(response);
//		assertEquals(1L,response.getCustomerId());
//		assertEquals("Ramesh Chandak",response.getCustomerName());
//		
//		verify(customerServiceRecordsRepository, times(2)).save(any(CustomerServiceRecord.class));
//	}
	
	 @Test
	 public void testselectService_Success() throws Exception {
	        
	        SelectServiceRequest request = new SelectServiceRequest();
	        request.setUserId("100");
	        request.setAgentId("200");
	        request.setServiceNames(Arrays.asList("Service A", "Service B"));

	        long customerId = Long.parseLong(request.getUserId());
	        long agentId = Long.parseLong(request.getAgentId());

	        Customer customer = new Customer();
	        customer.setUserId(customerId);
	        customer.setCname("Alice");
	        customer.setLocation("New York");

	        InsuranceAgent agent = new InsuranceAgent();
	        agent.setId(agentId);
	        agent.setName("Bob");
	        agent.setLocation("Chicago");
	        agent.setEmail("bob@example.com");
	        agent.setContactNo("1234567890");
	        
	        List<Services> agentProvidedServices = Arrays.asList(
	                new Services(1L, "Service A"),
	                new Services(2L, "Service B")
	        );
	        agent.setServices(agentProvidedServices);

	        List<Services> requestedServices = Arrays.asList(
	                new Services(1L, "Service A"),
	                new Services(2L, "Service B")
	        );

	        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
	        when(insuranceAgentRepository.findById(agentId)).thenReturn(Optional.of(agent));
	        when(servicesRepository.findByServiceIn(request.getServiceNames())).thenReturn(requestedServices);

	       
	        CustomerResponse response = insuranceAgentService.selectService(request);

	       
	        assertNotNull(response);
	        assertEquals(customerId, response.getCustomerId());
	        assertEquals("Alice", response.getCustomerName());
	        assertEquals("New York", response.getCustomerLocation());
	        assertNotNull(response.getServicesList());
	        assertEquals(1, response.getServicesList().size());

	        InsuranceAgentDetails agentDetails = new InsuranceAgentDetails();
	        agentDetails.setAgentId(String.valueOf(agentId));
	        agentDetails.setAgentName("Bob");
	        agentDetails.setLocation("Chicago");
	        agentDetails.setEmail("bob@example.com");
	        agentDetails.setContactInfo("1234567890");

	        assertTrue(response.getServicesList().containsKey(agentDetails));
	        List<ServicesResponse> serviceResponses = response.getServicesList().get(agentDetails);
	        assertEquals(2, serviceResponses.size());
	        assertEquals("Service A", serviceResponses.get(0).getService());
	        assertEquals("Service B", serviceResponses.get(1).getService());

	        verify(customerRepository, times(1)).findById(customerId);
	        verify(insuranceAgentRepository, times(1)).findById(agentId);
	        verify(servicesRepository, times(1)).findByServiceIn(request.getServiceNames());
	        verify(customerServiceRecordsRepository, times(2)).save(any(CustomerServiceRecord.class)); 
	    }
	
	@Test
	public void testSelectService_AgentNotFound_ThrowsException()
	{
		SelectServiceRequest request=new SelectServiceRequest();
		request.setUserId("1");
		request.setAgentId("2");
		
		InsuranceAgent agent=new InsuranceAgent();
		agent.setId(2L);
		
		Customer customer=new Customer();
		customer.setUserId(1L);
		customer.setCname("Ramesh Chandak");
		
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(insuranceAgentRepository.findById(2L)).thenReturn(Optional.empty());
		
		assertThrows(InsuranceAgentException.class, () -> {insuranceAgentService.selectService(request);});
	}
	
	@Test
	public void testSelectService_NotFoundServices_ThrowsException()
	{
		SelectServiceRequest request=new SelectServiceRequest();
		request.setUserId("1");
		request.setAgentId("2");
		request.setServiceNames(List.of("General Health","Emergency Services"));
		
		Services service=new Services();
		service.setService("Body Checkup");
		
		InsuranceAgent agent=new InsuranceAgent();
		agent.setId(2L);
		
		CustomerServiceRecord record=new CustomerServiceRecord();
		record.setInsuranceAgent(agent);
		record.setService(service);
		
		Customer customer=new Customer();
		customer.setUserId(1L);
		
		customer.setCustomerRecords(List.of(record));
		
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
	
		assertThrows(InsuranceAgentException.class, () -> {insuranceAgentService.selectService(request);});
	}
	
//	@Test
//	public void testGetCustomerDetails_Success() throws NumberFormatException, Exception
//	{
//		IDRequest request=new IDRequest();
//		request.setUserId("1");
//		
//		Customer customer=new Customer();
//		customer.setUserId(1L);
//		customer.setCname("Ramesh Chandak");
//		
//		InsuranceAgent agent=new InsuranceAgent();
//		agent.setName("Rajiv Gupta");
//		
//		Services service=new Services();
//		service.setService("Dental Care");
//		
//		CustomerServiceRecord record=new CustomerServiceRecord();
//		record.setInsuranceAgent(agent);
//		record.setService(service);
//		
//		customer.setCustomerRecords(List.of(record));
//		
//		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
//		
//		CustomerResponse response=insuranceAgentService.getCustomerAllDetails(request);
//		
//		assertEquals(1L,response.getCustomerId());
//		assertEquals("Ramesh Chandak",response.getCustomerName());
//		
//	}
	
	 @Test
	 public void testGetCustomerDetails_Success() throws Exception {
	       
	        IDRequest request = new IDRequest();
	        request.setUserId("101");
	        long userId = Long.parseLong(request.getUserId());

	        Customer customer = new Customer();
	        customer.setUserId(userId);
	        customer.setCname("Charlie");
	        customer.setLocation("Bangalore");

	        InsuranceAgent agent1 = new InsuranceAgent();
	        agent1.setId(201L);
	        agent1.setName("Agent Alpha");
	        agent1.setLocation("Pune");
	        agent1.setEmail("alpha@example.com");
	        agent1.setContactNo("7890123456");

	        Services service1 = new Services(301L, "Home Insurance");

	        CustomerServiceRecord record1 = new CustomerServiceRecord();
	        record1.setCustomer(customer);
	        record1.setInsuranceAgent(agent1);
	        record1.setService(service1);

	        InsuranceAgent agent2 = new InsuranceAgent();
	        agent2.setId(202L);
	        agent2.setName("Agent Beta");
	        agent2.setLocation("Chennai");
	        agent2.setEmail("beta@example.com");
	        agent2.setContactNo("6543210987");

	        Services service2 = new Services(302L, "Car Insurance");

	        CustomerServiceRecord record2 = new CustomerServiceRecord();
	        record2.setCustomer(customer);
	        record2.setInsuranceAgent(agent2);
	        record2.setService(service2);

	        customer.setCustomerRecords(Arrays.asList(record1, record2));

	        when(customerRepository.findById(userId)).thenReturn(Optional.of(customer));

	        
	        CustomerResponse response = insuranceAgentService.getCustomerAllDetails(request);

	        
	        assertNotNull(response);
	        assertEquals(userId, response.getCustomerId());
	        assertEquals("Charlie", response.getCustomerName());
	        assertEquals("Bangalore", response.getCustomerLocation());
	        assertNotNull(response.getServicesList());
	        assertEquals(2, response.getServicesList().size());

	        
	        InsuranceAgentDetails agentDetailsAlpha = new InsuranceAgentDetails();
	        agentDetailsAlpha.setAgentId("201");
	        agentDetailsAlpha.setAgentName("Agent Alpha");
	        agentDetailsAlpha.setLocation("Pune");
	        agentDetailsAlpha.setEmail("alpha@example.com");
	        agentDetailsAlpha.setContactInfo("7890123456");

	        assertTrue(response.getServicesList().containsKey(agentDetailsAlpha));
	        List<ServicesResponse> servicesAlpha = response.getServicesList().get(agentDetailsAlpha);
	        assertEquals(1, servicesAlpha.size());
	        assertEquals(301L, servicesAlpha.get(0).getId());
	        assertEquals("Home Insurance", servicesAlpha.get(0).getService());

	        
	        InsuranceAgentDetails agentDetailsBeta = new InsuranceAgentDetails();
	        agentDetailsBeta.setAgentId("202");
	        agentDetailsBeta.setAgentName("Agent Beta");
	        agentDetailsBeta.setLocation("Chennai");
	        agentDetailsBeta.setEmail("beta@example.com");
	        agentDetailsBeta.setContactInfo("6543210987");

	        assertTrue(response.getServicesList().containsKey(agentDetailsBeta));
	        List<ServicesResponse> servicesBeta = response.getServicesList().get(agentDetailsBeta);
	        assertEquals(1, servicesBeta.size());
	        assertEquals(302L, servicesBeta.get(0).getId());
	        assertEquals("Car Insurance", servicesBeta.get(0).getService());
	    }
	
	@Test
	public void testGetCustomerDetails_CustomerNotFound_ThrowsException()
	{
		IDRequest request=new IDRequest();
		request.setUserId("1");
		
		when(customerRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(CustomerNotFoundException.class, () -> insuranceAgentService.getCustomerAllDetails(request));
	}
}
