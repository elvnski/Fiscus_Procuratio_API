package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.AccountsPayable;
import com.fpapi.fiscus_procuratio_api.entity.AccountsPayablePayments;
import com.fpapi.fiscus_procuratio_api.entity.AccountsReceivable;
import com.fpapi.fiscus_procuratio_api.model.AccountsPayableModel;
import com.fpapi.fiscus_procuratio_api.model.AccountsPayablePaymentsModel;
import com.fpapi.fiscus_procuratio_api.model.AccountsReceivableModel;
import com.fpapi.fiscus_procuratio_api.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountsController {

    @Autowired
    private AccountsService accountsService;


    @PostMapping("/accounts/payable/record")
    public String recordAccountsPayable(@RequestBody AccountsPayableModel accountsPayableModel){

        AccountsPayable accountsPayable = accountsService.recordAccountsPayable(accountsPayableModel);

        return "New Accounts Payable Entry No '" + accountsPayable.getId() + "' Has Been Recorded Successfully!";
    }


    @PostMapping("/accounts/payable/payment")
    public String recordAccountsPayablePayment(@RequestBody AccountsPayablePaymentsModel accountsPayablePaymentsModel){

        AccountsPayablePayments accountsPayablePayments = accountsService.recordAccountsPayablePayment(accountsPayablePaymentsModel);

        return "New Accounts Payable Payment No '" + accountsPayablePayments.getPaymentNumber() + "' of KES " + accountsPayablePayments.getPayment() + " Has Been Recorded Successfully!";
    }


    @PostMapping("/accounts/receivable/record")
    public String recordAccountsReceivable(@RequestBody AccountsReceivableModel accountsReceivableModel){

        AccountsReceivable accountsReceivable = accountsService.recordAccountsReceivable(accountsReceivableModel);

        return "New Accounts Receivable Entry No '" + accountsReceivable.getId() + "' Has Been Recorded Successfully!";
    }





}
