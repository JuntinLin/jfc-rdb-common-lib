package com.jfc.rdb.postgres.entity.supplierEvaluation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier_evaluation_score")
public class SupplierEvaluationScore {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "score_id")
    private UUID scoreId;

    @Column(name = "period_id", nullable = false)
    private UUID periodId;

    @Column(name = "vendor_code", nullable = false, length = 20)
    private String vendorCode;

    @Column(name = "vendor_short_name", length = 50)
    private String vendorShortName;

    @Column(name = "defect_count")
    private Integer defectCount;

    @Column(name = "total_delivery_count")
    private Integer totalDeliveryCount;

    @Column(name = "quality_score")
    private BigDecimal qualityScore;

    @Column(name = "on_time_delivery_count")
    private Integer onTimeDeliveryCount;

    @Column(name = "delivery_score")
    private BigDecimal deliveryScore;

    @Column(name = "urgent_not_cooperated")
    private Integer urgentNotCooperated;

    @Column(name = "urgent_total")
    private Integer urgentTotal;

    @Column(name = "coordination_score")
    private BigDecimal coordinationScore;

    @Column(name = "price_trend", length = 10)
    private String priceTrend;   // DOWN / FLAT / UP

    @Column(name = "price_up_count")
    private Integer priceUpCount;

    @Column(name = "price_score")
    private BigDecimal priceScore;

    @Column(name = "total_score")
    private BigDecimal totalScore;

    @Column(name = "grade", length = 1)
    private String grade;

    @Column(name = "grade_overridden")
    private Boolean gradeOverridden;

    @Column(name = "grade_note", length = 200)
    private String gradeNote;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        updatedAt = LocalDateTime.now();
    }
}
