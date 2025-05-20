package com.examportal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.examportal.model.User;
import com.examportal.repository.UserRepository;

@SpringBootApplication
public class ExamPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamPortalApplication.class, args);
        System.out.println("Exam Portal Application is running...");
    }

    @Bean
    public CommandLineRunner addDefaultAdmin(UserRepository userRepository) {
        return args -> {
            String adminEmail = "admin@examportal.com";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail(adminEmail);
                admin.setPassword("admin123"); // In production, hash the password!
                admin.setRole("Admin");
                userRepository.save(admin);
                System.out.println("Default admin user created.");
            }
        };
    }
}
