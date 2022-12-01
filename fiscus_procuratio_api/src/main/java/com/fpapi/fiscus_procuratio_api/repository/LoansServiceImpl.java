package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.Loans;
import com.fpapi.fiscus_procuratio_api.model.LoansModel;
import com.fpapi.fiscus_procuratio_api.service.CodesAndDateService;
import com.fpapi.fiscus_procuratio_api.service.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoansServiceImpl implements LoansService {

    @Autowired
    private CodesAndDateService codesAndDateService;

    @Autowired
    private LoanCategoryRepository loanCategoryRepository;

    @Autowired
    private BanksRepository banksRepository;

    @Autowired
    private LoansRepository loansRepository;


    @Override
    public Loans recordNewLoan(LoansModel loansModel) {

        Loans loan = Loans.builder()
                .loanNumber(loansModel.getLoanNumber())
                .loanCategory(loanCategoryRepository.findByCategory(loansModel.getLoanCategory()))
                .bank(banksRepository.findByName(loansModel.getBankName()))
                .date(codesAndDateService.getDate())
                .details(loansModel.getDetails())
                .amount(loansModel.getAmount())
                .annualRate(loansModel.getAnnualRate())
                .period(loansModel.getPeriod())
                .numberOfPayments(loansModel.getNumberOfPayments())
                .startDate(loansModel.getStartDate())
                .endDate(loansModel.getEndDate())
                .scheduledPayment(loansModel.getScheduledPayment())
                .totalEarlyPayments(BigDecimal.ZERO)
                .totalInterest(BigDecimal.ZERO)
                .balance(loansModel.getAmount())
                .build();

        loansRepository.save(loan);

        return loan;
    }
}
