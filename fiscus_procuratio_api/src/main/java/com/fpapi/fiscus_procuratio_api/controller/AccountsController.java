package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.AccountsPayable;
import com.fpapi.fiscus_procuratio_api.model.AccountsPayableModel;
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

        return "";
    }





}
