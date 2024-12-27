package com.example.netflix.controller;

import com.example.netflix.entity.Payment;
import com.example.netflix.entity.SubscriptionType;
import com.example.netflix.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment processPayment(@RequestParam Integer userId, @RequestParam SubscriptionType subscriptionType, @RequestParam boolean discountApplied) {
        System.out.print("The user " + userId + "has paid Subscription" + subscriptionType);
        return paymentService.processPayment(userId, subscriptionType, discountApplied);
    }
}
