package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.DeptKpiConfig;

@Repository
public interface DeptKpiConfigRepository extends JpaRepository<DeptKpiConfig, UUID> {

    List<DeptKpiConfig> findByPeriodIdOrderByDeptCode(UUID periodId);

    Optional<DeptKpiConfig> findByPeriodIdAndDeptCode(UUID periodId, String deptCode);
}
