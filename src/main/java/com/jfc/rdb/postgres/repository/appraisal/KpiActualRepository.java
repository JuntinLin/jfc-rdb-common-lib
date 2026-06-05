package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.KpiActual;

@Repository
public interface KpiActualRepository extends JpaRepository<KpiActual, UUID> {

    List<KpiActual> findByKpiId(UUID kpiId);

    Optional<KpiActual> findByKpiIdAndEmpNo(UUID kpiId, String empNo);

    void deleteByKpiId(UUID kpiId);
}
