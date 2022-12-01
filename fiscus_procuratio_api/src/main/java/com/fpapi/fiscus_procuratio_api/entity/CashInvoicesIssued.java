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
public class CashInvoicesIssued {

    @Id
    private String invoiceNumber;

    private Date dateIssued;

    private Date datePaid;

    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_CII_C_clientId"))
    private Clients client;

    @ManyToOne
    @JoinColumn(name = "saleCategory", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_SC_CII_saleCategory"))
    private SaleCategory saleCategory;

    @ManyToOne
    @JoinColumn(name = "inventoryNumber", referencedColumnName = "inventoryNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_INVY_CII_inventoryNumber"))
    private Inventory inventory;

    private BigDecimal units;
    private BigDecimal invoiceAmount;
    private BigDecimal discount;
    private BigDecimal discountPercentage;
    private String details;
    private Boolean paid;



    @OneToOne(mappedBy = "cashInvoicesIssued", fetch = FetchType.EAGER)
    private Sales sale;


}
