package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.Loans;
import com.fpapi.fiscus_procuratio_api.model.LoansModel;

public interface LoansService {
    Loans recordNewLoan(LoansModel loansModel);
}
