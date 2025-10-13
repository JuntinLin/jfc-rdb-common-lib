package com.jfc.rdb.hrm.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
/*
 * 欄位名稱	中文名稱	類型
*CodeInfoId	代碼資訊ID	String
KindCode	代碼組編碼	String
KindName	代碼組名稱	String
InfoCode	代碼值編碼	String
ScName	簡體中文代碼值	String
IsSystem	是否系統資料	Boolean
Remark	備註	Text
CorporationId	代碼項.公司ID(資料來自Corporation表的CorporationId欄位)	Guid
Flag	代碼項.是否有效	Boolean
CreateDate	代碼項.創建日期	DateTime
LastModifiedDate	代碼項.最後修改日期	DateTime
CreateBy	代碼項.創建者(資料來自User表的UserId欄位)	Guid
LastModifiedBy	代碼項.最後修改者(資料來自User表的UserId欄位)	Guid
OrderNumber	排列序號	Int32
Enabled	是否啟用	Boolean
AssignReason	代碼項.分配原因	String
OwnerId	代碼項.所有者ID(資料來自User表的UserId欄位)	String
IsESSSelect	IsESSSelect	Boolean
IsEmployeeContract	IsEmployeeContract	Boolean
*/

@Data
@Entity
@Table(name = "CodeInfo")
public class CodeInfo {
	@Id
    private String codeInfoId; //CodeInfoId	代碼資訊ID	String
	private String kindCode; //KindCode	代碼組編碼	String
	private String kindName; //KindName	代碼組名稱	String
	private String infoCode; //InfoCode	代碼值編碼	String
}
