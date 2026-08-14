package com.jfc.rdb.postgres.repository.supplierEvaluation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.supplierEvaluation.SupplierIncomingInspection;

@Repository
public interface SupplierIncomingInspectionRepository extends JpaRepository<SupplierIncomingInspection, UUID> {

    List<SupplierIncomingInspection> findByInspectionDateBetweenAndVendorCodeOrderByInspectionDateDesc(
            LocalDate start, LocalDate end, String vendorCode);

    List<SupplierIncomingInspection> findByInspectionDateBetweenOrderByInspectionDateDesc(
            LocalDate start, LocalDate end);

    @Query("SELECT i.vendorCode, COUNT(i) FROM SupplierIncomingInspection i " +
           "WHERE i.inspectionDate BETWEEN :start AND :end AND i.result = 'NG' " +
           "GROUP BY i.vendorCode")
    List<Object[]> countNgByVendorBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
