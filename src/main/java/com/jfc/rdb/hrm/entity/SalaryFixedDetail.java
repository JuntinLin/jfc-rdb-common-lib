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
/*欄位名稱	中文名稱	類型
 * 
SalaryFixedDetail.Remark	員工固定薪資明細.備註	Text
SalaryFixedDetail.SalaryFixedId	員工固定薪資明細.主表ID 申請單編號	Guid
SalaryFixedDetail.SalaryAdjustForEmpId	员工定调薪Id	Guid

 */
@Data
@Entity
@Table(name = "SalaryFixedDetail")
public class SalaryFixedDetail {
	@Id
    private UUID salaryFixedDetailId; //SalaryFixedDetail.SalaryFixedDetailId	員工固定薪資明細.員工固定薪資明細id	Guid
	@ManyToOne
    @JoinColumn(name = "SalaryKeyId")
	private SalaryKey salaryKey; //SalaryFixedDetail.SalaryKeyId	員工固定薪資明細.穩定薪資項參數(資料來自SalaryKey表的SalaryKeyId欄位)	String
	
	@Column(precision = 15, scale = 2)
	private BigDecimal keyValue; //SalaryFixedDetail.KeyValue	員工固定薪資明細.穩定薪資項參數值	Decimal
	
	@ManyToOne
    @JoinColumn(name = "EmployeeId")
	private Employee employee; //SalaryFixedDetail.EmployeeId	員工固定薪資明細.員工(資料來自Employee表的EmployeeId欄位)	Guid
	
	private Boolean inure; //SalaryFixedDetail.IsInure	員工固定薪資明細.是否生效	Boolean
	private LocalDate beginDate; //SalaryFixedDetail.BeginDate	員工固定薪資明細.生效日期	DateTime
	private LocalDate endDate; //SalaryFixedDetail.EndDate	員工固定薪資明細.失效日期	DateTime
}
