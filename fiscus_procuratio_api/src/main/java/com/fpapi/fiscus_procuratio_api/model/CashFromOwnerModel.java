package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashFromOwnerModel {

//    private String source;
//    private String destination;
    private String details;

    //TODO : Change to use Account Name as identifier

    private String ownerAccountName;

    private BigDecimal cashDebit;








}
