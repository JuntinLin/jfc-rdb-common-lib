package com.jfc.rdb.hrm.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

/**
 * DTO for employee hourly wage information
 */
@Data
public class EmployeeWageDto {
	private UUID employeeId;
	private String employeeCode;
	private String employeeName;
	private BigDecimal hourlyWage; // 時薪
	private LocalDate hireDate; // 到職日期
	private int annualLeaveEntitlement; // 年資應休特休假天數
}
