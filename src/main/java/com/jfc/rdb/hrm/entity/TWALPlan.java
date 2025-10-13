package com.jfc.rdb.hrm.entity;

import java.util.UUID;

import com.jfc.rdb.tiptop.entity.OgbFile;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/*
 *欄位名稱	中文名稱	類型
*
CorporationId	公司ID(資料來自Corporation表的CorporationId欄位)	Guid


IsConfirm	是否歸檔	Boolean
Remark	備註	String
Flag	是否有效	Boolean
CreateDate	創建日期	DateTime
LastModifiedDate	最後修改日期	DateTime
CreateBy	創建者(資料來自User表的UserId欄位)	Guid
LastModifiedBy	最後修改人(資料來自User表的UserId欄位)	Guid
AssignReason	分配原因	String
OwnerId	擁有者ID(資料來自User表的UserId欄位)	String
Year	特休年度	Int32
IsAutosettlement	過期特休自動轉結算作業	Boolean
IsAutosettlementBalance	過期特休自動轉結算作業	Boolean

 */
@Data
@Entity
@Table(name = "TWALPlan")
public class TWALPlan {
	@Id
    private UUID twalPlanId; //*TWALPlanId	特休計劃ID	Guid
    
    @ManyToOne
    @JoinColumn(name = "FiscalYearId") 
    private FiscalYear fiscalYear; //FiscalYearId	財政年度ID(資料來自FiscalYear表的FiscalYearId欄位)	Guid
    
    private String code; //Code	特休計畫編碼	String
    private String name; //Name	特休計畫名稱	String
    
 // 添加反向關係（如果需要）
    @OneToMany(mappedBy = "twalPlan", fetch = FetchType.LAZY)
    private java.util.List<TWALPlanInfo> twalPlanInfoList;
    
    private Integer year; //Year	特休年度	Int32

}
