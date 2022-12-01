package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.exceptions.ExcessiveDiscountException;
import com.fpapi.fiscus_procuratio_api.exceptions.IllegalPricingException;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private InventoryPurchaseRepository inventoryPurchaseRepository;

    @Autowired
    private GeneralLedgerRepository generalLedgerRepository;

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private BusinessesRepository businessesRepository;

    @Autowired
    private InventoryCategoryRepository inventoryCategoryRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private GeneralLedgerService generalLedgerService;

    @Autowired
    private CashService cashService;

    @Autowired
    private CodesAndDateService codesAndDateService;

    @Autowired
    private BusinessAccountsRepository businessAccountsRepository;

    @Autowired
    private DefaultCashAccountsRepository defaultCashAccountsRepository;

    //TODO : Change the Credited Account For ALL PURCHASE TRANSACTIONS to credit the TRADING ACCOUNT and NOT THE CASH TABLE
    @Override
    public InventoryPurchase recordInventoryPurchase(InventoryPurchaseModel inventoryPurchaseModel) {

        BigDecimal paid = (inventoryPurchaseModel.getStockingPrice().multiply(inventoryPurchaseModel.getUnits())).setScale(2, RoundingMode.HALF_UP);

        try {
            cashService.checkForTradingCashOverdraw(paid);
        } catch (CashOverdrawException e) {
            throw new RuntimeException(e);
        }

        Businesses business = businessesRepository.findByName(inventoryPurchaseModel.getBusinessName());

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Inventory Purchase", "Expense", paid, BigDecimal.valueOf(0.0)));


        Cash cash = cashService.sendCashToBusiness(new CashToBusinessModel("Cash", "Inventory Purchase",
                "Purchase of " + inventoryPurchaseModel.getUnits() + " units of " + inventoryPurchaseModel.getItemName() + " from " + business.getName() + ".",
                businessAccountsRepository.findByAccountName(inventoryPurchaseModel.getBusinessAccountName()),
                paid));

        Inventory inventory = inventoryRepository.findByItemName(inventoryPurchaseModel.getItemName());

        InventoryPurchase inventoryPurchase = InventoryPurchase.builder()
                .inventoryPurchaseNumber(codesAndDateService.generateTransactionCode("IP-"))
                .date(codesAndDateService.getDate())
                .inventory(inventory)
                .inventoryCategory(inventoryCategoryRepository.findByCategory(inventoryPurchaseModel.getInventoryCategoryName()))
                .units(inventoryPurchaseModel.getUnits())
                .stockingPrice(inventoryPurchaseModel.getStockingPrice())
                .paid(paid)
                .business(business)
                .generalLedger(generalLedger)
                .cash(cash)
                .build();

        inventoryPurchaseRepository.save(inventoryPurchase);


        inventory.setCurrentQuantity(inventory.getCurrentQuantity().add(inventoryPurchaseModel.getUnits()));
        inventory.setStockingPrice(inventoryPurchaseModel.getStockingPrice());

        inventoryRepository.save(inventory);

        return inventoryPurchase;
    }

    @Override
    public Inventory addInventoryItem(InventoryModel inventoryModel) {

        Inventory inventory = Inventory.builder()
                .inventoryNumber(codesAndDateService.generateTransactionCode("INVY-"))
                .date(codesAndDateService.getDate())
                .itemName(inventoryModel.getItemName())
                .inventoryCategory(inventoryCategoryRepository.findByCategory(inventoryModel.getInventoryCategoryName()))
                .currentQuantity(BigDecimal.valueOf(0.0))
                .reorderLevel(inventoryModel.getReorderLevel())
                .stockingPrice(BigDecimal.valueOf(0.0))
                .sellingPrice(BigDecimal.valueOf(0.0))
                .allowedDiscountPercentage(BigDecimal.valueOf(0.0))
                .totalItemStockValue(BigDecimal.valueOf(0.0))
                .build();

        inventoryRepository.save(inventory);

        return inventory;
    }

    @Override
    public Inventory setSellingPrice(String invNo, InventoryModel inventoryModel) {

        Inventory inventory = inventoryRepository.findById(invNo).get();

        try {
            checkForIllegalPricing(inventoryModel.getSellingPrice(), inventory.getStockingPrice());
        } catch (IllegalPricingException e) {
            throw new RuntimeException(e);
        }

        inventory.setSellingPrice(inventoryModel.getSellingPrice());
        inventory.setTotalItemStockValue(inventoryModel.getSellingPrice().multiply(inventory.getCurrentQuantity()).setScale(2, RoundingMode.HALF_UP));

        inventoryRepository.save(inventory);

        return inventory;
    }

    @Override
    public Inventory setDiscount(String invNo, InventoryModel inventoryModel) {

        Inventory inventory = inventoryRepository.findById(invNo).get();

        try {
            checkForExcessiveDiscount(inventoryModel.getDiscount());
        } catch (ExcessiveDiscountException e) {
            throw new RuntimeException(e);
        }

        inventory.setAllowedDiscountPercentage(inventoryModel.getDiscount());

        inventoryRepository.save(inventory);

        return inventory;
    }


    public void checkForIllegalPricing(BigDecimal setPrice, BigDecimal stockingPrice) throws IllegalPricingException {

        BigDecimal lowestPrice = stockingPrice.multiply(BigDecimal.valueOf(1.200)).setScale(2, RoundingMode.HALF_UP);

         if (setPrice.compareTo(lowestPrice) < 0){
             throw new IllegalPricingException("Cannot set selling price to KES " + setPrice + " as it is below the " +
                     "allowed profit margin of 20% over the KES " + stockingPrice + " stocking price of this Item");
         }

    }

    @Override
    public void checkForExcessiveDiscount(BigDecimal setDiscount) throws ExcessiveDiscountException {

        BigDecimal maxDiscount = BigDecimal.valueOf(15.00);

        if (setDiscount.compareTo(maxDiscount) > 0){
            throw new ExcessiveDiscountException("Cannot set discount of " + setDiscount + "% as it is above the " +
                    "allowed maximum discount of 15%.");
        }
    }




}
