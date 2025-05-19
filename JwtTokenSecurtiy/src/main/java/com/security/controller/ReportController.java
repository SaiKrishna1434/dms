//package com.security.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//// Adjust your package name
//
//import com.security.model.MedicalReport;
//import com.security.service.MedicalReportService; // You'll create this service
//
//@Controller
//public class ReportController {
//
//    @Autowired
//    private MedicalReportService medicalReportService;
//
//    @GetMapping("/viewReport")
//    public String viewReportForm() {
//        return "viewReport"; // This will look for viewReport.html (or viewReport.thymeleaf)
//    }
//
//    @PostMapping("/viewReport")
//    public String viewReport(@RequestParam("reportId") Long reportId, Model model) {
//        MedicalReport report = medicalReportService.getReportById(reportId);
//
//        if (report != null) {
//            model.addAttribute("report", report);
//            return "showReport"; // This will look for showReport.html (or showReport.thymeleaf)
//        } else {
//            model.addAttribute("errorMessage", "Report not found with ID: " + reportId);
//            return "viewReport";
//        }
//    }
//}