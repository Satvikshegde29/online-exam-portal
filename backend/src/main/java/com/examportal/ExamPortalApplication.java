package com.examportal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.examportal.model.User;
import com.examportal.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ExamPortalApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ExamPortalApplication.class, args);
        System.out.println("Exam Portal Application is running...");
        System.out.println("Welcome to the Online Exam Portal!");
        System.out.println("You can register, login, and take exams.");
    }

    @PostConstruct
    public void initAdminUser() {
        String adminEmail = "admin@gmail.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
            System.out.println("Default admin user created: " + adminEmail);
        }
    }
}
