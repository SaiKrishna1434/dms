package com.example.dmcmsproject.responsemodel;

public class CustomerViewResponseModel<T> {

	private String status;
	private String message;
	private T Data;
	
	public CustomerViewResponseModel() {
		// TODO Auto-generated constructor stub
	}
	
	public String isStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		Data = data;
	}
}
