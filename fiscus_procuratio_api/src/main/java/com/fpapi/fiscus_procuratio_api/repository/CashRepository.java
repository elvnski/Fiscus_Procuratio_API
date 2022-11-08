package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CashRepository extends JpaRepository<Cash, String> {

    @Query(nativeQuery = true, value = "SELECT max(i.date) from cash as i")
    Date getMaxDate();

    Cash findByDate(Date maxDate);
}
