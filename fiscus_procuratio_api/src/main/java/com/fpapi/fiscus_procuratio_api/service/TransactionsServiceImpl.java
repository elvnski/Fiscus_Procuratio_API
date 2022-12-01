package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.exceptions.*;
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

    @Autowired
    private ClientAccountsRepository clientAccountsRepository;

    @Autowired
    private BusinessAccountsRepository businessAccountsRepository;

    @Autowired
    private DefaultCashAccountsRepository defaultCashAccountsRepository;


    @Override
    public Sales recordSale(SalesModel salesModel) {

        CashInvoicesIssued cashInvoicesIssued = cashInvoicesIssuedRepository.findById(salesModel.getCashInvoiceNumber()).get();

        try {
            checkIssuedInvoicePaid(cashInvoicesIssued);
        } catch (IssuedCashInvoicePaidException e) {
            throw new RuntimeException(e);
        }

        Inventory inventory = cashInvoicesIssued.getInventory();

        try {
            checkSellingPriceNotSet(inventory);
        } catch (SellingPriceNotSetException e) {
            throw new RuntimeException(e);
        }

        ClientAccounts payingClientAccount = clientAccountsRepository.findByAccountName(salesModel.getClientAccountName());
        CashAccounts defaultTradingAccount = defaultCashAccountsRepository.findByAccountFunction("Trading").getCashAccount();

        try {
            checkIfClientAccountIsActive(payingClientAccount);
        } catch (InactiveClientAccountException e) {
            throw new RuntimeException(e);
        }

        try {
            cashService.checkIfCashAccountIsActive(defaultTradingAccount);
        } catch (InactiveCashAccountException e) {
            throw new RuntimeException(e);
        }


        try {
            checkMatchingOwnerForAccount(cashInvoicesIssued, payingClientAccount);
        } catch (WrongAccountSelectionException e) {
            throw new RuntimeException(e);
        }


        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Inventory", "Asset", BigDecimal.valueOf(0.0), cashInvoicesIssued.getInvoiceAmount()));


        Cash cash = cashService.receiveCashFromClient(new CashFromClientModel("Inventory", "Cash",
                "Sale of " + cashInvoicesIssued.getUnits() + " units of '" + inventory.getItemName() + "' at a total of KES " + cashInvoicesIssued.getInvoiceAmount() + ".",
                payingClientAccount,
                cashInvoicesIssued.getInvoiceAmount()));


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
        cashInvoicesIssued.setDatePaid(codesAndDateService.getDate());
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

        try {
            checkItemSoldOut(inventory, cashInvoicesIssuedModel.getUnits());
        } catch (ItemSoldOutException e) {
            throw new RuntimeException(e);
        }

        try {
            checkIfAllowedDiscountExceeded(inventory, cashInvoicesIssuedModel.getDiscountPercentage());
        } catch (ExcessiveDiscountException e) {
            throw new RuntimeException(e);
        }


        BigDecimal totalPrice = inventory.getSellingPrice().multiply(cashInvoicesIssuedModel.getUnits()).setScale(2, RoundingMode.HALF_UP);

        BigDecimal discountValue = totalPrice.multiply(cashInvoicesIssuedModel.getDiscountPercentage().divide(BigDecimal.valueOf(100.0), RoundingMode.HALF_UP));

        BigDecimal invoiceTotal = totalPrice.subtract(discountValue);

        String details = "Cash Invoice for " + cashInvoicesIssuedModel.getUnits() + " Units of '" + cashInvoicesIssuedModel.getItemName() +
                "' For a Total of KES " + invoiceTotal + " Issued to " + cashInvoicesIssuedModel.getClientName() + " at " + codesAndDateService.getDate() + ".";

        CashInvoicesIssued cashInvoicesIssued = CashInvoicesIssued.builder()
                .invoiceNumber(codesAndDateService.generateTransactionCode("CII-"))
                .dateIssued(codesAndDateService.getDate())
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
                .dateReceived(codesAndDateService.getDate())
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

        try {
            checkReceivedInvoicePaid(cashInvoicesReceived);
        } catch (ReceivedCashInvoicePaidException e) {
            throw new RuntimeException(e);
        }


        //TODO Change ALL *accountNumber parameters to Account Name

        CashAccounts defaultTradingAccount = defaultCashAccountsRepository.findByAccountFunction("Trading").getCashAccount();
        BusinessAccounts receivingBusinessAccount = businessAccountsRepository.findByAccountName(purchasesModel.getBusinessAccountName());

        try {
            cashService.checkIfCashAccountIsActive(defaultTradingAccount);
        } catch (InactiveCashAccountException e) {
            throw new RuntimeException(e);
        }

        try {
            checkIfBusinessAccountIsActive(receivingBusinessAccount);
        } catch (InactiveBusinessAccountException e) {
            throw new RuntimeException(e);
        }

        try {
            checkMatchingOwnerForAccount(cashInvoicesReceived ,receivingBusinessAccount);
        } catch (WrongAccountSelectionException e) {
            throw new RuntimeException(e);
        }


        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Purchases", "Expenses", cashInvoicesReceived.getInvoiceAmount(), BigDecimal.ZERO));


        Cash cash = cashService.sendCashToBusiness(new CashToBusinessModel("Cash", "Purchases",
                cashInvoicesReceived.getDetails(),
                receivingBusinessAccount,
                cashInvoicesReceived.getInvoiceAmount()));

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
        cashInvoicesReceived.setDatePaid(codesAndDateService.getDate());

        cashInvoicesReceivedRepository.save(cashInvoicesReceived);

        return purchase;
    }

    @Override
    public void checkIfBusinessAccountIsActive(BusinessAccounts businessAccount) throws InactiveBusinessAccountException {

        if (!businessAccount.getActive()){
            throw new InactiveBusinessAccountException("The Selected Business Account '" + businessAccount.getAccountNumber() + " - " + businessAccount.getAccountName() +
                    "' is currently INACTIVE. \nPlease Select an Activated Account or Activate This Account.");
        }

    }

    @Override
    public void checkIfClientAccountIsActive(ClientAccounts clientAccount) throws InactiveClientAccountException {

        if (!clientAccount.getActive()){
            throw new InactiveClientAccountException("The Selected Client Account '" + clientAccount.getAccountNumber() + " - " + clientAccount.getAccountName() +
                    "' is currently INACTIVE. \nPlease Select an Activated Account or Activate This Account.");
        }

    }

    @Override
    public void checkSellingPriceNotSet(Inventory inventory) throws SellingPriceNotSetException {
        if (inventory.getSellingPrice().compareTo(BigDecimal.ZERO) == 0){
            throw new SellingPriceNotSetException("Cannot Complete Sale Because The Selling Price of The Inventory Item: '" + inventory.getItemName() + "' is Not Set.");
        }
    }

    @Override
    public void checkItemSoldOut(Inventory inventory, BigDecimal orderUnitsNo) throws ItemSoldOutException {

        BigDecimal currentQuantity = inventory.getCurrentQuantity();
        BigDecimal remainingQuantity = currentQuantity.subtract(orderUnitsNo);


        if (remainingQuantity.compareTo(BigDecimal.ZERO) < 0){

            BigDecimal stockExceededBy = remainingQuantity.multiply(BigDecimal.valueOf(-1)).setScale(2, RoundingMode.HALF_UP);

            throw new ItemSoldOutException("Cannot issue the cash invoice because the quantity of '" + inventory.getItemName() + "' in stock is not sufficient. " +
                    "We require " + stockExceededBy + " more units of to be able to satisfy this order.") ;
        }


    }

    @Override
    public void checkIssuedInvoicePaid(CashInvoicesIssued cashInvoicesIssued) throws IssuedCashInvoicePaidException {

        if (cashInvoicesIssued.getPaid()){
            throw new IssuedCashInvoicePaidException("This Action Is Forbidden as the issued cash invoice '" + cashInvoicesIssued.getInvoiceNumber() +
                    " - " + cashInvoicesIssued.getDetails() + "' from " + cashInvoicesIssued.getDateIssued() + " was already paid on " + cashInvoicesIssued.getDatePaid());
        }

    }

    @Override
    public void checkReceivedInvoicePaid(CashInvoicesReceived cashInvoicesReceived) throws ReceivedCashInvoicePaidException {

        if (cashInvoicesReceived.getPaid()){
            throw new ReceivedCashInvoicePaidException("This Action Is Forbidden as the received cash invoice '" + cashInvoicesReceived.getInvoiceNumber() +
                    " - " + cashInvoicesReceived.getDetails() + "' from " + cashInvoicesReceived.getDateReceived() + " was already paid on " + cashInvoicesReceived.getDatePaid());
        }

    }

    @Override
    public void checkIfAllowedDiscountExceeded(Inventory inventory, BigDecimal proposedDiscount) throws ExcessiveDiscountException {

        BigDecimal allowedDiscount = inventory.getAllowedDiscountPercentage();

        if (proposedDiscount.compareTo(allowedDiscount) > 0){
            throw new ExcessiveDiscountException("Cannot Issue the Cash Invoice as the Proposed Discount of " + proposedDiscount + " Percent " +
                    "has Exceeded the Allowed Discount of " + allowedDiscount + " Percent on this Item. Select an Acceptable Discount Value");
        }

    }

    @Override
    public void checkMatchingOwnerForAccount(CashInvoicesIssued cashInvoicesIssued, ClientAccounts clientAccounts) throws WrongAccountSelectionException {

        Clients payingClient = cashInvoicesIssued.getClient();

        Clients proposedPayingClient = clientAccounts.getClient();

        if (!payingClient.equals(proposedPayingClient)){
            throw new WrongAccountSelectionException("Cannot Process Payment Receipt as the client listed in the Cash Invoice Issued: '" + payingClient.getName() +
                    "', does not match with the client owning the specified paying account: '" + proposedPayingClient.getName() + "'.");
        }


    }

    @Override
    public void checkMatchingOwnerForAccount(CashInvoicesReceived cashInvoicesReceived, BusinessAccounts businessAccounts) throws WrongAccountSelectionException {

        Businesses paidBusiness = cashInvoicesReceived.getBusiness();

        Businesses proposedPaidBusiness = businessAccounts.getBusiness();

        if (!paidBusiness.equals(proposedPaidBusiness)) {
            throw new WrongAccountSelectionException("Cannot Process Payment Receipt as the Business listed in the Cash Invoice Received: '" + paidBusiness.getName() +
                    "', does not match with the business owning the specified receiving account: '" + proposedPaidBusiness.getName() + "'.");
        }


    }
}
