package com.jfc.rdb.postgres.entity.autobom;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autobom_quotation")
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quotation_number", nullable = false, unique = true, length = 50)
    private String quotationNumber;

    @Column(name = "customer_name", length = 200)
    private String customerName;

    @Column(name = "product_name", length = 200)
    private String productName;

    @Column(name = "original_file_name", length = 255)
    private String originalFileName;

    @Column(name = "total_material_cost", precision = 14, scale = 2)
    private BigDecimal totalMaterialCost;

    @Column(name = "total_machining_cost", precision = 14, scale = 2)
    private BigDecimal totalMachiningCost;

    @Column(name = "total_purchased_cost", precision = 14, scale = 2)
    private BigDecimal totalPurchasedCost;

    @Column(name = "total_price", precision = 14, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
