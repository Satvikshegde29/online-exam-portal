package com.examportal.service;

import java.util.List;
import java.util.Map;

import com.examportal.model.Exam;
import com.examportal.model.Question;


public interface AdminService {
    Question addQuestion(Question question);
    Question updateQuestion(Long questionId, Question updatedQuestion);
    void deleteQuestion(Long questionId);
    List<Question> getAllQuestions();
    List<Question> getQuestionsByCategory(String category);
    List<Question> getQuestionsByDifficulty(String difficulty);
    Exam createExam(Map<String, Object> examData);
    List<Exam> getAllExams();
    
}