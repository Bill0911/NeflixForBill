package com.example.netflix.controller;

import com.example.netflix.entity.Payment;
import com.example.netflix.entity.SubscriptionType;
import com.example.netflix.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment processPayment(@RequestParam Integer userId, @RequestParam SubscriptionType subscriptionType, @RequestParam boolean discountApplied)
    {
        logger.info("Processing payment for userId: {}, subscriptionType: {}, discountApplied: {}", userId, subscriptionType, discountApplied);
        try
        {
            Payment payment = paymentService.processPayment(userId, subscriptionType, discountApplied);
            logger.info("Payment processed successfully for userId: {}", userId);
            return payment;
        }
        catch (Exception e)
        {
            logger.error("Error processing payment for userId: {}", userId, e);
            throw e;
        }
    }
}
