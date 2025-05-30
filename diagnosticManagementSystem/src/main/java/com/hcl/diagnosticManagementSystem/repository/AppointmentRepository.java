package com.hcl.diagnosticManagementSystem.repository;

import com.hcl.diagnosticManagementSystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByAppointmentId(String appointmentId);


}
