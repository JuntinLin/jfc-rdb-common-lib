package com.jfc.rdb.tiptop.model.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditLimitDetailDTO {
    private String documentType;     // 單據類型 (應收帳款/出貨單/訂單等)
    private String documentNumber;   // 單據編號
    private String documentDate;     // 單據日期
    private String dueDate;          // 到期日 (應收帳款)
    private String currency;         // 幣別
    private BigDecimal amount;       // 金額
    private String description;      // 描述
    private String status;           // 狀態
}
