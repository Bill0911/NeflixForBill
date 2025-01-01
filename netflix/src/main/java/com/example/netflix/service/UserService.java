package com.example.netflix.service;

import com.example.netflix.entity.*;
import com.example.netflix.dto.ProfileRequest;
import com.example.netflix.repository.LanguageRepository;
import com.example.netflix.repository.ProfileRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final LanguageRepository languageRepository;

    @Autowired
    private final ProfileRepository profileRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, LanguageRepository languageRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user, String token) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setActive(false);
        userRepository.save(user);
        // Debug statement to check the encoded password
        System.out.println("Encoded password during registration: " + encodedPassword);
    }

    @Transactional
    public void activateUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(true); // Set active to true (1)
        userRepository.save(user);
        System.out.println("User activated: " + user.isActive()); // Debug statement
    }

    public String changeLanguage(Integer languageId, Integer accountId) {
        Optional<User> userOptional = userRepository.findByAccountId(accountId);
        Optional<Language> languageOptional = languageRepository.findById(languageId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Language language = languageOptional.get();
            user.setLanguage(language);
            userRepository.save(user);
            return language.getName();
        }
        return "none";
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Debug statement to check if the user is found
        System.out.println("User found: " + user.getEmail());

        // Check if account is blocked
        if (user.isIsBlocked()) {
            throw new RuntimeException("Account is blocked due to too many failed login attempts.");
        }

        // Debug statement to check the stored password
        System.out.println("Stored password: " + user.getPassword());
        System.out.println("Password to verify: " + password);

        // Verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            // Increment failed attempts and block account if necessary
            int failedAttempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(failedAttempts);

            if (failedAttempts >= 3) {
                user.setIsBlocked(true);
            }

            userRepository.save(user);

            // Debug statement to check failed attempts
            System.out.println("Failed attempts: " + failedAttempts);

            throw new RuntimeException("Invalid credentials");
        }

        // Reset failed attempts on successful login
        user.setFailedLoginAttempts(0);
        userRepository.save(user);

        // Debug statement to confirm successful login
        System.out.println("Login successful for user: " + user.getEmail());

        return user; // Return full User object
    }

    public String getLanguageName(Integer accountId) {
        Optional<User> user = userRepository.findByAccountId(accountId);
        if (user.isPresent()) {
            return user.get().getLanguage().getName();
        }
        throw new RuntimeException("Invalid id");
    }

    public Profile addProfile(ProfileRequest profileRequest, Integer accountId)
    {
        Optional<User> userOptional = userRepository.findByAccountId(accountId);

        Profile profile = new Profile();
        profile.setProfileImage(profileRequest.getProfileImage());
        profile.setAge(profileRequest.getAge());
        profile.setName(profileRequest.getName());

        if (userOptional.isPresent() && userOptional.get().getProfiles().size() <= 3) {
            profile.setUser(userOptional.get());

            System.out.println("Image: " + profile.getProfileImage() + " ,Age: " + profile.getAge());
            System.out.println("User: " + profile.getUser());

            profileRepository.save(profile);

            return profile;
        }

        return null;
    }

    public boolean isRoleForAccount(Integer accountId, Role role) {
        return userRepository.findByAccountId(accountId).map(user -> user.getRole() == role).orElse(false);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public boolean isAccountBlocked(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.isBlocked();
    }

    public void requestPasswordReset(String email)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

