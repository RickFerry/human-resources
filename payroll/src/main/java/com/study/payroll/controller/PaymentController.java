package com.study.payroll.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.study.payroll.model.Payment;
import com.study.payroll.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{workerId}/days/{days}")
    @HystrixCommand(fallbackMethod = "getPaymentFallback")
    public Payment getPayment(@PathVariable Long workerId, @PathVariable Integer days) {
        return paymentService.getPayment(workerId, days);
    }

    public Payment getPaymentFallback(Long workerId, Integer days) {
        return new Payment("Brann", BigDecimal.valueOf(400.0), days);
    }
}
