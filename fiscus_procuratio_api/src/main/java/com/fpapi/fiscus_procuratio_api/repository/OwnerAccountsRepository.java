package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.OwnerAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerAccountsRepository extends JpaRepository<OwnerAccounts, String> {


    OwnerAccounts findByAccountName(String ownerName);
}
