package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.InvoicesOwed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoicesOwedRepository extends JpaRepository<InvoicesOwed, String> {
}
