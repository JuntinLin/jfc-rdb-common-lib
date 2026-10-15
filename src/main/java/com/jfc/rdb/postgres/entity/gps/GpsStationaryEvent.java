package com.jfc.rdb.postgres.entity.gps;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上班時間同一地點停留超過門檻的歷史事件（總經理交辦，2026-09-30）。
 * 停留一旦跨過門檻（如30分鐘）才開始入庫，之後每次輪詢更新同一筆（ongoing=true，
 * end_time/duration_minutes持續往前推進），停留結束才標記 ongoing=false 定案。
 * 2026-10-06 改為進行中就持續更新，不再只在結束時一次寫入——後端重啟時可從
 * ongoing=true 的那筆資料接續計時，不會把進行中的停留時長歸零重算（總經理交辦）。
 * 2026-10-12 新增 workMinutes：durationMinutes 是牆鐘時間（含下班/過夜），workMinutes
 * 是跟上班時段（工作日×上班時段，扣午休）重疊的分鐘數——門檻判定、Email、頁面顯示的
 * 「已停留」都用 workMinutes，durationMinutes 只保留「實際停了多久」這個事實供參考。
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

    /** 跟上班時段重疊的分鐘數（扣掉下班/過夜），門檻判定與顯示的「已停留」都用這個。 */
    @Column(name = "work_minutes")
    private Integer workMinutes;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** true=停留仍在進行中、每次輪詢會更新這筆；false=已結束定案。 */
    @Column(name = "ongoing", nullable = false)
    private Boolean ongoing;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
