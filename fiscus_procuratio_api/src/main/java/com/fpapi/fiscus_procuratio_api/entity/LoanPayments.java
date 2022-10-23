package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LoanPayments {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    private String paymentNumber;

    @ManyToOne
    @JoinColumn(name = "loanNumber", referencedColumnName = "loanNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_L_LP_loanNumber"))
    private Loans loans;

    private String details;
    private BigDecimal amountPaid;
    private Date date;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_LP_transactionNumber"))
    private GeneralLedger generalLedger;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_LP_cashTransactionNumber"))
    private Cash cash;

}
