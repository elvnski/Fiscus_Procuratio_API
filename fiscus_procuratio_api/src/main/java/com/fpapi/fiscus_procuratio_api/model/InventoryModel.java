package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryModel {

    private String itemName;
    private String inventoryCategoryName;
    private BigDecimal reorderLevel;

    /* FOR SETTING SELLING PRICE */
    private BigDecimal sellingPrice;

    /* FOR SETTING discount */
    private BigDecimal discount;



}
