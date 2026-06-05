package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.KpiDefinition;

@Repository
public interface KpiDefinitionRepository extends JpaRepository<KpiDefinition, UUID> {

    List<KpiDefinition> findByPeriodIdAndDeptCodeOrderBySortOrderAsc(UUID periodId, String deptCode);

    List<KpiDefinition> findByPeriodIdAndDeptCodeAndKpiScopeOrderBySortOrderAsc(UUID periodId, String deptCode, String kpiScope);

    List<KpiDefinition> findByPeriodIdOrderByDeptCodeAscSortOrderAsc(UUID periodId);
}
