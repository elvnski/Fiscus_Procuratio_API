package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Loans {
    @Id
    private String loanNumber;

    /* LOAN CATEGORY */
    @ManyToOne
    @JoinColumn(name = "loanCategory", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_LC_L_loanCategory"))
    private LoanCategory loanCategory;

    /* Bank ID */
    @ManyToOne
    @JoinColumn(name = "bankId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_BAN_L_bankId"))
    private Banks bank;

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


    @OneToMany(mappedBy = "loans")
    @ToString.Exclude
    private List<LoanPayments> loanPaymentsList;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_L_transactionNumber"))
    private GeneralLedger generalLedger;



}
