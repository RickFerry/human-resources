package com.study.payroll.controller;

import com.study.payroll.model.Payment;
import com.study.payroll.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{workerId}/days/{days}")
    public Payment getPayment(@PathVariable Long workerId, @PathVariable Integer days) {
        return paymentService.getPayment(workerId, days);
    }
}
