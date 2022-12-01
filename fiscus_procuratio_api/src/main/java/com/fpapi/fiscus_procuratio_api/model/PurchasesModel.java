package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasesModel {

    private String cashInvoiceNumber;

    private String itemName;
    private String purchaseCategory;
    private String businessName;

    private String businessAccountName;

    private BigDecimal unitsBought;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal totalPrice;



}
