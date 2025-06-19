package com.examportal.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.examportal.exception.ResourceNotFoundException;
import com.examportal.model.Exam;
import com.examportal.model.User;
import com.examportal.repository.ExamRepository;
import com.examportal.repository.UserRepository;
import com.examportal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ExamRepository examRepository; // Assuming you have an ExamRepository for exam-related operations

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
        return userRepository.save(user);
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    

    @Override
    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
    @Override
    public List<Exam> getExams(String userRole) {
        List<Exam> exams = examRepository.findAll();
        if ("ROLE_STUDENT".equalsIgnoreCase(userRole)) {
            for (Exam exam : exams) {
                if (exam.getQuestions() != null) {
                    for (var question : exam.getQuestions()) {
                        question.setCorrectAnswer(null);
                    }
                }
            }
        }
        return exams;
    }
}