package com.example.netflix.service;

import com.example.netflix.entity.Payment;
import com.example.netflix.entity.SubscriptionType;
import com.example.netflix.entity.User;
import com.example.netflix.repository.PaymentRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    public Payment processPayment(Integer userId, SubscriptionType subscriptionType, boolean discountApplied) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        double paymentAmount = calculatePaymentAmount(subscriptionType, discountApplied);

        Optional<Payment> existingPaymentOpt = paymentRepository.findByUserAccountId(userId);

        Payment payment;
        if (existingPaymentOpt.isPresent()) {
            payment = existingPaymentOpt.get();
            payment.setSubscriptionType(subscriptionType);
            payment.setPaymentAmount(paymentAmount);
            payment.setDiscountApplied(discountApplied || user.isDiscount());
            payment.setPaid(true);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setNextBillingDate(LocalDateTime.now().plusMonths(1));
        } else {
            payment = new Payment();
            payment.setUser(user);
            payment.setSubscriptionType(subscriptionType);
            payment.setPaymentAmount(paymentAmount);
            payment.setDiscountApplied(discountApplied || user.isDiscount());
            payment.setPaid(true);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setNextBillingDate(LocalDateTime.now().plusMonths(1));
        }

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
