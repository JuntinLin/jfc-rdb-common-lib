package com.jfc.rdb.postgres.entity.pcn;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 會簽結果的查詢用副本。真相來源仍是 Flowable ACT_HI_*，此表只為列表頁快速顯示。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pcn_counter_sign_record")
public class PcnCounterSignRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "pcn_id", nullable = false)
    private UUID pcnId;

    @Column(name = "process_instance_id", nullable = false, length = 64)
    private String processInstanceId;

    /** LEAD_DESIGN_HEAD / LEAD_PRODUCTION_HEAD / LEAD_MATERIAL_HEAD / LEAD_QA_HEAD / LEAD_SALES_HEAD */
    @Column(name = "role_code", nullable = false, length = 30)
    private String roleCode;

    @Column(name = "emp_no", nullable = false, length = 20)
    private String empNo;

    @Column(name = "emp_name", length = 50)
    private String empName;

    /** AGREE / DISAGREE */
    @Column(name = "decision", length = 10)
    private String decision;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "signed_at")
    private LocalDateTime signedAt;
}
