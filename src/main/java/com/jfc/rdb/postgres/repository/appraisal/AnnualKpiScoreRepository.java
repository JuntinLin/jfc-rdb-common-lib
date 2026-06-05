package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.AnnualKpiScore;

@Repository
public interface AnnualKpiScoreRepository extends JpaRepository<AnnualKpiScore, UUID> {

    List<AnnualKpiScore> findByFormIdOrderByKpiScopeAsc(UUID formId);

    void deleteByFormId(UUID formId);
}
