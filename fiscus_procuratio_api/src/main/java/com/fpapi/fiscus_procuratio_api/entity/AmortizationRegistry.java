package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AmortizationRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanNumber;
    private String loanProvider;
    private String loanDetails;
    private BigDecimal loanAmount;
    private BigDecimal annualInterestRate;
    private BigDecimal loanPeriodInMonths;
    private BigDecimal extraPaymentPerMonth;
    private Date loanStartDate;

}
