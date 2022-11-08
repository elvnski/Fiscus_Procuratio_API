package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.Cash;
import com.fpapi.fiscus_procuratio_api.model.CashModel;
import com.fpapi.fiscus_procuratio_api.service.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CashController {

    @Autowired
    private CashService cashService;

    @PostMapping("/cash/deposit")
    public String depositCash(@RequestBody CashModel cashModel){

        Cash cash = cashService.depositCash(cashModel);

        return "New Cash Deposit Number '" + cash.getCashTransactionNumber() + "' of KES " + cash.getDebit() + " Has Been Recorded Successfully";
    }


}
