package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.exceptions.ExcessiveDiscountException;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TransactionsServiceImpl implements TransactionsService{

    @Autowired
    private GeneralLedgerService generalLedgerService;

    @Autowired
    private CashService cashService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private CodesAndDateService codesAndDateService;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private CashInvoicesIssuedRepository cashInvoicesIssuedRepository;

    @Autowired
    private BusinessesRepository businessesRepository;

    @Autowired
    private CashInvoicesReceivedRepository cashInvoicesReceivedRepository;

    @Autowired
    private PurchaseCategoryRepository purchaseCategoryRepository;

    @Autowired
    private PurchasesRepository purchasesRepository;

    @Autowired
    private SaleCategoryRepository saleCategoryRepository;

    @Override
    public Sales recordSale(SalesModel salesModel) {

        CashInvoicesIssued cashInvoicesIssued = cashInvoicesIssuedRepository.findById(salesModel.getCashInvoiceNumber()).get();

        Inventory inventory = cashInvoicesIssued.getInventory();


        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Inventory", "Asset", BigDecimal.valueOf(0.0), cashInvoicesIssued.getInvoiceAmount()));


        BigDecimal latestCashBalance = BigDecimal.valueOf(0.0);

        if (!cashRepository.findAll().isEmpty()) {
            latestCashBalance = cashRepository.findByDate(cashRepository.getMaxDate()).getBalance();
        }

        Cash cash = cashService.depositCash(new CashModel(generalLedger, "Inventory", "Cash",
            "Sale of " + cashInvoicesIssued.getUnits() + " units of '" + inventory.getItemName() + "' at a total of KES " + cashInvoicesIssued.getInvoiceAmount() + ".",
            cashInvoicesIssued.getInvoiceAmount(), BigDecimal.valueOf(0.0), latestCashBalance));


        Sales sale = Sales.builder()
                .generalLedger(generalLedger)
                .cash(cash)
                .cashInvoicesIssued(cashInvoicesIssued)
                .saleNumber(codesAndDateService.generateTransactionCode("SAL-"))
                .date(codesAndDateService.getDate())
                .saleCategory(cashInvoicesIssued.getSaleCategory())
                .itemName(inventory.getItemName())
                .inventory(inventory)
                .clients(cashInvoicesIssued.getClient())
                .unitsSold(cashInvoicesIssued.getUnits())
                .discount(cashInvoicesIssued.getDiscount())
                .price(cashInvoicesIssued.getInvoiceAmount())
                .build();

        salesRepository.save(sale);


        BigDecimal remainingQuantity = inventory.getCurrentQuantity().subtract(cashInvoicesIssued.getUnits());
        BigDecimal remainingStockValue = remainingQuantity.multiply(inventory.getSellingPrice()).setScale(2, RoundingMode.HALF_UP);

        inventory.setCurrentQuantity(remainingQuantity);
        inventory.setTotalItemStockValue(remainingStockValue);

        inventoryRepository.save(inventory);


        cashInvoicesIssued.setPaid(true);
        cashInvoicesIssuedRepository.save(cashInvoicesIssued);

        return sale;
    }

    @Override
    public CashInvoicesIssued recordInvoiceIssued(CashInvoicesIssuedModel cashInvoicesIssuedModel) {

        try {
            inventoryService.checkForExcessiveDiscount(cashInvoicesIssuedModel.getDiscountPercentage());
        } catch (ExcessiveDiscountException e) {
            throw new RuntimeException(e);
        }

        Inventory inventory = inventoryRepository.findByItemName(cashInvoicesIssuedModel.getItemName());

        BigDecimal totalPrice = inventory.getSellingPrice().multiply(cashInvoicesIssuedModel.getUnits()).setScale(2, RoundingMode.HALF_UP);

        BigDecimal discountValue = totalPrice.multiply(cashInvoicesIssuedModel.getDiscountPercentage().divide(BigDecimal.valueOf(100.0), RoundingMode.HALF_UP));

        BigDecimal invoiceTotal = totalPrice.subtract(discountValue);

        String details = "Cash Invoice for " + cashInvoicesIssuedModel.getUnits() + " Units of '" + cashInvoicesIssuedModel.getItemName() +
                "' For a Total of KES " + invoiceTotal + " Issued to " + cashInvoicesIssuedModel.getClientName() + " at " + codesAndDateService.getDate() + ".";

        CashInvoicesIssued cashInvoicesIssued = CashInvoicesIssued.builder()
                .invoiceNumber(codesAndDateService.generateTransactionCode("CII-"))
                .issueDate(codesAndDateService.getDate())
                .client(clientsRepository.findByName(cashInvoicesIssuedModel.getClientName()))
                .saleCategory(saleCategoryRepository.findByCategory(cashInvoicesIssuedModel.getSaleCategory()))
                .inventory(inventory)
                .units(cashInvoicesIssuedModel.getUnits())
                .invoiceAmount(invoiceTotal)
                .discountPercentage(cashInvoicesIssuedModel.getDiscountPercentage())
                .discount(discountValue)
                .details(details)
                .paid(false)
                .build();

        cashInvoicesIssuedRepository.save(cashInvoicesIssued);

        return cashInvoicesIssued;
    }

    @Override
    public CashInvoicesReceived recordPurchaseInvoice(CashInvoicesReceivedModel cashInvoicesReceivedModel) {

        BigDecimal totalPrice = cashInvoicesReceivedModel.getUnits().multiply(cashInvoicesReceivedModel.getUnitPrice()).setScale(2, RoundingMode.HALF_UP);

        BigDecimal discountValue = totalPrice.multiply(cashInvoicesReceivedModel.getDiscountPercentage().divide(BigDecimal.valueOf(100.00), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);

        BigDecimal invoiceTotal = totalPrice.subtract(discountValue);

        String details = "Cash Invoice for " + cashInvoicesReceivedModel.getUnits() + " Units of '" + cashInvoicesReceivedModel.getItemName() +
                "' For a Total of KES " + invoiceTotal + " From " + cashInvoicesReceivedModel.getBusinessName() + " at " + codesAndDateService.getDate();


        CashInvoicesReceived cashInvoicesReceived = CashInvoicesReceived.builder()
                .invoiceNumber(cashInvoicesReceivedModel.getInvoiceNumber())
                .date(codesAndDateService.getDate())
                .business(businessesRepository.findByName(cashInvoicesReceivedModel.getBusinessName()))
                .purchaseCategory(purchaseCategoryRepository.findByCategory(cashInvoicesReceivedModel.getPurchaseCategory()))
                .itemName(cashInvoicesReceivedModel.getItemName())
                .units(cashInvoicesReceivedModel.getUnits())
                .unitPrice(cashInvoicesReceivedModel.getUnitPrice())
                .discountPercentage(cashInvoicesReceivedModel.getDiscountPercentage())
                .discount(discountValue)
                .invoiceAmount(invoiceTotal)
                .details(details)
                .paid(false)
                .build();

        cashInvoicesReceivedRepository.save(cashInvoicesReceived);


        return cashInvoicesReceived;
    }

    @Override
    public Purchases recordPurchase(PurchasesModel purchasesModel) {

        CashInvoicesReceived cashInvoicesReceived = cashInvoicesReceivedRepository.findById(purchasesModel.getCashInvoiceNumber()).get();

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Purchases", "Expenses", cashInvoicesReceived.getInvoiceAmount(), BigDecimal.ZERO));

        Cash cash = cashService.spendCash(new CashModel(generalLedger, "Cash", "Purchases", cashInvoicesReceived.getDetails(), BigDecimal.ZERO, cashInvoicesReceived.getInvoiceAmount(), cashService.getLatestCashBalance()));

        Purchases purchase = Purchases.builder()
                .generalLedger(generalLedger)
                .cash(cash)
                .purchaseNumber(codesAndDateService.generateTransactionCode("PUR-"))
                .date(codesAndDateService.getDate())
                .cashInvoicesReceived(cashInvoicesReceived)
                .itemName(cashInvoicesReceived.getItemName())
                .purchaseCategory(cashInvoicesReceived.getPurchaseCategory())
                .businesses(cashInvoicesReceived.getBusiness())
                .unitsBought(cashInvoicesReceived.getUnits())
                .unitPrice(cashInvoicesReceived.getUnitPrice())
                .discountPercentage(cashInvoicesReceived.getDiscountPercentage())
                .discount(cashInvoicesReceived.getDiscount())
                .totalPrice(cashInvoicesReceived.getInvoiceAmount())
                .build();

        purchasesRepository.save(purchase);

        cashInvoicesReceived.setPaid(true);

        return purchase;
    }
}
