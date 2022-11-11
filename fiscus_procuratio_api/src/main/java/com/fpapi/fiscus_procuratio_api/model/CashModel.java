package com.fpapi.fiscus_procuratio_api.model;

import com.fpapi.fiscus_procuratio_api.entity.Cash;
import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashModel {

    /* FOR USE IN SERVICES */
    private GeneralLedger generalLedger;

    /* FOR REST ENDPOINT */
    private String sourceAccount;
    private String destinationAccount;
    private String details;
    private BigDecimal debit;
    private BigDecimal credit;

    /* FOR USE IN SERVICES */
    private BigDecimal latestCashBalance;


    public Cash createCashEntry(GeneralLedger generalLedger, String sourceAccount, String destinationAccount, String details, BigDecimal debit, BigDecimal credit, BigDecimal latestCashBalance){

        return Cash.builder()
                .generalLedger(generalLedger)
                .cashTransactionNumber(generateCashTransactionNumber())
                .date(getDate())
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount)
                .details(details)
                .credit(credit)
                .debit(debit)
                .balance((latestCashBalance.add(debit)).subtract(credit))
                .build();
    }


    public static String generateCashTransactionNumber() {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(18);

        for (int i = 0; i < 18; i++) {
            // generate a random number between 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));

        }

        return "CSH-" + sb;
    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        return new Date(calendar.getTime().getTime());
    }

}
