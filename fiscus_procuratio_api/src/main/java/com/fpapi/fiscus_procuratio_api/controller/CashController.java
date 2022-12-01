package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.service.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CashController {

    @Autowired
    private CashService cashService;


    @PutMapping("/cash/defaults/trading/set")
    public String setDefaultTradingAccount(@RequestBody DefaultCashAccountsModel defaultCashAccountsModel){

        DefaultCashAccounts defaultCashAccounts = cashService.setDefaultTradingAccount(defaultCashAccountsModel);
        CashAccounts cashAccount = defaultCashAccounts.getCashAccount();

        return "Default Trading Account Set to '" + cashAccount.getAccountName() + " - " +
                cashAccount.getAccountNumber() + "' in " + cashAccount.getBank().getName() +
                " on " + defaultCashAccounts.getSetDate() + ".";
    }

    @PutMapping("/cash/defaults/capital/set")
    public String setDefaultCapitalAccount(@RequestBody DefaultCashAccountsModel defaultCashAccountsModel){

        DefaultCashAccounts defaultCashAccounts = cashService.setDefaultCapitalAccount(defaultCashAccountsModel);
        CashAccounts cashAccount = defaultCashAccounts.getCashAccount();

        return "Default Capital Account Set to '" + cashAccount.getAccountName() + " - " +
                cashAccount.getAccountNumber() + "' in " + cashAccount.getBank().getName() +
                " on " + defaultCashAccounts.getSetDate() + ".";
    }

    @PutMapping("/cash/accounts/trading/funds/allocate")
    public String allocateTradingFunds(@RequestBody TradingFundsAllocationModel tradingFundsAllocationModel){

        CashAccounts defaultTradingAccount = cashService.allocateTradingFunds(tradingFundsAllocationModel);

        return "The Trading Account '" + defaultTradingAccount.getAccountName() + "' Has Been Debited With KES " + tradingFundsAllocationModel.getAllocatedAmount() + " From the Capital Account.";
    }




    @PostMapping("/cash/accounts/local/create")
    public String createCashAccount(@RequestBody CashAccountsModel cashAccountsModel){

        CashAccounts cashAccount = cashService.createCashAccount(cashAccountsModel);

        return "New Cash Account '" + cashAccount.getAccountNumber() + " - " + cashAccount.getAccountName() + "' in " + cashAccount.getBank().getName() +
                " Has Been Added Successfully on " + cashAccount.getDateAdded() + ".";
    }


    @PutMapping("/cash/accounts/local/{accountNo}/activate")
    private String activateCashAccount(@PathVariable("accountNo") String accountNo){

        CashAccounts cashAccount = cashService.activateCashAccount(accountNo);

        return "Cash Account '" + cashAccount.getAccountNumber() + " - " + cashAccount.getAccountName() + "' is Now ACTIVATED.";
    }

    @PutMapping("/cash/accounts/local/{accountNo}/deactivate")
    private String deactivateCashAccount(@PathVariable("accountNo") String accountNo){

        CashAccounts cashAccount = cashService.deactivateCashAccount(accountNo);

        return "Cash Account '" + cashAccount.getAccountNumber() + " - " + cashAccount.getAccountName() + "' is Now DEACTIVATED.";
    }


    @PostMapping("/cash/accounts/local/capital/deposit")
    public String receiveCapitalFromOwner(@RequestBody CashFromOwnerModel cashFromOwnerModel){

        Cash cash = cashService.receiveCapitalFromOwner(cashFromOwnerModel);

        return "New Capital Deposit Number '" + cash.getCashTransactionNumber() + "' of KES " + cash.getDebit() + " from Owner '"
                + cash.getCreditedOwnerAccount().getOwner().getName() + "' Has Been Recorded Successfully";
    }

    //TODO : Create Endpoint for transferring Funds from capital account into trading account


    @PostMapping("/cash/accounts/businesses/create")
    public String createBusinessAccount(@RequestBody BusinessAccountsModel businessAccountsModel){

        BusinessAccounts businessAccount = cashService.createBusinessAccount(businessAccountsModel);

        return "New Business Account '" + businessAccount.getAccountNumber() + " - " + businessAccount.getAccountName() + "' in " + businessAccount.getBank().getName() +
                " Has Been Added Successfully on " + businessAccount.getDateAdded() + ".";
    }


    @PutMapping("/cash/accounts/businesses/{accountNo}/activate")
    public String activateBusinessAccount(@PathVariable("accountNo") String accountNo){

        BusinessAccounts businessAccount = cashService.activateBusinessAccount(accountNo);

        return "Business Account '" + businessAccount.getAccountNumber() + " - " + businessAccount.getAccountName() + "' is Now ACTIVATED.";
    }

    @PutMapping("/cash/accounts/businesses/{accountNo}/deactivate")
    public String deactivateBusinessAccount(@PathVariable("accountNo") String accountNo){

        BusinessAccounts businessAccount = cashService.deactivateBusinessAccount(accountNo);

        return "Business Account '" + businessAccount.getAccountNumber() + " - " + businessAccount.getAccountName() + "' is Now DEACTIVATED.";
    }


    @PostMapping("/cash/accounts/clients/create")
    public String createClientAccount(@RequestBody ClientsAccountsModel clientsAccountsModel){

        ClientAccounts clientAccount = cashService.createClientAccount(clientsAccountsModel);

        return "New Client Account '" + clientAccount.getAccountNumber() + " - " + clientAccount.getAccountName() + "' in " + clientAccount.getBank().getName() +
                " Has Been Added Successfully on " + clientAccount.getDateAdded() + ".";
    }

    @PutMapping("/cash/accounts/clients/{accountNo}/activate")
    public String activateClientAccount(@PathVariable("accountNo") String accountNo){

        ClientAccounts clientAccount = cashService.activateClientAccount(accountNo);

        return "Client Account '" + clientAccount.getAccountNumber() + " - " + clientAccount.getAccountName() + "' is Now ACTIVATED.";
    }

    @PutMapping("/cash/accounts/clients/{accountNo}/deactivate")
    public String deactivateClientAccount(@PathVariable("accountNo") String accountNo){

        ClientAccounts clientAccount = cashService.deactivateClientAccount(accountNo);

        return "Client Account '" + clientAccount.getAccountNumber() + " - " + clientAccount.getAccountName() + "' is Now DEACTIVATED.";
    }




    @PostMapping("/cash/accounts/owners/create")
    public String createOwnerAccount(@RequestBody OwnerAccountsModel ownerAccountsModel){

        OwnerAccounts ownerAccount = cashService.createOwnerAccount(ownerAccountsModel);

        return "New Owner Account '" + ownerAccount.getAccountNumber() + " - " + ownerAccount.getAccountName() +
                "' Has Been Saved Successfully on " + ownerAccount.getDateAdded() + ".";
    }

    @PutMapping("/cash/accounts/owners/{accountNo}/activate")
    public String activateOwnerAccount(@PathVariable("accountNo") String accountNo){

        OwnerAccounts ownerAccount = cashService.activateOwnerAccount(accountNo);

        return "Owner Account '" + ownerAccount.getAccountNumber() + " - " + ownerAccount.getAccountName() + "' is Now ACTIVATED.";
    }


    @PutMapping("/cash/accounts/owners/{accountNo}/deactivate")
    public String deactivateOwnerAccount(@PathVariable("accountNo") String accountNo){

        OwnerAccounts ownerAccount = cashService.deactivateOwnerAccount(accountNo);

        return "Owner Account '" + ownerAccount.getAccountNumber() + " - " + ownerAccount.getAccountName() + "' is Now DEACTIVATED.";
    }


    /* Work in Progress **/
    @GetMapping("/entities/owners/shareholding")
    public String getOwnerShareholding(){


        return cashService.getOwnerShareholding();
    }




}
