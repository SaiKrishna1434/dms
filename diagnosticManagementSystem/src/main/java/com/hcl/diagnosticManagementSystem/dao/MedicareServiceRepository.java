package com.hcl.diagnosticManagementSystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hcl.diagnosticManagementSystem.entity.MedicareService;

public interface MedicareServiceRepository extends JpaRepository<MedicareService, Long> {
	// a custom query to search by name or eligibility
    @Query("SELECT ms FROM MedicareService ms WHERE ms.name LIKE %:query% OR ms.eligibility LIKE %:query%")
    List<MedicareService> findByNameOrEligibility(String query);
}
