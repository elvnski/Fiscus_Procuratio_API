package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.Cash;
import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.model.CashModel;
import com.fpapi.fiscus_procuratio_api.model.GeneralLedgerModel;
import com.fpapi.fiscus_procuratio_api.repository.CashRepository;
import com.fpapi.fiscus_procuratio_api.repository.GeneralLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Service
public class CashServiceImpl implements CashService{

    @Autowired
    private CashRepository cashRepository;


    @Autowired
    private GeneralLedgerService generalLedgerService;

    @Autowired
    private CodesAndDateService codesAndDateService;


    @Override
    public Cash depositCash(CashModel cashModel) {

        BigDecimal latestCashBalance = BigDecimal.valueOf(0.0);

        if (!cashRepository.findAll().isEmpty()) {
            latestCashBalance = cashRepository.findByDate(cashRepository.getMaxDate()).getBalance();
        }


        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Cash", "Asset", cashModel.getDebit(), BigDecimal.valueOf(0.0)));

        new CashModel();
        Cash cash = Cash.builder()
                .generalLedger(generalLedger)
                .cashTransactionNumber(codesAndDateService.generateTransactionCode("CSH-"))
                .date(codesAndDateService.getDate())
                .sourceAccount(cashModel.getSourceAccount())
                .destinationAccount(cashModel.getDestinationAccount())
                .details(cashModel.getDetails())
                .credit(BigDecimal.valueOf(0.0))
                .debit(cashModel.getDebit())
                .balance(latestCashBalance.add(cashModel.getDebit()))
                .build();


        cashRepository.save(cash);

        return cash;
    }

    @Override
    public Cash spendCash(CashModel cashModel) {

        generalLedgerService.recordTransaction(new GeneralLedgerModel("Cash", "Asset", cashModel.getDebit(), cashModel.getCredit()));

        Cash cash = Cash.builder()
                .generalLedger(cashModel.getGeneralLedger())
                .cashTransactionNumber(codesAndDateService.generateTransactionCode("CSH-"))
                .date(codesAndDateService.getDate())
                .sourceAccount(cashModel.getSourceAccount())
                .destinationAccount(cashModel.getDestinationAccount())
                .details(cashModel.getDetails())
                .credit(cashModel.getCredit())
                .debit(cashModel.getDebit())
                .balance((cashModel.getLatestCashBalance().add(cashModel.getDebit())).subtract(cashModel.getCredit()))
                .build();

        cashRepository.save(cash);

        return cash;
    }

    @Override
    public void checkForCashOverdraw(BigDecimal spendingAmount) throws CashOverdrawException {

        BigDecimal latestCashBalance = BigDecimal.valueOf(0.0);

        if (!cashRepository.findAll().isEmpty()) {
            latestCashBalance = cashRepository.findByDate(cashRepository.getMaxDate()).getBalance();

            if(latestCashBalance.subtract(spendingAmount).compareTo(BigDecimal.valueOf(200000.00)) < 0) {
                throw new CashOverdrawException("Cannot process Cash Spending transaction of KES " + spendingAmount + " as it exceeds the overdraw limits.");
            }
        }

    }

    @Override
    public BigDecimal getLatestCashBalance() {

        BigDecimal latestCashBalance = BigDecimal.valueOf(0.0);

        if (!cashRepository.findAll().isEmpty()) {
            latestCashBalance = cashRepository.findByDate(cashRepository.getMaxDate()).getBalance();
        }

        return latestCashBalance;
    }


}
