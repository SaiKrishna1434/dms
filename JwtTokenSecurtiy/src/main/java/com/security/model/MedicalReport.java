package com.security.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MedicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String reportTitle;
    private String reportDetails;
    private LocalDate reportDate;
    
    
	public MedicalReport(Long id, String userId, String reportTitle, String reportDetails, LocalDate reportDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.reportTitle = reportTitle;
		this.reportDetails = reportDetails;
		this.reportDate = reportDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public String getReportDetails() {
		return reportDetails;
	}
	public void setReportDetails(String reportDetails) {
		this.reportDetails = reportDetails;
	}
	public LocalDate getReportDate() {
		return reportDate;
	}
	public void setReportDate(LocalDate reportDate) {
		this.reportDate = reportDate;
	}
    
    

    // Constructors, Getters, Setters
}

