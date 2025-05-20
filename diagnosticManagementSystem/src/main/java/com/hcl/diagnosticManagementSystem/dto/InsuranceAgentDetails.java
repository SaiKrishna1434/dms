package com.hcl.diagnosticManagementSystem.dto;

import java.util.Objects;

public class InsuranceAgentDetails {

	private String agentId;
	private String agentName;
	private String location;
	private String email;
	private String contactInfo;
	
	public InsuranceAgentDetails() {
		// TODO Auto-generated constructor stub
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsuranceAgentDetails that = (InsuranceAgentDetails) o;
        return Objects.equals(agentId, that.agentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agentId);
    }

    @Override
    public String toString() {
        return "Agent ID:" + agentId  +
               " Agent Name:" + agentName  +
               " Location:" + location  +
               " Email:" + email  +
               " Contact No:" + contactInfo;
    }
	
}
