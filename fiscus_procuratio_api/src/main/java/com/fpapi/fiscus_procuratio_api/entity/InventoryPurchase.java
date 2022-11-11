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
public class InventoryPurchase {

    @Id
    private String inventoryPurchaseNumber;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "inventoryNumber", referencedColumnName = "inventoryNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_INV_IP_inventoryNumber"))
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_IC_IP_categoryId"))
    private InventoryCategory inventoryCategory;

    private BigDecimal units;
    private BigDecimal stockingPrice;
    private BigDecimal paid;

    @ManyToOne
    @JoinColumn(name = "businessId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_B_IP_businessId"))
    private Businesses business;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_IP_transactionNumber"))
    private GeneralLedger generalLedger;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_IP_cashTransactionNumber"))
    private Cash cash;


}
