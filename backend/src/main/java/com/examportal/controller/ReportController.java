package com.examportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examportal.model.Report;
import com.examportal.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * Get all reports.
     * @return List of all reports.
     */
    @GetMapping("/all")
    public List<Report> getAllReports() {
        // System.out.println("Fetching all reports");
        return reportService.getAllReports();
    }

    /**
     * Get reports based on criteria (examID and/or userID).
     * @param examID The exam ID to filter reports (optional).
     * @param userID The user ID to filter reports (optional).
     * @return List of filtered reports.
     */
    @GetMapping("/filter")
    public List<Report> getReportsByCriteria(
            @RequestParam(required = false) Long examID,
            @RequestParam(required = false) Long userID) {
        return reportService.getReportsByCriteria(examID, userID);
    }

    /**
     * Save a new report.
     * @param report The report to save.
     * @return The saved report.
     */
    @PostMapping
    public Report saveReport(@RequestBody Report report) {
        return reportService.saveReport(report);
    }
}