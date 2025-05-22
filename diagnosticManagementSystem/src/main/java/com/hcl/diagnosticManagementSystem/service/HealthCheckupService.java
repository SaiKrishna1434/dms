package com.hcl.diagnosticManagementSystem.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.diagnosticManagementSystem.dto.HealthCheckupApplicationResponseDTO;
import com.hcl.diagnosticManagementSystem.dto.HealthCheckupCreationRequestDTO;
import com.hcl.diagnosticManagementSystem.entity.Customer;
import com.hcl.diagnosticManagementSystem.entity.CustomerHealthCheckupApplication;
import com.hcl.diagnosticManagementSystem.entity.HealthCheckup;
import com.hcl.diagnosticManagementSystem.exception.ApplicationAlreadyExistsException;
import com.hcl.diagnosticManagementSystem.exception.CustomerNotFoundException;
import com.hcl.diagnosticManagementSystem.exception.HealthCheckupCreationException;
import com.hcl.diagnosticManagementSystem.exception.HealthCheckupNotFoundException;
import com.hcl.diagnosticManagementSystem.repository.CustomerHealthCheckupApplicationRepository;
import com.hcl.diagnosticManagementSystem.repository.HealthCheckupRepository;

import jakarta.validation.Valid;


@Service
public class HealthCheckupService {
	
	// Logger for this class
	private static final Logger logger = LoggerFactory.getLogger(HealthCheckupService.class);
	
	@Autowired
	private HealthCheckupRepository healthCheckupRepository;
	
	@Autowired
	private CustomerHealthCheckupApplicationRepository customerHealthCheckupApplicationRepository;
	
	@Autowired
	private CustomerService customerService;
	
	
	public List<HealthCheckup> searchHealthCheckupPlans (String query) {		// This method was returning HealthCheckup entities.
		logger.info("Service: Searching health checkup plans with query: {}", query);
		try {
			List<HealthCheckup> plans = healthCheckupRepository.findByNameContaining(query);
			if(plans == null || plans.isEmpty()) {
				logger.warn("Service: No health checkup plans found for query: {}", query);
				return Collections.emptyList();
			}
			logger.info("Service: Successfully retrieved {} health checkup plans for query: {}", plans.size(), query);
			return plans;
		} catch (Exception e) {
			logger.error("Service: An unexpected error occured while searching for health checkup for query: {}", query);
			throw new RuntimeException("Failed to search health checkup plans due to an internal error.", e);
		}
	}
		 
	
	public HealthCheckup getHealthCheckupById(Long id) {
		try {
			return healthCheckupRepository.findById(id)
					.orElseThrow(() -> {
						logger.warn("Service: Health Checkup not found with ID: {}", id);
						return new HealthCheckupNotFoundException("Health Checkup not found with ID " + id);
					});
		} catch (HealthCheckupNotFoundException e) {
			logger.warn("Service: specific business exception caught: {}" + e.getMessage());
			throw e;
		} catch (IllegalArgumentException e) {
			logger.error("Invalid argument passed for health checkup ID: " + id);
			throw new RuntimeException("Invalid ID provided: " + id, e);
		} catch (Exception e) {
			logger.error("Service: An unexpected error occurred while retriving health checkup by ID:" + id, e);
			throw new RuntimeException("Unexpected error while retrieving health checkup by ID: " + id +" due to an unexpected error.");
		}
	}
	
	
	@Transactional
	public HealthCheckupApplicationResponseDTO applyForHealthCheckup(String userId, Long healthCheckupId) throws Exception {
	    try {
	    	logger.info("Service: Processing health checkup application: UserID = {}, HealthCheckupID = {}", userId, healthCheckupId);
	    	
	        Customer customer = customerService.getCustomerByUserId(userId)
	                .orElseThrow(() -> new CustomerNotFoundException("Customer not found for userId: " + userId));
	        logger.debug("Service: Customer found: {}", customer.getUserId());
	        
	        HealthCheckup healthCheckup = healthCheckupRepository.findById(healthCheckupId)
	                .orElseThrow(() -> new HealthCheckupNotFoundException("Health Checkup not found with ID: " + healthCheckupId));
	        logger.debug("Service: Health Checkup found: {}", healthCheckup.getName());

	        boolean alreadyApplied = customerHealthCheckupApplicationRepository
	                .existsByCustomerAndHealthCheckup(customer, healthCheckup);

	        if (alreadyApplied) {
	        	logger.warn("Service: Customer {} has already applied for Health Checkup {}", userId, healthCheckupId);
	            throw new ApplicationAlreadyExistsException("Customer " + userId + " has already applied for Health Checkup " + healthCheckupId);
	        }

	        CustomerHealthCheckupApplication application = new CustomerHealthCheckupApplication();
	        application.setCustomer(customer);
	        application.setHealthCheckup(healthCheckup);
	        application.setApplicationDate(LocalDate.now());
	        application.setStatus("Pending");

	        CustomerHealthCheckupApplication savedApplication = customerHealthCheckupApplicationRepository.save(application);
	        
	        logger.info("Service: Health checkup application successful for UserID = {}, HealthCheckupId = {}", userId, healthCheckupId);
	        return new HealthCheckupApplicationResponseDTO(savedApplication);

	    } catch (CustomerNotFoundException e) {
	        logger.error("Service: Customer not found for UserID: {}", userId);
	        throw new CustomerNotFoundException("Customer not found for UserID " + userId);
	    } catch (HealthCheckupNotFoundException e) {
	    	logger.error("Health checkup plan not found for ID: {}", healthCheckupId);
	    	throw e;
		} catch (ApplicationAlreadyExistsException e) {
	    	logger.error("Application already exists for UserID: {}", userId);
	    	throw e;
		} catch (IllegalArgumentException e) {
			logger.error("Service: Invalid argument provided: UserID = {}, HealthCheckupID = {}", userId, healthCheckupId);
			throw new RuntimeException("Invalid input recieved for health checkup application", e);
		} catch (Exception e) {
			logger.error("Unexpected error while applying for healthcheckup: UserID = {}, HealthCheckupID = {}", userId, healthCheckupId, e);
			throw new RuntimeException("Unexpected error while processing the health checkup application", e);
		}
	}

	
	public List<HealthCheckupApplicationResponseDTO> getApplicationsByCustomer(String userId) {	
		try {
			logger.info("Service: Fetching health checkup applications for UserID: {}", userId);
			
			Customer customer = customerService.getCustomerByUserId(userId)
					.orElseThrow(() -> new CustomerNotFoundException("Customer not found for userId: " + userId));
			
			List<CustomerHealthCheckupApplication> applications = customerHealthCheckupApplicationRepository.findByCustomer(customer);
			
			if(applications == null | applications.isEmpty()) {
				logger.warn("Service: No health checkup applications found for UserID: {}", userId);
				throw new CustomerNotFoundException("No health checkup application foudn for the given UserID: {}"+ userId);
			}
			logger.info("Service: Successfully retrived {} health checkup applications for UserID: {}", userId);
			return applications.stream()
					.map(HealthCheckupApplicationResponseDTO::new)
					.collect(Collectors.toList());
		} catch (CustomerNotFoundException e) {
			logger.error("Service: Customer not found or no applications exist for ID: {}", userId);
			throw new CustomerNotFoundException("Customer not found or no applications exist." + e);
		} catch (IllegalArgumentException e) {
			logger.info("Service: Invalid argument provided: UserID = {}", userId);
			throw new RuntimeException("Invalid input recieved for health checkup applications.");
		}
		catch (Exception e) {
			logger.error("Service: Unexpected error while fetching health checkup applications for UserID: {}", userId, e);
			throw new RuntimeException("Unexpected error while retrieving health checkup applications.", e);
		}
		
	}
	
	
	public HealthCheckup createHealthCheckup(@Valid HealthCheckupCreationRequestDTO requestDTO) {
		try {
			logger.info("Service: Creating a new health checkup plan with details: {}", requestDTO);
			
	        HealthCheckup healthCheckup = new HealthCheckup();
	        healthCheckup.setName(requestDTO.getName());
	        healthCheckup.setDescription(requestDTO.getDescription());
	        healthCheckup.setEligibility(requestDTO.getEligibility());
	        healthCheckup.setFrequency(requestDTO.getFrequency());
	        healthCheckup.setCost(requestDTO.getCost());
	        
	        return healthCheckupRepository.save(healthCheckup);
		} catch (HealthCheckupCreationException e) {
			logger.error("Service: Error creating health checkup plan: {}", e.getMessage());
			throw new HealthCheckupCreationException("Invalid input provided for creating Health Checkup."+ e);
		}
		catch (Exception e) {
			logger.error("Service: Unexpected error occured while creating Health Checkup", e);
			throw new RuntimeException("Unexpected error occured while creating Health Checkup." + e);
		}
         
    }
}
