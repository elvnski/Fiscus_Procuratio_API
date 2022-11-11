package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import com.fpapi.fiscus_procuratio_api.model.GeneralLedgerModel;

public interface GeneralLedgerService {

    GeneralLedger recordTransaction(GeneralLedgerModel generalLedgerModel);

}
