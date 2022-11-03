package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    /* ADDS A NEW BUSINESS CATEGORY */
    @PostMapping("/categories/business/add")
    public String addBusinessCategory(@RequestBody BusinessCategoryModel businessCategoryModel){

        BusinessCategory businessCategory = categoriesService.addBusinessCategory(businessCategoryModel);
        String name = businessCategory.getCategory();

        return "Business Category: '" + name + "', Saved Successfully!";
    }

    /* CHANGES BUSINESS CATEGORY NAME */
    @PutMapping("/categories/business/alter/{id}")
    public String alterBusinessCategory(@PathVariable("id") Long id, @RequestBody BusinessCategoryModel businessCategoryModel) {

        BusinessCategory businessCategory = categoriesService.alterBusinessCategoryById(id, businessCategoryModel);

        return "Business category with ID '" + id + "' name changed to '" + businessCategory.getCategory() + "'.";
    }


    /* ADDS A NEW INVENTORY CATEGORY */
    @PostMapping("/categories/inventory/add")
    public String addInventoryCategory(@RequestBody InventoryCategoryModel inventoryCategoryModel) {

        InventoryCategory inventoryCategory = categoriesService.addInventoryCategory(inventoryCategoryModel);
        String name = inventoryCategory.getCategory();

        return "Inventory Category: '" + name + "', Saved Successfully!";
    }

    /* CHANGES INVENTORY CATEGORY NAME */
    @PutMapping("/categories/inventory/alter/{id}")
    public String alterInventoryCategory(@PathVariable("id") Long id, @RequestBody InventoryCategoryModel inventoryCategoryModel) {

        InventoryCategory inventoryCategory = categoriesService.alterInventoryCategoryById(id, inventoryCategoryModel);

        return "Inventory category with ID '" + id + "' name changed to '" + inventoryCategory.getCategory() + "'";
    }


    /* ADDS A NEW CLIENT CATEGORY */
    @PostMapping("/categories/client/add")
    public String addClientCategory(@RequestBody ClientCategoryModel clientCategoryModel) {

        ClientCategory clientCategory = categoriesService.addClientCategory(clientCategoryModel);
        String name = clientCategory.getCategory();

        return "Client Category: '" + name + "', Saved Successfully!";
    }

    /* CHANGES CLIENT CATEGORY NAME */
    @PutMapping("/categories/client/alter/{id}")
    public String alterClientCategory(@PathVariable("id") Long id, @RequestBody ClientCategoryModel clientCategoryModel) {

        ClientCategory clientCategory = categoriesService.alterClientCategoryById(id, clientCategoryModel);

        return "Client category with ID '" + id + "' name changed to '" + clientCategory.getCategory() + "'";
    }


    /* ADDS A NEW LOAN CATEGORY */
    @PostMapping("/categories/loan/add")
    public String addLoanCategory(@RequestBody LoanCategoryModel loanCategoryModel){

        LoanCategory loanCategory = categoriesService.addLoanCategory(loanCategoryModel);
        String name = loanCategory.getCategory();

        return "Loan Category: '" + name + "', Saved Successfully!";
    }

    /* CHANGES LOAN CATEGORY NAME */
    @PutMapping("/categories/loan/alter/{id}")
    public String alterLoanCategory(@PathVariable("id") Long id, @RequestBody LoanCategoryModel loanCategoryModel) {

        LoanCategory loanCategory = categoriesService.alterLoanCategoryById(id, loanCategoryModel);

        return "Loan category with ID '" + id + "' name changed to '" + loanCategory.getCategory() + "'";
    }


    /* ADDS A NEW PURCHASE CATEGORY */
    @PostMapping("/categories/purchase/add")
    public String addPurchaseCategory(@RequestBody PurchaseCategoryModel purchaseCategoryModel) {

        PurchaseCategory purchaseCategory = categoriesService.addPurchaseCategory(purchaseCategoryModel);
        String name = purchaseCategory.getCategory();

        return "Purchase Category: '" + name + "', Saved Successfully!";
    }

    /* CHANGES PURCHASE CATEGORY NAME */
    @PutMapping("categories/purchase/alter/{id}")
    public String alterPurchaseCategory(@PathVariable("id") Long id, @RequestBody PurchaseCategoryModel purchaseCategoryModel) {

        PurchaseCategory purchaseCategory = categoriesService.alterPurchaseCategoryById(id, purchaseCategoryModel);

        return "Purchase category with ID '" + id + "' name changed to '" + purchaseCategory.getCategory() + "'";
    }




}
