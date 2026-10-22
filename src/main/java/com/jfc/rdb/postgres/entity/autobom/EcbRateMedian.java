package com.jfc.rdb.postgres.entity.autobom;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RFQ㉑ 加工費 ECB 費率 NULL 兜底——同工序代號(shb081)歷史有效費率中位數，推導自 Tiptop
 * ECB_FILE/SHB_FILE/SFB_FILE（全系統一次 GROUP BY）→ 寫我方 PostgreSQL 自有表
 * `autobom_ecb_rate_median`。取代原本每次 precise-estimate/rough-estimate 請求都即時
 * GROUP BY 全系統（5~16s→30~60s，正式站已實測逾時）的做法，查詢時改 O(1) 查表。
 * 見 docs/RFQ/㉑_成本引擎收尾_回退+案7bStep0+收手_brief.md。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autobom_ecb_rate_median")
public class EcbRateMedian {

    /** 工序代號（shb081，如 T07-A2），語意工序碼，非序號 */
    @Id
    @Column(name = "opcode", length = 50)
    private String opcode;

    /** 全系統同工序代號有效費率(ecb48>0)中位數 */
    @Column(name = "median_rate", precision = 14, scale = 4)
    private BigDecimal medianRate;

    @Column(name = "sample_count", nullable = false)
    private Integer sampleCount;

    @Column(name = "refreshed_at")
    private LocalDateTime refreshedAt;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        refreshedAt = LocalDateTime.now();
    }
}
