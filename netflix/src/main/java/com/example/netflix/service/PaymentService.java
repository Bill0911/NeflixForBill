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
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setSubscriptionType(subscriptionType);
        payment.setDiscountApplied(discountApplied);
        payment.setPaid(true);
        payment.setPaymentDate(LocalDateTime.now());

        LocalDateTime nextBillingDate = LocalDateTime.now().plusMonths(1);
        payment.setNextBillingDate(nextBillingDate);

        paymentRepository.save(payment);

        return payment;
    }

    public void updateBillingStatus() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Payment> payments = paymentRepository.findAll();
        for (Payment payment : payments) {
            if (payment.getNextBillingDate().isBefore(currentDate) && !payment.isPaid()) {
                payment.setPaid(false);
                paymentRepository.save(payment);
            }
        }
    }
}
