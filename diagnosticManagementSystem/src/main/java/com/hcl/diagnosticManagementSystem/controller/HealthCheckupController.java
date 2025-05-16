package com.hcl.diagnosticManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.diagnosticManagementSystem.dto.HealthCheckupPlanRequestDTO;
import com.hcl.diagnosticManagementSystem.dto.HealthCheckupPlanResponseDTO;
import com.hcl.diagnosticManagementSystem.dto.HealthCheckupRequestDTO;
import com.hcl.diagnosticManagementSystem.dto.HealthCheckupResponseDTO;
import com.hcl.diagnosticManagementSystem.service.HealthCheckupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/healthcheckup")
public class HealthCheckupController {
	
	@Autowired
	private HealthCheckupService healthCheckupService;
	
	@PostMapping("/plans/create")
	public ResponseEntity<HealthCheckupPlanResponseDTO> createHealthCheckupPlan(@RequestBody @Valid HealthCheckupPlanRequestDTO requestDTO) {
		HealthCheckupPlanResponseDTO responseDTO = healthCheckupService.createHealthCheckupPlan(requestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}
	
	@GetMapping("/plans/search")
	public ResponseEntity<List<HealthCheckupPlanResponseDTO>> searchHealthCheckupPlans(@RequestParam String keyword) {
		List<HealthCheckupPlanResponseDTO> result = healthCheckupService.searchHealthCheckupPlans(keyword);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/apply")
	public ResponseEntity<HealthCheckupResponseDTO> applyForHealthCheckup(@RequestBody @Valid HealthCheckupRequestDTO requestDTO) {
		HealthCheckupResponseDTO responseDTO = healthCheckupService.applyForHealthCheckup(requestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}
	
	@PostMapping("/testapply")
	public ResponseEntity<String> testApply() {
	    return new ResponseEntity<>("Test Apply Endpoint Hit", HttpStatus.OK);
	}
}
