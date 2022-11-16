package com.fpapi.fiscus_procuratio_api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashInvoicesIssuedModel {

    private String clientName;
    private String itemName;
    private String saleCategory;
    private BigDecimal units;
    private BigDecimal discountPercentage;


}
