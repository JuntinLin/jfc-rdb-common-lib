package com.jfc.rdb.hrm.model.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PunchDetailDTO {
	private UUID attendanceRollcallCollectId;
    private UUID employeeId;
    private String employeeCode;
    private String employeeName;
    private Date punchTime;
    private String machineName;
    private String machineLocation;
    private Boolean isManual;
    private String remark;
}
