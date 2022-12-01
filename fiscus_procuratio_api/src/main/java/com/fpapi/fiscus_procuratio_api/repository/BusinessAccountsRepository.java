package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.BusinessAccounts;
import com.fpapi.fiscus_procuratio_api.entity.ClientAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessAccountsRepository extends JpaRepository<BusinessAccounts, String> {
    BusinessAccounts findByAccountName(String businessAccountName);
}
