package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.Cash;
import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.model.CashModel;

import java.math.BigDecimal;

public interface CashService {
    Cash depositCash(CashModel cashModel);

    Cash spendCash(CashModel cashModel);

    void checkForCashOverdraw(BigDecimal spendingAmount) throws CashOverdrawException;


}
