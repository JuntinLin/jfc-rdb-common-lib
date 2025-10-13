package com.jfc.rdb.tiptop.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgingAnalysisDTO {
	private String customerCode;      // oma03
    private String customerName;      // oma032   
    private String salesmanCode; 
    private String salesmanName;
    private BigDecimal totalAmount;   // SUM(oma56t)
    private BigDecimal total30Days;   // 0-30天
    private BigDecimal total60Days;   // 31-60天
    private BigDecimal total120Days;  // 61-120天
    private BigDecimal total180Days;  // 121-180天
    private BigDecimal total365Days;  // 181-365天
    private BigDecimal total545Days;  // 366-545天
    private BigDecimal totalOver546Days; // >545天
    private String sendEmailFlag;     // occud05 1st code,用於判斷是否寄送逾期催收email    
}
