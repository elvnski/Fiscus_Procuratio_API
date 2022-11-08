package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AccountsPayablePayments {

    @Id
    private String paymentNumber;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "accounts_payable_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_AP_APP_accountsPayableId") )
    private AccountsPayable accountsPayable;

    private BigDecimal payment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_APP_transactionNumber"))
    private GeneralLedger generalLedger;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_APP_cashTransactionNumber"))
    private Cash cash;
}
