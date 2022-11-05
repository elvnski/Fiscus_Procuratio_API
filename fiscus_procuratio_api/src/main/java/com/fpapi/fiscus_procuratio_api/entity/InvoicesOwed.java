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
public class InvoicesOwed {

    @Id
    private String invoiceNumber;
    private Date issueDate;
    private Date paymentDate;
    private BigDecimal invoiceAmount;
    private BigDecimal discount;
    private String details;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "businessId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_B_IO_BusinessId"))
    private Businesses business;

    @OneToMany(mappedBy = "invoicesOwed")
    @ToString.Exclude
    private List<CashPayments> cashPaymentsList;

    @OneToOne(mappedBy = "invoicesOwed", fetch = FetchType.EAGER)
    private AccountsPayable accountsPayable;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_INVO_transactionNumber"))
    private GeneralLedger generalLedger;



}
