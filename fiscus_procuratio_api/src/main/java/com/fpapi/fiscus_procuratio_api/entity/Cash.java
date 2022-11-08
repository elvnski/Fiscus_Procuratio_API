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
public class Cash {

    @Id
    private String cashTransactionNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_CASH_transactionNumber"))
    private GeneralLedger generalLedger;

    private Date date;
    private String sourceAccount;
    private String destinationAccount;
    private String details;
    private BigDecimal credit;
    private BigDecimal debit;
    private BigDecimal balance;


    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private AccountsPayablePayments accountsPayablePayments;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private AccountsReceivableReceipts accountsReceivableReceipts;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private CashPayments cashPayments;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private CashReceipts cashReceipts;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private InventoryPurchase inventoryPurchase;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private LoanPayments loanPayments;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private Sales sales;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private Purchases purchases;



}
