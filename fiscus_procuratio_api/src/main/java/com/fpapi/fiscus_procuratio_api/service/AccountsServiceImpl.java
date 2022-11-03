package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.AccountsPayable;
import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import com.fpapi.fiscus_procuratio_api.model.AccountsPayableModel;
import com.fpapi.fiscus_procuratio_api.repository.GeneralLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class AccountsServiceImpl implements AccountsService{

    @Autowired
    private GeneralLedgerRepository generalLedgerRepository;

    @Override
    public AccountsPayable recordAccountsPayable(AccountsPayableModel accountsPayableModel) {

        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setTransactionNumber(UUID.randomUUID().toString());
        generalLedger.setDate(getDate());
        generalLedger.setAccountName("Accounts Payable");
        generalLedger.setAccountType("Liability");
        generalLedger.setDebit(BigDecimal.valueOf(0.00));
        generalLedger.setCredit(accountsPayableModel.getInvoiceAmount());

        generalLedgerRepository.save(generalLedger);

        /*HEY ELVIN!!! REMOVE WHEN YOU RETURN HERE*/
        AccountsPayable accountsPayable = new AccountsPayable();

        return accountsPayable;
    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        return new Date(calendar.getTime().getTime());
    }
}
