package com.hcl.diagnosticManagementSystem.service;

import com.hcl.diagnosticManagementSystem.dto.AppointmentCheckupRequest;
import com.hcl.diagnosticManagementSystem.dto.AppointmentCheckupResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentCheckupResponse applyForCheckup(AppointmentCheckupRequest request);

    AppointmentCheckupResponse deleteAppointmentById(String appointmentId);

    AppointmentCheckupResponse getAppointmentDetailsById(String appointmentId);

    AppointmentCheckupResponse updateAppointment(String appointmentId, AppointmentCheckupRequest request);

    List<AppointmentCheckupResponse> getAllAppointments();
}
