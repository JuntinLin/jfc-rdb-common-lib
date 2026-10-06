package com.jfc.rdb.postgres.entity.gps;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上班時間同一地點停留超過門檻的歷史事件（總經理交辦，2026-09-30）。
 * 由 GpsStationaryAlertService 在停留狀態結束時寫入（事件已結束才入庫，不收錄進行中的停留）。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gps_stationary_event")
public class GpsStationaryEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_serial_number", length = 30, nullable = false)
    private String deviceSerialNumber;

    @Column(name = "car_name", length = 50)
    private String carName;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
