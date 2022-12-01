package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.exceptions.InactiveCashAccountException;
import com.fpapi.fiscus_procuratio_api.exceptions.InactiveOwnerAccountException;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class CashServiceImpl implements CashService{

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private BanksRepository banksRepository;

    @Autowired
    private CashAccountsRepository cashAccountsRepository;

    @Autowired
    private GeneralLedgerService generalLedgerService;

    @Autowired
    private CodesAndDateService codesAndDateService;

    @Autowired
    private ClientAccountsRepository clientAccountsRepository;

    @Autowired
    private BusinessAccountsRepository businessAccountsRepository;

    @Autowired
    private OwnerAccountsRepository ownerAccountsRepository;

    @Autowired
    private DefaultCashAccountsRepository defaultCashAccountsRepository;

    @Autowired
    private OwnersRepository ownersRepository;

    @Autowired
    private BusinessesRepository businessesRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired CapitalContributionsRepository capitalContributionsRepository;

    @Override
    public Cash receiveCashFromClient(CashFromClientModel cashFromClientModel) {

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Cash", "Asset", cashFromClientModel.getDebit(), BigDecimal.valueOf(0.0)));

        CashAccounts debitedCashAccount = defaultCashAccountsRepository.findByAccountFunction("Trading").getCashAccount();

        Cash cash = Cash.builder()
                .generalLedger(generalLedger)
                .cashTransactionNumber(codesAndDateService.generateTransactionCode("CSH-"))
                .date(codesAndDateService.getDate())
                .nature("Deposit")
                .source(cashFromClientModel.getSource())
                .destination(cashFromClientModel.getDestination())
                .details(cashFromClientModel.getDetails())
                .creditedClientAccount(cashFromClientModel.getClientAccount())
                .debitedCashAccount(debitedCashAccount)
                .credit(BigDecimal.ZERO)
                .debit(cashFromClientModel.getDebit())
                .balance(getLatestCashBalance().add(cashFromClientModel.getDebit()))
                .build();

        cashRepository.save(cash);

        debitedCashAccount.setBalance(debitedCashAccount.getBalance().add(cashFromClientModel.getDebit()));
        cashAccountsRepository.save(debitedCashAccount);

        return cash;
    }

    //TODO Change the method parameters in POSTMAN
    @Override
    public Cash sendCashToBusiness(CashToBusinessModel cashToBusinessModel) {

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Cash", "Asset", BigDecimal.ZERO, cashToBusinessModel.getCredit()));

        CashAccounts creditedCashAccount = defaultCashAccountsRepository.findByAccountFunction("Trading").getCashAccount();

        Cash cash = Cash.builder()
                .generalLedger(generalLedger)
                .cashTransactionNumber(codesAndDateService.generateTransactionCode("CSH-"))
                .date(codesAndDateService.getDate())
                .nature("Withdrawal")
                .source(cashToBusinessModel.getSource())
                .destination(cashToBusinessModel.getDestination())
                .details(cashToBusinessModel.getDetails())
                .creditedCashAccount(creditedCashAccount)
                .debitedBusinessAccount(cashToBusinessModel.getBusinessAccount())
                .credit(cashToBusinessModel.getCredit())
                .debit(BigDecimal.ZERO)
                .balance(getLatestCashBalance().subtract(cashToBusinessModel.getCredit()))
                .build();

        cashRepository.save(cash);

        creditedCashAccount.setBalance(creditedCashAccount.getBalance().subtract(cashToBusinessModel.getCredit()));
        cashAccountsRepository.save(creditedCashAccount);

        return cash;
    }


    @Override
    public Cash receiveCapitalFromOwner(CashFromOwnerModel cashFromOwnerModel) {

        CashAccounts debitedCashAccount = defaultCashAccountsRepository.findByAccountFunction("Capital").getCashAccount();

        OwnerAccounts ownerAccount = ownerAccountsRepository.findByAccountName(cashFromOwnerModel.getOwnerAccountName());

        try {
            checkIfCashAccountIsActive(debitedCashAccount);
        } catch (InactiveCashAccountException e) {
            throw new RuntimeException(e);
        }

        try {
            checkIfOwnerAccountIsActive(ownerAccount);
        } catch (InactiveOwnerAccountException e) {
            throw new RuntimeException(e);
        }

        generalLedgerService.recordTransaction(new GeneralLedgerModel("Owner", "Owner: " + ownerAccount.getOwner().getName(), BigDecimal.ZERO, cashFromOwnerModel.getCashDebit()));

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Cash - Capital", "Asset", cashFromOwnerModel.getCashDebit(), BigDecimal.ZERO));


        Cash intoCapitalAccount = Cash.builder()
                .generalLedger(generalLedger)
                .cashTransactionNumber(codesAndDateService.generateTransactionCode("CSH-"))
                .date(codesAndDateService.getDate())
                .nature("Deposit")
                .source("Owner: " + ownerAccount.getOwner().getName())
                .destination("Cash Trading Account: " + debitedCashAccount.getAccountNumber() + " - " + debitedCashAccount.getAccountName())
                .details(cashFromOwnerModel.getDetails())
                .creditedOwnerAccount(ownerAccount)
                .debitedCashAccount(debitedCashAccount)
                .credit(BigDecimal.ZERO)
                .debit(cashFromOwnerModel.getCashDebit())
                .balance(getLatestCashBalance().add(cashFromOwnerModel.getCashDebit()))
                .build();

        cashRepository.save(intoCapitalAccount);

        debitedCashAccount.setBalance(debitedCashAccount.getBalance().add(cashFromOwnerModel.getCashDebit()));
        cashAccountsRepository.save(debitedCashAccount);


        CapitalContributions capitalContributions = CapitalContributions.builder()
                .owner(ownerAccount.getOwner())
                .date(codesAndDateService.getDate())
                .contribution(cashFromOwnerModel.getCashDebit())
                .build();

        capitalContributionsRepository.save(capitalContributions);

        return intoCapitalAccount;
    }




    @Override
    public void checkForTradingCashOverdraw(BigDecimal spendingAmount) throws CashOverdrawException {

        BigDecimal tradingCashBalance = defaultCashAccountsRepository.findByAccountFunction("Trading").getCashAccount().getBalance();

        if(tradingCashBalance.subtract(spendingAmount).compareTo(BigDecimal.valueOf(200000.00)) < 0) {
            throw new CashOverdrawException("Cannot process Cash Spending transaction of KES " + spendingAmount + " as it exceeds the overdraw limits.");
        }

    }

    @Override
    public BigDecimal getLatestCashBalance() {

        BigDecimal latestCashBalance = BigDecimal.valueOf(0.0);

        if (!cashRepository.findAll().isEmpty()) {
            latestCashBalance = cashRepository.findByDate(cashRepository.getMaxDate()).getBalance();
        }

        return latestCashBalance;
    }

    @Override
    public CashAccounts createCashAccount(CashAccountsModel cashAccountsModel) {

        CashAccounts cashAccount = CashAccounts.builder()
                .accountNumber(cashAccountsModel.getAccountNumber())
                .dateAdded(codesAndDateService.getDate())
                .active(false)
                .accountName(cashAccountsModel.getAccountName())
                .bank(banksRepository.findByName(cashAccountsModel.getBankName()))
                .balance(BigDecimal.ZERO)
                .build();

        cashAccountsRepository.save(cashAccount);

        return cashAccount;
    }

    @Override
    public void checkIfCashAccountIsActive(CashAccounts cashAccount) throws InactiveCashAccountException {

        if (!cashAccount.getActive()){

            throw new InactiveCashAccountException("The Selected Cash Account '" + cashAccount.getAccountNumber() + " - " + cashAccount.getAccountName() +
                    "' is currently INACTIVE. Please Select an Activated Account or Activate This Account.");

        }

    }

    @Override
    public void checkIfOwnerAccountIsActive(OwnerAccounts ownerAccount) throws InactiveOwnerAccountException {

        if (!ownerAccount.getActive()){
            throw new InactiveOwnerAccountException("The Selected Owner Account '" + ownerAccount.getAccountNumber() + " - " + ownerAccount.getAccountName() +
                    "' is currently INACTIVE. Please Select an Activated Account or Activate This Account.");
        }

    }

    @Override
    public String getOwnerShareholding() {

        List <String> shareholdingStatementsList = new ArrayList<>();

        BigDecimal totalCapital = capitalContributionsRepository.getOverallTotalContributions();

        for (long i = 1L; i <= ownersRepository.count(); i++) {

            Owners owner = ownersRepository.findById(i).get();

            BigDecimal ownersTotalContribution = capitalContributionsRepository.getOwnerTotalContributions(i);

            BigDecimal ownerShareholding = ownersTotalContribution.divide(totalCapital, 7, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).stripTrailingZeros();

            String shareholdingStatement = "Owner '" + owner.getName() + "' Has a Shareholding of " + ownerShareholding + " Percent.";

            shareholdingStatementsList.add(shareholdingStatement);
        }


        return shareholdingStatementsList.toString();
    }

    @Override
    public CashAccounts allocateTradingFunds(TradingFundsAllocationModel tradingFundsAllocationModel) {

        DefaultCashAccounts defaultTradingAccount = defaultCashAccountsRepository.findByAccountFunction("Trading");
        DefaultCashAccounts defaultCapitalAccount = defaultCashAccountsRepository.findByAccountFunction("Capital");

        CashAccounts tradingCashAccount = defaultTradingAccount.getCashAccount();
        CashAccounts capitalCashAccount = defaultCapitalAccount.getCashAccount();

        try {
            checkIfCashAccountIsActive(tradingCashAccount);
        } catch (InactiveCashAccountException e) {
            throw new RuntimeException(e);
        }
        try {
            checkIfCashAccountIsActive(capitalCashAccount);
        } catch (InactiveCashAccountException e) {
            throw new RuntimeException(e);
        }

        BigDecimal allocatedFunds = tradingFundsAllocationModel.getAllocatedAmount();

        /* Recording the crediting transaction on the Capital Account **/
        GeneralLedger creditingTransaction = generalLedgerService.recordTransaction(new GeneralLedgerModel("Capital Account", "Asset", BigDecimal.ZERO, tradingFundsAllocationModel.getAllocatedAmount()));

        Cash creditCapitalAccount = Cash.builder()
                .cashTransactionNumber(codesAndDateService.generateTransactionCode("CSH-"))
                .generalLedger(creditingTransaction)
                .date(codesAndDateService.getDate())
                .nature("Withdarawal")
                .source("Capital Account")
                .destination("Trading Account")
                .details("Allocation of KES " + allocatedFunds + " from Capital Account to Trading Account.")
                .creditedCashAccount(capitalCashAccount)
                .debitedCashAccount(tradingCashAccount)
                .credit(allocatedFunds)
                .debit(BigDecimal.ZERO)
                .balance(getLatestCashBalance().subtract(allocatedFunds))
                .build();

        cashRepository.save(creditCapitalAccount);

        capitalCashAccount.setBalance(capitalCashAccount.getBalance().subtract(allocatedFunds));
        cashAccountsRepository.save(capitalCashAccount);

        /* Recording the debiting transaction on the Trading Account **/
        GeneralLedger debtingTransaction = generalLedgerService.recordTransaction(new GeneralLedgerModel("Trading Account", "Asset", tradingFundsAllocationModel.getAllocatedAmount(), BigDecimal.ZERO));

        Cash debitTradingAccount = Cash.builder()
                .cashTransactionNumber(codesAndDateService.generateTransactionCode("CSH-"))
                .generalLedger(debtingTransaction)
                .date(codesAndDateService.getDate())
                .nature("Deposit")
                .source("Capital Account")
                .destination("Trading Account")
                .details("Allocation of KES " + allocatedFunds + " from Capital Account to Trading Account.")
                .creditedCashAccount(capitalCashAccount)
                .debitedCashAccount(tradingCashAccount)
                .credit(BigDecimal.ZERO)
                .debit(allocatedFunds)
                .balance(getLatestCashBalance().add(allocatedFunds))
                .build();

        cashRepository.save(debitTradingAccount);

        tradingCashAccount.setBalance(tradingCashAccount.getBalance().add(allocatedFunds));
        cashAccountsRepository.save(tradingCashAccount);


        return tradingCashAccount;
    }


    @Override
    public DefaultCashAccounts setDefaultTradingAccount(DefaultCashAccountsModel defaultCashAccountsModel) {

        CashAccounts cashAccount = cashAccountsRepository.findByAccountName(defaultCashAccountsModel.getAccountName());

        try {
            checkIfCashAccountIsActive(cashAccount);
        } catch (InactiveCashAccountException e) {
            throw new RuntimeException(e);
        }

        DefaultCashAccounts defaultCashAccounts = DefaultCashAccounts.builder()
                .accountFunction("Trading")
                .cashAccount(cashAccount)
                .setDate(codesAndDateService.getDate())
                .build();

        defaultCashAccountsRepository.save(defaultCashAccounts);

        return defaultCashAccounts;
    }

    @Override
    public DefaultCashAccounts setDefaultCapitalAccount(DefaultCashAccountsModel defaultCashAccountsModel) {

        CashAccounts cashAccount = cashAccountsRepository.findByAccountName(defaultCashAccountsModel.getAccountName());

        try {
            checkIfCashAccountIsActive(cashAccount);
        } catch (InactiveCashAccountException e) {
            throw new RuntimeException(e);
        }

        DefaultCashAccounts defaultCashAccounts = DefaultCashAccounts.builder()
                .accountFunction("Capital")
                .cashAccount(cashAccount)
                .setDate(codesAndDateService.getDate())
                .build();

        defaultCashAccountsRepository.save(defaultCashAccounts);

        return defaultCashAccounts;
    }

    @Override
    public OwnerAccounts createOwnerAccount(OwnerAccountsModel ownerAccountsModel) {

        OwnerAccounts ownerAccount = OwnerAccounts.builder()
                .accountNumber(ownerAccountsModel.getAccountNumber())
                .dateAdded(codesAndDateService.getDate())
                .active(false)
                .accountName(ownerAccountsModel.getAccountName())
                .bank(banksRepository.findByName(ownerAccountsModel.getBankName()))
                .owner(ownersRepository.findByName(ownerAccountsModel.getOwnerName()))
                .build();

        ownerAccountsRepository.save(ownerAccount);

        return ownerAccount;
    }

    //TODO Add exception to check if account exists and prompt user to create account first
    @Override
    public OwnerAccounts activateOwnerAccount(String accountNo) {

        OwnerAccounts ownerAccount = null;

        if (ownerAccountsRepository.findById(accountNo).isPresent()){
            ownerAccount = ownerAccountsRepository.findById(accountNo).get();

            ownerAccount.setActive(true);
            ownerAccount.setDateActivated(codesAndDateService.getDate());

            ownerAccountsRepository.save(ownerAccount);
        }

        return ownerAccount;
    }

    @Override
    public OwnerAccounts deactivateOwnerAccount(String accountNo) {

        OwnerAccounts ownerAccount = null;

        if (ownerAccountsRepository.findById(accountNo).isPresent()){
            ownerAccount = ownerAccountsRepository.findById(accountNo).get();

            ownerAccount.setActive(false);
            ownerAccount.setDateDeactivated(codesAndDateService.getDate());

            ownerAccountsRepository.save(ownerAccount);
        }

        return ownerAccount;

    }

    @Override
    public CashAccounts activateCashAccount(String accountNo) {

        CashAccounts cashAccount = null;

        if (cashAccountsRepository.findById(accountNo).isPresent()){
            cashAccount = cashAccountsRepository.findById(accountNo).get();

            cashAccount.setActive(true);
            cashAccount.setDateActivated(codesAndDateService.getDate());

            cashAccountsRepository.save(cashAccount);
        }

        return cashAccount;
    }

    @Override
    public CashAccounts deactivateCashAccount(String accountNo) {

        CashAccounts cashAccount = null;

        if (cashAccountsRepository.findById(accountNo).isPresent()){
            cashAccount = cashAccountsRepository.findById(accountNo).get();

            cashAccount.setActive(false);
            cashAccount.setDateDeactivated(codesAndDateService.getDate());

            cashAccountsRepository.save(cashAccount);
        }

        return cashAccount;
    }

    @Override
    public BusinessAccounts createBusinessAccount(BusinessAccountsModel businessAccountsModel) {

        BusinessAccounts businessAccount = BusinessAccounts.builder()
                .accountNumber(businessAccountsModel.getAccountNumber())
                .dateAdded(codesAndDateService.getDate())
                .active(false)
                .accountName(businessAccountsModel.getAccountName())
                .business(businessesRepository.findByName(businessAccountsModel.getBusinessName()))
                .bank(banksRepository.findByName(businessAccountsModel.getBankName()))
                .build();

        businessAccountsRepository.save(businessAccount);

        return businessAccount;
    }

    @Override
    public BusinessAccounts activateBusinessAccount(String accountNo) {

        BusinessAccounts businessAccount = null;

        if (businessAccountsRepository.findById(accountNo).isPresent()){
            businessAccount = businessAccountsRepository.findById(accountNo).get();

            businessAccount.setActive(true);
            businessAccount.setDateActivated(codesAndDateService.getDate());

            businessAccountsRepository.save(businessAccount);
        }

        return businessAccount;
    }

    @Override
    public BusinessAccounts deactivateBusinessAccount(String accountNo) {

        BusinessAccounts businessAccount = null;

        if (businessAccountsRepository.findById(accountNo).isPresent()){
            businessAccount = businessAccountsRepository.findById(accountNo).get();

            businessAccount.setActive(false);
            businessAccount.setDateDeactivated(codesAndDateService.getDate());

            businessAccountsRepository.save(businessAccount);
        }

        return businessAccount;
    }

    @Override
    public ClientAccounts createClientAccount(ClientsAccountsModel clientsAccountsModel) {

        ClientAccounts clientAccount = ClientAccounts.builder()
                .accountNumber(clientsAccountsModel.getAccountNumber())
                .dateAdded(codesAndDateService.getDate())
                .active(false)
                .accountName(clientsAccountsModel.getAccountName())
                .client(clientsRepository.findByName(clientsAccountsModel.getClientName()))
                .bank(banksRepository.findByName(clientsAccountsModel.getBankName()))
                .build();

        clientAccountsRepository.save(clientAccount);

        return clientAccount;
    }

    @Override
    public ClientAccounts activateClientAccount(String accountNo) {

        ClientAccounts clientAccount = null;

        if (clientAccountsRepository.findById(accountNo).isPresent()){
             clientAccount = clientAccountsRepository.findById(accountNo).get();

             clientAccount.setActive(true);
             clientAccount.setDateActivated(codesAndDateService.getDate());

             clientAccountsRepository.save(clientAccount);
        }

        return clientAccount;
    }

    @Override
    public ClientAccounts deactivateClientAccount(String accountNo) {

        ClientAccounts clientAccount = null;

        if (clientAccountsRepository.findById(accountNo).isPresent()){
            clientAccount = clientAccountsRepository.findById(accountNo).get();

            clientAccount.setActive(false);
            clientAccount.setDateDeactivated(codesAndDateService.getDate());

            clientAccountsRepository.save(clientAccount);
        }

        return clientAccount;
    }




}
