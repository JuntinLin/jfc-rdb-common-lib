package com.jfc.rdb.postgres.entity.appraisal;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appraisal_supervisor_override")
public class AppraisalSupervisorOverride {

    @Id
    @Column(name = "emp_no", length = 20)
    private String empNo;

    @Column(name = "override_supervisor_emp_no", nullable = false, length = 20)
    private String overrideSupervisorEmpNo;

    @Column(name = "reason", length = 200)
    private String reason;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
