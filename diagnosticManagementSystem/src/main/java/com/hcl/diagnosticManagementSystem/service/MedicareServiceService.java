package com.hcl.diagnosticManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.diagnosticManagementSystem.dao.MedicareServiceRepository;
import com.hcl.diagnosticManagementSystem.entity.MedicareService;
import com.hcl.diagnosticManagementSystem.exception.MedicareServiceNotFoundException;


@Service
public class MedicareServiceService {
	
	@Autowired
	private MedicareServiceRepository medicareServiceRepository;
	
	public List<MedicareService> searchMedicareServices(String query) {
		return medicareServiceRepository.findByNameOrEligibility(query);
	}
	
	public List<MedicareService> getAllMedicareServices() {
		return medicareServiceRepository.findAll();
	}
	
	public MedicareService findMedicareServiceById(Long id) {
		return medicareServiceRepository.findById(id).orElse(null);
	}
	
	public MedicareService createMedicareService(MedicareService medicareService) {
		return medicareServiceRepository.save(medicareService);
	}
	
	public MedicareService updateMedicareService(Long id, MedicareService updatedMedicareService) {
		Optional<MedicareService> service = medicareServiceRepository.findById(id);
		if(service.isPresent()) {
			updatedMedicareService.setId(id);
			MedicareService savedService = medicareServiceRepository.save(updatedMedicareService);
			return savedService;
		} else {
            throw new MedicareServiceNotFoundException("Medicare service with ID " + id + " not found.");
		}
	}
}
