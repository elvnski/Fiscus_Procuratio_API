package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

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

    private String itemName;

    @ManyToOne
    @JoinColumn(name = "purchaseCategory", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_PC_P_purchaseCategory"))
    private PurchaseCategory purchaseCategory;

    @ManyToOne
    @JoinColumn(name = "businessId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_B_P_businessId"))
    private Businesses businesses;

    private BigDecimal unitsBought;
    private BigDecimal unitPrice;
    private BigDecimal unitDiscount;
    private BigDecimal totalPrice;
    private BigDecimal totalDiscount;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_P_transactionNumber"))
    private GeneralLedger generalLedger;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_P_cashTransactionNumber"))
    private Cash cash;



}
