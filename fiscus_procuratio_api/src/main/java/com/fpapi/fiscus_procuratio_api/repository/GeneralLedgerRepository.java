package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.GeneralLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralLedgerRepository extends JpaRepository<GeneralLedger, String> {
}
