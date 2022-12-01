package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.Loans;
import com.fpapi.fiscus_procuratio_api.model.LoansModel;
import com.fpapi.fiscus_procuratio_api.service.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    @Autowired
    private LoansService loansService;

    @PostMapping("/loans/new")
    public String recordNewLoan(LoansModel loansModel){

        Loans loan = loansService.recordNewLoan(loansModel);

    }


}
