package com.hcl.diagnosticManagementSystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hcl.diagnosticManagementSystem.entity.MedicareService;

public interface MedicareServiceRepository extends JpaRepository<MedicareService, Long> {
	// a custom query to search by name or category
    @Query("SELECT ms FROM MedicareService ms WHERE ms.name LIKE %:query% OR ms.category LIKE %:query%")
    List<MedicareService> findByNameOrCategory(String query);
}
