package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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


    @Override
    public AccountsPayable recordAccountsPayable(AccountsPayableModel accountsPayableModel) {

        InvoicesOwed invoicesOwed = invoicesOwedRepository.findById(accountsPayableModel.getInvoiceNumber()).get();

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Accounts Payable", "Liability", BigDecimal.valueOf(0.00),
                invoicesOwed.getInvoiceAmount()));

        AccountsPayable accountsPayable = new AccountsPayable();

        accountsPayable.setGeneralLedger(generalLedger);
        accountsPayable.setDate(codesAndDateService.getDate());
        accountsPayable.setBusiness(businessesRepository.findByName(accountsPayableModel.getBusinessName()));

        if (Objects.nonNull(accountsPayableModel.getInvoiceNumber())) {
            accountsPayable.setInvoicesOwed(invoicesOwed);
        } else if (Objects.nonNull(accountsPayableModel.getLoanNumber())) {
            accountsPayable.setLoans(loansRepository.findById(accountsPayableModel.getLoanNumber()).get());
        }


        accountsPayable.setDescription(invoicesOwed.getDetails());
        accountsPayable.setInvoiceAmount(invoicesOwed.getInvoiceAmount());
        accountsPayable.setDiscount(invoicesOwed.getDiscount());
        accountsPayable.setBalance(invoicesOwed.getInvoiceAmount());
        accountsPayable.setDueDate(invoicesOwed.getPaymentDate());

        accountsPayableRepository.save(accountsPayable);

        return accountsPayable;
    }

    @Override
    public AccountsReceivable recordAccountsReceivable(AccountsReceivableModel accountsReceivableModel) {

        InvoicesIssued invoicesIssued = invoicesIssuedRepository.findById(accountsReceivableModel.getInvoiceNumber()).get();

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Accounts Receivable", "Asset", invoicesIssued.getInvoiceAmount(), BigDecimal.valueOf(0.00)));

        AccountsReceivable accountsReceivable = AccountsReceivable.builder()
                .generalLedger(generalLedger)
                .date(codesAndDateService.getDate())
                .client(clientsRepository.findByName(accountsReceivableModel.getClientName()))
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
            cashService.checkForCashOverdraw(accountsPayablePaymentsModel.getPayment());
        } catch (CashOverdrawException e) {
            throw new RuntimeException(e);
        }

        AccountsPayable accountsPayable = accountsPayableRepository.findById(accountsPayablePaymentsModel.getAccountsPayableId()).get();

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Accounts Payable",
                "Liability", accountsPayablePaymentsModel.getPayment(), BigDecimal.valueOf(0.0)));


        BigDecimal latestCashBalance = BigDecimal.valueOf(0.0);

        if (!cashRepository.findAll().isEmpty()) {
            latestCashBalance = cashRepository.findByDate(cashRepository.getMaxDate()).getBalance();
        }

        Cash cash = cashService.spendCash(new CashModel(generalLedger, "Cash", "Accounts Payable", accountsPayablePaymentsModel.getDetails(), BigDecimal.valueOf(0.0),
                accountsPayablePaymentsModel.getPayment(), latestCashBalance));


        AccountsPayablePayments accountsPayablePayments = AccountsPayablePayments.builder()
                .generalLedger(generalLedger)
                .cash(cash)
                .accountsPayable(accountsPayable)
                .paymentNumber(codesAndDateService.generateTransactionCode("APP-"))
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

        GeneralLedger generalLedger = generalLedgerService.recordTransaction(new GeneralLedgerModel("Accounts Receivable",
                "Asset", BigDecimal.valueOf(0.0), accountsReceivableReceiptsModel.getPayment()));


        BigDecimal latestCashBalance = BigDecimal.valueOf(0.0);
        if (!cashRepository.findAll().isEmpty()) {
            latestCashBalance = cashRepository.findByDate(cashRepository.getMaxDate()).getBalance();
        }

        Cash cash = cashService.depositCash(new CashModel(generalLedger, accountsReceivableReceiptsModel.getClientName(), "Accounts Receivable",
                accountsReceivableReceiptsModel.getDetails(), accountsReceivableReceiptsModel.getPayment(), BigDecimal.valueOf(0.0), latestCashBalance));

        AccountsReceivableReceipts accountsReceivableReceipts = AccountsReceivableReceipts.builder()
                .generalLedger(generalLedger)
                .cash(cash)
                .accountsReceivable(accountsReceivable)
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





}
