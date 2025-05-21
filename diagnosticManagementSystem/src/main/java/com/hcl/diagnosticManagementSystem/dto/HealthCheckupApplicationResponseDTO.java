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
         this.healthCheckup = new HealthCheckupInfoDTO (
        		application.getHealthCheckup().getId(),
                application.getHealthCheckup().getName(),
                application.getHealthCheckup().getDescription(),
                application.getHealthCheckup().getCost()
         );
     }
 }


 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }
 
 public String getStatus() { return status; }
 public void setStatus(String status) { this.status = status; }
 
 public CustomerApplicationInfoDTO getCustomer() { return customer; }
 public void setCustomer(CustomerApplicationInfoDTO customer) { this.customer = customer; }

 public LocalDate getApplicationDate() { return applicationDate; }
 public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }
 
 public HealthCheckupInfoDTO getHealthCheckup() { return healthCheckup; }
 public void setHealthCheckup(HealthCheckupInfoDTO healthCheckup) { this.healthCheckup = healthCheckup; }
}
