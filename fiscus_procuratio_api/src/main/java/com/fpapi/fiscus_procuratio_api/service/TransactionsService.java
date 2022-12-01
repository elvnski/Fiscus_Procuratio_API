package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.exceptions.*;
import com.fpapi.fiscus_procuratio_api.model.CashInvoicesIssuedModel;
import com.fpapi.fiscus_procuratio_api.model.CashInvoicesReceivedModel;
import com.fpapi.fiscus_procuratio_api.model.PurchasesModel;
import com.fpapi.fiscus_procuratio_api.model.SalesModel;

import java.math.BigDecimal;

public interface TransactionsService {
    Sales recordSale(SalesModel salesModel);

    CashInvoicesIssued recordInvoiceIssued(CashInvoicesIssuedModel cashInvoicesIssuedModel);

    CashInvoicesReceived recordPurchaseInvoice(CashInvoicesReceivedModel cashInvoicesReceivedModel);

    Purchases recordPurchase(PurchasesModel purchasesModel);

    void checkIfBusinessAccountIsActive(BusinessAccounts businessAccount) throws InactiveBusinessAccountException;

    void checkIfClientAccountIsActive(ClientAccounts clientAccount) throws InactiveClientAccountException;

    void checkSellingPriceNotSet(Inventory inventory) throws SellingPriceNotSetException;

    void checkItemSoldOut(Inventory inventory, BigDecimal orderUnitsNo) throws ItemSoldOutException;

    void checkIssuedInvoicePaid(CashInvoicesIssued cashInvoicesIssued) throws IssuedCashInvoicePaidException;

    void checkReceivedInvoicePaid(CashInvoicesReceived cashInvoicesReceived) throws ReceivedCashInvoicePaidException;

    void checkIfAllowedDiscountExceeded(Inventory inventory, BigDecimal proposedDiscount) throws ExcessiveDiscountException;

    void checkMatchingOwnerForAccount(CashInvoicesIssued cashInvoicesIssued, ClientAccounts clientAccounts) throws WrongAccountSelectionException;

    void checkMatchingOwnerForAccount(CashInvoicesReceived cashInvoicesReceived, BusinessAccounts businessAccounts) throws WrongAccountSelectionException;


}
