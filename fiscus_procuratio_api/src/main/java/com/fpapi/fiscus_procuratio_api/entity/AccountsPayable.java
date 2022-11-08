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

    @ManyToOne
    @JoinColumn(name = "businessId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_B_AP_businessId"))
    private Businesses business;

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


}
