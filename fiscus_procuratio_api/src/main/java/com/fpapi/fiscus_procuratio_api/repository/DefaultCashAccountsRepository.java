package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.CashAccounts;
import com.fpapi.fiscus_procuratio_api.entity.DefaultCashAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultCashAccountsRepository extends JpaRepository<DefaultCashAccounts, String> {


    DefaultCashAccounts findByAccountFunction(String accountAction);
}
