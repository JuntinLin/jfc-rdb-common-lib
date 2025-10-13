package com.jfc.rdb.hrm.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
/**
 * Department entity containing department information
 */
@Data
@Entity
@Table(name = "Department")
public class Department {
	@Id
    private UUID departmentId; //*DepartmentId	部門ID	Guid
    
	@Column(name = "Code")
    private String departmentCode; //Code	部門編碼	String
    private String shortName; //ShortName	部門簡稱	String
    private String describe; //Describe	部門描述	Text
}
