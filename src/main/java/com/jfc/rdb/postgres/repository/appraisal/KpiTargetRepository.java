package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.KpiTarget;

@Repository
public interface KpiTargetRepository extends JpaRepository<KpiTarget, UUID> {

    List<KpiTarget> findByKpiId(UUID kpiId);

    Optional<KpiTarget> findByKpiIdAndEmpNo(UUID kpiId, String empNo);

    void deleteByKpiId(UUID kpiId);
}
