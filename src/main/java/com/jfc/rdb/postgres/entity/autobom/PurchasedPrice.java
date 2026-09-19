package com.jfc.rdb.postgres.entity.autobom;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RFQ⑤-A 市購件 price book——推導自 Tiptop PMN 實際採購價，取代 QuotationService
 * 現行 flat-100 佔位值。見 docs/RFQ/⑤-A市購件price-book_task_brief.md。
 *
 * normalized_key 非空 = Tier1 精準鍵列（如螺帽 M12xP1.5）；
 * normalized_key 為空字串 = 該 category 的 Tier2 彙總列（類別中位數）。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autobom_purchased_price")
public class PurchasedPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "normalized_key", nullable = false, length = 100)
    private String normalizedKey;

    @Column(name = "weighted_avg_price", precision = 14, scale = 4)
    private BigDecimal weightedAvgPrice;

    @Column(name = "median_price", precision = 14, scale = 4)
    private BigDecimal medianPrice;

    @Column(name = "sample_count", nullable = false)
    private Integer sampleCount;

    @Column(name = "data_as_of")
    private LocalDateTime dataAsOf;

    @Column(name = "stale", nullable = false)
    private Boolean stale;

    @Column(name = "source_part_numbers", length = 500)
    private String sourcePartNumbers;

    @Column(name = "refreshed_at")
    private LocalDateTime refreshedAt;

    @PrePersist
    protected void onCreate() {
        refreshedAt = LocalDateTime.now();
    }
}
