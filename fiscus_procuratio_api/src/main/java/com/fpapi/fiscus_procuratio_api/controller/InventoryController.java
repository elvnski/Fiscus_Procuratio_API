package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.Inventory;
import com.fpapi.fiscus_procuratio_api.entity.InventoryPurchase;
import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.model.InventoryModel;
import com.fpapi.fiscus_procuratio_api.model.InventoryPurchaseModel;
import com.fpapi.fiscus_procuratio_api.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/inventory/items/add")
    public String addInventoryItem(@RequestBody InventoryModel inventoryModel){

        Inventory inventory = inventoryService.addInventoryItem(inventoryModel);

        return "New Inventory Item: '" + inventory.getItemName() + "' Has Been Added Successfully!";
    }



    @PostMapping("/inventory/purchases/record")
    public String recordInventoryPurchase(@RequestBody InventoryPurchaseModel inventoryPurchaseModel){

        InventoryPurchase inventoryPurchase = inventoryService.recordInventoryPurchase(inventoryPurchaseModel);

        return "Inventory Purchase of " + inventoryPurchase.getUnits() + " units of " + inventoryPurchaseModel.getItemName() +
                " from " + inventoryPurchaseModel.getBusinessName() + " has been recorded successfully!";
    }

    @PutMapping("/inventory/pricing/sellingprice/{invNo}")
    public String setSellingPrice(@PathVariable("invNo") String invNo, @RequestBody InventoryModel inventoryModel){

        Inventory inventory = inventoryService.setSellingPrice(invNo, inventoryModel);

        return "Selling Price for Inventory Item " + inventory.getInventoryNumber() + " - " + inventory.getItemName() +
                " Set at KES " + inventory.getSellingPrice() + " Successfully!";
    }

    @PutMapping("/inventory/pricing/discount/{invNo}")
    public String setDiscount(@PathVariable("invNo") String invNo, @RequestBody InventoryModel inventoryModel){

        Inventory inventory = inventoryService.setDiscount(invNo, inventoryModel);

        return " Discount for Inventory Item " + inventory.getInventoryNumber() + " - " + inventory.getItemName() +
                " Set at " + inventory.getAllowedDiscountPercentage() + "% Successfully!";
    }

}
