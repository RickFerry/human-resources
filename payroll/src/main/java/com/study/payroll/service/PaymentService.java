package com.study.payroll.service;

import com.study.payroll.feign.WorkerFeignClient;
import com.study.payroll.model.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentService {
    private final WorkerFeignClient workerFeignClient;

    public Payment getPayment(Long workerId, Integer days) {
        var worker = workerFeignClient.findById(workerId).getBody();
        assert worker != null;
        return new Payment(worker.getName(), worker.getDailyIncome(), days);
    }
}
