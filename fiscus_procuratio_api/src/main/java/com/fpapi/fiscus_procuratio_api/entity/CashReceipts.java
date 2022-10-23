package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CashReceipts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_CR_transactionNumber"))
    private GeneralLedger generalLedger;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "invoiceNumber", referencedColumnName = "invoiceNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_INVI_CP_invoiceNumber"))
    private InvoicesIssued invoicesIssued;

    private BigDecimal cashReceived;
    private BigDecimal invoiceBalance;

    private String details;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_CR_cashTransactionNumber"))
    private Cash cash;

    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_CL_CR_client_id"))
    private Clients clients;


}
