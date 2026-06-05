package com.jfc.rdb.postgres.entity.appraisal;

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
@Table(name = "proposal_record")
public class ProposalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "proposal_id")
    private UUID proposalId;

    @Column(name = "period_id")
    private UUID periodId;

    @Column(name = "emp_no", nullable = false, length = 20)
    private String empNo;

    @Column(name = "proposal_date")
    private LocalDate proposalDate;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = "SUBMITTED";
    }
}
