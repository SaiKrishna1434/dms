package com.hcl.diagnosticManagementSystem.exception;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(String msg){
        super(msg);
    }
}
