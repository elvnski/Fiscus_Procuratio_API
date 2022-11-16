package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import com.fpapi.fiscus_procuratio_api.model.GeneralLedgerModel;
import com.fpapi.fiscus_procuratio_api.repository.GeneralLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class GeneralLedgerServiceImpl implements GeneralLedgerService{

    @Autowired
    private GeneralLedgerRepository generalLedgerRepository;

    @Autowired
    private CodesAndDateService codesAndDateService;


    @Override
    public GeneralLedger recordTransaction(GeneralLedgerModel generalLedgerModel) {

        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setTransactionNumber(codesAndDateService.generateTransactionCode("GL-"));
        generalLedger.setDate(codesAndDateService.getDate());
        generalLedger.setAccountName(generalLedgerModel.getAccountName());
        generalLedger.setAccountType(generalLedgerModel.getAccountType());
        generalLedger.setDebit(generalLedgerModel.getDebit());
        generalLedger.setCredit(generalLedgerModel.getCredit());

        generalLedgerRepository.save(generalLedger);

        return generalLedger;
    }





}
