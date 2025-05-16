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

import com.hcl.diagnosticManagementSystem.dao.CustomerRepository;
import com.hcl.diagnosticManagementSystem.dao.HealthCheckupPlanRepository;
import com.hcl.diagnosticManagementSystem.dao.HealthCheckupRepository;
import com.hcl.diagnosticManagementSystem.dto.HealthCheckupPlanRequestDTO;
import com.hcl.diagnosticManagementSystem.dto.HealthCheckupPlanResponseDTO;
import com.hcl.diagnosticManagementSystem.dto.HealthCheckupRequestDTO;
import com.hcl.diagnosticManagementSystem.dto.HealthCheckupResponseDTO;
import com.hcl.diagnosticManagementSystem.entity.Customer;
import com.hcl.diagnosticManagementSystem.entity.HealthCheckup;
import com.hcl.diagnosticManagementSystem.entity.HealthCheckupPlan;
import com.hcl.diagnosticManagementSystem.exception.CustomerNotFoundException;
import com.hcl.diagnosticManagementSystem.exception.HealthCheckupPlanNotFoundException;


@Service
public class HealthCheckupService {
	
    private static final Logger logger = LoggerFactory.getLogger(HealthCheckupService.class);
	
	private final CustomerRepository customerRepository;
	private final HealthCheckupRepository healthCheckupRepository;
	
	@Autowired
	private HealthCheckupPlanRepository healthCheckupPlanRepository;

	@Autowired
    public HealthCheckupService(CustomerRepository customerRepository, HealthCheckupRepository healthCheckupRepository) {
        this.customerRepository = customerRepository;
        this.healthCheckupRepository = healthCheckupRepository;
    }

	public HealthCheckupPlanResponseDTO createHealthCheckupPlan(HealthCheckupPlanRequestDTO requestDTO) {
		HealthCheckupPlan plan = new HealthCheckupPlan();
		plan.setName(requestDTO.getName());
		plan.setDescription(requestDTO.getDescription());
		plan.setPrice(requestDTO.getPrice());
		HealthCheckupPlan savedPlan = healthCheckupPlanRepository.save(plan);
		return convertToDto(savedPlan);
	}
	
	
	/*
	public void saveHealthCheckup(Long customerId, String checkupPlanName) {
        // Fetch the customer from the database dynamically
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        // Create a new HealthCheckup entry
        HealthCheckup healthCheckup = new HealthCheckup();
        healthCheckup.setCheckupPlanName(checkupPlanName);
        healthCheckup.setApplicationDate(LocalDate.now());
        healthCheckup.setCustomers(Collections.singletonList(customer));

        // Save the health checkup
        healthCheckupRepository.save(healthCheckup);
    }
	*/
	
	public List<HealthCheckupPlanResponseDTO> searchHealthCheckupPlans (String keyword) {
		List<HealthCheckupPlan> plans = healthCheckupPlanRepository.findByNameContainingIgnoreCase(keyword);
		return plans.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public HealthCheckupResponseDTO applyForHealthCheckup(HealthCheckupRequestDTO requestDTO) {
		// Find a customer by the provided userId
		Customer customer = customerRepository.findByUserId(requestDTO.getUser_id())
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with User ID: " + requestDTO.getUser_id()));
			
		HealthCheckupPlan healthCheckupPlan = 	healthCheckupPlanRepository.findById(requestDTO.getHealthCheckupPlanId())
				.orElseThrow(() -> new HealthCheckupPlanNotFoundException("Health Checkup Plan not found with ID: " + requestDTO.getHealthCheckupPlanId()));
		
		// Create a new HealthCheckup object / entity
		HealthCheckup healthCheckup = new HealthCheckup();
		healthCheckup.setCustomers(Collections.singletonList(customer));
		healthCheckup.setCheckupPlanName(healthCheckupPlan.getName());
		healthCheckup.setApplicationDate(LocalDate.now());
		
		// Save HealthCheckup to the database
		HealthCheckup savedHealthCheckup = healthCheckupRepository.save(healthCheckup);
		
		// Log the customer ID
        logger.info("Saved HealthCheckup ID: {}", savedHealthCheckup.getId());
        logger.info("Customer ID: {}", customer.getId());

		// Convert the saved HealthCheckup to a DTO
		HealthCheckupResponseDTO responseDTO = new HealthCheckupResponseDTO();
		responseDTO.setId(savedHealthCheckup.getId());
		responseDTO.setCustomerId(customer.getId());
		responseDTO.setCheckupPlanName(savedHealthCheckup.getCheckupPlanName());
		responseDTO.setApplicationDate(savedHealthCheckup.getApplicationDate());
		
		return responseDTO;
	}
	
	
	public HealthCheckupPlanResponseDTO convertToDto(HealthCheckupPlan plan) {
		HealthCheckupPlanResponseDTO dto = new HealthCheckupPlanResponseDTO();
		dto.setId(plan.getId());
		dto.setName(plan.getName());
		dto.setDescription(plan.getDescription());
		dto.setPrice(plan.getPrice());
		return dto;
	}

}
