package com.jfc.rdb.hrm.model.dto;

import lombok.Data;

@Data
public class AttendanceRateDTO {
    private String departmentCode;
    private String departmentName;
    private String employeeCode;
    private String employeeName;
    private String corporationName;
    private double requiredHours;
    private double actualHours;
    private double attendanceRate;
    private String period;
}
