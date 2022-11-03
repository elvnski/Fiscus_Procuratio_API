package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.AccountsPayable;
import com.fpapi.fiscus_procuratio_api.model.AccountsPayableModel;

public interface AccountsService {
    AccountsPayable recordAccountsPayable(AccountsPayableModel accountsPayableModel);
}
