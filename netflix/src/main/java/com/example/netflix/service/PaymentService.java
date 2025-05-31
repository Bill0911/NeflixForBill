package com.example.netflix.service;

import com.example.netflix.entity.Payment;
import com.example.netflix.entity.SubscriptionType;
import com.example.netflix.entity.User;
import com.example.netflix.repository.PaymentRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. Process/Create or update payment for a user
    public Payment processPayment(Integer userId, SubscriptionType subscriptionType, boolean discountApplied) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        double paymentAmount = calculatePaymentAmount(subscriptionType, discountApplied);

        Optional<Payment> existingPaymentOpt = paymentRepository.findByUser_AccountId(userId);

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

        return paymentRepository.save(payment);
    }

    // 2. Get payment by payment ID
    public Optional<Payment> getPaymentById(Integer paymentId) {
        return paymentRepository.findById(paymentId);
    }

    // 3. Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // 4. Get payments for a specific user
    public List<Payment> getPaymentsByUserId(Integer userId) {
        User user = userRepository.findByAccountId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return paymentRepository.findByUser(user);
    }

    // 5. Delete payment by ID
    public void deletePayment(Integer paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    // 6. Update payment by ID
    public Payment updatePayment(Integer paymentId, Payment newPayment) {
        return paymentRepository.findById(paymentId)
                .map(payment -> {
                    payment.setUser(newPayment.getUser());
                    payment.setDiscountApplied(newPayment.isDiscountApplied());
                    payment.setPaid(newPayment.isPaid());
                    payment.setPaymentDate(newPayment.getPaymentDate());
                    payment.setSubscriptionType(newPayment.getSubscriptionType());
                    payment.setPaymentAmount(newPayment.getPaymentAmount());
                    payment.setNextBillingDate(newPayment.getNextBillingDate());
                    return paymentRepository.save(payment);
                })
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    // Helper for subscription pricing
    private double calculatePaymentAmount(SubscriptionType subscriptionType, boolean discountApplied) {
        double amount;
        switch (subscriptionType) {
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
