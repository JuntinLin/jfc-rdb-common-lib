package com.jfc.rdb.hrm.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class AttendanceStatisticsDTO {
	private String col1;              // 統計類型
    private String departmentCode;    // 部門代碼
    private String employeeCode;      // 工號
    private String departmentName;    // 部門名稱
    private String employeeName;      // 姓名
    private String itemName;          // 項目名稱
    private BigDecimal value;         // 值
    private String corporationName;   // 公司名稱
    private String period;            // 期間
    private Date date;                // 日期
    private String shift;             // 班別
    
    // Constructor
    public AttendanceStatisticsDTO() {}
}
