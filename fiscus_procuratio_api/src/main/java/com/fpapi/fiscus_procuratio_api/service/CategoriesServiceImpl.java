package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
    public InventoryCategory addInventoryCategory(InventoryCategoryModel inventoryCategoryModel) {

        InventoryCategory inventoryCategory = new InventoryCategory();
        inventoryCategory.setCategory(inventoryCategoryModel.getCategory());

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
    public LoanCategory addLoanCategory(LoanCategoryModel loanCategoryModel) {

        LoanCategory loanCategory = new LoanCategory();
        loanCategory.setCategory(loanCategoryModel.getCategory());

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
    public InventoryCategory alterInventoryCategoryById(Long id, InventoryCategoryModel inventoryCategoryModel) {

        InventoryCategory inventoryCategory = null;

        if(inventoryCategoryRepository.findById(id).isPresent()){

            inventoryCategory = inventoryCategoryRepository.findById(id).get();

            if (Objects.nonNull(inventoryCategoryModel.getNewCategoryName())){
                inventoryCategory.setCategory(inventoryCategoryModel.getNewCategoryName());
            }

            inventoryCategoryRepository.save(inventoryCategory);
        }

        return inventoryCategory;
    }

    @Override
    public BusinessCategory alterBusinessCategoryById(Long id, BusinessCategoryModel businessCategoryModel) {

        BusinessCategory businessCategory = null;

        if (businessCategoryRepository.findById(id).isPresent()){
            businessCategory = businessCategoryRepository.findById(id).get();

            if (Objects.nonNull(businessCategoryModel.getNewCategoryName())) {
                businessCategory.setCategory(businessCategoryModel.getNewCategoryName());
            }

            businessCategoryRepository.save(businessCategory);
        }

        return businessCategory;
    }

    @Override
    public ClientCategory alterClientCategoryById(Long id, ClientCategoryModel clientCategoryModel) {

        ClientCategory clientCategory = null;

        if (clientCategoryRepository.findById(id).isPresent()){

            clientCategory = clientCategoryRepository.findById(id).get();

            if (Objects.nonNull(clientCategoryModel.getNewCategoryName())){
                clientCategory.setCategory(clientCategoryModel.getNewCategoryName());
            }

            clientCategoryRepository.save(clientCategory);
        }

        return clientCategory;
    }

    @Override
    public LoanCategory alterLoanCategoryById(Long id, LoanCategoryModel loanCategoryModel) {

        LoanCategory loanCategory = null;

        if (loanCategoryRepository.findById(id).isPresent()){
            loanCategory = loanCategoryRepository.findById(id).get();

            if (Objects.nonNull(loanCategoryModel.getNewCategoryName())){
                loanCategory.setCategory(loanCategoryModel.getNewCategoryName());
            }

            loanCategoryRepository.save(loanCategory);
        }

        return loanCategory;
    }

    @Override
    public PurchaseCategory alterPurchaseCategoryById(Long id, PurchaseCategoryModel purchaseCategoryModel) {

        PurchaseCategory purchaseCategory = null;

        if (purchaseCategoryRepository.findById(id).isPresent()){
            purchaseCategory = purchaseCategoryRepository.findById(id).get();

            if (Objects.nonNull(purchaseCategoryModel.getNewCategoryName())){
                purchaseCategory.setCategory(purchaseCategoryModel.getNewCategoryName());
            }

            purchaseCategoryRepository.save(purchaseCategory);
        }

        return purchaseCategory;
    }
}
