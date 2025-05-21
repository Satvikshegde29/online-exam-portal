package com.examportal.service.Impl;

import com.examportal.exception.ResourceNotFoundException;
import com.examportal.model.User;
import com.examportal.repository.UserRepository;
import com.examportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // @Override
    // public boolean authenticateUser(String email, String password) {
    //     // Find user by email and check password
    //     Optional<User> user = userRepository.findByEmail(email);
    //     return user.isPresent() && user.get().getPassword().equals(password);
    // }

    @Override
    public boolean authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    @Override
    public User getUserById(Long userId) {
        // Find user by ID or throw ResourceNotFoundException
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        user.setPassword(null); // Do not expose password
        return user;
    }

    @Override
    public User updateUser(Long userId, User updatedUser) {
        // Find the existing user or throw ResourceNotFoundException
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        // Update user details
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());

        // Save updated user
        return userRepository.save(existingUser);
    }
}