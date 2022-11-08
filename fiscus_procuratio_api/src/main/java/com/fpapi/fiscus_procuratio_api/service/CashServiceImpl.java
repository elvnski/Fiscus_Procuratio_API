package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.Cash;
import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import com.fpapi.fiscus_procuratio_api.model.CashModel;
import com.fpapi.fiscus_procuratio_api.model.GeneralLedgerModel;
import com.fpapi.fiscus_procuratio_api.repository.CashRepository;
import com.fpapi.fiscus_procuratio_api.repository.GeneralLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CashServiceImpl implements CashService{

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private GeneralLedgerRepository generalLedgerRepository;

    @Override
    public Cash depositCash(CashModel cashModel) {

        GeneralLedger generalLedger = new GeneralLedgerModel().createGeneralLedgerEntry("Cash", "Asset", cashModel.getAmount(), BigDecimal.valueOf(0.0));
        generalLedgerRepository.save(generalLedger);

        BigDecimal latestCashBalance = BigDecimal.valueOf(0.0);

        if (!cashRepository.findAll().isEmpty()) {
            latestCashBalance = cashRepository.findByDate(cashRepository.getMaxDate()).getBalance();
        }


        new CashModel();
        Cash cash = Cash.builder()
                .generalLedger(generalLedger)
                .cashTransactionNumber(CashModel.generateCashTransactionNumber())
                .date(getDate())
                .sourceAccount(cashModel.getSourceAccount())
                .destinationAccount(cashModel.getDestinationAccount())
                .details(cashModel.getDetails())
                .credit(BigDecimal.valueOf(0.0))
                .debit(cashModel.getAmount())
                .balance(latestCashBalance.add(cashModel.getAmount()))
                .build();


        cashRepository.save(cash);

        return cash;
    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        return new Date(calendar.getTime().getTime());
    }


}
