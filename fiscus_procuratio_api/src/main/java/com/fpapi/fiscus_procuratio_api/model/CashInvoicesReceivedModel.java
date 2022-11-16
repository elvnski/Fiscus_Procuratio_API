package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashInvoicesReceivedModel {

    private String invoiceNumber;
    private String businessName;
    private String purchaseCategory;
    private String itemName;
    private BigDecimal units;
    private BigDecimal unitPrice;
    private BigDecimal discountPercentage;


}
