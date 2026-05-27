package com.jfc.rdb.hrm.entity;

import java.time.LocalDate;
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
    
    @Column(name = "Name")
    private String name;                // 部門名稱
    
    @Column(name = "ParentId")
    private String parentId;            // 上級部門ID (資料來自Department表)
    
    @Column(name = "Type")
    private Integer type;               // 部門類型
    
    @Column(name = "Flag")
    private Boolean flag;               // 是否有效
    
    @Column(name = "DeptLevel")
    private Integer deptLevel;          // 部門層級
    
    @Column(name = "OrderNumber")
    private Integer orderNumber;        // 部門描述/排序
    
    @Column(name = "IsERP")
    private Boolean isERP;              // 是否導入ERP
    
    @Column(name = "CreateDate")
    private LocalDate createDate;   // 創建日期
    
    @Column(name = "LastModifiedDate")
    private LocalDate lastModifiedDate; // 最後修改日期
}
