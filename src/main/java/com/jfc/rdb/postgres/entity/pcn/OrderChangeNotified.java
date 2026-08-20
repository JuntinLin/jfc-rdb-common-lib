package com.jfc.rdb.postgres.entity.pcn;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 訂單變更通知追蹤（納入 PCN 變更管理，訂單變更通知 Phase 1）。
 * 對應 Tiptop OEP_FILE 的 (OEP01=訂單號, OEP02=變更序號)，記錄是否已通知過、通知了哪些部門，
 * 避免重複通知；狀態變 WITHDRAWN 時代表已補發撤銷通知。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_change_notified", uniqueConstraints = @UniqueConstraint(columnNames = {"order_no", "change_seq"}))
public class OrderChangeNotified {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no", nullable = false, length = 20)
    private String orderNo;

    @Column(name = "change_seq", nullable = false)
    private BigDecimal changeSeq;

    @Column(name = "notified_at", nullable = false)
    private LocalDateTime notifiedAt;

    /** 逗號分隔的 pcn_role_assignee.role_code 清單；空字串代表判定為不需通知 */
    @Column(name = "notified_depts", nullable = false, length = 200)
    private String notifiedDepts;

    @Column(name = "changed_summary", length = 1000)
    private String changedSummary;

    /** NOTIFIED / NO_ACTION_NEEDED / WITHDRAWN */
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "withdrawn_at")
    private LocalDateTime withdrawnAt;

    @PrePersist
    protected void onCreate() {
        if (notifiedAt == null) notifiedAt = LocalDateTime.now();
    }
}
