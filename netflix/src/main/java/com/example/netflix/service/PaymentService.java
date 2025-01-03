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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    public Payment processPayment(Integer userId, SubscriptionType subscriptionType, boolean discountApplied) {
        // Check if user exists
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Calculate payment amount based on subscription type and discount
        double paymentAmount = calculatePaymentAmount(subscriptionType, discountApplied);

        // Create a new payment record
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setSubscriptionType(subscriptionType);
        payment.setPaymentAmount(paymentAmount);
        payment.setDiscountApplied(discountApplied);
        payment.setPaid(true);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setNextBillingDate(LocalDateTime.now().plusMonths(1));

        // Save the payment record
        paymentRepository.save(payment);

        return payment;
    }

    private double calculatePaymentAmount(SubscriptionType subscriptionType, boolean discountApplied) {
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
        if (discountApplied) {
            amount -= 2.00;
        }
        return amount;
    }
}
