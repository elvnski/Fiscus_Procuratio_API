package com.fpapi.fiscus_procuratio_api.repository;


import com.fpapi.fiscus_procuratio_api.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {


    Inventory findByItemName(String itemName);
}
