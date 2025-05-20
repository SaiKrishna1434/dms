package com.hcl.diagnosticManagementSystem.dto;

import java.time.LocalDate;

import com.hcl.diagnosticManagementSystem.entity.CustomerHealthCheckupApplication;

public class HealthCheckupApplicationResponseDTO {
 private Long id;
 private LocalDate applicationDate;
 private String status;
 private CustomerApplicationInfoDTO customer; 
 private HealthCheckupInfoDTO healthCheckup;

 public HealthCheckupApplicationResponseDTO() {}

 // Constructor to convert the entity to DTO
 public HealthCheckupApplicationResponseDTO(CustomerHealthCheckupApplication application) {
     this.id = application.getId();
     this.applicationDate = application.getApplicationDate();
     this.status = application.getStatus();

     if (application.getCustomer() != null) {
         this.customer = new CustomerApplicationInfoDTO(application.getCustomer().getUserId(), application.getCustomer().getEmailId());
     }

     if (application.getHealthCheckup() != null) {
         this.healthCheckup = new HealthCheckupInfoDTO(application.getHealthCheckup().getId(),
                                                        application.getHealthCheckup().getName(),
                                                        application.getHealthCheckup().getDescription(),
                                                        application.getHealthCheckup().getPrice());
     }
 }

 // Getters
 public Long getId() { return id; }
 public LocalDate getApplicationDate() { return applicationDate; }
 public String getStatus() { return status; }
 public CustomerApplicationInfoDTO getCustomer() { return customer; }
 public HealthCheckupInfoDTO getHealthCheckup() { return healthCheckup; }

 // Setters (if needed for deserialization, though typically not for response DTOs)
 public void setId(Long id) { this.id = id; }
 public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }
 public void setStatus(String status) { this.status = status; }
 public void setCustomer(CustomerApplicationInfoDTO customer) { this.customer = customer; }
 public void setHealthCheckup(HealthCheckupInfoDTO healthCheckup) { this.healthCheckup = healthCheckup; }
}
