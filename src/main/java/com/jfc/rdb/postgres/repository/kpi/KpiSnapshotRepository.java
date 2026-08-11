package com.jfc.rdb.postgres.repository.kpi;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jfc.rdb.postgres.entity.kpi.KpiSnapshot;

@Repository
public interface KpiSnapshotRepository extends JpaRepository<KpiSnapshot, UUID> {

    List<KpiSnapshot> findBySourceAndPeriodMonthBetween(String source, String startMonth, String endMonth);

    @Modifying
    @Transactional
    void deleteByPeriodMonthAndSource(String periodMonth, String source);

    @Query("SELECT DISTINCT k.periodMonth FROM KpiSnapshot k WHERE k.source = :source ORDER BY k.periodMonth")
    List<String> findDistinctPeriodMonthsBySource(@Param("source") String source);
}
