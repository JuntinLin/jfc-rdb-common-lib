package com.jfc.rdb.postgres.entity.appraisal;

import java.math.BigDecimal;
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
@Table(name = "kpi_target")
public class KpiTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "target_id")
    private UUID targetId;

    @Column(name = "kpi_id", nullable = false)
    private UUID kpiId;

    @Column(name = "emp_no", length = 20)
    private String empNo;

    @Column(name = "target_value")
    private BigDecimal targetValue;

    @Column(name = "last_year_value")
    private BigDecimal lastYearValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
