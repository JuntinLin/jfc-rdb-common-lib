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
@Table(name = "appraisal_form")
public class AppraisalForm {

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

    @Column(name = "actual_hours")
    private BigDecimal actualHours;

    @Column(name = "required_hours")
    private BigDecimal requiredHours;

    @Column(name = "overtime_hours")
    private BigDecimal overtimeHours;

    @Column(name = "annual_leave")
    private BigDecimal annualLeave;

    @Column(name = "sick_leave")
    private BigDecimal sickLeave;

    @Column(name = "personal_leave")
    private BigDecimal personalLeave;

    @Column(name = "other_leave")
    private BigDecimal otherLeave;

    @Column(name = "late_count")
    private Integer lateCount;

    @Column(name = "reward_penalty", columnDefinition = "TEXT")
    private String rewardPenalty;

    @Column(name = "total_score")
    private BigDecimal totalScore;

    @Column(name = "deduction")
    private BigDecimal deduction;

    @Column(name = "final_score")
    private BigDecimal finalScore;

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
        if (lateCount == null) lateCount = 0;
        if (deduction == null) deduction = BigDecimal.ZERO;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
