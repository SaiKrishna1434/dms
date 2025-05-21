package com.hcl.dmms.exeption;

public class ResourceAlreadyExistsException extends RuntimeException {
	public ResourceAlreadyExistsException(String msg) {
		super(msg);
	}
}
