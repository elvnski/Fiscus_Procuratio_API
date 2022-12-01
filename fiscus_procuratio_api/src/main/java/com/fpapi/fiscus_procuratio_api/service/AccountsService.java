package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.exceptions.OverpaymentException;
import com.fpapi.fiscus_procuratio_api.exceptions.WrongAccountSelectionException;
import com.fpapi.fiscus_procuratio_api.model.AccountsPayableModel;
import com.fpapi.fiscus_procuratio_api.model.AccountsPayablePaymentsModel;
import com.fpapi.fiscus_procuratio_api.model.AccountsReceivableModel;
import com.fpapi.fiscus_procuratio_api.model.AccountsReceivableReceiptsModel;

import java.math.BigDecimal;

public interface AccountsService {
    AccountsPayable recordAccountsPayable(AccountsPayableModel accountsPayableModel);

    AccountsReceivable recordAccountsReceivable(AccountsReceivableModel accountsReceivableModel);

    AccountsPayablePayments recordAccountsPayablePayment(AccountsPayablePaymentsModel accountsPayablePaymentsModel);

    AccountsReceivableReceipts recordAccountsReceivableReceipt(AccountsReceivableReceiptsModel accountsReceivableReceiptsModel);

    void checkMatchingOwnerForAccount(InvoicesIssued invoicesIssued, ClientAccounts clientAccount) throws WrongAccountSelectionException;

    void checkMatchingOwnerForAccount(InvoicesOwed invoicesOwed, BusinessAccounts businessAccount) throws WrongAccountSelectionException;

    void checkForOverpayment(AccountsPayable accountsPayable, BigDecimal payment) throws OverpaymentException;

    void checkForOverpayment(AccountsReceivable accountsReceivable, BigDecimal payment) throws OverpaymentException;


}
