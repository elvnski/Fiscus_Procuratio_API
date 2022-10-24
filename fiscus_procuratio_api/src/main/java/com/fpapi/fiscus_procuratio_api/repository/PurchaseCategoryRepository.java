package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.PurchaseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseCategoryRepository extends JpaRepository<PurchaseCategory, Long> {
    PurchaseCategory findByCategory(String category);
}
