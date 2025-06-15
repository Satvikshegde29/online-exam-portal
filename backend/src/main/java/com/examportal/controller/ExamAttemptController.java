package com.examportal.controller;

import com.examportal.model.ExamAttempt;
import com.examportal.service.ExamAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/exams")
public class ExamAttemptController {

    @Autowired
    private ExamAttemptService examAttemptService;

    // Submit answers
    @PostMapping("/{examId}/submit")
    public ResponseEntity<?> submitExam(
            @PathVariable Long examId,
            @RequestParam Long userId,
            @RequestBody Map<Long, Integer> answers) { // Changed String to Integer
        ExamAttempt attempt = examAttemptService.submitAttempt(userId, examId, answers);
        return ResponseEntity.ok(Map.of("score", attempt.getScore(), "total", attempt.getExam().getQuestions().size()));
    }

    // Get report
    @GetMapping("/{examId}/report/{userId}")
    public ResponseEntity<?> getReport(@PathVariable Long examId, @PathVariable Long userId) {
        ExamAttempt attempt = examAttemptService.getReport(userId, examId);
        return ResponseEntity.ok(attempt);
    }
}