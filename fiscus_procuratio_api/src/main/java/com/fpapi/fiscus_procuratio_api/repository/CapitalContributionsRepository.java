package com.fpapi.fiscus_procuratio_api.repository;

import com.fpapi.fiscus_procuratio_api.entity.CapitalContributions;
import com.fpapi.fiscus_procuratio_api.entity.Owners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CapitalContributionsRepository extends JpaRepository<CapitalContributions, Long> {
    List<CapitalContributions> findByOwnerEquals(Owners owner);


    @Query(value = "SELECT SUM(contribution) FROM capital_contributions", nativeQuery = true)
    BigDecimal getOverallTotalContributions();

    @Query(value = "SELECT SUM(contribution) FROM capital_contributions WHERE owner_id = ?1", nativeQuery = true)
    BigDecimal getOwnerTotalContributions(Long ownerId);

}
