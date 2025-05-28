# Diagnostic Medicare Center Management System


A Diagnostic Medicare Center Management System (DMS), is a system used for maintaining the details of the "Diagnostic Medicare System". It provides several operations booking an apointment, creating an account for the customer who wants to be a member of the Diagnostic center, to search for a Medicare service, to apply for any health checkup, to find a medical insurance agent and services and to view the medical reports online.

The following are the few important modules in the system:  
a.	To create an account for the new customer   
b.	To search for any Medicare service.  
c.	 Apply for any health checkup   
d.	To find an medical Insurance agent and services  
e.	To view the medical report online.  



---

## 1. ❓ What is the Problem?
In many healthcare environments, especially diagnostic centers and clinics, the process of managing health checkup applications, user records, and test scheduling is still manual or semi-automated. This leads to:

- Inefficiencies in handling patient data and appointments.
- Delays in processing health checkup applications.
- Errors in record-keeping and communication.
- Lack of transparency for patients regarding their application status or test results.


### Motivation Behind Building It
The goal is to digitize and streamline the diagnostic process by creating a centralized system that:

  - Allows users to apply for health checkups online.
  - Enables diagnostic centers to manage applications, users, and test packages efficiently.
  - Reduces administrative overhead and improves accuracy.
  - Provides real-time status updates to users.
  - Enhances data security and accessibility.
    
*This system is especially valuable in the post-pandemic world, where contactless and remote healthcare services are increasingly important.


  ### Target Users / Beneficiaries

  #### Patients / Customers:
  
      Can apply for health checkups online.
      Track the status of their applications.
      View available health packages and choose accordingly.
  
  #### Diagnostic Center Staff / Admins:
    
      Manage user applications and appointments.
      Assign or approve health checkups.
      Monitor system reports and analytics.
  
  #### Healthcare Providers / Doctors:
    
      Access patient application history.
      Recommend appropriate checkups based on history.
      System Integrators / Developers:
  
  Can extend the system to integrate with labs, hospitals, or insurance providers.

---

## 2. System Architecture Overview

 

---

## 3. Core Features & Functionality
- Highlight key features implemented in the backend:
  - REST APIs
  - Authentication/Authorization
  - Business logic
  - Data persistence
- We used Swagger UI, or Postman to demonstrate API endpoints.

---

## 4. Tech Stack & Tools
- **Spring Boot** (3.5.0)
- **Database**: MySQL, PostgreSQL
- **JPA/Hibernate**
- **Security**: Spring Security, JWT
- **Testing**: JUnit, Mockito
- **Build Tools**: Maven

---

## 5. Code Walkthrough (Go through flow of functionality)
- Show important parts of the code:
  - Controller
  - Service
  - Repository
  - DTOs and Models
- Emphasize clean architecture and design patterns used (e.g., MVC, layered architecture).

- Major entities that going to covers our project are as given-
    #### Entities
    - AppUser             ->    Used for creating a customer
    - Appointment         ->    Used for creating a customer
    - Insurance Agent     ->    
    - Medicare Service    ->    To search the medicare services based on the keyword 
    - Health Checkup      ->    To apply for health checkup, search health checkup, and select health checkup pakage or plan
    - Medical Reports    ->    To search the medicare services based on the keyword 


      
    #### Field Descriptions (AppUser)
      - private Long id;
      - private String userId;
      - private String userName;
      - private String contactNumber;
      - private String password;
      - private LocalDate dateOfBirth;
      - private String emailId;
      - private Integer status;
      - private Role role;
      - protected void onCreate()

    #### Field Description (Apointment)
      - private String doctorName
      - private String department
      - appointmentDate
      - private LocalTime appointmentTime
      - private Stringstatus
      - private String consultationMode
      - private String reasonForVisit
      - [rivate Long patientId

  #### Field Description (Insurance Agent)
      - private Long id
      - private String name
      - private String location
      - private String email
      - private String contactNo
      - private List<Services> services
      - private List<CustomerServiceRecord> agentRecords
  
  #### Field Description ( Medicare Service)
      - private String serviceName
  
  #### Field Description ( Health Checkup)
      -  private Long id
      - private String name
      - private String description
      - private String eligibility
      - private String frequency
      - private double cost

  ##### Database Entities
    - AppUser: Represents users of the system.
    - HealthCheckup: Represents available health checkup packages.
    - CustomerHealthCheckupApplication: Central entity that links users to checkups.
      
  ##### - Relationships:
     - CustomerHealthCheckupApplication → AppUser: Many-to-One
     -  CustomerHealthCheckupApplication → HealthCheckup: Many-to-One

     This mapping results in a structure like: 

      Table: user_health_checkup_application 
      ------------------------------------------------------------------- 
      | id | application_date | status | appUser_id | health_checkup_id | 
      -------------------------------------------------------------------
     
    
    appUser_id → Foreign key to AppUser table 

  #### Field Description ( Medical Report)
        - private Long id
        - private String reportId
        - private AppUser customer
        - private String reportName
        - private String reportContent
        - private LocalDate reportDate

---

## 6. Testing & Validation
- Unit and integration tests demonstration.
- Sonarqube reports, Code coverage.
- We tried to achieve coverage percentage of each of the test classes with valid test method, and ensured to be reliabile and correctness.

---

## 7. Future Plans
- UI/Frontend plans (React, Angular, etc.)
- CI/CD pipeline
- Dockerization and deployment
- Scalability and performance improvements

---

## 8. Demo (Live or Recorded)
- Using Postman or Swagger to:
  - Show API requests and responses.
  - Simulate real-world use cases.

---

## 📄 9. Documentation
- A README with:
  - Setup instructions
  - API documentation

---

## 10. Q&A and Feedback
- Ready to answer questions about:
  - Design decisions
  - Trade-offs
  - Future enhancements

---
