package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.Inventory;
import com.fpapi.fiscus_procuratio_api.entity.InventoryPurchase;
import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.model.InventoryModel;
import com.fpapi.fiscus_procuratio_api.model.InventoryPurchaseModel;
import com.fpapi.fiscus_procuratio_api.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/inventory/items/add")
    public String addInventoryItem(@RequestBody InventoryModel inventoryModel){

        Inventory inventory = inventoryService.addInventoryItem(inventoryModel);

        return "New Inventory Item: '" + inventory.getItemName() + "' Has Been Added Successfully!";
    }



    @PostMapping("/inventory/purchase/record")
    public String recordInventoryPurchase(@RequestBody InventoryPurchaseModel inventoryPurchaseModel){

        InventoryPurchase inventoryPurchase = inventoryService.recordInventoryPurchase(inventoryPurchaseModel);

        return "Inventory Purchase of " + inventoryPurchase.getUnits() + " units of " + inventoryPurchaseModel.getItemName() +
                " from " + inventoryPurchaseModel.getBusinessName() + " has been recorded successfully!";
    }


}
