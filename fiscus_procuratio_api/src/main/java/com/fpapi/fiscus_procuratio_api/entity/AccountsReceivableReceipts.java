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
public class AccountsReceivableReceipts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "accounts_receivable_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_AR_ARR_accountsReceivableId"))
    private AccountsReceivable accountsReceivable;

    private BigDecimal paymentReceived;
    private BigDecimal balanceDue;
    private String debitedAccount;



    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_GL_ARR_transactionNumber"), name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false)
    private GeneralLedger generalLedger;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_ARR_cashTransactionNumber"))
    private Cash cash;

}
