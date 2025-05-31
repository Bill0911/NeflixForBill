package com.example.netflix.controller;

import com.example.netflix.entity.Payment;
import com.example.netflix.entity.SubscriptionType;
import com.example.netflix.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/payments", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Process (create/update) payment
    @PostMapping
    public ResponseEntity<Object> processPayment(
            @RequestParam Integer userId,
            @RequestParam SubscriptionType subscriptionType,
            @RequestParam boolean discountApplied) {

        try {
            Payment payment = paymentService.processPayment(userId, subscriptionType, discountApplied);
            return ResponseEntity.status(HttpStatus.CREATED).body(payment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // Get a payment by ID
    @GetMapping("/{paymentId}")
    public ResponseEntity<Object> getPayment(@PathVariable Integer paymentId) {
        return paymentService.getPaymentById(paymentId)
                .<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found"));
    }

    // List all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // List all payments by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserId(userId));
    }

    // Delete a payment by ID
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable Integer paymentId) {
        try {
            paymentService.deletePayment(paymentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
        }
    }

    // Update payment details (rare in real systems, but included for completeness)
    @PutMapping("/{paymentId}")
    public ResponseEntity<?> updatePayment(@PathVariable Integer paymentId, @RequestBody Payment payment) {
        try {
            Payment updated = paymentService.updatePayment(paymentId, payment);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
        }
    }
}
