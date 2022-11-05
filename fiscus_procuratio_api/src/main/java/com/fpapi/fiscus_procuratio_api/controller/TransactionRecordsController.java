package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.InvoicesIssued;
import com.fpapi.fiscus_procuratio_api.entity.InvoicesOwed;
import com.fpapi.fiscus_procuratio_api.model.InvoicesIssuedModel;
import com.fpapi.fiscus_procuratio_api.model.InvoicesOwedModel;
import com.fpapi.fiscus_procuratio_api.service.TransactionRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionRecordsController {

    @Autowired
    private TransactionRecordsService transactionRecordsService;


    @PostMapping("/records/invoices/issued/add")
    public String addInvoicesIssued(@RequestBody InvoicesIssuedModel invoicesIssuedModel){

        InvoicesIssued invoicesIssued = transactionRecordsService.addInvoicesIssued(invoicesIssuedModel);

        return "New Invoice Issued With Number '" + invoicesIssued.getInvoiceNumber() + "' Has Been Recorded Successfully!";
    }

    @PostMapping("/records/invoices/owed/add")
    public String addInvoicesOwed(@RequestBody InvoicesOwedModel invoicesOwedModel){

        InvoicesOwed invoicesOwed = transactionRecordsService.addInvoicesOwed(invoicesOwedModel);


        return "New Invoice Received With Number '" + invoicesOwed.getInvoiceNumber() + "' Has Been Recorded Successfully!";
    }


}
