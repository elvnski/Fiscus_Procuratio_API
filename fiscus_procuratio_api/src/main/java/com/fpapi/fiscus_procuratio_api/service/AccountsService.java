package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.AccountsPayable;
import com.fpapi.fiscus_procuratio_api.entity.AccountsPayablePayments;
import com.fpapi.fiscus_procuratio_api.entity.AccountsReceivable;
import com.fpapi.fiscus_procuratio_api.entity.AccountsReceivableReceipts;
import com.fpapi.fiscus_procuratio_api.model.AccountsPayableModel;
import com.fpapi.fiscus_procuratio_api.model.AccountsPayablePaymentsModel;
import com.fpapi.fiscus_procuratio_api.model.AccountsReceivableModel;
import com.fpapi.fiscus_procuratio_api.model.AccountsReceivableReceiptsModel;

public interface AccountsService {
    AccountsPayable recordAccountsPayable(AccountsPayableModel accountsPayableModel);

    AccountsReceivable recordAccountsReceivable(AccountsReceivableModel accountsReceivableModel);

    AccountsPayablePayments recordAccountsPayablePayment(AccountsPayablePaymentsModel accountsPayablePaymentsModel);

    AccountsReceivableReceipts recordAccountsReceivableReceipt(AccountsReceivableReceiptsModel accountsReceivableReceiptsModel);
}
