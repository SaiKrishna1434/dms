package healthcheckup.controller;

import java.util.List;

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
import com.hcl.diagnosticManagementSystem.service.HealthCheckupService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/healthcheckup")
public class HealthCheckupController {
	
	@Autowired
	private HealthCheckupService healthCheckupService;
	
	@GetMapping("/search")
	public ResponseEntity<List<HealthCheckup>> searhPlans(@RequestParam String query) {
		List<HealthCheckup> plans = healthCheckupService.searchHealthCheckupPlans(query);
		return new ResponseEntity<>(plans, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<HealthCheckup> getPlanById(@PathVariable Long id) {
		HealthCheckup plans = healthCheckupService.getHealthCheckupById(id);
		if(plans != null) {
			return ResponseEntity.ok(plans);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/apply/{userId}/{healthCheckupId}")
	public ResponseEntity<HealthCheckupApplicationResponseDTO> applyForHealthCheckup(
			@PathVariable String userId,
			@PathVariable Long healthCheckupId) {
		HealthCheckupApplicationResponseDTO application = healthCheckupService.applyForHealthCheckup(userId, healthCheckupId);
		if(application != null) {
			return new ResponseEntity<>(application, HttpStatus.CREATED);
		} else {
			return ResponseEntity.badRequest().build();	
		}
	}
	
	@GetMapping("/applications/{userId}")
	public ResponseEntity<List<HealthCheckupApplicationResponseDTO>> getApplications(@PathVariable String userId){
		List<HealthCheckupApplicationResponseDTO> applications = healthCheckupService.getApplicationsByCustomer(userId);
		return ResponseEntity.ok(applications);
	}
	
	@PostMapping("/create")
    public ResponseEntity<HealthCheckup> createHealthCheckup(
            @Valid @RequestBody HealthCheckupCreationRequestDTO requestDTO) {
        HealthCheckup createdPlan = healthCheckupService.createHealthCheckup(requestDTO);
        return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
    }
}
