package com.fpapi.fiscus_procuratio_api.service;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class CodesAndDateServiceImpl implements CodesAndDateService {


    @Override
    public Date getDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        return new Date(calendar.getTime().getTime());
    }

    @Override
    public String generateTransactionCode(String accountPrefix) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
        int length = 16;

        if (accountPrefix.equals("GL-") || accountPrefix.equals("CSH-")){
            AlphaNumericString += "abcdefghijklmnopqrstuvxyz";
        }

        if (accountPrefix.equals("GL-")){
            length = 20;
        } else if (accountPrefix.equals("CSH-")) {
            length = 18;
        }

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(18);

        for (int i = 0; i < length; i++) {
            // generate a random number between 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));

        }

        return accountPrefix + sb;
    }


}
