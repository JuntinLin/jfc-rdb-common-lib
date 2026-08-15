package com.jfc.rdb.postgres.entity.aps;

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
@Table(name = "aps_workstation_capacity")
public class ApsWorkstationCapacity {

    @Id
    @Column(name = "workstation_id", length = 20)
    private String workstationId;

    @Column(name = "daily_capacity", nullable = false)
    private Integer dailyCapacity;

    @Column(name = "efficiency", nullable = false)
    private BigDecimal efficiency;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = LocalDateTime.now();
        if (dailyCapacity == null) dailyCapacity = 480;
        if (efficiency == null) efficiency = new BigDecimal("0.85");
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
