package com.study.payroll.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String name;
    private BigDecimal dailyIncome;
    private Integer days;

    public BigDecimal getTotal() {
        return dailyIncome.multiply(BigDecimal.valueOf(days));
    }
}
