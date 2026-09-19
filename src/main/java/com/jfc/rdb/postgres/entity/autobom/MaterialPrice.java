package com.jfc.rdb.postgres.entity.autobom;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RFQ⑤-C 原料 NT/kg 牌號別定價——推導自 Tiptop PMN 實際採購價，取代 QuotationService
 * 現行 MACHINED 件寫死 體積×7.85×30 的公式。見 docs/RFQ/⑤-C材質NTperKG_build_task_brief.md。
 *
 * grade 非空 = Tier1 牌號精準列（如 S45C）；grade 為空字串 = 該 category 的 Tier2 大類彙總列。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autobom_material_price")
public class MaterialPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 大類代碼：CARBON_ALLOY_STEEL/STAINLESS_AUSTENITIC/STAINLESS_MARTENSITIC/ALUMINUM/BRASS */
    @Column(name = "category", nullable = false, length = 50)
    private String category;

    /** 牌號代碼（S45C/SUS304/...）；空字串代表該 category 的 Tier2 彙總列 */
    @Column(name = "grade", nullable = false, length = 50)
    private String grade;

    /** 密度種子常數 g/cm³（非 ERP 查得，已核對業界標準值） */
    @Column(name = "density", precision = 6, scale = 3, nullable = false)
    private BigDecimal density;

    /** 主值：中位數 NT/kg（穩健，對離群/鋁管棒混雜免拆） */
    @Column(name = "nt_per_kg", precision = 14, scale = 4)
    private BigDecimal ntPerKg;

    /** 參考值：IQR 去極值後加權均價 */
    @Column(name = "weighted_avg", precision = 14, scale = 4)
    private BigDecimal weightedAvg;

    @Column(name = "sample_count", nullable = false)
    private Integer sampleCount;

    /** 用到的採購紀錄裡最新一筆的單頭日期，供「資料時點」誠實顯示 */
    @Column(name = "data_as_of")
    private LocalDateTime dataAsOf;

    /** 樣本不足或資料過舊時 true（見 brief：sample_count < min-samples 或 data_as_of 過舊） */
    @Column(name = "stale", nullable = false)
    private Boolean stale;

    /** 追溯用：抽樣幾筆真實料號，供人工核對 */
    @Column(name = "source_note", length = 500)
    private String sourceNote;

    @Column(name = "refreshed_at")
    private LocalDateTime refreshedAt;

    @PrePersist
    protected void onCreate() {
        refreshedAt = LocalDateTime.now();
    }
}
