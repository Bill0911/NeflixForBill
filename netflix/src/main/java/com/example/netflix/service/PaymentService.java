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
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = userOptional.get();

        double paymentAmount = 0.0;
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
                break;
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

        return paymentRepository.save(payment);
    }
}
