package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.exceptions.InactiveCashAccountException;
import com.fpapi.fiscus_procuratio_api.exceptions.InactiveOwnerAccountException;
import com.fpapi.fiscus_procuratio_api.model.*;

import java.math.BigDecimal;

public interface CashService {
    Cash receiveCashFromClient(CashFromClientModel cashFromClientModel);

    Cash sendCashToBusiness(CashToBusinessModel cashToBusinessModel);

    void checkForTradingCashOverdraw(BigDecimal spendingAmount) throws CashOverdrawException;

    BigDecimal getLatestCashBalance();

    CashAccounts createCashAccount(CashAccountsModel cashAccountsModel);

    DefaultCashAccounts setDefaultTradingAccount(DefaultCashAccountsModel defaultCashAccountsModel);

    DefaultCashAccounts setDefaultCapitalAccount(DefaultCashAccountsModel defaultCashAccountsModel);

    OwnerAccounts createOwnerAccount(OwnerAccountsModel ownerAccountsModel);

    OwnerAccounts activateOwnerAccount(String accountNo);

    OwnerAccounts deactivateOwnerAccount(String accountNo);

    CashAccounts activateCashAccount(String accountNo);

    CashAccounts deactivateCashAccount(String accountNo);

    BusinessAccounts createBusinessAccount(BusinessAccountsModel businessAccountsModel);

    BusinessAccounts activateBusinessAccount(String accountNo);

    BusinessAccounts deactivateBusinessAccount(String accountNo);

    ClientAccounts createClientAccount(ClientsAccountsModel clientsAccountsModel);

    ClientAccounts activateClientAccount(String accountNo);

    ClientAccounts deactivateClientAccount(String accountNo);

    Cash receiveCapitalFromOwner(CashFromOwnerModel cashFromOwnerModel);

    void checkIfCashAccountIsActive(CashAccounts cashAccount) throws InactiveCashAccountException;

    void checkIfOwnerAccountIsActive(OwnerAccounts ownerAccount) throws InactiveOwnerAccountException;

    String getOwnerShareholding();

    CashAccounts allocateTradingFunds(TradingFundsAllocationModel tradingFundsAllocationModel);
}
