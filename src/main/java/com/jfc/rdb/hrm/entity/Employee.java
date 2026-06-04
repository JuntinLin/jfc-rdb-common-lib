package com.jfc.rdb.hrm.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
/**
 * Employee entity containing basic employee information
 */
@Data
@Entity
@Table(name = "Employee")
public class Employee {
	@Id
    private UUID employeeId; //*EmployeeId	員工ID	Guid
	
	@Column(name = "Code")
    private String employeeCode; //Code	員工工號	String
    private String cnName; //CnName	中文名稱	String
    
    @ManyToOne
    @JoinColumn(name = "DepartmentId")
    private Department department;
    
    @Column(name = "Date")
    private LocalDate hireDate; //Date	到職日期	DateTime
    private LocalDate lastWorkDate; //LastWorkDate	最後工作日	DateTime

    @Column(name = "DirectorId")
    private UUID directorId; //DirectorId	直接主管(Employee.EmployeeId)

    @Column(name = "JobId")
    private UUID jobId; //JobId	主要職位ID(Job.JobId)

    private Boolean flag; //Flag	是否有效	Boolean
}
