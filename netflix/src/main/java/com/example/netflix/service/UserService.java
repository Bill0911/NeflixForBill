package com.example.netflix.service;

import com.example.netflix.dto.SubscriptionOverview;
import com.example.netflix.entity.*;
import com.example.netflix.repository.*;
import com.example.netflix.security.JwtUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final LanguageRepository languageRepository;

    @Autowired
    private final ProfileRepository profileRepository;

    @Autowired
    private final PaymentRepository paymentRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final SubscriptionCostRepository subscriptionCostRepository;

    public UserService(UserRepository userRepository, LanguageRepository languageRepository, ProfileRepository profileRepository, PaymentRepository paymentRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, SubscriptionCostRepository subscriptionCostRepository) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.profileRepository = profileRepository;
        this.paymentRepository = paymentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.subscriptionCostRepository = subscriptionCostRepository;
    }

    public User register(User user) {
        System.out.println("CHECKPOINT - 5");
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            System.out.println("CHECKPOINT - email exists");
            throw new RuntimeException("This email is taken");
        }
        System.out.println("CHECKPOINT - email does not exist");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println("CHECKPOINT - 6");
        user.setPassword(encodedPassword);
        System.out.println("CHECKPOINT - 7");
        //userRepository.save(user); // Save the user directly using the repository
        System.out.println("CHECKPOINT - 9");
        user.setTrialStartDate(LocalDateTime.now());

        // Set the trial end date to 7 days after the trial start date
        user.setTrialEndDate(user.getTrialEndDate());
        addUser(user);

        // Debug statement to check the encoded password
        System.out.println("Encoded password during registration: " + encodedPassword);
        return user;
    }

    @Transactional
    public void activateUser(String email) {
        System.out.println("CHECKPOINT - 5");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("CHECKPOINT - 6");
        Integer id = user.getAccountId();
        System.out.println("CHECKPOINT - 7");
        System.out.println("CHECKPOINT - 8");
        userRepository.patchByAccountId(id, null, null, true, null, null, null, null, null, null, null, null,  null);
    }

    public User loginUser(String email, String password) {
        email = email.trim();
        System.out.println("Attempting login for email: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        checkAccountStatus(user);

        if (!verifyPassword(password, user.getPassword())) {
            handleFailedLoginAttempt(user);
        } else {
            resetFailedAttempts(user);
        }

        System.out.println("Login successful for user: " + user.getEmail());
        return user;
    }

    private void checkAccountStatus(User user) {
        if (user.isIsBlocked()) {
            throw new RuntimeException("Account is blocked due to too many failed login attempts.");
        }
    }

    private boolean verifyPassword(String rawPassword, String encodedPassword) {
        System.out.println("Verifying password...");
        System.out.println("Raw password: " + rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private void handleFailedLoginAttempt(User user) {
        int failedAttempts = user.getFailedLoginAttempts() + 1;
        userRepository.patchByAccountId(
                user.getAccountId(), null, null, null, null, null, null, null, null, null, failedAttempts, null, null);

        if (failedAttempts >= 3) {
            userRepository.patchByAccountId(
                    user.getAccountId(), null, null, null, true, null, null, null, null, null, null, LocalDateTime.now(), null);
            throw new RuntimeException("Invalid credentials. The account has been blocked.");
        }

        System.out.println("Failed login attempt count: " + failedAttempts);
        throw new RuntimeException("Invalid credentials.");
    }

    private void resetFailedAttempts(User user) {
        userRepository.patchByAccountId(
                user.getAccountId(), null, null, null, null, null, null, null, null, null, 0, null, null);
    }

    public void addUser(User user) {
        userRepository.addUser(user.getEmail(), user.getPassword(), user.getPaymentMethod(), user.getLanguage(), user.getSubscription());
    }
    public User getUserById(Integer accountId) {
        Optional<User> user = userRepository.findByAccountId(accountId);
        return user.orElse(null);
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public List<User> getManyUsers() {
        return userRepository.findMany();
    }

    public void deleteUserById(Integer accountId) {
        userRepository.deleteByAccountId(accountId);
    }

    public void patchUserById(Integer accountId, User user) {
        System.out.println("role:" + user.getRole() + " and subs:" + user.getSubscription());
        userRepository.patchByAccountId(accountId, user.getPassword(), user.getPaymentMethod(), user.isActive(), user.isBlocked(), user.getSubscription(), user.getTrialStartDate(), user.getTrialEndDate(), user.getAccountId(), user.getRole(), user.getFailedLoginAttempts(), user.getLockTime(), user.isDiscount());
    }

    public void updateUserById(Integer accountId, User user) {
        userRepository.updateByAccountId(accountId, user.getPassword(), user.getPaymentMethod(), user.isActive(), user.isBlocked(), user.getSubscription(), user.getTrialStartDate(), user.getTrialEndDate(), user.getAccountId(), user.getRole(), user.getFailedLoginAttempts(), user.getLockTime(), user.isDiscount());
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void requestPasswordReset(String email)
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.patchByAccountId(user.getAccountId(), passwordEncoder.encode(newPassword), null, null, null, null, null, null, null, null, 0, null,  null);
    }

    @Transactional
    public void inviteUser(Integer inviterId, Integer inviteeId) {
        throwErrorIfUsersDontExists(inviterId, inviteeId);

        if (this.getUserById(inviterId) == this.getUserById(inviteeId) || this.getUserById(inviterId).isDiscount() || this.getUserById(inviteeId).isDiscount()) {
            throw new IllegalArgumentException("At least one of the user already has discount");
        }

        userRepository.patchByAccountId(inviterId, null, null, null, null, null, null, null, null, null, null, null, true);
        userRepository.patchByAccountId(inviteeId, null, null, null, null, null, null, null, null, null, null, null, true);
    }

    public void throwErrorIfUsersDontExists(Integer inviterId, Integer inviteeId)
    {
        if (this.getUserById(inviterId) == null && this.getUserById(inviteeId) == null) {
            throw new IllegalArgumentException("Users do not exist");
        }
    }

    public List<SubscriptionOverview> getAllSubscriptionCosts() {
        return subscriptionCostRepository.findAllSubscriptionCosts();
    }

}

