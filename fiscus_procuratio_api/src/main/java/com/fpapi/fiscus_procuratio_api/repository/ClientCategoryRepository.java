package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.ClientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCategoryRepository extends JpaRepository<ClientCategory, Long> {
    ClientCategory findByCategory(String category);
}
