package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.QuarterlyKpiForm;

@Repository
public interface QuarterlyKpiFormRepository extends JpaRepository<QuarterlyKpiForm, UUID> {

    List<QuarterlyKpiForm> findByPeriodIdOrderByDeptCodeAscEmpNoAsc(UUID periodId);

    List<QuarterlyKpiForm> findByPeriodIdAndDeptCodeOrderByEmpNoAsc(UUID periodId, String deptCode);

    Optional<QuarterlyKpiForm> findByPeriodIdAndEmpNo(UUID periodId, String empNo);
}
