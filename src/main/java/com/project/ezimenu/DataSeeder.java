package com.project.ezimenu;

import com.project.ezimenu.entities.User;
import com.project.ezimenu.repositories.UserRepository;
import com.project.ezimenu.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String...args) {
        String adminUsername = "admin@gmail.com";
        if (userRepository.findByEmail(adminUsername).isEmpty()) {
            User adminUser = new User();
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setEmail(adminUsername);
            adminUser.setRole(Role.ADMIN.toString());
            userRepository.save(adminUser);
        }
    }
}
