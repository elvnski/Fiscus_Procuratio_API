package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientsAccountsModel {

    private String clientName;
    private String bankName;
    private String accountNumber;
    private String accountName;

    private Boolean active;



}
