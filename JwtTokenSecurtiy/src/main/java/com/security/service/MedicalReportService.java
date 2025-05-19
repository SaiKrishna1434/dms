package com.security.service; // Adjust your package name

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.model.MedicalReport;
import com.security.repo.MedicalReportRepository;

@Service
public class MedicalReportService {

    @Autowired
    private MedicalReportRepository medicalReportRepository;

    public MedicalReport getReportById(Long id) {
        Optional<MedicalReport> reportOptional = medicalReportRepository.findById(id);
        return reportOptional.orElse(null);
    }
}