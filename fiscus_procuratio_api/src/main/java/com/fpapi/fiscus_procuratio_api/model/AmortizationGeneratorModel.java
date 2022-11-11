package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmortizationGeneratorModel {

    private BigDecimal loanAmount;
    private BigDecimal intRate;
    private int loanPeriod;
    private BigDecimal extraPayment;
    private String saveToRegistry;
    private String details;
    private String provider;



}
