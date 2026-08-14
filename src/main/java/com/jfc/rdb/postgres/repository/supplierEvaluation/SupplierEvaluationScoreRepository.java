package com.jfc.rdb.postgres.repository.supplierEvaluation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.supplierEvaluation.SupplierEvaluationScore;

@Repository
public interface SupplierEvaluationScoreRepository extends JpaRepository<SupplierEvaluationScore, UUID> {

    List<SupplierEvaluationScore> findByPeriodIdOrderByTotalScoreDesc(UUID periodId);

    SupplierEvaluationScore findByPeriodIdAndVendorCode(UUID periodId, String vendorCode);
}
