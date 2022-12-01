package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoansModel {

    private String loanNumber;
    private String loanCategory;
    private String bankName;

    private Date date;
    private String details;
    private BigDecimal amount;
    private BigDecimal annualRate;
    private BigDecimal period;
    private BigDecimal numberOfPayments;
    private Date startDate;
    private Date endDate;
    private BigDecimal scheduledPayment;
    private BigDecimal totalEarlyPayments;
    private BigDecimal totalInterest;
    private BigDecimal balance;



}
