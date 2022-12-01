package com.fpapi.fiscus_procuratio_api.model;

import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsPayableModel {


    private String invoiceNumber;
    private String businessAccountName;


    //TODO use LoansPayments Table track Loan Payments
    private String loanNumber;

}
