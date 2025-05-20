package com.hcl.diagnosticManagementSystem.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
public class Customer {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String userId;
	
	@NotBlank(message = "Password can not be blank")
	private String password;
	
	@NotBlank(message="Customer name can not be blank")
	private String customerName;
	
	@Past(message = "Date of birth must be in past")
	@NotNull(message = "Date should not be null")
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Email(message= "Invalid email format")
	@NotBlank(message= "Email Id can not be blank")
	private String emailId;
	
	private String location;	
	
	@OneToMany(mappedBy="customer")
	private List<CustomerServiceRecord> customerRecords=new ArrayList<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="Roles_User",
			joinColumns = @JoinColumn(name="userId")
			)
	@Column(name="role")
	private Set<String> roles;
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public Customer(String userId, String name,String password, Date dob, String emailId) {
		this.userId = userId;
		this.password = password;
		this.customerName=name;
		this.dob = dob;
		this.emailId = emailId;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String cname) {
		this.customerName = cname;
	}

	public List<CustomerServiceRecord> getCustomerRecords() {
		return customerRecords;
	}

	public void setCustomerRecords(List<CustomerServiceRecord> customerRecords) {
		this.customerRecords = customerRecords;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
}
