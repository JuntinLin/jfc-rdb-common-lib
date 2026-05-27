package com.jfc.rdb.tiptop.model.dto;

import java.math.BigDecimal;

import com.jfc.rdb.common.dto.AbstractDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class MonthlyCustomerStatDTO  extends AbstractDTO{
	private String customerCode; // 客戶編號
    private String customerName; // 客戶名稱
    private String statMonth;    // 統計月份 (YYYY-MM)
    private BigDecimal orderAmount = BigDecimal.ZERO;
    private BigDecimal shipmentAmount = BigDecimal.ZERO;
    private BigDecimal invoiceAmount = BigDecimal.ZERO;

    // 完整的建構子 (Constructor)
    public MonthlyCustomerStatDTO(String customerCode, String customerName, String statMonth) {
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.statMonth = statMonth;
    }
    
}
