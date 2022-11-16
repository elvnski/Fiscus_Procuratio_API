package com.fpapi.fiscus_procuratio_api.service;

import java.util.Date;

public interface CodesAndDateService {

    Date getDate();

    String generateTransactionCode(String accountPrefix);

}
