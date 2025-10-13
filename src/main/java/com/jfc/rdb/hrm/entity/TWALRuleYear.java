package com.jfc.rdb.hrm.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
/*
 * 欄位名稱	中文名稱	類型
TWALRuleYear.Remark	Remark	String
TWALRuleYear.Flag	識別字	Boolean
TWALRuleYear.CreateDate	創建日期	DateTime
TWALRuleYear.LastModifiedDate	最後修改日期	DateTime
TWALRuleYear.CreateBy	創建者(資料來自User表的UserId欄位)	Guid
TWALRuleYear.LastModifiedBy	最後修改人(資料來自User表的UserId欄位)	Guid

*/
@Data
@Entity
@Table(name = "TWALRuleYear")
public class TWALRuleYear {
	@Id
    private UUID twalRuleYearId; //TWALRuleYear.TWALRuleYearId	TWALRuleMonthId	Guid
	
	@ManyToOne
    @JoinColumn(name = "TWALRuleId")
    private TWALRule twalRule; //TWALRuleYear.TWALRuleId	TWALRuleId(資料來自TWALRule表的TWALRuleId欄位)	Guid
	
	private Integer workYear; //TWALRuleYear.Year	工作年限	Int32
	private Integer annualLeaveDays; //TWALRuleYear.Days	特休天數	Decimal

}
