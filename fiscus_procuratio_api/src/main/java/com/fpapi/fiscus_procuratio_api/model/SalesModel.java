package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesModel {

    //TODO Change to use Client Account Name to identify the Client Account in request

    private String cashInvoiceNumber;
//    private String clientAccountNumber;
    private String clientAccountName;



}
