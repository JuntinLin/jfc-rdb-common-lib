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
}
