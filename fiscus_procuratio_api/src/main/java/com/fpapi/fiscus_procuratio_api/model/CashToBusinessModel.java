package com.fpapi.fiscus_procuratio_api.model;

import com.fpapi.fiscus_procuratio_api.entity.BusinessAccounts;
import com.fpapi.fiscus_procuratio_api.entity.CashAccounts;
import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashToBusinessModel {


    /* FOR REST ENDPOINT */
    private String source;
    private String destination;
    private String details;

//    private CashAccounts cashAccount;
    private BusinessAccounts businessAccount;

//    private String creditedCashAccountNumber;
//    private String debitedBusinessAccountNumber;

    private BigDecimal credit;




}
