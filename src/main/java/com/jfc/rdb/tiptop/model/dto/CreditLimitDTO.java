package com.jfc.rdb.tiptop.model.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditLimitDTO {
    private String customerCode;        // 客戶代號
    private String customerName;        // 客戶名稱
    private String creditCurrency;      // 信用幣別
    private BigDecimal creditLimit;     // 信用額度
    private BigDecimal thisMonthUnshippedOrders; // 訂單未出貨, 約定交貨日在本月底以前
    private BigDecimal receivables;     // 應收帳款
    private BigDecimal unprocessedShipments; // 出貨未轉應收金額
    private BigDecimal shipmentNotices; // 出貨通知單金額
    private BigDecimal unshippedOrders; // 訂單未轉出貨金額
    private BigDecimal creditBalance;   // 信用餘額
    private String salesmanCode;        // 業務員代號
    private String salesmanName;        // 業務員姓名
    private boolean hasSufficientCredit; // 信用餘額是否足夠
}