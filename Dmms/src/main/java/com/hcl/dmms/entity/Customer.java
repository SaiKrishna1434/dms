package com.hcl.dmms.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "AppUser")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	@NotBlank(message = "User ID is mandatory")
	private String userId;

	@Column
	private String userName;	

	@NotBlank
	@Column(unique = true)
	private String contactNumber;

	@Column(name = "password")
	@NotBlank(message = "Password name is mandatory")
	private String password;

	@Column(name = "date_of_birth")
	@NotNull(message = "DOB name is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate dateOfBirth;

	@Column(name = "email_id", unique = true)
	@NotBlank(message = "Email name is mandatory")
	private String emailId;

	public Customer() {
		// Default constructor
	}
	
	

	public Customer(Long id, @NotBlank(message = "User ID is mandatory") String userId, String userName,
			@NotBlank String contactNumber, @NotBlank(message = "Password name is mandatory") String password,
			@NotBlank(message = "DOB name is mandatory") LocalDate dateOfBirth,
			@NotBlank(message = "Email name is mandatory") String emailId) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.contactNumber = contactNumber;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.emailId = emailId;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}




}
