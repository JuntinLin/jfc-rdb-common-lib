package com.jfc.rdb.postgres.entity.supplierEvaluation;

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
@Table(name = "supplier_evaluation_roster")
public class SupplierEvaluationRoster {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "roster_id")
    private UUID rosterId;

    @Column(name = "period_id", nullable = false)
    private UUID periodId;

    @Column(name = "vendor_category", nullable = false, length = 10)
    private String vendorCategory;   // SUB(加工/委外) / REG(原料)

    @Column(name = "vendor_code", nullable = false, length = 20)
    private String vendorCode;

    @Column(name = "vendor_short_name", length = 50)
    private String vendorShortName;

    @Column(name = "vendor_type", length = 100)
    private String vendorType;

    @Column(name = "main_routing", length = 1000)
    private String mainRouting;

    @Column(name = "routing_transaction_count")
    private Integer routingTransactionCount;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (active == null) active = true;
        if (routingTransactionCount == null) routingTransactionCount = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
