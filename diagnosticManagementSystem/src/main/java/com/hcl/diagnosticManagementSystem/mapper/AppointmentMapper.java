package com.hcl.diagnosticManagementSystem.mapper;

import com.hcl.diagnosticManagementSystem.dto.AppointmentCheckupRequest;
import com.hcl.diagnosticManagementSystem.dto.AppointmentCheckupResponse;
import com.hcl.diagnosticManagementSystem.entity.Appointment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mappings({
            @Mapping(source = "preferredDate", target = "appointmentDate"),
            @Mapping(source = "preferredTime", target = "appointmentTime")
    })
    Appointment toAppointment(AppointmentCheckupRequest request);

    AppointmentCheckupResponse toResponse(Appointment appointment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(source = "preferredDate", target = "appointmentDate"),
            @Mapping(source = "preferredTime", target = "appointmentTime")
    })
    void updateAppointmentFromRequest(AppointmentCheckupRequest request, @MappingTarget Appointment appointment);
}

