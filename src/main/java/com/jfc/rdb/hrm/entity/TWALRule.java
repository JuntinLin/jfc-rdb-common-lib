package com.jfc.rdb.hrm.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
/*
 *欄位名稱	中文名稱	類型

Code	特休制度編碼	String
Name	特休制度名稱	String
IsCorporation	是否公司級制度	Boolean
Remark	備註	String
Flag	識別字	Boolean
CreateDate	創建日期	DateTime
LastModifiedDate	最後修改日期	DateTime
CreateBy	創建者(資料來自User表的UserId欄位)	Guid
LastModifiedBy	最後修改人(資料來自User表的UserId欄位)	Guid
AssignReason	分配原因	String
OwnerId	擁有者ID(資料來自User表的UserId欄位)	String

*/
@Data
@Entity
@Table(name = "TWALRule")
public class TWALRule {
	@Id
    private UUID twalRuleId; //*TWALRuleId	特休制度ID	Guid
	
	@ManyToOne
    @JoinColumn(name = "CorporationId")
    private Corporation corporation; //CorporationId	公司ID(資料來自Corporation表的CorporationId欄位)	Guid
	
	private String annualLeaveRuleCode; //Code	特休制度編碼	String
	private String annualLeaveRuleName; //Name	特休制度名稱	String
}
