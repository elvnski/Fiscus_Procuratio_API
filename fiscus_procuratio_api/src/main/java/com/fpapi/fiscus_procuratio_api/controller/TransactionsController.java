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
                + cashInvoicesIssuedModel.getClientName() + " Has Been Recorded on " + cashInvoicesIssued.getIssueDate() + " Successfully!";
    }


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

    @PostMapping("/transactions/purchases/execute")
    private String recordPurchase(@RequestBody PurchasesModel purchasesModel){

        Purchases purchase = transactionsService.recordPurchase(purchasesModel);

        return "Purchase of " + purchase.getUnitsBought() + " Units of " + purchase.getItemName() + " at a Total of "
                + purchase.getTotalPrice() + " from " + purchase.getBusinesses().getName() + " on " + purchase.getDate() + " Has Been Recorded Successfully!";
    }




}
