package com.example.dmcmsproject.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

	
	@Test
	public void testDeleteAllServices_ShouldDeleteSuccessfullyAndReturnResponse() throws Exception
	{
		String userId="2";
		
		Customer customer=new Customer();
		customer.setUserId(2L);
		customer.setCname("Ramesh Chandak");
		
		InsuranceAgent agent=new InsuranceAgent();
		agent.setName("Rajiv Gupta");
		
		Services service=new Services();
		service.setService("General Health Checkup");
		
		CustomerServiceRecord record=new CustomerServiceRecord();
		record.setInsuranceAgent(agent);
		record.setService(service);
		
		List<CustomerServiceRecord> records=new ArrayList<CustomerServiceRecord>();
		records.add(record);
		
		customer.setCustomerRecords(records);
		
		when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));
		
		CustomerResponse response = insuranceAgentService.deleteAllServices(userId);
		
		assertEquals(2L, response.getCustomerId());
		assertEquals("Ramesh Chandak", response.getCustomerName());
		assertTrue(response.getServicesList().get("Rajiv Gupta").contains("General Health Checkup"));
		
		verify(customerServiceRecordsRepository).deleteAll(records);
		verify(customerRepository).save(customer);
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
	
	@Test
	public void testDeleteSelectedService_Success() throws Exception
	{
		DeselectRequest request = new DeselectRequest();
		request.setUserId("1");
		request.setServiceId("101");
		
		Customer customer=new Customer();
		customer.setUserId(1L);
		customer.setCname("Ramesh Chandak");
		
		Services service=new Services();
		service.setId(101L);
		service.setService("Dental Care");
		
		InsuranceAgent agent=new InsuranceAgent();
		agent.setName("Rajiv Gupta");
		
		CustomerServiceRecord record=new CustomerServiceRecord();
		record.setService(service);
		record.setInsuranceAgent(agent);
		
		List<CustomerServiceRecord> records=new ArrayList<>();
		records.add(record);
		customer.setCustomerRecords(records);
		
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(servicesRepository.findById(101L)).thenReturn(Optional.of(service));
		
		CustomerResponse response=insuranceAgentService.deleteSelectedService(request);
		
		assertEquals(1L,response.getCustomerId());
		assertEquals("Ramesh Chandak",response.getCustomerName());
		
		Map<String, List<String>> servicesMap=response.getServicesList();
		assertNotNull(servicesMap);
		
		if(servicesMap.containsKey("Rajiv Gupta"))
		{
			List<String> services=servicesMap.get("Rajiv Gupta");
			assertFalse(services.contains("Dental Care"));
		}
		
		verify(customerServiceRecordsRepository).delete(record);
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
	
	@Test
	public void testSelectService_Success() throws NumberFormatException, Exception
	{
		
		SelectServiceRequest request=new SelectServiceRequest();
		request.setUserId("1");
		request.setAgentId("2");
		request.setServiceNames(List.of("Eye Checkup","Dental Care"));
		
		Customer customer =new Customer();
		customer.setUserId(1L);
		customer.setCname("Ramesh Chandak");
		
		Services service1=new Services();
		service1.setId(101L);
		service1.setService("Eye Checkup");
		
		Services service2=new Services();
		service2.setId(102L);
		service2.setService("Dental Care");
		
		InsuranceAgent agent=new InsuranceAgent();
		agent.setId(2L);
		agent.setName("Rajiv Gupta");
		agent.setServices(Arrays.asList(service1,service2));
		
		List<Services> matchedServices=List.of(service1,service2);
		
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		when(insuranceAgentRepository.findById(2L)).thenReturn(Optional.of(agent));
		when(servicesRepository.findByServiceIn(request.getServiceNames())).thenReturn(matchedServices);
		
		CustomerResponse response = insuranceAgentService.selectService(request);
		
		assertNotNull(response);
		assertEquals(1L,response.getCustomerId());
		assertEquals("Ramesh Chandak",response.getCustomerName());
		assertTrue(response.getServicesList().containsKey("Rajiv Gupta"));
		assertEquals(List.of("Eye Checkup","Dental Care"),response.getServicesList().get("Rajiv Gupta"));
		
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
	
	@Test
	public void testGetCustomerDetails_Success() throws NumberFormatException, Exception
	{
		IDRequest request=new IDRequest();
		request.setUserId("1");
		
		Customer customer=new Customer();
		customer.setUserId(1L);
		customer.setCname("Ramesh Chandak");
		
		InsuranceAgent agent=new InsuranceAgent();
		agent.setName("Rajiv Gupta");
		
		Services service=new Services();
		service.setService("Dental Care");
		
		CustomerServiceRecord record=new CustomerServiceRecord();
		record.setInsuranceAgent(agent);
		record.setService(service);
		
		customer.setCustomerRecords(List.of(record));
		
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		
		CustomerResponse response=insuranceAgentService.getCustomerAllDetails(request);
		
		assertEquals(1L,response.getCustomerId());
		assertEquals("Ramesh Chandak",response.getCustomerName());
		assertTrue(response.getServicesList().containsKey("Rajiv Gupta"));
		assertTrue(response.getServicesList().get("Rajiv Gupta").contains("Dental Care"));
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
