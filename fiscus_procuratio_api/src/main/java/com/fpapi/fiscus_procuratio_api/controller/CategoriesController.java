package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/categories/business/altername")
    public String alterBusinessCategoryName(@RequestBody BusinessCategoryModel businessCategoryModel) {

        BusinessCategory businessCategory = categoriesService.findBusinessCategoryByName(businessCategoryModel.getCategory());
        String newName = categoriesService.alterBusinessCategoryName(businessCategory, businessCategoryModel.getNewCategoryName()).getCategory();

        return "Business category name changed from: '" + businessCategoryModel.getCategory() + "' to: '" + newName + "'";
    }


    /* ADDS A NEW INVENTORY CATEGORY */
    @PostMapping("/categories/inventory/add")
    public String addInventoryCategory(@RequestBody InventoryCategoryModel inventoryCategoryModel) {

        InventoryCategory inventoryCategory = categoriesService.addInventoryCategory(inventoryCategoryModel);
        String name = inventoryCategory.getCategory();

        return "Inventory Category: '" + name + "', Saved Successfully!";
    }

    /* CHANGES INVENTORY CATEGORY NAME */
    @PostMapping("/categories/inventory/altername")
    public String alterInventoryCategoryName(@RequestBody InventoryCategoryModel inventoryCategoryModel) {

        InventoryCategory inventoryCategory = categoriesService.findInventoryCategoryByName(inventoryCategoryModel.getCategory());
        String newName = categoriesService.alterInventoryCategoryName(inventoryCategory, inventoryCategoryModel.getNewCategoryName()).getCategory();

        return "Inventory category name changed from: '" + inventoryCategoryModel.getCategory() + "' to: '" + newName + "'";
    }

    /* ADDS A NEW CLIENT CATEGORY */
    @PostMapping("/categories/client/add")
    public String addClientCategory(@RequestBody ClientCategoryModel clientCategoryModel) {

        ClientCategory clientCategory = categoriesService.addClientCategory(clientCategoryModel);
        String name = clientCategory.getCategory();

        return "Client Category: '" + name + "', Saved Successfully!";
    }

    /* CHANGES INVENTORY CATEGORY NAME */
    @PostMapping("/categories/client/altername")
    public String alterClientCategoryName(@RequestBody ClientCategoryModel clientCategoryModel) {

        ClientCategory clientCategory = categoriesService.findClientCategoryByName(clientCategoryModel.getCategory());
        String newName = categoriesService.alterClientCategoryName(clientCategory, clientCategoryModel.getNewCategoryName()).getCategory();

        return "Client category name changed from: '" + clientCategoryModel.getCategory() + "' to: '" + newName + "'";
    }

    /* ADDS A NEW LOAN CATEGORY */
    @PostMapping("/categories/loan/add")
    public String addLoanCategory(@RequestBody LoanCategoryModel loanCategoryModel){

        LoanCategory loanCategory = categoriesService.addLoanCategory(loanCategoryModel);
        String name = loanCategory.getCategory();

        return "Loan Category: '" + name + "', Saved Successfully!";
    }

    /* CHANGES LOAN CATEGORY NAME */
    @PostMapping("/categories/loan/altername")
    public String alterLoanCategoryName(@RequestBody LoanCategoryModel loanCategoryModel) {

        LoanCategory loanCategory = categoriesService.findLoanCategoryByName(loanCategoryModel.getCategory());
        String newName = categoriesService.alterLoanCategoryName(loanCategory, loanCategoryModel.getNewCategoryName()).getCategory();

        return "Loan category name changed from: '" + loanCategoryModel.getCategory() + "' to: '" + newName + "'";
    }

    /* ADDS A NEW PURCHASE CATEGORY */
    @PostMapping("/categories/purchase/add")
    public String addPurchaseCategory(@RequestBody PurchaseCategoryModel purchaseCategoryModel) {

        PurchaseCategory purchaseCategory = categoriesService.addPurchaseCategory(purchaseCategoryModel);
        String name = purchaseCategory.getCategory();

        return "Purchase Category: '" + name + "', Saved Successfully!";
    }

    /* CHANGES PURCHASE CATEGORY NAME */
    @PostMapping("categories/purchase/altername")
    public String alterPurchaseCategoryName(@RequestBody PurchaseCategoryModel purchaseCategoryModel) {

        PurchaseCategory purchaseCategory = categoriesService.findPurchaseCategoryByName(purchaseCategoryModel.getCategory());
        String newName = categoriesService.alterPurchaseCategoryName(purchaseCategory, purchaseCategoryModel.getNewCategoryName()).getCategory();

        return "Purchase category name changed from: '" + purchaseCategoryModel.getCategory() + "' to: '" + newName + "'";
    }








}
