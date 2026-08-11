package com.jfc.rdb.postgres.entity.kpi;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 加工課/組立課 KPI 月快照 — 個人 × 月 最細粒度
 * 課/組/季/年/YOY/YTD 皆由查詢端加總分量後再算比率
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kpi_snapshot")
public class KpiSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "snapshot_id")
    private UUID snapshotId;

    @Column(name = "period_month", nullable = false, length = 7)
    private String periodMonth;

    @Column(name = "source", nullable = false, length = 20)
    private String source;

    @Column(name = "emp_no", nullable = false, length = 20)
    private String empNo;

    @Column(name = "emp_name", length = 50)
    private String empName;

    @Column(name = "dept_code", length = 20)
    private String deptCode;

    @Column(name = "dept_name", length = 50)
    private String deptName;

    @Column(name = "parent_dept_name", length = 50)
    private String parentDeptName;

    @Column(name = "output_value")
    private BigDecimal outputValue;

    @Column(name = "labor_hours")
    private BigDecimal laborHours;

    @Column(name = "attendance_hours")
    private BigDecimal attendanceHours;

    @Column(name = "required_hours")
    private BigDecimal requiredHours;

    @Column(name = "report_count")
    private Integer reportCount;

    @Column(name = "work_order_count")
    private Integer workOrderCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (outputValue == null) outputValue = BigDecimal.ZERO;
        if (laborHours == null) laborHours = BigDecimal.ZERO;
        if (attendanceHours == null) attendanceHours = BigDecimal.ZERO;
        if (requiredHours == null) requiredHours = BigDecimal.ZERO;
        if (reportCount == null) reportCount = 0;
        if (workOrderCount == null) workOrderCount = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
