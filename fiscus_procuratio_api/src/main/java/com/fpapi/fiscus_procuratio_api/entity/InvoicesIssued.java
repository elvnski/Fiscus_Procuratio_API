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
public class InvoicesIssued {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    private String invoiceNumber;
    private Date issueDate;
    private Date paymentDate;
    private BigDecimal invoiceAmount;
    private BigDecimal discount;
    private String details;
    private String entity;


    @OneToMany(mappedBy = "invoicesIssued")
    @ToString.Exclude
    private List<CashReceipts>  cashReceipts;

    @OneToOne(mappedBy = "invoicesIssued", fetch = FetchType.EAGER)
    private AccountsReceivable accountsReceivable;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_INVI_transactionNumber"))
    private GeneralLedger generalLedger;




}
