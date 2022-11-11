package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.AmortizationGenerator;
import com.fpapi.fiscus_procuratio_api.model.AmortizationGeneratorModel;

public interface AmortizationGeneratorService {
    AmortizationGenerator generateAmortizationSchedule(AmortizationGeneratorModel amortizationGeneratorModel);
}
