package com.fpapi.fiscus_procuratio_api.model;


import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsReceivableModel {

    private String invoiceNumber;
    private String clientAccountName;


}
