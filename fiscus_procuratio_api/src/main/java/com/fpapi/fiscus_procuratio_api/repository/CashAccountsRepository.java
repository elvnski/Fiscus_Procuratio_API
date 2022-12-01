package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.CashAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashAccountsRepository extends JpaRepository<CashAccounts, String> {
    CashAccounts findByAccountName(String accountName);
}
