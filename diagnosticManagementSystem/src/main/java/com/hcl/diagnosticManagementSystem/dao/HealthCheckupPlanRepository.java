package com.hcl.diagnosticManagementSystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.diagnosticManagementSystem.entity.HealthCheckupPlan;

@Repository
public interface HealthCheckupPlanRepository extends JpaRepository<HealthCheckupPlan, Long> {
	List<HealthCheckupPlan> findByNameContainingIgnoreCase(String keyword);
}
