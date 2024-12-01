package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BookService bookService;

    @Autowired
    private BookForUserService bookForUserService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(String name, String password) {
        if (userRepository.findByName(name).isPresent()) {
            throw new RuntimeException("User already exists with this name.");
        }
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(name, hashedPassword, LocalDateTime.now());
        return userRepository.save(user);
    }

    public User login(String name, String password) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        } else {
            throw new RuntimeException("Invalid username or password.");
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
