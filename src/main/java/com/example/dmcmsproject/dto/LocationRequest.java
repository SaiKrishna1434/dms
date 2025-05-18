package com.example.dmcmsproject.dto;

import jakarta.validation.constraints.NotBlank;

public class LocationRequest {

	@NotBlank(message ="Location cannot be blank")
	private String location;
	
	public LocationRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
