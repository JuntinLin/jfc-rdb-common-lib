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
@Table(name = "dept_kpi_config",
        uniqueConstraints = @UniqueConstraint(columnNames = {"period_id", "dept_code"}))
public class DeptKpiConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "config_id")
    private UUID configId;

    @Column(name = "period_id", nullable = false)
    private UUID periodId;

    @Column(name = "dept_code", nullable = false, length = 20)
    private String deptCode;

    @Column(name = "dept_name", length = 50)
    private String deptName;

    @Column(name = "dept_kpi_weight", nullable = false)
    private BigDecimal deptKpiWeight;

    @Column(name = "personal_kpi_weight", nullable = false)
    private BigDecimal personalKpiWeight;

    @Column(name = "supervisor_weight", nullable = false)
    private BigDecimal supervisorWeight;

    @Column(name = "proposal_weight", nullable = false)
    private BigDecimal proposalWeight;

    @Column(name = "configured_by", length = 20)
    private String configuredBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (deptKpiWeight == null) deptKpiWeight = new BigDecimal("30");
        if (personalKpiWeight == null) personalKpiWeight = new BigDecimal("30");
        if (supervisorWeight == null) supervisorWeight = new BigDecimal("30");
        if (proposalWeight == null) proposalWeight = new BigDecimal("10");
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
