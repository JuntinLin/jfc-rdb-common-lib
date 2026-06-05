package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.AnnualKpiForm;

@Repository
public interface AnnualKpiFormRepository extends JpaRepository<AnnualKpiForm, UUID> {

    List<AnnualKpiForm> findByPeriodIdOrderByDeptCodeAscEmpNoAsc(UUID periodId);

    List<AnnualKpiForm> findByPeriodIdAndDeptCodeOrderByEmpNoAsc(UUID periodId, String deptCode);

    Optional<AnnualKpiForm> findByPeriodIdAndEmpNo(UUID periodId, String empNo);
}
