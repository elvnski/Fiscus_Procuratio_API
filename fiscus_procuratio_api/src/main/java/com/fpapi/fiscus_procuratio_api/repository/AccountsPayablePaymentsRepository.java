package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.AccountsPayablePayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsPayablePaymentsRepository extends JpaRepository<AccountsPayablePayments, String> {
}
