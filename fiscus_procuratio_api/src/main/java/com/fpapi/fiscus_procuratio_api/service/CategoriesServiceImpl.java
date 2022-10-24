package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriesServiceImpl implements CategoriesService{

    @Autowired
    private BusinessCategoryRepository businessCategoryRepository;

    @Autowired
    private InventoryCategoryRepository inventoryCategoryRepository;

    @Autowired
    private ClientCategoryRepository clientCategoryRepository;

    @Autowired
    private LoanCategoryRepository loanCategoryRepository;

    @Autowired
    private PurchaseCategoryRepository purchaseCategoryRepository;

    @Override
    public BusinessCategory addBusinessCategory(BusinessCategoryModel businessCategoryModel) {

        BusinessCategory businessCategory = new BusinessCategory();
        businessCategory.setCategory(businessCategoryModel.getCategory());

        businessCategoryRepository.save(businessCategory);

        return businessCategory;
    }

    @Override
    public BusinessCategory findBusinessCategoryByName(String category) {

        return businessCategoryRepository.findByCategory(category);
    }

    @Override
    public BusinessCategory alterBusinessCategoryName(BusinessCategory businessCategory, String newCategoryName) {
        businessCategory.setCategory(newCategoryName);
        businessCategoryRepository.save(businessCategory);

        return businessCategory;
    }

    @Override
    public InventoryCategory addInventoryCategory(InventoryCategoryModel inventoryCategoryModel) {

        InventoryCategory inventoryCategory = new InventoryCategory();
        inventoryCategory.setCategory(inventoryCategoryModel.getCategory());

        inventoryCategoryRepository.save(inventoryCategory);

        return inventoryCategory;
    }

    @Override
    public InventoryCategory findInventoryCategoryByName(String category) {
        return inventoryCategoryRepository.findByCategory(category);
    }

    @Override
    public InventoryCategory alterInventoryCategoryName(InventoryCategory inventoryCategory, String newCategoryName) {

        inventoryCategory.setCategory(newCategoryName);
        inventoryCategoryRepository.save(inventoryCategory);

        return inventoryCategory;
    }

    @Override
    public ClientCategory addClientCategory(ClientCategoryModel clientCategoryModel) {

        ClientCategory clientCategory = new ClientCategory();
        clientCategory.setCategory(clientCategoryModel.getCategory());

        clientCategoryRepository.save(clientCategory);

        return clientCategory;
    }

    @Override
    public ClientCategory findClientCategoryByName(String category) {
        return clientCategoryRepository.findByCategory(category);
    }

    @Override
    public ClientCategory alterClientCategoryName(ClientCategory clientCategory, String newCategoryName) {

        clientCategory.setCategory(newCategoryName);
        clientCategoryRepository.save(clientCategory);

        return clientCategory;
    }

    @Override
    public LoanCategory addLoanCategory(LoanCategoryModel loanCategoryModel) {

        LoanCategory loanCategory = new LoanCategory();
        loanCategory.setCategory(loanCategoryModel.getCategory());

        loanCategoryRepository.save(loanCategory);

        return loanCategory;
    }

    @Override
    public LoanCategory findLoanCategoryByName(String category) {
        return loanCategoryRepository.findByCategory(category);
    }

    @Override
    public LoanCategory alterLoanCategoryName(LoanCategory loanCategory, String newCategoryName) {

        loanCategory.setCategory(newCategoryName);
        loanCategoryRepository.save(loanCategory);

        return loanCategory;
    }

    @Override
    public PurchaseCategory addPurchaseCategory(PurchaseCategoryModel purchaseCategoryModel) {

        PurchaseCategory purchaseCategory = new PurchaseCategory();
        purchaseCategory.setCategory(purchaseCategoryModel.getCategory());

        purchaseCategoryRepository.save(purchaseCategory);

        return purchaseCategory;
    }

    @Override
    public PurchaseCategory findPurchaseCategoryByName(String category) {
        return purchaseCategoryRepository.findByCategory(category);
    }

    @Override
    public PurchaseCategory alterPurchaseCategoryName(PurchaseCategory purchaseCategory, String newCategoryName) {

        purchaseCategory.setCategory(newCategoryName);
        purchaseCategoryRepository.save(purchaseCategory);

        return purchaseCategory;
    }
}
