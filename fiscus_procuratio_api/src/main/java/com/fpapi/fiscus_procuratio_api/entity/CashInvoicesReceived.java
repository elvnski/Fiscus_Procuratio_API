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
public class CashInvoicesReceived {

    @Id
    private String invoiceNumber;

    private Date dateReceived;

    private Date datePaid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "businessId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_BIZ_CIR_businessId"))
    private Businesses business;

    @ManyToOne
    @JoinColumn(name = "purchaseCategory", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_PC_CIR_purchaseCategory"))
    private PurchaseCategory purchaseCategory;

    private String itemName;
    private BigDecimal units;
    private BigDecimal unitPrice;
    private BigDecimal discountPercentage;
    private BigDecimal discount;
    private BigDecimal invoiceAmount;
    private String details;
    private Boolean paid;



    @OneToOne(mappedBy = "cashInvoicesReceived", fetch = FetchType.EAGER)
    private Purchases purchase;





}
