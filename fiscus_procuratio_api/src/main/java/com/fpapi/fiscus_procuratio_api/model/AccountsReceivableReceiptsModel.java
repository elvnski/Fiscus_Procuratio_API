package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsReceivableReceiptsModel {

    private Long accountsReceivableId;
    private String details;
    private BigDecimal payment;




}
