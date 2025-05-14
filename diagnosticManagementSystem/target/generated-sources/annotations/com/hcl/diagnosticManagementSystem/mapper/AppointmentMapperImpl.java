package com.hcl.diagnosticManagementSystem.mapper;

import com.hcl.diagnosticManagementSystem.dto.AppointmentCheckupRequest;
import com.hcl.diagnosticManagementSystem.dto.AppointmentCheckupResponse;
import com.hcl.diagnosticManagementSystem.entity.Appointment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-14T22:25:51+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class AppointmentMapperImpl implements AppointmentMapper {

    @Override
    public Appointment toAppointment(AppointmentCheckupRequest request) {
        if ( request == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setAppointmentDate( request.getPreferredDate() );
        appointment.setAppointmentTime( request.getPreferredTime() );
        appointment.setPatientName( request.getPatientName() );
        appointment.setAge( request.getAge() );
        appointment.setGender( request.getGender() );
        appointment.setMobile( request.getMobile() );
        appointment.setEmail( request.getEmail() );
        appointment.setCheckupType( request.getCheckupType() );

        return appointment;
    }

    @Override
    public AppointmentCheckupResponse toResponse(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentCheckupResponse appointmentCheckupResponse = new AppointmentCheckupResponse();

        appointmentCheckupResponse.setAppointmentId( appointment.getAppointmentId() );
        appointmentCheckupResponse.setPatientName( appointment.getPatientName() );
        appointmentCheckupResponse.setCheckupType( appointment.getCheckupType() );
        appointmentCheckupResponse.setAppointmentDate( appointment.getAppointmentDate() );
        appointmentCheckupResponse.setAppointmentTime( appointment.getAppointmentTime() );
        appointmentCheckupResponse.setStatus( appointment.getStatus() );
        appointmentCheckupResponse.setRemark( appointment.getRemark() );

        return appointmentCheckupResponse;
    }

    @Override
    public void updateAppointmentFromRequest(AppointmentCheckupRequest request, Appointment appointment) {
        if ( request == null ) {
            return;
        }

        if ( request.getPreferredDate() != null ) {
            appointment.setAppointmentDate( request.getPreferredDate() );
        }
        if ( request.getPreferredTime() != null ) {
            appointment.setAppointmentTime( request.getPreferredTime() );
        }
        if ( request.getPatientName() != null ) {
            appointment.setPatientName( request.getPatientName() );
        }
        appointment.setAge( request.getAge() );
        if ( request.getGender() != null ) {
            appointment.setGender( request.getGender() );
        }
        if ( request.getMobile() != null ) {
            appointment.setMobile( request.getMobile() );
        }
        if ( request.getEmail() != null ) {
            appointment.setEmail( request.getEmail() );
        }
        if ( request.getCheckupType() != null ) {
            appointment.setCheckupType( request.getCheckupType() );
        }
    }
}
