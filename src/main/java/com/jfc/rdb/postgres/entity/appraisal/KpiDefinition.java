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
@Table(name = "kpi_definition")
public class KpiDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "kpi_id")
    private UUID kpiId;

    @Column(name = "period_id", nullable = false)
    private UUID periodId;

    @Column(name = "dept_code", nullable = false, length = 20)
    private String deptCode;

    @Column(name = "kpi_scope", nullable = false, length = 20)
    private String kpiScope;

    @Column(name = "kpi_code", nullable = false, length = 50)
    private String kpiCode;

    @Column(name = "kpi_name", nullable = false, length = 100)
    private String kpiName;

    @Column(name = "kpi_category", length = 30)
    private String kpiCategory;

    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "data_source", nullable = false, length = 30)
    private String dataSource;

    @Column(name = "source_config", columnDefinition = "JSONB")
    private String sourceConfig;

    @Column(name = "weight", nullable = false)
    private BigDecimal weight;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "defined_by", length = 20)
    private String definedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (sortOrder == null) sortOrder = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
