package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.InvoicesIssued;
import com.fpapi.fiscus_procuratio_api.entity.InvoicesOwed;
import com.fpapi.fiscus_procuratio_api.model.InvoicesIssuedModel;
import com.fpapi.fiscus_procuratio_api.model.InvoicesOwedModel;

public interface TransactionRecordsService {
    InvoicesIssued addInvoicesIssued(InvoicesIssuedModel invoicesIssuedModel);

    InvoicesOwed addInvoicesOwed(InvoicesOwedModel invoicesOwedModel);
}
