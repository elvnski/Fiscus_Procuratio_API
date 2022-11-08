package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.Cash;
import com.fpapi.fiscus_procuratio_api.model.CashModel;

public interface CashService {
    Cash depositCash(CashModel cashModel);
}
