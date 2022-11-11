package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.AmortizationGenerator;
import com.fpapi.fiscus_procuratio_api.model.AmortizationGeneratorModel;
import com.fpapi.fiscus_procuratio_api.service.AmortizationGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AmortizationGeneratorController {

    @Autowired
    private AmortizationGeneratorService amortizationGeneratorService;

    @PostMapping("/loans/amortization/generator")
    public String generateAmortizationSchedule(@RequestBody AmortizationGeneratorModel amortizationGeneratorModel){

        AmortizationGenerator amortizationGenerator = amortizationGeneratorService.generateAmortizationSchedule(amortizationGeneratorModel);

        String saved = "";

        if (amortizationGeneratorModel.getSaveToRegistry().equals("YES")){
            saved = "Loan Details Saved To Amortization Registry!";
        }

        return "New Amortizanion Schedule Generated For Loan: \n" +
                "Amount : " + amortizationGeneratorModel.getLoanAmount() + "\n" +
                "Interest : " + amortizationGeneratorModel.getIntRate() + "% per anum\n\n" +
                saved;
    }


}
