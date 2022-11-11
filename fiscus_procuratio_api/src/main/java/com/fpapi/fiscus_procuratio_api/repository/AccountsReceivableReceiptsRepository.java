package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.AccountsReceivableReceipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsReceivableReceiptsRepository extends JpaRepository<AccountsReceivableReceipts, String> {

}
