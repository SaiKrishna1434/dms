package com.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.security.model.MedicalReport;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long> {

    @Query(value = "SELECT * FROM medical_report ORDER BY RAND() LIMIT 1", nativeQuery = true)
    MedicalReport findRandomReport();
}
