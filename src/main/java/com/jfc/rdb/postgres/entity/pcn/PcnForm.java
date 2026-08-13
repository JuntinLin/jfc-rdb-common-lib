package com.jfc.rdb.postgres.entity.pcn;

import java.time.LocalDate;
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
@Table(name = "pcn_form")
public class PcnForm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pcn_id")
    private UUID pcnId;

    @Column(name = "pcn_no", unique = true, length = 20)
    private String pcnNo;

    @Column(name = "spec", length = 100)
    private String spec;

    @Column(name = "order_no", length = 50)
    private String orderNo;

    @Column(name = "material_no", length = 50)
    private String materialNo;

    @Column(name = "product_name", length = 100)
    private String productName;

    @Column(name = "apply_dept_code", nullable = false, length = 20)
    private String applyDeptCode;

    @Column(name = "apply_emp_no", nullable = false, length = 20)
    private String applyEmpNo;

    @Column(name = "apply_date")
    private LocalDate applyDate;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    /** JSON: {productDesign,drawingRevision,specChange,materialChange,bomChange,customerRequest,
     *         processChange,equipmentChange,fixtureChange,processConditionChange,weldingConditionChange,other,otherText} */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "change_type_flags", columnDefinition = "JSONB")
    private String changeTypeFlags;

    @Column(name = "change_before", columnDefinition = "TEXT")
    private String changeBefore;

    @Column(name = "change_after", columnDefinition = "TEXT")
    private String changeAfter;

    /** JSON: {customerRequest,qualityImprovement,costReduction,capacityImprovement,
     *         processImprovement,complaintImprovement,other,otherText} */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "change_reason_flags", columnDefinition = "JSONB")
    private String changeReasonFlags;

    @Column(name = "risk_assembly", length = 10)
    private String riskAssembly;

    @Column(name = "risk_appearance", length = 10)
    private String riskAppearance;

    @Column(name = "risk_function", length = 10)
    private String riskFunction;

    @Column(name = "notify_customer")
    private Boolean notifyCustomer;

    @Column(name = "trial_required")
    private Boolean trialRequired;

    @Column(name = "verification_result", length = 10)
    private String verificationResult;

    @Column(name = "verification_comment", columnDefinition = "TEXT")
    private String verificationComment;

    @Column(name = "effective_batch_wo", length = 100)
    private String effectiveBatchWo;

    /** JSON: {drawing,bom,sop,other,otherText} */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "doc_update_flags", columnDefinition = "JSONB")
    private String docUpdateFlags;

    @Column(name = "inventory_disposal", length = 20)
    private String inventoryDisposal;

    @Column(name = "close_note", columnDefinition = "TEXT")
    private String closeNote;

    @Column(name = "process_instance_id", length = 64)
    private String processInstanceId;

    @Column(name = "created_by", nullable = false, length = 20)
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (notifyCustomer == null) notifyCustomer = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
