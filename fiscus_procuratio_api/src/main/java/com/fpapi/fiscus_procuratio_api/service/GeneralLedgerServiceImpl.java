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

    @Override
    public GeneralLedger recordTransaction(GeneralLedgerModel generalLedgerModel) {

        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setTransactionNumber(getTransactionNumber());
        generalLedger.setDate(getDate());
        generalLedger.setAccountName(generalLedgerModel.getAccountName());
        generalLedger.setAccountType(generalLedgerModel.getAccountType());
        generalLedger.setDebit(generalLedgerModel.getDebit());
        generalLedger.setCredit(generalLedgerModel.getCredit());

        generalLedgerRepository.save(generalLedger);

        return generalLedger;
    }


    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        return new Date(calendar.getTime().getTime());
    }

    static String getTransactionNumber() {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(20);

        for (int i = 0; i < 20; i++) {
            // generate a random number between 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));

        }

        return "GL-" + sb;
    }


}
