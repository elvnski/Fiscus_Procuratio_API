package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerAccountsModel {

    private String ownerName;
    private String bankName;
    private String accountNumber;
    private String accountName;

    private Boolean active;





}
