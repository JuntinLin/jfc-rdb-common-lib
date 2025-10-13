package com.jfc.rdb.hrm.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
/**
 * Fiscal Year entity for tracking annual periods
 * 欄位名稱	中文名稱	類型

Year	年度	Int32
OrderNumber	排序標識	Int32

CorporationId	財政年度.公司ID(資料來自Corporation表的CorporationId欄位)	Guid
CreateDate	財政年度.創建日期	DateTime
LastModifiedDate	財政年度.最後修改日期	DateTime
CreateBy	財政年度.創建者(資料來自User表的UserId欄位)	Guid
LastModifiedBy	財政年度.最後修改者(資料來自User表的UserId欄位)	Guid
AssignReason	財政年度.分配原因	String
OwnerId	財政年度.所有者ID(資料來自User表的UserId欄位)	String



 */
@Data
@Entity
@Table(name = "FiscalYear")
public class FiscalYear {
	@Id
    private UUID fiscalYearId; //*FiscalYearId	財政年度ID	Guid
    
    private Integer year;//Year	年度	Int32
    @Column(name = "BeginEndDate_BeginDate")
    private LocalDate BeginDate; //BeginEndDate_BeginDate	開始結束日期_開始日期	DateTime
    @Column(name = "BeginEndDate_EndDate")
    private LocalDate EndDate;  //BeginEndDate_EndDate	開始結束日期_結束日期	DateTime
    private Boolean flag; //Flag	財政年度.是否有效	Boolean
}
