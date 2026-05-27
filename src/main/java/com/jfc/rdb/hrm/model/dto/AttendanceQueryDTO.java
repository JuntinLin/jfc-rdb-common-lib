package com.jfc.rdb.hrm.model.dto;

import java.util.Date;

import lombok.Data;
@Data
public class AttendanceQueryDTO {
	private Date startDate;
    private Date endDate;
    private String employeeId;
    private String departmentId;
    private String corporationId;
    //2025-12-10 前端無須傳入attendanceTypeId, 由後端自行決定
    //private String attendanceTypeId; //907:單次未刷卡, 901:正常
}
