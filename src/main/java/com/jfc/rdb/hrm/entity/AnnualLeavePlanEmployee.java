package com.jfc.rdb.hrm.entity;

import java.math.BigDecimal;
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
 * 年假計劃實體類
 * 欄位名稱	中文名稱	類型
 * 



AnnualLeavePlanEmployee.AnnualLeaveUnit	員工年假表.核算單位(資料來自CodeInfo表的CodeInfoId欄位)	String
AnnualLeavePlanEmployee.Date	員工年假表.計算截至日期	DateTime
AnnualLeavePlanEmployee.BeginDate	員工年假表.起休日期	DateTime
AnnualLeavePlanEmployee.EndDate	員工年假表.截至日期	DateTime
AnnualLeavePlanEmployee.IsSpecial	員工年假表.是否特批	Boolean
AnnualLeavePlanEmployee.IsConfirm	員工年假表.是否歸檔	Boolean
AnnualLeavePlanEmployee.Remark	員工年假表.備註	Text
AnnualLeavePlanEmployee.CreateDate	員工年假表.創建日期	DateTime
AnnualLeavePlanEmployee.LastModifiedDate	員工年假表.最後修改日期	DateTime
AnnualLeavePlanEmployee.CreateBy	員工年假表.創建者(資料來自User表的UserId欄位)	Guid
AnnualLeavePlanEmployee.LastModifiedBy	員工年假表.最後修改者(資料來自User表的UserId欄位)	Guid
AnnualLeavePlanEmployee.Flag	員工年假表.是否有效	Boolean
AnnualLeavePlanEmployee.CorporationId	員工年假表.公司ID(資料來自Corporation表的CorporationId欄位)	Guid
AnnualLeavePlanEmployee.WorkingAgeBeginDate	員工年假表.年資起算日期	DateTime
AnnualLeavePlanEmployee.LegalDays	員工年假表.法定年假天數	Decimal
AnnualLeavePlanEmployee.WelfareDays	員工年假表.福利年假天數	Decimal
 * */

@Data
@Entity
@Table(name = "AnnualLeavePlanEmployee")
public class AnnualLeavePlanEmployee {
	@Id
    private UUID annualLeavePlanEmployeeId; //*AnnualLeavePlanEmployee.AnnualLeavePlanEmployeeId	員工年假表.員工年假ID	Guid
	
	@ManyToOne
    @JoinColumn(name = "FiscalYearId") 
    private FiscalYear fiscalYear; //AnnualLeavePlanEmployee.FiscalYearId	員工年假表.財政年度ID(資料來自FiscalYear表的FiscalYearId欄位)	Guid
	
	@ManyToOne
    @JoinColumn(name = "EmployeeId")
    private Employee employee; //AnnualLeavePlanEmployee.EmployeeId	員工年假表.員工ID(資料來自Employee表的EmployeeId欄位)	Guid
	
	@Column(precision = 10, scale = 2)
    private BigDecimal allocatedDays; // 分配的特休天數 AnnualLeavePlanEmployee.Days	員工年假表.年假天數	Decimal
    
    @Column(precision = 10, scale = 2)
    private BigDecimal usedDays;     // 已使用的特休天數

}
