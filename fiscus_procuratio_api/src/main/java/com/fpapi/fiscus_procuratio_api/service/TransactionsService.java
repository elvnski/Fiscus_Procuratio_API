package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.CashInvoicesIssued;
import com.fpapi.fiscus_procuratio_api.entity.CashInvoicesReceived;
import com.fpapi.fiscus_procuratio_api.entity.Purchases;
import com.fpapi.fiscus_procuratio_api.entity.Sales;
import com.fpapi.fiscus_procuratio_api.model.CashInvoicesIssuedModel;
import com.fpapi.fiscus_procuratio_api.model.CashInvoicesReceivedModel;
import com.fpapi.fiscus_procuratio_api.model.PurchasesModel;
import com.fpapi.fiscus_procuratio_api.model.SalesModel;

public interface TransactionsService {
    Sales recordSale(SalesModel salesModel);

    CashInvoicesIssued recordInvoiceIssued(CashInvoicesIssuedModel cashInvoicesIssuedModel);

    CashInvoicesReceived recordPurchaseInvoice(CashInvoicesReceivedModel cashInvoicesReceivedModel);

    Purchases recordPurchase(PurchasesModel purchasesModel);
}
