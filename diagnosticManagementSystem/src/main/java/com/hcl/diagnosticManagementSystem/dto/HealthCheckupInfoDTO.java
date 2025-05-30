package com.hcl.diagnosticManagementSystem.dto;


//This DTO contains ONLY the health checkup info needed for the application response
public class HealthCheckupInfoDTO {
 private Long id;
 private String name;
 private String description;
 private double price;

 public HealthCheckupInfoDTO() {}

 public HealthCheckupInfoDTO(Long id, String name, String description, double price) {
     this.id = id;
     this.name = name;
     this.description = description;
     this.price = price;
 }

 // Getters
 public Long getId() { return id; }
 public String getName() { return name; }
 public String getDescription() { return description; }
 public double getPrice() { return price; }
 
 // Setters
 public void setId(Long id) { this.id = id; }
 public void setName(String name) { this.name = name; }
 public void setDescription(String description) { this.description = description; }
 public void setPrice(double price) { this.price = price; }
}