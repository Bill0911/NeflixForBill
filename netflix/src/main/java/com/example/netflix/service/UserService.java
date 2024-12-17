package com.example.netflix.service;

import com.example.netflix.entity.Language;
import com.example.netflix.entity.Profile;
import com.example.netflix.dto.ProfileRequest;
import com.example.netflix.entity.User;
import com.example.netflix.repository.LanguageRepository;
import com.example.netflix.repository.ProfileRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final LanguageRepository languageRepository;

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, LanguageRepository languageRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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

        // Check if account is blocked
        if (user.isBlocked()) {
            throw new RuntimeException("Account is blocked due to too many failed login attempts.");
        }

        // Verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            // Increment failed attempts and block account if necessary
            int failedAttempts = user.getFailedAttempts() + 1;
            user.setFailedAttempts(failedAttempts);

            if (failedAttempts >= 3) {
                user.setBlocked(true);
            }

            userRepository.save(user);
            throw new RuntimeException("Invalid credentials");
        }

        // Reset failed attempts on successful login
        user.setFailedAttempts(0);
        userRepository.save(user);

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
}

