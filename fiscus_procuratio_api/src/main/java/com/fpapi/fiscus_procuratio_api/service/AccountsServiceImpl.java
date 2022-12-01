package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.exceptions.OverpaymentException;
import com.fpapi.fiscus_procuratio_api.exceptions.WrongAccountSelectionException;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class AccountsServiceImpl implements AccountsService{

    @Autowired
    private GeneralLedgerRepository generalLedgerRepository;

    @Autowired
    private BusinessesRepository businessesRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private InvoicesOwedRepository invoicesOwedRepository;

    @Autowired
    private InvoicesIssuedRepository invoicesIssuedRepository;

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private AccountsPayableRepository accountsPayableRepository;

    @Autowired
    private AccountsReceivableRepository accountsReceivableRepository;

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private AccountsPayablePaymentsRepository accountsPayablePaymentsRepository;

    @Autowired
    private AccountsReceivableReceiptsRepository accountsReceivableReceiptsRepository;

    @Autowired
    private CashService cashService;

    @Autowired
    private GeneralLedgerService generalLedgerService;

    @Autowired
    private CodesAndDateService codesAndDateService;

    @Autowired
    private DefaultCashAccountsRepository defaultCashAccountsRepository;

    @Autowired
    private ClientAccountsRepository clientAccountsRepository;

    @Autowired
    private BusinessAccountsRepository businessAccountsRepository;


    //TODO Add Business Account Name to AccountsPayableModel and REMOVE FROM AccountsPayablePaymentsModel
    @Override
    public AccountsPayable recordAccountsPayable(AccountsPayableModel accountsPayableModel) {

        InvoicesOwed invoicesOwed = invoicesOwedRepository.findById(accountsPayableModel.getInvoiceNumber()).get();

        BusinessAccounts businessAccount = businessAccountsRepository.findByAccountName(accountsPayableModel.getBusinessAccountName());

        try {
            checkMatchingOwnerForAccount(invoicesOwed, businessAccount);
        } catch (WrongAccountSelectionException e) {
            throw new RuntimeException(e);
        }

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Accounts Payable", "Liability", BigDecimal.valueOf(0.00),
                invoicesOwed.getInvoiceAmount()));



        AccountsPayable accountsPayable = AccountsPayable.builder()
                .generalLedger(generalLedger)
                .date(codesAndDateService.getDate())
                .invoicesOwed(invoicesOwed)
                .business(invoicesOwed.getBusiness())
                .businessAccount(businessAccount)
                .description(invoicesOwed.getDetails())
                .invoiceAmount(invoicesOwed.getInvoiceAmount())
                .balance(invoicesOwed.getInvoiceAmount())
                .discount(invoicesOwed.getDiscount())
                .dueDate(invoicesOwed.getPaymentDate())
                .build();

        accountsPayableRepository.save(accountsPayable);

        return accountsPayable;
    }

    @Override
    public AccountsReceivable recordAccountsReceivable(AccountsReceivableModel accountsReceivableModel) {

        InvoicesIssued invoicesIssued = invoicesIssuedRepository.findById(accountsReceivableModel.getInvoiceNumber()).get();

        ClientAccounts clientAccount = clientAccountsRepository.findByAccountName(accountsReceivableModel.getClientAccountName());

        try {
            checkMatchingOwnerForAccount(invoicesIssued, clientAccount);
        } catch (WrongAccountSelectionException e) {
            throw new RuntimeException(e);
        }

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Accounts Receivable", "Asset", invoicesIssued.getInvoiceAmount(), BigDecimal.valueOf(0.00)));

        AccountsReceivable accountsReceivable = AccountsReceivable.builder()
                .generalLedger(generalLedger)
                .date(codesAndDateService.getDate())
                .client(invoicesIssued.getClient())
                .clientAccount(clientAccount)
                .invoicesIssued(invoicesIssued)
                .description(invoicesIssued.getDetails())
                .invoiceAmount(invoicesIssued.getInvoiceAmount())
                .discount(invoicesIssued.getDiscount())
                .balance(invoicesIssued.getInvoiceAmount())
                .dueDate(invoicesIssued.getPaymentDate())
                .build();

        accountsReceivableRepository.save(accountsReceivable);

        return accountsReceivable;
    }

    @Override
    public AccountsPayablePayments recordAccountsPayablePayment(AccountsPayablePaymentsModel accountsPayablePaymentsModel) {

        try {
            cashService.checkForTradingCashOverdraw(accountsPayablePaymentsModel.getPayment());
        } catch (CashOverdrawException e) {
            throw new RuntimeException(e);
        }

        AccountsPayable accountsPayable = accountsPayableRepository.findById(accountsPayablePaymentsModel.getAccountsPayableId()).get();

        try {
            checkForOverpayment(accountsPayable, accountsPayablePaymentsModel.getPayment());
        } catch (OverpaymentException e) {
            throw new RuntimeException(e);
        }

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Accounts Payable",
                "Liability", accountsPayablePaymentsModel.getPayment(), BigDecimal.valueOf(0.0)));



        Cash cash = cashService.sendCashToBusiness(new CashToBusinessModel("Cash", "Accounts Payable",
                accountsPayablePaymentsModel.getDetails(),
                accountsPayable.getBusinessAccount(),
                accountsPayablePaymentsModel.getPayment()));


        AccountsPayablePayments accountsPayablePayments = AccountsPayablePayments.builder()
                .generalLedger(generalLedger)
                .cash(cash)
                .accountsPayable(accountsPayable)
                .paymentNumber(codesAndDateService.generateTransactionCode("APP-"))
                .businessAccount(accountsPayable.getBusinessAccount())
                .date(codesAndDateService.getDate())
                .payment(accountsPayablePaymentsModel.getPayment())
                .build();
        accountsPayablePaymentsRepository.save(accountsPayablePayments);

        BigDecimal accountsPayableBalance = accountsPayable.getBalance().subtract(accountsPayablePaymentsModel.getPayment());
        accountsPayable.setBalance(accountsPayableBalance);
        accountsPayableRepository.save(accountsPayable);

        return accountsPayablePayments;
    }

    @Override
    public AccountsReceivableReceipts recordAccountsReceivableReceipt(AccountsReceivableReceiptsModel accountsReceivableReceiptsModel) {

        AccountsReceivable accountsReceivable = accountsReceivableRepository.findById(accountsReceivableReceiptsModel.getAccountsReceivableId()).get();

        try {
            checkForOverpayment(accountsReceivable, accountsReceivableReceiptsModel.getPayment());
        } catch (OverpaymentException e) {
            throw new RuntimeException(e);
        }

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Accounts Receivable",
                "Asset", BigDecimal.valueOf(0.0), accountsReceivableReceiptsModel.getPayment()));



        Cash cash = cashService.receiveCashFromClient(new CashFromClientModel( "Accounts Receivable", "Cash",
                accountsReceivableReceiptsModel.getDetails(),
                accountsReceivable.getClientAccount(),
                accountsReceivableReceiptsModel.getPayment()));

        AccountsReceivableReceipts accountsReceivableReceipts = AccountsReceivableReceipts.builder()
                .generalLedger(generalLedger)
                .cash(cash)
                .accountsReceivable(accountsReceivable)
                .clientAccount(accountsReceivable.getClientAccount())
                .receiptNumber(codesAndDateService.generateTransactionCode("ARR-"))
                .date(codesAndDateService.getDate())
                .paymentReceived(accountsReceivableReceiptsModel.getPayment())
                .build();

        accountsReceivableReceiptsRepository.save(accountsReceivableReceipts);

        BigDecimal accountsReceivableBalance = accountsReceivable.getBalance().subtract(accountsReceivableReceiptsModel.getPayment());
        accountsReceivable.setBalance(accountsReceivableBalance);
        accountsReceivableRepository.save(accountsReceivable);

        return accountsReceivableReceipts;
    }

    @Override
    public void checkMatchingOwnerForAccount(InvoicesIssued invoicesIssued, ClientAccounts clientAccount) throws WrongAccountSelectionException {

        Clients payingClient = invoicesIssued.getClient();
        Clients proposedClient = clientAccount.getClient();

        if (!payingClient.equals(proposedClient)){
            throw new WrongAccountSelectionException("Cannot Process New Accounts Receivable entry as the client listed in the Invoice Issued: '" + payingClient.getName() +
                    "', does not match with the client owning the specified paying account: '" + proposedClient.getName() + "'.");
        }

    }

    @Override
    public void checkMatchingOwnerForAccount(InvoicesOwed invoicesOwed, BusinessAccounts businessAccount) throws WrongAccountSelectionException {

        Businesses paidBusiness = invoicesOwed.getBusiness();
        Businesses proposedBusiness = businessAccount.getBusiness();

        if (!paidBusiness.equals(proposedBusiness)){
            throw new WrongAccountSelectionException("Cannot Process New Accounts Payable entry as the business listed in the Invoice Received: '" + paidBusiness.getName() +
                    "', does not match with the business owning the specified receiving account: '" + proposedBusiness.getName() + "'.");
        }

    }

    @Override
    public void checkForOverpayment(AccountsPayable accountsPayable, BigDecimal payment) throws OverpaymentException {

        BigDecimal invoiceBalance  = accountsPayable.getBalance();

        if (invoiceBalance.compareTo(payment) < 0){
            throw new OverpaymentException("Cannot process the payment of KES " + payment + " to '" + accountsPayable.getBusiness().getName() +
                    "' because the payment amount exceeds the KES " + invoiceBalance + " balance on the invoice.");
        }

    }

    @Override
    public void checkForOverpayment(AccountsReceivable accountsReceivable, BigDecimal payment) throws OverpaymentException {

        BigDecimal invoiceBalance  = accountsReceivable.getBalance();

        if (invoiceBalance.compareTo(payment) < 0){
            throw new OverpaymentException("Cannot process the receipt of KES " + payment + " to '" + accountsReceivable.getClient().getName() +
                    "' because the payment amount exceeds the KES " + invoiceBalance + " balance on the invoice.");
        }

    }


}
