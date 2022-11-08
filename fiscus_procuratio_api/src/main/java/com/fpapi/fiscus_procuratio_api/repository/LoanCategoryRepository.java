package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.LoanCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanCategoryRepository extends JpaRepository<LoanCategory, Long> {

    LoanCategory findByCategory(String category);
}
