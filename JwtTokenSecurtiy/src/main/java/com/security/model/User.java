package com.security.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="Customer")
public class User 
{
	@Id
//	@GeneratedValue
	private String userId;
//	private String name;
//	private String userName;
	private String password;
	private String email;
	private String dob;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name="Roles_User",
			joinColumns = @JoinColumn(name="userId")
			)
	@Column(name="role")
	private Set<String> roles;



	public String getDob() {
		return dob;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}

	public void setDob(String name) {
		this.dob = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		System.out.println("getting roles"+roles);
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", dob=" + dob +  ", password=" + password
				+ ", email=" + email  + ", roles=" + roles + "]";
	}


}
