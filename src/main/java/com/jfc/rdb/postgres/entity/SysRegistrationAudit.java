package com.jfc.rdb.postgres.entity;

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
@Table(name = "sys_registration_audit")
public class SysRegistrationAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Integer auditId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "applicant_username", length = 100)
    private String applicantUsername;

    @Column(name = "employee_no", nullable = false, length = 20)
    private String employeeNo;

    @Column(name = "employee_name", length = 100)
    private String employeeName;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "position", length = 100)
    private String position;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING";

    @Column(name = "reviewer", length = 50)
    private String reviewer;

    @Column(name = "review_note", length = 500)
    private String reviewNote;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
