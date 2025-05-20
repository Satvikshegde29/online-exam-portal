package com.examportal.service.Impl;

import com.examportal.model.User;
import com.examportal.repository.UserRepository;
import com.examportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        // Save the user to the database
        return userRepository.save(user);
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        // Find user by email and check password
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    @Override
    public User getUserById(Long userId) {
        // Find user by ID
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(Long userId, User updatedUser) {
        // Find the existing user
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Update user details
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());

        // Save updated user
        return userRepository.save(existingUser);
    }
}