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
public class AccountsPayable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoiceNumber", referencedColumnName = "invoiceNumber", foreignKey = @ForeignKey(name = "FK_INVO_AP_invoiceNumber"))
    private InvoicesOwed invoicesOwed;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loanNumber", referencedColumnName = "loanNumber", foreignKey = @ForeignKey(name = "FK_L_AP_loanNumber"))
    private Loans loans;

    private String supplierName;
    private String description;
    private BigDecimal invoiceAmount;
    private BigDecimal balance;
    private BigDecimal discount;
    private Date dueDate;

    @OneToMany(mappedBy = "accountsPayable")
    @ToString.Exclude
    private List<AccountsPayablePayments> accountsPayablePayments;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_AP_transactionNumber"))
    private GeneralLedger generalLedger;

    /* CONSTRUCTOR FOR REGULAR ACCOUNTS PAYABLE */

    public AccountsPayable(Date date, InvoicesOwed invoicesOwed, Loans loans, String supplierName, String description, BigDecimal invoiceAmount, BigDecimal balance, BigDecimal discount, Date dueDate, GeneralLedger generalLedger) {
        this.date = date;
        this.invoicesOwed = invoicesOwed;
        this.loans = loans;
        this.supplierName = supplierName;
        this.description = description;
        this.invoiceAmount = invoiceAmount;
        this.balance = balance;
        this.discount = discount;
        this.dueDate = dueDate;
        this.generalLedger = generalLedger;
    }
}
