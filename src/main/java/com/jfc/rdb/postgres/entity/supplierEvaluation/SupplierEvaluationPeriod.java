package com.jfc.rdb.postgres.entity.supplierEvaluation;

import java.time.LocalDate;
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
@Table(name = "supplier_evaluation_period")
public class SupplierEvaluationPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "period_id")
    private UUID periodId;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "quarter", nullable = false)
    private Integer quarter;

    @Column(name = "period_name", nullable = false, length = 50)
    private String periodName;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = "DRAFT";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
