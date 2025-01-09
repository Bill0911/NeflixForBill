package com.example.netflix.service;

import com.example.netflix.dto.MethodResponse;
import com.example.netflix.entity.*;
import com.example.netflix.dto.ProfileRequest;
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

    public UserService(UserRepository userRepository, LanguageRepository languageRepository, ProfileRepository profileRepository, InvitationRepository invitationRepository, PaymentRepository paymentRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.profileRepository = profileRepository;
        this.invitationRepository = invitationRepository;
        this.paymentRepository = paymentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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
        userRepository.save(user); // Save the user directly using the repository
        System.out.println("CHECKPOINT - 9");

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
        System.out.println("User activated: " + user.isActive()); // Debug statement
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Integer id = user.getAccountId();
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
            userRepository.patchByAccountId(id, null, null, null, null, null, null, null, null, null, failedAttempts, null,  null);

            if (failedAttempts >= 3) {
                userRepository.patchByAccountId(id, null, null, null, true, null, null, null, null, null, null, LocalDateTime.now(),  null);
                throw new RuntimeException("Invalid credentials. The account has been blocked :(");
            }
            // Debug statement to check failed attempts
            System.out.println("Failed attempts: " + failedAttempts);

            throw new RuntimeException("Invalid credentials");
        }

        // Reset failed attempts on successful login
        userRepository.patchByAccountId(id, null, null, null, null, null, null, null, null, null, 0, null,  null);

        // Debug statement to confirm successful login
        System.out.println("Login successful for user: " + user.getEmail());

        return user; // Return full User object
    }

    public void addUser(User user) {
        userRepository.addUser(user.getEmail(), user.getPassword(), user.getPaymentMethod(), user.getLanguage());
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

    public void enforceRoleRestriction (String token, Role role)
    {
        if (role.isHigherThan(jwtUtil.extractRole(token))) {
            throw new RuntimeException("Your role is to low to access this endpoint");
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

    public void inviteUser(Integer accountId, Integer invitedUserId) {
        Optional<User> userOptional = userRepository.findByAccountId(accountId);
        Optional<User> invitedUserOptional = userRepository.findByAccountId(invitedUserId);

        if (userOptional.isEmpty() || invitedUserOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();
        User invitedUser = invitedUserOptional.get();

        if (accountId.equals(invitedUserId)) {
            throw new IllegalArgumentException("User cannot invite themselves");
        }

        //We will prob. need another crud procedures for that btw
        if (invitationRepository.existsByInviterAndInvitee(user, invitedUser)) {
            throw new IllegalArgumentException("User has already invited this user");
        }

        if (user.isActive() && invitedUser.isActive() && !user.isDiscount() && !invitedUser.isDiscount()) {
            userRepository.patchByAccountId(user.getAccountId(), null, null, null, null, null, null, null, null, null, null, null,  true);
            userRepository.patchByAccountId(invitedUser.getAccountId(), null, null, null, null, null, null, null, null, null, null, null,  true);

            //We will prob. need another crud procedures for that btw
            Invitation invitation = new Invitation();
            invitation.setInviter(user);
            invitation.setInvitee(invitedUser);
            invitationRepository.save(invitation);

            processPayment(user);
            processPayment(invitedUser);
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


}

