package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.Inventory;
import com.fpapi.fiscus_procuratio_api.entity.InventoryPurchase;
import com.fpapi.fiscus_procuratio_api.exceptions.ExcessiveDiscountException;
import com.fpapi.fiscus_procuratio_api.model.InventoryModel;
import com.fpapi.fiscus_procuratio_api.model.InventoryPurchaseModel;

import java.math.BigDecimal;

public interface InventoryService {
    InventoryPurchase recordInventoryPurchase(InventoryPurchaseModel inventoryPurchaseModel);

    Inventory addInventoryItem(InventoryModel inventoryModel);

    Inventory setSellingPrice(String invNo, InventoryModel inventoryModel);

    Inventory setDiscount(String invNo, InventoryModel inventoryModel);

    void checkForExcessiveDiscount(BigDecimal setDiscount) throws ExcessiveDiscountException;
}
