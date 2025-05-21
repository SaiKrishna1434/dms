package com.hcl.dmms.dto;

public class UserLoginRequest {
	private String userId;
	private String password;

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

	public UserLoginRequest(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}

}
