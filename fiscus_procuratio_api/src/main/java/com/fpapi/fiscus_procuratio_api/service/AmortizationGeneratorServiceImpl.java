package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.AmortizationGenerator;
import com.fpapi.fiscus_procuratio_api.entity.AmortizationRegistry;
import com.fpapi.fiscus_procuratio_api.model.AmortizationGeneratorModel;
import com.fpapi.fiscus_procuratio_api.repository.AmortizationGeneratorRepository;
import com.fpapi.fiscus_procuratio_api.repository.AmortizationRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

@Service
public class AmortizationGeneratorServiceImpl implements AmortizationGeneratorService {

    @Autowired
    private AmortizationGeneratorRepository amortizationGeneratorRepository;

    @Autowired
    private AmortizationRegistryRepository amortizationRegistryRepository;

    @Override
    public AmortizationGenerator generateAmortizationSchedule(AmortizationGeneratorModel amortizationGeneratorModel) {

        AmortizationGenerator amortizationGenerator = null;

        amortizationGeneratorRepository.deleteAll();

        BigDecimal loanAmount;
        BigDecimal intRate;
        int loanPeriod;
        int noOfPayments;
        LocalDate ASG_Date = new Date(System.currentTimeMillis()).toLocalDate();


        loanAmount = amortizationGeneratorModel.getLoanAmount();
        intRate = amortizationGeneratorModel.getIntRate();
        loanPeriod = amortizationGeneratorModel.getLoanPeriod();
        BigDecimal LoanBalance = loanAmount;
        BigDecimal CumulativeInterest = BigDecimal.valueOf(0);
        Long PaymentNo = 1L;

        BigDecimal Perc_IntRate = intRate.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal I = (Perc_IntRate).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        System.out.println("Intrate is : " + I);

        BigDecimal sub1 = (I.add(BigDecimal.valueOf(1))).pow(loanPeriod);
        BigDecimal TotMonthlyPayment = loanAmount.multiply((I.multiply(sub1)).divide((sub1.subtract(BigDecimal.valueOf(1))), 10, RoundingMode.HALF_UP));
        TotMonthlyPayment = TotMonthlyPayment.setScale(2, RoundingMode.HALF_UP);

        System.out.println("Total Monthly Payment is : " + TotMonthlyPayment);

        BigDecimal ExtraPayment = amortizationGeneratorModel.getExtraPayment();


        BigDecimal TotalPlusExtra = TotMonthlyPayment.add(ExtraPayment);
        System.out.println("TOTAL PAYMENT IS : " + TotalPlusExtra);

        BigDecimal InitialBalance = BigDecimal.valueOf(0);
        BigDecimal InterestPayment;
        BigDecimal PrincipalPayment;

        while (LoanBalance.compareTo(BigDecimal.valueOf(0.0)) > 0) {


            if (LoanBalance.compareTo(TotalPlusExtra) > 0) {

                System.out.println("CALCULATING PAYMENT NUMBER : " + PaymentNo);

                InitialBalance = LoanBalance;

                InterestPayment = LoanBalance.multiply((Perc_IntRate).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP));
                InterestPayment = InterestPayment.setScale(2, RoundingMode.HALF_UP);
                System.out.println("INTEREST PAYMENT IS : " + InterestPayment);

                PrincipalPayment = TotalPlusExtra.subtract(InterestPayment);
                System.out.println("PRINCIPAL PAYMENT IS : " + PrincipalPayment);

                if (PaymentNo > 1) {
                    ASG_Date = ASG_Date.plusMonths(1);
                }

                LoanBalance = LoanBalance.subtract(PrincipalPayment);
                CumulativeInterest = CumulativeInterest.add(InterestPayment);

                System.out.println("ENDING LOAN BALANCE IS " + LoanBalance + " ON DATE " + ASG_Date);



                amortizationGenerator = AmortizationGenerator.builder()
                        .paymentNo(PaymentNo)
                        .paymentDate(java.sql.Date.valueOf(ASG_Date))
                        .startingBalance(InitialBalance)
                        .scheduledPayment(TotMonthlyPayment)
                        .extraPayment(ExtraPayment)
                        .totalPayment(TotalPlusExtra)
                        .principal(PrincipalPayment)
                        .interest(InterestPayment)
                        .endingBalance(LoanBalance)
                        .cumulativeInterest(CumulativeInterest)
                        .build();

                amortizationGeneratorRepository.save(amortizationGenerator);

            } else {
                InitialBalance = LoanBalance;
                TotMonthlyPayment = LoanBalance;
                ExtraPayment = BigDecimal.valueOf(0);
                TotalPlusExtra = TotMonthlyPayment.add(ExtraPayment);
                InterestPayment = LoanBalance.multiply((Perc_IntRate).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP));
                InterestPayment = InterestPayment.setScale(2, RoundingMode.HALF_UP);
                PrincipalPayment = TotalPlusExtra.subtract(InterestPayment);

                if (PaymentNo > 1) {
                    ASG_Date = ASG_Date.plusMonths(1);
                }

                LoanBalance = LoanBalance.subtract(TotMonthlyPayment);
                CumulativeInterest = CumulativeInterest.add(InterestPayment);


                System.out.println("ENDING LOAN BALANCE IS " + LoanBalance + " ON DATE " + ASG_Date);


                amortizationGenerator = AmortizationGenerator.builder()
                        .paymentNo(PaymentNo)
                        .paymentDate(java.sql.Date.valueOf(ASG_Date))
                        .startingBalance(InitialBalance)
                        .scheduledPayment(TotMonthlyPayment)
                        .extraPayment(ExtraPayment)
                        .totalPayment(TotalPlusExtra)
                        .principal(PrincipalPayment)
                        .interest(InterestPayment)
                        .endingBalance(LoanBalance)
                        .cumulativeInterest(CumulativeInterest)
                        .build();

                amortizationGeneratorRepository.save(amortizationGenerator);


            }

            PaymentNo = PaymentNo + 1;

        }

        if (amortizationGeneratorModel.getSaveToRegistry().equals("YES")){

            AmortizationRegistry amortizationRegistry = AmortizationRegistry.builder()
                    .annualInterestRate(amortizationGeneratorModel.getIntRate())
                    .extraPaymentPerMonth(amortizationGeneratorModel.getExtraPayment())
                    .loanAmount(amortizationGeneratorModel.getLoanAmount())
                    .loanDetails(amortizationGeneratorModel.getDetails())
                    .loanPeriodInMonths(amortizationGeneratorModel.getLoanPeriod())
                    .loanProvider(amortizationGeneratorModel.getProvider())
                    .loanStartDate(java.sql.Date.valueOf(new Date(System.currentTimeMillis()).toLocalDate()))
                    .build();


            amortizationRegistryRepository.save(amortizationRegistry);
        }



        return amortizationGenerator;
    }



}





