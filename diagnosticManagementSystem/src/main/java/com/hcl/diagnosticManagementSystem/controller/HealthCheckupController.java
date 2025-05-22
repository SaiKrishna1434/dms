package com.hcl.diagnosticManagementSystem.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.diagnosticManagementSystem.dto.HealthCheckupApplicationResponseDTO;
import com.hcl.diagnosticManagementSystem.dto.HealthCheckupCreationRequestDTO;
import com.hcl.diagnosticManagementSystem.entity.HealthCheckup;
import com.hcl.diagnosticManagementSystem.exception.CustomerNotFoundException;
import com.hcl.diagnosticManagementSystem.exception.HealthCheckupCreationException;
import com.hcl.diagnosticManagementSystem.exception.HealthCheckupNotFoundException;
import com.hcl.diagnosticManagementSystem.service.HealthCheckupService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/healthcheckup")
public class HealthCheckupController {
	
	// Logger for this class
	private static final Logger logger = LoggerFactory.getLogger(HealthCheckupController.class);
	
	@Autowired
	private HealthCheckupService healthCheckupService;
	
	
	@GetMapping("/search-healthcheckup")
	public ResponseEntity<List<HealthCheckup>> searhPlans(@RequestParam String query) {
		try {
			logger.info("Controller: Recieved request to search health checlup plans with query: {}", query);
			List<HealthCheckup> plans = healthCheckupService.searchHealthCheckupPlans(query);
			
			if(plans.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			logger.info("Controller: Successfully retrieved {} health checkup plans for query: {}", plans.size(), query);
			return new ResponseEntity<>(plans, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("Controller: Error searching health checkup plans with query: {}", query);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<HealthCheckup> getPlanById(@PathVariable Long id) {
		try {
			logger.info("Controller: Fetching health checkup plan with ID: {}", id);
			HealthCheckup plans = healthCheckupService.getHealthCheckupById(id);
			
			if(plans != null) {
				return ResponseEntity.ok(plans);
			} else {
				throw new HealthCheckupNotFoundException("No health checkup plan found for the given ID: {}" + id);
			}
		} catch (HealthCheckupNotFoundException e) {
			logger.error("Controller: Health checkup not found for ID: {}" + id);
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@PostMapping("/apply-healthcheckup/{userId}/{healthCheckupId}")
	public ResponseEntity<HealthCheckupApplicationResponseDTO> applyForHealthCheckup(
			@PathVariable String userId,
			@PathVariable Long healthCheckupId) 
	{
		try {
			logger.info("Controller: Applying for health checkup: UserID = {}, HealthCheckupId = {}", userId, healthCheckupId);
			HealthCheckupApplicationResponseDTO application = healthCheckupService.applyForHealthCheckup(userId, healthCheckupId);
			
			if(application != null) {
				logger.info("Controller: Health checkup application successful for UserID = {}, HealthCheckupID = {}", userId, healthCheckupId);
				return new ResponseEntity<>(application, HttpStatus.CREATED);
			} else {
				logger.warn("Controller: Failed to apply health checkup: UserID = {}, HealthCheckupID = {}", userId, healthCheckupId);
				return ResponseEntity.badRequest().build();	
			}
		} catch (HealthCheckupNotFoundException e) {
			logger.error("Controller: Health checkup not found for ID: {}" + healthCheckupId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (CustomerNotFoundException e) {
			logger.error("Controller: Customer not found for UserID: {}" + userId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			logger.error("Controller: Unexpected error while applying for health checkup: UserId = {}, HealthCheckupId = {}", userId, healthCheckupId);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<HealthCheckupApplicationResponseDTO>> getApplications(@PathVariable String userId){
		try {
			logger.info("Controller: Fetching health checkup applications for UserID: {}", userId);
			List<HealthCheckupApplicationResponseDTO> applications = healthCheckupService.getApplicationsByCustomer(userId);
			
			if(applications.isEmpty() || applications == null) {
				logger.warn("Controller: No health checkup applications found for customer with UserID: {}", userId);
				throw new CustomerNotFoundException("No applications found for the given customer: {}" + userId);
			}
			
			logger.info("Controller: Successfully retrieved {} health checkup applications for UserID: {}",applications.size(), userId);
			return ResponseEntity.ok(applications);
		} catch (CustomerNotFoundException e) {
			logger.error("Controller: Customer not found or no applications exist for ID: {}", userId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			logger.error("Controller: Unexpected error while fetching health checkup applications for UserID: {}", userId);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@PostMapping("/create-new-healthcheckup")
    public ResponseEntity<HealthCheckup> createHealthCheckup(
            @Valid @RequestBody HealthCheckupCreationRequestDTO requestDTO) 
	{
		try {
			logger.info("Controller: Creating a new health checkup plan with details: {}", requestDTO);
			HealthCheckup createdPlan = healthCheckupService.createHealthCheckup(requestDTO);
			
			if(createdPlan != null) {
				logger.info("Controller: Health checkup plan created successfully with ID: {}", createdPlan.getId());
				return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
			} else {
				logger.warn("Controller: Failed to create health checkup plan.");
				throw new HealthCheckupCreationException("Failed to create health checkup plan due to validation or processing error.");
			}
		} catch (HealthCheckupCreationException e) {
			logger.error("Controller: Error creating health checkup plan: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			logger.error("Controller: Unexpected error while creating health checkup plan: {}");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
        
    }
}
