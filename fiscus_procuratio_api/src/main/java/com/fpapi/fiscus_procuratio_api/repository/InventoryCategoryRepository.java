package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.InventoryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryCategoryRepository extends JpaRepository<InventoryCategory, Long> {
    InventoryCategory findByCategory(String category);
}
