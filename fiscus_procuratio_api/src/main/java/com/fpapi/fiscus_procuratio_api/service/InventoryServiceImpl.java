package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.exceptions.ExcessiveDiscountException;
import com.fpapi.fiscus_procuratio_api.exceptions.IllegalPricingException;
import com.fpapi.fiscus_procuratio_api.model.CashModel;
import com.fpapi.fiscus_procuratio_api.model.GeneralLedgerModel;
import com.fpapi.fiscus_procuratio_api.model.InventoryModel;
import com.fpapi.fiscus_procuratio_api.model.InventoryPurchaseModel;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

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

    @Override
    public InventoryPurchase recordInventoryPurchase(InventoryPurchaseModel inventoryPurchaseModel) {

        BigDecimal paid = (inventoryPurchaseModel.getStockingPrice().multiply(inventoryPurchaseModel.getUnits())).setScale(2, RoundingMode.HALF_UP);

        try {
            cashService.checkForCashOverdraw(paid);
        } catch (CashOverdrawException e) {
            throw new RuntimeException(e);
        }

        Businesses business = businessesRepository.findByName(inventoryPurchaseModel.getBusinessName());

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Inventory Purchase", "Expense", paid, BigDecimal.valueOf(0.0)));

        BigDecimal latestCashBalance = BigDecimal.valueOf(0.0);
        if (!cashRepository.findAll().isEmpty()) {
            latestCashBalance = cashRepository.findByDate(cashRepository.getMaxDate()).getBalance();
        }

        Cash cash = cashService.spendCash(new CashModel(generalLedger, "Cash", "Inventory Purchase",
                "Purchase of " + inventoryPurchaseModel.getUnits() + " units of " + inventoryPurchaseModel.getItemName() + " from " + business.getName() + ".",
                BigDecimal.valueOf(0.0), paid, latestCashBalance));

        Inventory inventory = inventoryRepository.findByItemName(inventoryPurchaseModel.getItemName());

        InventoryPurchase inventoryPurchase = InventoryPurchase.builder()
                .inventoryPurchaseNumber("IP-" + generateInventoryNumber())
                .date(getDate())
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
                .inventoryNumber("INV-" + generateInventoryNumber())
                .date(getDate())
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

    public void checkForExcessiveDiscount(BigDecimal setDiscount) throws ExcessiveDiscountException {

        BigDecimal maxDiscount = BigDecimal.valueOf(15.00);

        if (setDiscount.compareTo(maxDiscount) > 0){
            throw new ExcessiveDiscountException("Cannot set dicount of " + setDiscount + "% as it is above the " +
                    "allowed maximum discount of 15%.");
        }
    }


    public static String generateInventoryNumber() {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";

        StringBuilder sb = new StringBuilder(18);

        for (int i = 0; i < 18; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        return new Date(calendar.getTime().getTime());
    }

}
