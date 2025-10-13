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

/*
 * 

TWALPlanInfo.TWALParaId	特休參數	String
TWALPlanInfo.TWALRuleId	特休制度	String
TWALPlanInfo.SeniorityDate	計年資日期	DateTime
TWALPlanInfo.SeniorityBeginDate	年資起算日期	DateTime
TWALPlanInfo.SeniorityActual	實際年資	Decimal
TWALPlanInfo.SeniorityPlan	計特休年資	Decimal

TWALPlanInfo.WholeYearDate	滿年度日期	DateTime
TWALPlanInfo.WholeYearAmount	滿年度日期核算量	Decimal
TWALPlanInfo.BalanceLastYear	上年結轉	Decimal
TWALPlanInfo.BalanceEndDate	結轉截止日期	DateTime
TWALPlanInfo.BalanceActual	結轉已休	Decimal
TWALPlanInfo.BalanceRemainder	結轉未休	Decimal
TWALPlanInfo.BalanceVoid	結轉作廢	Decimal

TWALPlanInfo.BalanceNextYear	結轉下年	Decimal
TWALPlanInfo.IsSpecial	特批	Boolean
TWALPlanInfo.IsBalance	已結轉	Boolean
TWALPlanInfo.Remark	備註	Text
TWALPlanInfo.Flag	識別字	Boolean
TWALPlanInfo.CreateDate	創建日期	DateTime
TWALPlanInfo.LastModifiedDate	最後修改日期	DateTime
TWALPlanInfo.CreateBy	創建者(資料來自User表的UserId欄位)	Guid
TWALPlanInfo.LastModifiedBy	最後修改人(資料來自User表的UserId欄位)	Guid
TWALPlanInfo.ChangeAmount	異動帶入	Decimal
TWALPlanInfo.Year	特休年度	Int32
TWALPlanInfo.IsBalanceSettlement	3天已結算	Boolean
TWALPlanInfo.IsTWALSettlement	今年已結算	Boolean
TWALPlanInfo.BalanceOverAmount	首年剩餘數量	Decimal
TWALPlanInfo.BalanceSettlementAmount	三天已結算數量	Decimal
TWALPlanInfo.TWALSettlementAmount	今年已結算數量	Decimal*/

@Data
@Entity
@Table(name = "TWALPlanInfo")
public class TWALPlanInfo {
	@Id
    private UUID twalPlanInfoId; //*TWALPlanInfo.TWALPlanInfoId	員工特休計畫ID	Guid
	
	@ManyToOne
    @JoinColumn(name = "TWALPlanId") 
    private TWALPlan twalPlan;//TWALPlanInfo.TWALPlanId	特休計畫ID(資料來自TWALPlan表的TWALPlanId欄位)	Guid
	
	@ManyToOne
    @JoinColumn(name = "FiscalYearId") 
    private FiscalYear fiscalYear; //TWALPlanInfo.FiscalYearId	財政年度ID(資料來自FiscalYear表的FiscalYearId欄位)	Guid	
	
	@ManyToOne
    @JoinColumn(name = "EmployeeId")
    private Employee employee; //TWALPlanInfo.EmployeeId	員工(資料來自Employee表的EmployeeId欄位)	Guid
	
	private LocalDate beginDate;    // TWALPlanInfo.BeginDate	起休日期	DateTime
    private LocalDate endDate;      // TWALPlanInfo.EndDate	截止日期	DateTime
	
	
    @Column(precision = 10, scale = 2)
    private BigDecimal Amount; //TWALPlanInfo.Amount	核算量	Decimal
    
    @ManyToOne
    @JoinColumn(name = "UnitId")
    private CodeInfo unit; //TWALPlanInfo.UnitId	核算單位(資料來自CodeInfo表的CodeInfoId欄位)	String
	
    @Column(precision = 10, scale = 2)
    private BigDecimal thisYearAmount; //TWALPlanInfo.ThisYearAmount	本年可休	Decimal
    
    @Column(precision = 10, scale = 2)
    private BigDecimal actualAmount; //TWALPlanInfo.ActualAmount	本年已休	Decimal
    
    @Column(precision = 10, scale = 2)
    private BigDecimal remainderAmount; //TWALPlanInfo.RemainderAmount	本年未休	Decimal
    
    private Integer year; //TWALPlanInfo.Year	特休年度	Int32
}
