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
public class CashPayments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_CP_transactionNumber"))
    private GeneralLedger generalLedger;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "invoiceNumber", referencedColumnName = "invoiceNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_INVO_CP_invoiceNumber"))
    private InvoicesOwed invoicesOwed;

    private BigDecimal cashPaid;
    private BigDecimal invoiceBalance;

    private String entity;
    private String details;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_CP_cashTransactionNumber"))
    private Cash cash;



}
