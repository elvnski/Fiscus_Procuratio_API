package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.CashInvoicesIssued;
import com.fpapi.fiscus_procuratio_api.entity.CashInvoicesReceived;
import com.fpapi.fiscus_procuratio_api.entity.Purchases;
import com.fpapi.fiscus_procuratio_api.entity.Sales;
import com.fpapi.fiscus_procuratio_api.model.CashInvoicesIssuedModel;
import com.fpapi.fiscus_procuratio_api.model.CashInvoicesReceivedModel;
import com.fpapi.fiscus_procuratio_api.model.PurchasesModel;
import com.fpapi.fiscus_procuratio_api.model.SalesModel;
import com.fpapi.fiscus_procuratio_api.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping("/transactions/sales/invoices/issue")
    private String recordInvoiceReceived(@RequestBody CashInvoicesIssuedModel cashInvoicesIssuedModel) {

        CashInvoicesIssued cashInvoicesIssued = transactionsService.recordInvoiceIssued(cashInvoicesIssuedModel);

        return "New Cash Invoice Issued No. " + cashInvoicesIssued.getInvoiceNumber() + " of Total KES " + cashInvoicesIssued.getInvoiceAmount() + " to "
                + cashInvoicesIssuedModel.getClientName() + " Has Been Recorded on " + cashInvoicesIssued.getDateIssued() + " Successfully!";
    }


    //TODO Create Exception to prevent selling an item whose selling price is not set(i.e Selling price or Stocking price is at 0.00)
    //TODO Create Exception to prevent selling item quantity which makes stock quantity below zero

    //TODO Create exception to prevent executing a cash invoice sale for a PAID Cash Invoice

    @PostMapping("/transactions/sales/execute")
    private String recordSale(@RequestBody SalesModel salesModel) {

        Sales sales = transactionsService.recordSale(salesModel);

        return "Sale of " + sales.getUnitsSold() + " Units of " + sales.getItemName() + " at a Total of "
                + sales.getPrice() + " to " + sales.getClients().getName() + " on " + sales.getDate() + " Has Been Recorded Successfully!";
    }

    @PostMapping("/transactions/purchases/invoices/record")
    private String recordPurchaseInvoice(@RequestBody CashInvoicesReceivedModel cashInvoicesReceivedModel){

        CashInvoicesReceived cashInvoicesReceived = transactionsService.recordPurchaseInvoice(cashInvoicesReceivedModel);

        return cashInvoicesReceived.getDetails() + " Has Been Recorded Successfully!";
    }

    //TODO Create exception to prevent executing a cash invoice PURCHASE for a PAID Cash Invoice

    //TODO Create a BUSINESS ASSETS CATEGORY table
    //TODO Create a BUSINESS ASSETS table to record and keep value of ALL ACQUISITIONS/PURCHASES of the Business
    //TODO Ensure BUSINESS ASSETS table is debited after every purchase and RECORDED IN GENERAL LEDGER

    @PostMapping("/transactions/purchases/execute")
    private String recordPurchase(@RequestBody PurchasesModel purchasesModel){

        Purchases purchase = transactionsService.recordPurchase(purchasesModel);

        return "Purchase of " + purchase.getUnitsBought() + " Units of " + purchase.getItemName() + " at a Total of "
                + purchase.getTotalPrice() + " from " + purchase.getBusinesses().getName() + " on " + purchase.getDate() + " Has Been Recorded Successfully!";
    }




}
