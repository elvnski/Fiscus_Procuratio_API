package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.Clients;
import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import com.fpapi.fiscus_procuratio_api.entity.InvoicesIssued;
import com.fpapi.fiscus_procuratio_api.entity.InvoicesOwed;
import com.fpapi.fiscus_procuratio_api.model.GeneralLedgerModel;
import com.fpapi.fiscus_procuratio_api.model.InvoicesIssuedModel;
import com.fpapi.fiscus_procuratio_api.model.InvoicesOwedModel;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Service
public class TransactionRecordsServiceImpl implements TransactionRecordsService{

    @Autowired
    private InvoicesIssuedRepository invoicesIssuedRepository;

    @Autowired
    private InvoicesOwedRepository invoicesOwedRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private BusinessesRepository businessesRepository;

    @Autowired
    private GeneralLedgerRepository generalLedgerRepository;


    @Override
    public InvoicesIssued addInvoicesIssued(InvoicesIssuedModel invoicesIssuedModel) {

        GeneralLedgerModel generalLedgerModel = new GeneralLedgerModel();
        GeneralLedger generalLedger = generalLedgerModel.createGeneralLedgerEntry("Invoices Issued", "Asset", invoicesIssuedModel.getInvoiceAmount(), BigDecimal.valueOf(0.0));
        generalLedgerRepository.save(generalLedger);

        InvoicesIssued invoicesIssued = new InvoicesIssued();

        invoicesIssued.setInvoiceNumber(generateInvoiceNumber());
        invoicesIssued.setClient(clientsRepository.findByName(invoicesIssuedModel.getClientName()));
        invoicesIssued.setIssueDate(getDate());
        invoicesIssued.setPaymentDate(calculatePaymentDate(invoicesIssuedModel.getDaysToPay()));
        invoicesIssued.setInvoiceAmount(invoicesIssuedModel.getInvoiceAmount());
        invoicesIssued.setDiscount(invoicesIssuedModel.getDiscount());
        invoicesIssued.setDetails(invoicesIssuedModel.getDetails());
        invoicesIssued.setGeneralLedger(generalLedger);

        invoicesIssuedRepository.save(invoicesIssued);

        return invoicesIssued;
    }

    @Override
    public InvoicesOwed addInvoicesOwed(InvoicesOwedModel invoicesOwedModel) {

        GeneralLedgerModel generalLedgerModel = new GeneralLedgerModel();
        GeneralLedger generalLedger = generalLedgerModel.createGeneralLedgerEntry("Invoices Owed", "Liability", BigDecimal.valueOf(0.0), invoicesOwedModel.getInvoiceAmount());
        generalLedgerRepository.save(generalLedger);

        InvoicesOwed invoicesOwed = new InvoicesOwed();

        invoicesOwed.setInvoiceNumber(invoicesOwedModel.getInvoiceNumber());
        invoicesOwed.setBusiness(businessesRepository.findByName(invoicesOwedModel.getBusinessName()));
        invoicesOwed.setIssueDate(getDate());
        invoicesOwed.setPaymentDate(calculatePaymentDate(invoicesOwedModel.getDaysToPay()));
        invoicesOwed.setInvoiceAmount(invoicesOwedModel.getInvoiceAmount());
        invoicesOwed.setDiscount(invoicesOwedModel.getDiscount());
        invoicesOwed.setDetails(invoicesOwedModel.getDetails());
        invoicesOwed.setGeneralLedger(generalLedger);

        invoicesOwedRepository.save(invoicesOwed);

        return invoicesOwed;
    }


    static String generateInvoiceNumber() {

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


    private Date calculatePaymentDate(int daysToPay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.DATE, daysToPay);

        return new Date(calendar.getTime().getTime());
    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());

        return new Date(calendar.getTime().getTime());
    }


}
