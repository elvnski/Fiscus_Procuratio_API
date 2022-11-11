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
public class AmortizationGenerator {

    @Id
    private Long paymentNo;

    private java.sql.Date paymentDate;
    private BigDecimal startingBalance;
    private BigDecimal scheduledPayment;
    private BigDecimal extraPayment;
    private BigDecimal totalPayment;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal endingBalance;
    private BigDecimal cumulativeInterest;





}
