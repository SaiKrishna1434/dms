package com.hcl.diagnosticManagementSystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
import com.hcl.diagnosticManagementSystem.exception.HealthCheckupNotFoundException;
import com.hcl.diagnosticManagementSystem.repository.CustomerHealthCheckupApplicationRepository;
import com.hcl.diagnosticManagementSystem.repository.HealthCheckupRepository;

import jakarta.validation.Valid;


@Service
public class HealthCheckupService {
	
	@Autowired
	private HealthCheckupRepository healthCheckupRepository;
	
	@Autowired
	private CustomerHealthCheckupApplicationRepository customerHealthCheckupApplicationRepository;
	
	@Autowired
	private CustomerService customerService;
	
	
	public List<HealthCheckup> searchHealthCheckupPlans (String query) {		// This method was returning HealthCheckup entities.
		return healthCheckupRepository.findByNameContaining(query);
	}
	
	public HealthCheckup getHealthCheckupById(Long id) {
		return healthCheckupRepository.findById(id)
				.orElseThrow(() -> new HealthCheckupNotFoundException("Health Checkup not found with ID " + id));
	}
	
	@Transactional
	public HealthCheckupApplicationResponseDTO applyForHealthCheckup(String userId, Long healthCheckupId) {
		Customer customer = customerService.getCustomerByUserId(userId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found for userId: " + userId));
		HealthCheckup healthCheckup = 	healthCheckupRepository.findById(healthCheckupId)
				.orElseThrow(() -> new HealthCheckupNotFoundException("Health Checkup not found with ID: " + healthCheckupId));
			
			// Check if already exist or already applied for 'health checkup'
			boolean alreadyApplied = customerHealthCheckupApplicationRepository.existsByCustomerAndHealthCheckup(customer, healthCheckup);
		 	if (alreadyApplied) {
			     throw new ApplicationAlreadyExistsException("Customer " + userId + " has already applied for Health Checkup " + healthCheckupId);
			}
		
			CustomerHealthCheckupApplication application = new CustomerHealthCheckupApplication();
			application.setCustomer(customer);
			application.setHealthCheckup((healthCheckup));
			application.setApplicationDate(LocalDate.now());
			application.setStatus("Pending");
			CustomerHealthCheckupApplication savedApplication =  customerHealthCheckupApplicationRepository.save(application);
			return new HealthCheckupApplicationResponseDTO(savedApplication);
	}
	
	
	public List<HealthCheckupApplicationResponseDTO> getApplicationsByCustomer(String userId) {		
		Customer customer = customerService.getCustomerByUserId(userId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found for userId: " + userId));
		List<CustomerHealthCheckupApplication> applications = customerHealthCheckupApplicationRepository.findByCustomer(customer);
		return applications.stream()
				.map(HealthCheckupApplicationResponseDTO::new)
				.collect(Collectors.toList());
	}
	
	
	public HealthCheckup createHealthCheckup(@Valid HealthCheckupCreationRequestDTO requestDTO) {
        HealthCheckup healthCheckup = new HealthCheckup();
        healthCheckup.setName(requestDTO.getName());
        healthCheckup.setDescription(requestDTO.getDescription());
        healthCheckup.setEligibility(requestDTO.getEligibility());
        healthCheckup.setFrequency(requestDTO.getFrequency());
        healthCheckup.setCost(requestDTO.getCost());
         
        return healthCheckupRepository.save(healthCheckup);
    }
}
