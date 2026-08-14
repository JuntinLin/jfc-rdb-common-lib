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

    /** 同一廠商同期間可能同時有 SUB(加工/委外) 與 REG(原料) 交易，須分開查找/更新，不能只用 vendorCode */
    SupplierEvaluationRoster findByPeriodIdAndVendorCodeAndVendorCategory(UUID periodId, String vendorCode, String vendorCategory);
}
