package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoansRepository extends JpaRepository<Loans, String> {
}
