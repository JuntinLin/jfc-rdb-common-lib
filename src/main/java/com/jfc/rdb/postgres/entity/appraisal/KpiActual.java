package com.jfc.rdb.postgres.entity.appraisal;

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
@Table(name = "kpi_actual")
public class KpiActual {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "actual_id")
    private UUID actualId;

    @Column(name = "kpi_id", nullable = false)
    private UUID kpiId;

    @Column(name = "emp_no", length = 20)
    private String empNo;

    @Column(name = "actual_value")
    private BigDecimal actualValue;

    @Column(name = "score")
    private BigDecimal score;

    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;

    @Column(name = "manual_override")
    private Boolean manualOverride;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = LocalDateTime.now();
        if (manualOverride == null) manualOverride = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
