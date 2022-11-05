package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoicesOwedModel {

    private String invoiceNumber;
    private String businessName;
    private Integer daysToPay;
    private BigDecimal invoiceAmount;
    private BigDecimal discount;
    private String details;

}
