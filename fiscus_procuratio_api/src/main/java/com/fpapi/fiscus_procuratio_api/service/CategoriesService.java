package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.model.*;

public interface CategoriesService {
    BusinessCategory addBusinessCategory(BusinessCategoryModel businessCategoryModel);


    BusinessCategory findBusinessCategoryByName(String category);

    BusinessCategory alterBusinessCategoryName(BusinessCategory businessCategory, String newCategoryName);

    InventoryCategory addInventoryCategory(InventoryCategoryModel inventoryCategoryModel);

    InventoryCategory findInventoryCategoryByName(String category);

    InventoryCategory alterInventoryCategoryName(InventoryCategory inventoryCategory, String newCategoryName);

    ClientCategory addClientCategory(ClientCategoryModel clientCategoryModel);

    ClientCategory findClientCategoryByName(String category);

    ClientCategory alterClientCategoryName(ClientCategory clientCategory, String newCategoryName);

    LoanCategory addLoanCategory(LoanCategoryModel loanCategoryModel);

    LoanCategory findLoanCategoryByName(String category);

    LoanCategory alterLoanCategoryName(LoanCategory loanCategory, String newCategoryName);

    PurchaseCategory addPurchaseCategory(PurchaseCategoryModel purchaseCategoryModel);

    PurchaseCategory findPurchaseCategoryByName(String category);

    PurchaseCategory alterPurchaseCategoryName(PurchaseCategory purchaseCategory, String newCategoryName);
}
