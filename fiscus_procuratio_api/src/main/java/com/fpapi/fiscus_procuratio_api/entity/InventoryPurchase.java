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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    private String inventoryPurchaseNumber;

    private Date date;
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_IC_IP_categoryId"))
    private InventoryCategory inventoryCategory;

    private BigDecimal units;
    private BigDecimal stockingPrice;
    private BigDecimal paid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_IP_transactionNumber"))
    private GeneralLedger generalLedger;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_IP_cashTransactionNumber"))
    private Cash cash;


}
