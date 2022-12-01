package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.OwnerAccounts;
import com.fpapi.fiscus_procuratio_api.entity.Owners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnersRepository extends JpaRepository<Owners, Long> {


    Owners findByName(String ownerName);
}
