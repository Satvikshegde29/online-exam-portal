package com.examportal.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examportal.model.Exam;
import com.examportal.model.Question;
import com.examportal.model.User;
import com.examportal.repository.ExamRepository;
import com.examportal.repository.QuestionRepository;
import com.examportal.repository.UserRepository;
import com.examportal.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Long questionId, Question updatedQuestion) {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));
        existingQuestion.setText(updatedQuestion.getText());
        existingQuestion.setCategory(updatedQuestion.getCategory());
        existingQuestion.setDifficulty(updatedQuestion.getDifficulty());
        existingQuestion.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
        return questionRepository.save(existingQuestion);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new RuntimeException("Question not found with ID: " + questionId);
        }
        questionRepository.deleteById(questionId);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    @Override
    public List<Question> getQuestionsByDifficulty(String difficulty) {
        return questionRepository.findByDifficulty(difficulty);
    }

    @Override
    public Exam createExam(Map<String, Object> examData) {
        String title = (String) examData.get("title");
        String description = (String) examData.get("description");
        int duration = (int) examData.get("duration");
        int totalMarks = (int) examData.get("totalMarks");
        Number examinerIdNum = (Number) examData.get("examinerId");
        if (examinerIdNum == null) {
            throw new RuntimeException("examinerId is required");
        }
        Long examinerId = examinerIdNum.longValue();

        User examiner = userRepository.findById(examinerId)
            .orElseThrow(() -> new RuntimeException("Examiner not found"));
        if (!"ROLE_EXAMINER".equalsIgnoreCase(examiner.getRole())) {
            throw new RuntimeException("User is not an examiner");
        }

        Exam exam = new Exam();
        exam.setTitle(title);
        exam.setDescription(description);
        exam.setDuration(duration);
        exam.setTotalMarks(totalMarks);
        exam.setExaminer(examiner);

        if (examData.containsKey("questionIds")) {
            List<Integer> questionIdsInt = (List<Integer>) examData.get("questionIds");
            List<Long> questionIds = questionIdsInt.stream().map(Integer::longValue).toList();
            List<Question> questions = questionRepository.findAllById(questionIds);
            exam.setQuestions(questions);
        }

        return examRepository.save(exam);
    }

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }
    
}

