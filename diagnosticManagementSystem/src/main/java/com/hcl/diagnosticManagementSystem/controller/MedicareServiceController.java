package com.hcl.diagnosticManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.diagnosticManagementSystem.entity.MedicareService;
import com.hcl.diagnosticManagementSystem.exception.MedicareServiceNotFoundException;
import com.hcl.diagnosticManagementSystem.service.MedicareServiceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicare-services")
public class MedicareServiceController {

    @Autowired
    private MedicareServiceService medicareServiceService;

    @GetMapping("/search")
    public ResponseEntity<List<MedicareService>> searchServices(@RequestParam String query) {
        List<MedicareService> services = medicareServiceService.searchMedicareServices(query);
        return ResponseEntity.ok(services);
    }

    @GetMapping
     public ResponseEntity<List<MedicareService>> getAllServices() {
        List<MedicareService> services = medicareServiceService.getAllMedicareServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicareService> getServiceById(@PathVariable Long id) {
        MedicareService service = medicareServiceService.findMedicareServiceById(id);
        if (service != null) {
            return ResponseEntity.ok(service);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<MedicareService> createService(@Valid @RequestBody MedicareService medicareService) {
    	MedicareService createdService = medicareServiceService.createMedicareService(medicareService);
    	return new ResponseEntity<>(createdService, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MedicareService> updateService(@PathVariable Long id, @Valid @RequestBody MedicareService medicareService) {
    	try {
    		MedicareService service = medicareServiceService.updateMedicareService(id, medicareService);
    		return ResponseEntity.ok(service);
    	} catch(MedicareServiceNotFoundException e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    	}
    }
}
