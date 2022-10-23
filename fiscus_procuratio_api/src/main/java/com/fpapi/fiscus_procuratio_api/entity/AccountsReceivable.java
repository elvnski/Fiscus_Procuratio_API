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
public class AccountsReceivable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoiceNumber", referencedColumnName = "invoiceNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_INVI_AR_invoiceNumber"))
    private InvoicesIssued invoicesIssued;


    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_CL_AR_client_id"))
    private Clients clients;


    private String description;
    private BigDecimal invoiceAmount;
    private BigDecimal balance;
    private BigDecimal discount;
    private Date dueDate;

    @OneToMany(mappedBy = "accountsReceivable")
    @ToString.Exclude
    private List<AccountsReceivableReceipts> accountsReceivableReceipts;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_AR_transactionNumber"))
    private GeneralLedger generalLedger;


}
