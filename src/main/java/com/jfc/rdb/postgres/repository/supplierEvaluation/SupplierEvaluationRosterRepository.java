package com.jfc.rdb.postgres.repository.supplierEvaluation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.supplierEvaluation.SupplierEvaluationRoster;

@Repository
public interface SupplierEvaluationRosterRepository extends JpaRepository<SupplierEvaluationRoster, UUID> {

    List<SupplierEvaluationRoster> findByPeriodIdOrderByVendorCategoryAscRoutingTransactionCountDesc(UUID periodId);

    List<SupplierEvaluationRoster> findByPeriodIdAndVendorCategoryOrderByRoutingTransactionCountDesc(
            UUID periodId, String vendorCategory);

    SupplierEvaluationRoster findByPeriodIdAndVendorCode(UUID periodId, String vendorCode);
}
