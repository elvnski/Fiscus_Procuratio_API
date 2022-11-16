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
public class Purchases {
    @Id
    private String purchaseNumber;

    private Date date;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashInvoiceNumber", referencedColumnName = "invoiceNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_CIR_PUR_invoiceNumber"))
    private CashInvoicesReceived cashInvoicesReceived;

    private String itemName;

    @ManyToOne
    @JoinColumn(name = "purchaseCategory", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_PC_P_purchaseCategory"))
    private PurchaseCategory purchaseCategory;

    @ManyToOne
    @JoinColumn(name = "businessId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_B_P_businessId"))
    private Businesses businesses;

    private BigDecimal unitsBought;
    private BigDecimal unitPrice;
    private BigDecimal discountPercentage;
    private BigDecimal discount;
    private BigDecimal totalPrice;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_P_transactionNumber"))
    private GeneralLedger generalLedger;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_P_cashTransactionNumber"))
    private Cash cash;




}
