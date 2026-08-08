package com.jfc.rdb.postgres.entity.appraisal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appraisal_form_version",
        uniqueConstraints = @UniqueConstraint(columnNames = {"form_id", "version"}))
public class AppraisalFormVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "version_id")
    private UUID versionId;

    @Column(name = "form_id", nullable = false)
    private UUID formId;

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "actor_emp_no", nullable = false, length = 100)
    private String actorEmpNo;

    @Column(name = "actor_name", length = 50)
    private String actorName;

    @Column(name = "action", nullable = false, length = 20)
    private String action; // SUBMIT, APPROVE, REJECT

    @Column(name = "task_name", length = 20)
    private String taskName; // 建立, 審核, 覆核, 核決

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "scores", columnDefinition = "jsonb", nullable = false)
    private String scores; // JSON array of score snapshots

    @Column(name = "total_score")
    private BigDecimal totalScore;

    @Column(name = "deduction")
    private BigDecimal deduction;

    @Column(name = "final_score")
    private BigDecimal finalScore;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
