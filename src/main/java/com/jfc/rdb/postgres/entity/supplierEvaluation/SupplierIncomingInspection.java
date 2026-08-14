package com.jfc.rdb.postgres.entity.supplierEvaluation;

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
@Table(name = "supplier_incoming_inspection")
public class SupplierIncomingInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "inspection_id")
    private UUID inspectionId;

    @Column(name = "inspection_date", nullable = false)
    private LocalDate inspectionDate;

    @Column(name = "customer_name", length = 50)
    private String customerName;

    @Column(name = "order_no", length = 100)
    private String orderNo;

    @Column(name = "spec", length = 200)
    private String spec;

    @Column(name = "part_name", length = 100)
    private String partName;

    @Column(name = "vendor_code", length = 20)
    private String vendorCode;

    @Column(name = "vendor_name", length = 50)
    private String vendorName;

    @Column(name = "incoming_qty")
    private BigDecimal incomingQty;

    @Column(name = "sample_qty")
    private BigDecimal sampleQty;

    @Column(name = "result", nullable = false, length = 2)
    private String result;   // OK / NG

    @Column(name = "inspector", length = 20)
    private String inspector;

    @Column(name = "rework_qty")
    private BigDecimal reworkQty;

    @Column(name = "issue_desc", length = 500)
    private String issueDesc;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "factory_return_date")
    private LocalDate factoryReturnDate;

    @Column(name = "closed", length = 1)
    private String closed;   // V=結案

    @Column(name = "remark", length = 500)
    private String remark;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

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
