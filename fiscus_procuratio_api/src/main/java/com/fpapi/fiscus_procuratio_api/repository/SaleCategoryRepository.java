package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.SaleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleCategoryRepository extends JpaRepository<SaleCategory, Long> {
    SaleCategory findByCategory(String saleCategory);
}
