package com.examportal.repository;

import com.examportal.model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {
    Optional<ExamAttempt> findByUser_UserIdAndExam_ExamId(Long userId, Long examId);
}