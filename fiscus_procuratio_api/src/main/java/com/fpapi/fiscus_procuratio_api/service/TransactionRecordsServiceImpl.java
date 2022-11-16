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
    private CodesAndDateService codesAndDateService;


    @Override
    public InvoicesIssued addInvoicesIssued(InvoicesIssuedModel invoicesIssuedModel) {

        InvoicesIssued invoicesIssued = new InvoicesIssued();

        invoicesIssued.setInvoiceNumber(codesAndDateService.generateTransactionCode("II-"));
        invoicesIssued.setClient(clientsRepository.findByName(invoicesIssuedModel.getClientName()));
        invoicesIssued.setIssueDate(codesAndDateService.getDate());
        invoicesIssued.setPaymentDate(calculatePaymentDate(invoicesIssuedModel.getDaysToPay()));
        invoicesIssued.setInvoiceAmount(invoicesIssuedModel.getInvoiceAmount());
        invoicesIssued.setDiscount(invoicesIssuedModel.getDiscount());
        invoicesIssued.setDetails(invoicesIssuedModel.getDetails());

        invoicesIssuedRepository.save(invoicesIssued);

        return invoicesIssued;
    }

    @Override
    public InvoicesOwed addInvoicesOwed(InvoicesOwedModel invoicesOwedModel) {

        InvoicesOwed invoicesOwed = new InvoicesOwed();

        invoicesOwed.setInvoiceNumber(invoicesOwedModel.getInvoiceNumber());
        invoicesOwed.setBusiness(businessesRepository.findByName(invoicesOwedModel.getBusinessName()));
        invoicesOwed.setIssueDate(codesAndDateService.getDate());
        invoicesOwed.setPaymentDate(calculatePaymentDate(invoicesOwedModel.getDaysToPay()));
        invoicesOwed.setInvoiceAmount(invoicesOwedModel.getInvoiceAmount());
        invoicesOwed.setDiscount(invoicesOwedModel.getDiscount());
        invoicesOwed.setDetails(invoicesOwedModel.getDetails());

        invoicesOwedRepository.save(invoicesOwed);

        return invoicesOwed;
    }



    private Date calculatePaymentDate(int daysToPay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.DATE, daysToPay);

        return new Date(calendar.getTime().getTime());
    }


}
