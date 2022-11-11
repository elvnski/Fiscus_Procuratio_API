package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryPurchaseModel {

    private String businessName;
    private String itemName;
    private String inventoryCategoryName;
    private BigDecimal units;
    private BigDecimal stockingPrice;


}
