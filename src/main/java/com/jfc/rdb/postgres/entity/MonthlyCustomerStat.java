package com.jfc.rdb.postgres.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monthly_customer_stat")
public class MonthlyCustomerStat {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_code", nullable = false, length = 50)
    private String customerCode; // 客戶編號

    @Column(name = "customer_name", length = 200)
    private String customerName; // 客戶名稱

    @Column(name = "stat_month", nullable = false, length = 7)
    private String statMonth; // 統計月份 (YYYY-MM)

    @Column(name = "order_amount", precision = 18, scale = 2)
    private BigDecimal orderAmount = BigDecimal.ZERO; // 訂單金額

    @Column(name = "shipment_amount", precision = 18, scale = 2)
    private BigDecimal shipmentAmount = BigDecimal.ZERO; // 出貨金額

    @Column(name = "invoice_amount", precision = 18, scale = 2)
    private BigDecimal invoiceAmount = BigDecimal.ZERO; // 發票金額

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 三參數建構子
    public MonthlyCustomerStat(String customerCode, String customerName, String statMonth) {
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.statMonth = statMonth;
    }

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
