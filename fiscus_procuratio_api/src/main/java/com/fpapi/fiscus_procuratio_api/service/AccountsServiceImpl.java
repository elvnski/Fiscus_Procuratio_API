package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.*;
import com.fpapi.fiscus_procuratio_api.model.*;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

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

    @Override
    public AccountsPayable recordAccountsPayable(AccountsPayableModel accountsPayableModel) {

        InvoicesOwed invoicesOwed = invoicesOwedRepository.findById(accountsPayableModel.getInvoiceNumber()).get();

        GeneralLedger generalLedger = new GeneralLedgerModel().createGeneralLedgerEntry("Accounts Payable", "Liability", BigDecimal.valueOf(0.00),
                invoicesOwed.getInvoiceAmount());
        generalLedgerRepository.save(generalLedger);

        AccountsPayable accountsPayable = new AccountsPayable();

        accountsPayable.setGeneralLedger(generalLedger);
        accountsPayable.setDate(getDate());
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

        GeneralLedger generalLedger = new GeneralLedgerModel().createGeneralLedgerEntry("Accounts Receivable", "Asset", invoicesIssued.getInvoiceAmount(), BigDecimal.valueOf(0.00));
        generalLedgerRepository.save(generalLedger);

        AccountsReceivable accountsReceivable = AccountsReceivable.builder()
                .generalLedger(generalLedger)
                .date(getDate())
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

        AccountsPayable accountsPayable = accountsPayableRepository.findById(accountsPayablePaymentsModel.getAccountsPayableId()).get();

        GeneralLedger generalLedger = new GeneralLedgerModel().createGeneralLedgerEntry("Accounts Payable",
                "Liability", accountsPayablePaymentsModel.getPayment(), BigDecimal.valueOf(0.0));
        generalLedgerRepository.save(generalLedger);


        BigDecimal latestCashBalance = BigDecimal.valueOf(0.0);

        if (!cashRepository.findAll().isEmpty()) {
            latestCashBalance = cashRepository.findByDate(cashRepository.getMaxDate()).getBalance();
        }

        Cash cash = new CashModel().createCashEntry(generalLedger, "Cash", "Accounts Payable", accountsPayablePaymentsModel.getDetails(), BigDecimal.valueOf(0.0),
                accountsPayablePaymentsModel.getPayment(), latestCashBalance);
        cashRepository.save(cash);


        AccountsPayablePayments accountsPayablePayments = AccountsPayablePayments.builder()
                .generalLedger(generalLedger)
                .cash(cash)
                .accountsPayable(accountsPayable)
                .paymentNumber(generatePaymentNumber())
                .date(getDate())
                .payment(accountsPayablePaymentsModel.getPayment())
                .build();
        accountsPayablePaymentsRepository.save(accountsPayablePayments);

        BigDecimal accountsPayableBalance = accountsPayable.getBalance().subtract(accountsPayablePaymentsModel.getPayment());
        accountsPayable.setBalance(accountsPayableBalance);
        accountsPayableRepository.save(accountsPayable);

        return accountsPayablePayments;
    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        return new Date(calendar.getTime().getTime());
    }

    private Date calculateDueDate(int daysToPay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.DATE, daysToPay);

        return new Date(calendar.getTime().getTime());
    }

    static String generatePaymentNumber() {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(16);

        for (int i = 0; i < 16; i++) {
            // generate a random number between 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));

        }

        return sb.toString();
    }

}
