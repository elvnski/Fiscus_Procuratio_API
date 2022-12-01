package com.fpapi.fiscus_procuratio_api.model;

import com.fpapi.fiscus_procuratio_api.entity.CashAccounts;
import com.fpapi.fiscus_procuratio_api.entity.ClientAccounts;
import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashFromClientModel {

    private String source;
    private String destination;
    private String details;

//    private String debitedCashAccountNumber;
//    private String creditedClientAccountNumber;

    private ClientAccounts clientAccount;



    private BigDecimal debit;








}
