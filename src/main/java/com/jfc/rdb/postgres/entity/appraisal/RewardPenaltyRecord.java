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
@Table(name = "reward_penalty_record")
public class RewardPenaltyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "record_id")
    private UUID recordId;

    @Column(name = "period_id")
    private UUID periodId;

    @Column(name = "emp_no", nullable = false, length = 20)
    private String empNo;

    @Column(name = "rp_type", nullable = false, length = 20)
    private String rpType;

    @Column(name = "rp_level", nullable = false, length = 20)
    private String rpLevel;

    @Column(name = "rp_count")
    private Integer rpCount;

    @Column(name = "score_adj")
    private BigDecimal scoreAdj;

    @Column(name = "record_date")
    private LocalDate recordDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "source", length = 20)
    private String source;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (rpCount == null) rpCount = 1;
        if (source == null) source = "HRM";
    }
}
