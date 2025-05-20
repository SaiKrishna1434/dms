package com.hcl.diagnosticManagementSystem.dto;


//This DTO contains ONLY the customer info needed for the application response
public class CustomerApplicationInfoDTO {
 private String userId;
 private String emailId;

 public CustomerApplicationInfoDTO() {}

 public CustomerApplicationInfoDTO(String userId, String emailId) {
     this.userId = userId;
     this.emailId = emailId;
 }

 // Getters
 public String getUserId() { return userId; }
 public String getEmailId() { return emailId; }

 // Setters
 public void setUserId(String userId) { this.userId = userId; }
 public void setEmailId(String emailId) { this.emailId = emailId; }
}