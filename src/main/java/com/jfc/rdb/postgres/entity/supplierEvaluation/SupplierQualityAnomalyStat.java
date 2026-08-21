package com.jfc.rdb.postgres.entity.supplierEvaluation;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 品質異常開單數統計（品保「廠商品質異常統計」季度匯入，非進料檢驗登記）。
 * 依 vendorShortName 對應 SupplierEvaluationRoster 該廠商 ERP 簡稱，
 * 計分時併入 SupplierEvaluationScore.defectCount。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier_quality_anomaly_stat")
public class SupplierQualityAnomalyStat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "stat_id")
    private UUID statId;

    @Column(name = "fiscal_year", nullable = false)
    private Integer fiscalYear;

    @Column(name = "quarter", nullable = false)
    private Integer quarter;

    @Column(name = "vendor_short_name", nullable = false, length = 50)
    private String vendorShortName;

    @Column(name = "ticket_count", nullable = false)
    private Integer ticketCount;

    @Column(name = "source", length = 30)
    private String source;

    @Column(name = "imported_at")
    private LocalDateTime importedAt;

    @PrePersist
    protected void onCreate() {
        if (importedAt == null) {
            importedAt = LocalDateTime.now();
        }
        if (source == null) {
            source = "QE_TICKET";
        }
    }
}
