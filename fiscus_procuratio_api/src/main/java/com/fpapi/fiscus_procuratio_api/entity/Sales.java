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
public class Sales {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    private String saleNumber;

    private String itemName;

    @ManyToOne
    @JoinColumn(name = "inventoryNumber", referencedColumnName = "inventoryNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_I_S_inventoryNumber"))
    private Inventory inventory;

    /* CLIENT ID */
    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_CL_S_clientId"))
    private Clients clients;

    private BigDecimal unitsSold;
    private BigDecimal unitPrice;
    private BigDecimal unitDiscount;
    private BigDecimal totalPrice;
    private BigDecimal totalDiscount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_GL_S_transactionNumber"))
    private GeneralLedger generalLedger;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_S_cashTransactionNumber"))
    private Cash cash;




}
