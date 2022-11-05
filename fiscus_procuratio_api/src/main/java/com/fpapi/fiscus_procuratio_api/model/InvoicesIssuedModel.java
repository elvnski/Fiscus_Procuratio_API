package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoicesIssuedModel {

    private String clientName;
    private Integer daysToPay;
    private BigDecimal invoiceAmount;
    private BigDecimal discount;
    private String details;



}
