package com.fpapi.fiscus_procuratio_api.model;

import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class GeneralLedgerModel {


    public GeneralLedger createGeneralLedgerEntry(String accountName, String accountType, BigDecimal debit, BigDecimal credit){

        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setTransactionNumber(getTransactionNumber());
        generalLedger.setDate(getDate());
        generalLedger.setAccountName(accountName);
        generalLedger.setAccountType(accountType);
        generalLedger.setDebit(debit);
        generalLedger.setCredit(credit);

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

        return sb.toString();
    }



}
