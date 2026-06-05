package com.jfc.rdb.postgres.entity.appraisal;

import java.math.BigDecimal;
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
@Table(name = "annual_kpi_form")
public class AnnualKpiForm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "form_id")
    private UUID formId;

    @Column(name = "period_id", nullable = false)
    private UUID periodId;

    @Column(name = "emp_no", nullable = false, length = 20)
    private String empNo;

    @Column(name = "emp_name", length = 50)
    private String empName;

    @Column(name = "dept_code", length = 20)
    private String deptCode;

    @Column(name = "dept_name", length = 50)
    private String deptName;

    @Column(name = "job_title", length = 50)
    private String jobTitle;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "seniority")
    private BigDecimal seniority;

    @Column(name = "dept_kpi_score")
    private BigDecimal deptKpiScore;

    @Column(name = "personal_kpi_score")
    private BigDecimal personalKpiScore;

    @Column(name = "supervisor_score")
    private BigDecimal supervisorScore;

    @Column(name = "proposal_score")
    private BigDecimal proposalScore;

    @Column(name = "dept_kpi_weighted")
    private BigDecimal deptKpiWeighted;

    @Column(name = "personal_kpi_weighted")
    private BigDecimal personalKpiWeighted;

    @Column(name = "supervisor_weighted")
    private BigDecimal supervisorWeighted;

    @Column(name = "proposal_weighted")
    private BigDecimal proposalWeighted;

    @Column(name = "reward_penalty_adj")
    private BigDecimal rewardPenaltyAdj;

    @Column(name = "reward_penalty_desc", columnDefinition = "TEXT")
    private String rewardPenaltyDesc;

    @Column(name = "total_score")
    private BigDecimal totalScore;

    @Column(name = "grade", length = 5)
    private String grade;

    @Column(name = "quality_issues")
    private Integer qualityIssues;

    @Column(name = "quality_loss")
    private BigDecimal qualityLoss;

    @Column(name = "annual_leave_entitled")
    private BigDecimal annualLeaveEntitled;

    @Column(name = "annual_leave_unused")
    private BigDecimal annualLeaveUnused;

    @Column(name = "total_overtime")
    private BigDecimal totalOvertime;

    @Column(name = "proposal_count")
    private Integer proposalCount;

    @Column(name = "supervisor_comment", columnDefinition = "TEXT")
    private String supervisorComment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (rewardPenaltyAdj == null) rewardPenaltyAdj = BigDecimal.ZERO;
        if (qualityIssues == null) qualityIssues = 0;
        if (qualityLoss == null) qualityLoss = BigDecimal.ZERO;
        if (proposalCount == null) proposalCount = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
