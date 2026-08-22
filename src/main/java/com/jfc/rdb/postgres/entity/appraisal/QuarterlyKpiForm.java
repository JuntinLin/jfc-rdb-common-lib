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
@Table(name = "quarterly_kpi_form",
        uniqueConstraints = @UniqueConstraint(columnNames = {"period_id", "emp_no"}))
public class QuarterlyKpiForm {

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

    /** 直屬主管工號快照（產生批次時寫入，外網 ext 實例據此篩選直屬部屬） */
    @Column(name = "supervisor_emp_no", length = 20)
    private String supervisorEmpNo;

    /** 簽核鏈快照 JSON（產生批次時預解析，外網 ext 實例送簽時讀取，不連 HRM） */
    @Column(name = "approval_chain_json", columnDefinition = "TEXT")
    private String approvalChainJson;

    // 比重（從 dept_kpi_config 帶入，可覆寫）
    @Column(name = "dept_kpi_weight")
    private BigDecimal deptKpiWeight;

    @Column(name = "personal_kpi_weight")
    private BigDecimal personalKpiWeight;

    @Column(name = "supervisor_weight")
    private BigDecimal supervisorWeight;

    @Column(name = "proposal_weight")
    private BigDecimal proposalWeight;

    // 各構面原始分數（滿分 100）
    @Column(name = "dept_kpi_score")
    private BigDecimal deptKpiScore;

    @Column(name = "personal_kpi_score")
    private BigDecimal personalKpiScore;

    @Column(name = "supervisor_score")
    private BigDecimal supervisorScore;

    @Column(name = "proposal_score")
    private BigDecimal proposalScore;

    // 各構面加權後分數
    @Column(name = "dept_kpi_weighted")
    private BigDecimal deptKpiWeighted;

    @Column(name = "personal_kpi_weighted")
    private BigDecimal personalKpiWeighted;

    @Column(name = "supervisor_weighted")
    private BigDecimal supervisorWeighted;

    @Column(name = "proposal_weighted")
    private BigDecimal proposalWeighted;

    // 獎懲
    @Column(name = "reward_penalty_adj")
    private BigDecimal rewardPenaltyAdj;

    @Column(name = "reward_penalty_desc", columnDefinition = "TEXT")
    private String rewardPenaltyDesc;

    // 總分 = 四構面加權 + 獎懲
    @Column(name = "total_score")
    private BigDecimal totalScore;

    @Column(name = "grade", length = 5)
    private String grade;

    @Column(name = "grade_override", length = 5)
    private String gradeOverride;

    // 參考資訊
    @Column(name = "quality_issues")
    private Integer qualityIssues;

    @Column(name = "quality_loss")
    private BigDecimal qualityLoss;

    @Column(name = "customer_complaints")
    private Integer customerComplaints;

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
        if (customerComplaints == null) customerComplaints = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
