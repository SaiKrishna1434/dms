package com.hcl.diagnosticManagementSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.diagnosticManagementSystem.entity.HealthCheckup;


@Repository
public interface HealthCheckupRepository extends JpaRepository<HealthCheckup, Long> {
	
}
