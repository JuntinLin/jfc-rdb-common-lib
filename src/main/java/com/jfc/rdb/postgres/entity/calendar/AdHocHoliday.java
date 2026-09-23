package com.jfc.rdb.postgres.entity.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 天災停班等臨時性停止上班日期登錄（不定期發生，非固定行事曆假日）。
 * 供 WorkingDaysService 計算工作天數時排除，修正 HRM 企業行事曆未即時補登
 * 臨時停班公告（如颱風/豪雨）導致工作天數多算的問題。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_hoc_holiday")
public class AdHocHoliday {

    @Id
    @Column(name = "holiday_date")
    private LocalDate holidayDate;

    @Column(name = "reason", length = 200)
    private String reason;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_by", length = 20)
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (active == null) active = true;
        createdAt = LocalDateTime.now();
    }
}
