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
public class GeneralLedger {

    @Id
    private String transactionNumber;

    private Date date;
    private String accountType;
    private String accountName;
    private BigDecimal debit;
    private BigDecimal credit;

    /* RELATIONSHIPS */
    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private AccountsPayablePayments accountsPayablePayments;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private AccountsReceivableReceipts accountsReceivableReceipts;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private AccountsReceivable accountsReceivable;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private AccountsPayable accountsPayable;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private Cash cash;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private CashPayments cashPayments;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private CashReceipts cashReceipts;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private LoanPayments loanPayments;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private Loans loans;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private InventoryPurchase inventoryPurchase;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private Sales sales;

    @OneToOne(mappedBy = "generalLedger", fetch = FetchType.EAGER)
    private Purchases purchases;



}
