package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.model.*;

public interface CategoriesService {
    BusinessCategory addBusinessCategory(BusinessCategoryModel businessCategoryModel);

    InventoryCategory addInventoryCategory(InventoryCategoryModel inventoryCategoryModel);

    ClientCategory addClientCategory(ClientCategoryModel clientCategoryModel);

    LoanCategory addLoanCategory(LoanCategoryModel loanCategoryModel);

    PurchaseCategory addPurchaseCategory(PurchaseCategoryModel purchaseCategoryModel);


    InventoryCategory alterInventoryCategoryById(Long id, InventoryCategoryModel inventoryCategoryModel);

    BusinessCategory alterBusinessCategoryById(Long id, BusinessCategoryModel businessCategoryModel);

    ClientCategory alterClientCategoryById(Long id, ClientCategoryModel clientCategoryModel);

    LoanCategory alterLoanCategoryById(Long id, LoanCategoryModel loanCategoryModel);

    PurchaseCategory alterPurchaseCategoryById(Long id, PurchaseCategoryModel purchaseCategoryModel);

    SaleCategory addSaleCategory(SaleCategoryModel saleCategoryModel);

    SaleCategory alterSaleCategoryById(Long id, SaleCategoryModel saleCategoryModel);
}
