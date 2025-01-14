package com.example.netflix.service;

import com.example.netflix.dto.MethodResponse;
import com.example.netflix.dto.SubscriptionOverview;
import com.example.netflix.entity.*;
import com.example.netflix.dto.ProfileRequest;
import com.example.netflix.exception.AccessDeniedException;
import com.example.netflix.repository.*;
import com.example.netflix.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private final InvitationRepository invitationRepository;

    @Autowired
    private final PaymentRepository paymentRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final SubscriptionCostRepository subscriptionCostRepository;

    public UserService(UserRepository userRepository, LanguageRepository languageRepository, ProfileRepository profileRepository, InvitationRepository invitationRepository, PaymentRepository paymentRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, SubscriptionCostRepository subscriptionCostRepository) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.profileRepository = profileRepository;
        this.invitationRepository = invitationRepository;
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

//    public User loginUser(String email, String password) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        Integer id = user.getAccountId();
//        // Debug statement to check if the user is found
//        System.out.println("User found: " + user.getEmail());
//
//        // Check if account is blocked
//        if (user.isIsBlocked()) {
//            throw new RuntimeException("Account is blocked due to too many failed login attempts.");
//        }
//
//        // Debug statement to check the stored password
//        System.out.println("Stored password: " + user.getPassword());
//        System.out.println("Password to verify: " + password);
//
//        // Verify password
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            // Increment failed attempts and block account if necessary
//            int failedAttempts = user.getFailedLoginAttempts() + 1;
//            userRepository.patchByAccountId(id, null, null, null, null, null, null, null, null, null, failedAttempts, null,  null);
//
//            if (failedAttempts >= 3) {
//                userRepository.patchByAccountId(id, null, null, null, true, null, null, null, null, null, null, LocalDateTime.now(),  null);
//                throw new RuntimeException("Invalid credentials. The account has been blocked :(");
//            }
//            // Debug statement to check failed attempts
//            System.out.println("Failed attempts: " + failedAttempts);
//
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        // Reset failed attempts on successful login
//        userRepository.patchByAccountId(id, null, null, null, null, null, null, null, null, null, 0, null,  null);
//
//        // Debug statement to confirm successful login
//        System.out.println("Login successful for user: " + user.getEmail());
//
//        return user; // Return full User object
//    }

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
        userRepository.patchByAccountId(accountId, user.getPassword(), user.getPaymentMethod(), user.isActive(), user.isBlocked(), user.getSubscription(), user.getTrialStartDate(), user.getTrialEndDate(), user.getAccountId(), user.getRole(), user.getFailedLoginAttempts(), user.getLockTime(), user.isDiscount());
    }

    public void updateUserById(Integer accountId, User user) {
        userRepository.updateByAccountId(accountId, user.getPassword(), user.getPaymentMethod(), user.isActive(), user.isBlocked(), user.getSubscription(), user.getTrialStartDate(), user.getTrialEndDate(), user.getAccountId(), user.getRole(), user.getFailedLoginAttempts(), user.getLockTime(), user.isDiscount());
    }

    public void enforceRoleRestriction (String token, Role requiredRole) throws AccessDeniedException {
        int id = jwtUtil.extractId(token.substring(7));
        Role authedUserRole = getUserById(id).getRole();
        if (authedUserRole.isLowerThan(requiredRole))
        {
            throw new AccessDeniedException("Access denied. Minimal required level - " + requiredRole);
        }
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

        userRepository.patchByAccountId(user.getAccountId(), passwordEncoder.encode(newPassword), null, null, null, null, null, null, null, null, 0, null,  null);;
    }

    public void inviteUser(String inviterEmail, String inviteeEmail) {
        Optional<User> inviterOptional = userRepository.findByEmail(inviterEmail);
        Optional<User> inviteeOptional = userRepository.findByEmail(inviteeEmail);

        if (inviterOptional.isEmpty() || inviteeOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User inviter = inviterOptional.get();
        User invitee = inviteeOptional.get();

        if (inviterEmail.equals(inviteeEmail)) {
            throw new IllegalArgumentException("User cannot invite themselves");
        }

        if (invitationRepository.existsByInviterAndInvitee(inviter, invitee)) {
            throw new IllegalArgumentException("User has already invited this user");
        }

        if (inviter.isActive() && invitee.isActive() && !inviter.isDiscount() && !invitee.isDiscount()) {
            inviter.setDiscount(true);
            invitee.setDiscount(true);
            userRepository.save(inviter);
            userRepository.save(invitee);

            Invitation invitation = new Invitation();
            invitation.setInviter(inviter);
            invitation.setInvitee(invitee);
            invitationRepository.save(invitation);

            processPayment(inviter);
            processPayment(invitee);
        }
    }

    private void processPayment(User user) {
        // Calculate payment amount based on subscription type and discount
        double paymentAmount = calculatePaymentAmount(user.getSubscription(), user.isDiscount());

        // Check if a payment record already exists for this user
        Optional<Payment> existingPaymentOpt = paymentRepository.findByUserAccountId(user.getAccountId());

        Payment payment;
        if (existingPaymentOpt.isPresent())
        {
            payment = existingPaymentOpt.get();
            payment.setSubscriptionType(user.getSubscription());
            payment.setPaymentAmount(paymentAmount);
            payment.setDiscountApplied(user.isDiscount());
            payment.setPaid(true);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setNextBillingDate(LocalDateTime.now().plusMonths(1));
        }
        else
        {
            payment = new Payment();
            payment.setUser(user);
            payment.setSubscriptionType(user.getSubscription());
            payment.setPaymentAmount(paymentAmount);
            payment.setDiscountApplied(user.isDiscount());
            payment.setPaid(true);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setNextBillingDate(LocalDateTime.now().plusMonths(1));
        }

        paymentRepository.save(payment);
    }
    
    //I know this code right here is quite controversial
    // as I already implemented these in payment service
    // but for some reasons it does not update table after getting 200 OK,
    // so I just did like this as a temporary solution.

    private double calculatePaymentAmount(SubscriptionType subscriptionType, boolean discountApplied)
    {
        double amount;
        switch (subscriptionType)
        {
            case SD:
                amount = 7.99;
                break;
            case HD:
                amount = 10.99;
                break;
            case UHD:
                amount = 13.99;
                break;
            default:
                throw new IllegalArgumentException("Unknown subscription type: " + subscriptionType);
        }
        if (discountApplied)
        {
            amount -= 2.00;
        }
        return amount;
    }

    public List<SubscriptionOverview> getAllSubscriptionCosts() {
        return subscriptionCostRepository.findAllSubscriptionCosts();
    }

}

