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



Remark	備註	Text
IsConfirm	是否歸檔	Boolean
FoundOperationDate	制訂操作日期	DateTime
FoundUserId	制訂操作人ID(資料來自User表的UserId欄位)	Guid
ConfirmOperationDate	歸檔操作日期	DateTime
ConfirmUserId	歸檔操作人ID(資料來自User表的UserId欄位)	Guid
CorporationId	所屬公司ID(資料來自Corporation表的CorporationId欄位)	Guid
IsBalance	是否結餘過	Boolean
ShortName	計畫簡稱	String
Code	計畫編碼	String
CreateDate	年假計畫.創建日期	DateTime
LastModifiedDate	年假計畫.最後修改日期	DateTime
CreateBy	年假計畫.創建者(資料來自User表的UserId欄位)	Guid
LastModifiedBy	年假計畫.最後修改者(資料來自User表的UserId欄位)	Guid
Name	計畫名稱	String
Flag	年假計畫.是否有效	Boolean
AssignReason	年假計畫.分配原因	String
OwnerId	年假計畫.所有者ID(資料來自User表的UserId欄位)	String
IsCaculate	是否計算	Boolean
WorkYearEnd	年資截止日期	DateTime
ApproveEmployeeId	審核人ID(資料來自Employee表的EmployeeId欄位)	Guid
ApproveEmployeeName	審核人名稱	String
ApproveRemark	審核批註	Text
ApproveOperationDate	審核操作日期	DateTime
ApproveUserId	審核操作人ID(資料來自User表的UserId欄位)	Guid
RepealOperationDate	撤銷操作日期	DateTime
RepealUserId	撤銷操作人ID(資料來自User表的UserId欄位)	Guid
ApproveResultId	審核結果ID(資料來自CodeInfo表的CodeInfoId欄位)	String
SubmitUserId	提交操作人ID(資料來自User表的UserId欄位)	Guid
SubmitOperationDate	提交操作日期	DateTime
StateId	狀態ID(資料來自CodeInfo表的CodeInfoId欄位)	String
ApproveDate	審核日期	DateTime


 */

@Data
@Entity
@Table(name = "AnnualLeavePlan")
public class AnnualLeavePlan {
	@Id
    private UUID annualLeavePlanId; //*AnnualLeavePlanId	年假計畫ID	Guid
    
    @ManyToOne
    @JoinColumn(name = "FiscalYearId") 
    private FiscalYear fiscalYear; //FiscalYearId	計畫年度ID(資料來自FiscalYear表的FiscalYearId欄位)	Guid
    
    
    
    
    
    
}
