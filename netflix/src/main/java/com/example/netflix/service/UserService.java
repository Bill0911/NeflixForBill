package com.example.netflix.service;

import com.example.netflix.entity.*;
import com.example.netflix.dto.ProfileRequest;
import com.example.netflix.repository.LanguageRepository;
import com.example.netflix.repository.PasswordResetTokenRepository;
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

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public UserService(UserRepository userRepository, LanguageRepository languageRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public void registerUser(User user, String token) {
        user.setActive(false);
        userRepository.save(user);
        // Save the token in a way that it can be verified later (e.g., in-memory or database)
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

        // Check if account is blocked
        if (user.isIsBlocked()) {
            throw new RuntimeException("Account is blocked due to too many failed login attempts.");
        }

        // Verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            // Increment failed attempts and block account if necessary
            int failedAttempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(failedAttempts);

            if (failedAttempts >= 3) {
                user.setIsBlocked(true);
            }

            userRepository.save(user);
            throw new RuntimeException("Invalid credentials");
        }

        // Reset failed attempts on successful login
        user.setFailedLoginAttempts(0);
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

    public boolean isRoleForAccount(Integer accountId, Role role) {
        return userRepository.findByAccountId(accountId).map(user -> user.getRole() == role).orElse(false);
    }

    public void createPasswordResetToken(String email, String token) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiryDate(calculateExpiryDate(2 * 60)); // only 2 hours
            tokenRepository.save(resetToken);
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }

    public void resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);
        if (tokenOptional.isPresent()) {
            PasswordResetToken resetToken = tokenOptional.get();
            if (isTokenExpired(resetToken)) {
                throw new RuntimeException("Token has expired");
            }
            User user = resetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new RuntimeException("Invalid token");
        }
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    private boolean isTokenExpired(PasswordResetToken token) {
        final Calendar cal = Calendar.getInstance();
        return token.getExpiryDate().before(cal.getTime());
    }

    public boolean isAccountBlocked(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.isBlocked();
    }
}

