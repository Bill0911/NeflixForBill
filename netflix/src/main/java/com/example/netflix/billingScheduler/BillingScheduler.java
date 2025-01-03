package com.example.netflix.billingScheduler;

import com.example.netflix.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BillingScheduler {

    @Autowired
    private PaymentService paymentService;

    @Scheduled(cron = "0 0 0 * * ?") // I set this one to let it runs every day at midnight
    public void updateBillingStatus() {
        paymentService.updateBillingStatus();
    }
}
