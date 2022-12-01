package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.CashAccounts;
import com.fpapi.fiscus_procuratio_api.entity.ClientAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAccountsRepository extends JpaRepository<ClientAccounts, String> {
    ClientAccounts findByAccountName(String clientAccountName);
}
