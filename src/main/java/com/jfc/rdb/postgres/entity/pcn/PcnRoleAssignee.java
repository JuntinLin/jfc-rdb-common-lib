package com.jfc.rdb.postgres.entity.pcn;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色 → 目前工號 對照表。日後人事異動只改資料不改程式。
 * roleCode: LEAD_DESIGN_HEAD / LEAD_PRODUCTION_HEAD / LEAD_MATERIAL_HEAD / LEAD_QA_HEAD /
 *           LEAD_SALES_HEAD / DECISION_MAKER / QA_VERIFIER / CLOSER
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pcn_role_assignee")
public class PcnRoleAssignee {

    @Id
    @Column(name = "role_code", length = 30)
    private String roleCode;

    @Column(name = "emp_no", nullable = false, length = 20)
    private String empNo;

    @Column(name = "emp_name", length = 50)
    private String empName;

    /** Email 通知收件地址（無既有工號↔Email 對照來源，比照其他排程郵件功能人工維護） */
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        updatedAt = LocalDateTime.now();
    }
}
