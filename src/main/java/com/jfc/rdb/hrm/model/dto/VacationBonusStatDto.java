package com.jfc.rdb.hrm.model.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * DTO (Data Transfer Object) for vacation bonus statistics
 */
@Data
public class VacationBonusStatDto {
	private String departmentCode;
    private String departmentName;
    private int employeeCount;
    private BigDecimal acquiredUnusedHours;    // 已取得但未休的時數
    private BigDecimal unearnedHours;          // 未取得的特休時數
    private BigDecimal totalUnusedHours;       // 總未休時數
    private BigDecimal totalBonusAmount;       // 總獎金金額
    private BigDecimal averageBonusPerEmployee;// 平均每人獎金
}
