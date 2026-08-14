package com.jfc.rdb.postgres.repository.supplierEvaluation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.supplierEvaluation.SupplierEvaluationPeriod;

@Repository
public interface SupplierEvaluationPeriodRepository extends JpaRepository<SupplierEvaluationPeriod, UUID> {

    List<SupplierEvaluationPeriod> findAllByOrderByYearDescQuarterDesc();

    SupplierEvaluationPeriod findByYearAndQuarter(Integer year, Integer quarter);
}
