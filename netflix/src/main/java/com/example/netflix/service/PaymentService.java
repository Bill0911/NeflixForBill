package com.example.netflix.service;

import com.example.netflix.entity.Payment;
import com.example.netflix.entity.SubscriptionType;
import com.example.netflix.entity.User;
import com.example.netflix.repository.PaymentRepository;
import com.example.netflix.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    public Payment processPayment(Integer userId, SubscriptionType subscriptionType, boolean discountApplied) {
        logger.info("Processing payment for userId: {}, subscriptionType: {}, discountApplied: {}", userId, subscriptionType, discountApplied);

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            logger.error("User not found for userId: {}", userId);
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();

        // Check if the trial period has ended
        if (LocalDateTime.now().isBefore(user.getTrialEndDate())) {
            logger.error("Trial period is still active for userId: {}", userId);
            throw new IllegalArgumentException("Trial period is still active");
        }

        double paymentAmount;
        switch (subscriptionType) {
            case SD:
                paymentAmount = 7.99;
                break;
            case HD:
                paymentAmount = 10.99;
                break;
            case UHD:
                paymentAmount = 13.99;
                break;
            default:
                logger.error("Invalid subscription type: {}", subscriptionType);
                throw new IllegalArgumentException("Invalid subscription type");
        }

        if (discountApplied) {
            paymentAmount -= 2.00;
        }

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setSubscriptionType(subscriptionType);
        payment.setPaymentAmount(paymentAmount);
        payment.setDiscountApplied(discountApplied);
        payment.setPaid(true);
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Payment processed successfully for userId: {}", userId);
        return savedPayment;
    }
}
