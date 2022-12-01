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

    private String nature; /* "DEPOSIT" or "WITHDRAWAL" */

    private String source;
    private String destination;

    private String details;

    @ManyToOne
    @JoinColumn(name = "debitedLocalAccount", referencedColumnName = "accountNumber", foreignKey = @ForeignKey(name = "FK_CA_C_debitedLocalAccount"))
    private CashAccounts debitedCashAccount;

    @ManyToOne
    @JoinColumn(name = "creditedLocalAccount", referencedColumnName = "accountNumber", foreignKey = @ForeignKey(name = "FK_CA_C_creditedLocalAccount"))
    private CashAccounts creditedCashAccount;

    @ManyToOne
    @JoinColumn(name = "debitedBusinessAccount", referencedColumnName = "accountNumber", foreignKey = @ForeignKey(name = "FK_BACC_C_debitedBusinessAccount"))
    private BusinessAccounts debitedBusinessAccount;

    @ManyToOne
    @JoinColumn(name = "creditedClientAccount", referencedColumnName = "accountNumber", foreignKey = @ForeignKey(name = "FK_CACC_C_creditedClientAccount"))
    private ClientAccounts creditedClientAccount;

    @ManyToOne
    @JoinColumn(name = "debitedOwnerAccount", referencedColumnName = "accountNumber", foreignKey = @ForeignKey(name = "FK_OWACC_C_debitedOwnerAccount"))
    private OwnerAccounts debitedOwnerAccount;

    @ManyToOne
    @JoinColumn(name = "creditedOwnerAccount", referencedColumnName = "accountNumber", foreignKey = @ForeignKey(name = "FK_OWACC_C_creditedOwnerAccount"))
    private OwnerAccounts creditedOwnerAccount;



    private BigDecimal credit;
    private BigDecimal debit;
    private BigDecimal balance;


    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private AccountsPayablePayments accountsPayablePayments;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private AccountsReceivableReceipts accountsReceivableReceipts;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private InventoryPurchase inventoryPurchase;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private LoanPayments loanPayments;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private Sales sales;

    @OneToOne(mappedBy = "cash", fetch = FetchType.EAGER)
    private Purchases purchases;



}
