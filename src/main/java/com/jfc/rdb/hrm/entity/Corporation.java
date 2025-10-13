package com.jfc.rdb.hrm.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
/*欄位名稱	中文名稱	類型
*
Share	所有權份額	Decimal
IndustryId	所屬行業ID(資料來自CodeInfo表的CodeInfoId欄位)	String
Introduction	公司簡介	Text
IsHoldingId	是否控股ID(資料來自CodeInfo表的CodeInfoId欄位)	String
CorpLogo	公司標示	Image
ReportLogo	報表標示	Image
Code	公司編碼	String
Flag	集團公司管理.是否有效	Boolean
ParentId	上級公司ID(資料來自Corporation表的CorporationId欄位)	String
OrderNumber	排序標識	Int32
CreateDate	集團公司管理.創建日期	DateTime
LastModifiedDate	集團公司管理.最後修改日期	DateTime
CreateBy	集團公司管理.創建者(資料來自User表的UserId欄位)	Guid
LastModifiedBy	集團公司管理.最後修改者(資料來自User表的UserId欄位)	Guid
Name	公司名稱	String
AssignReason	集團公司管理.分配原因	String
OwnerId	集團公司管理.所有者ID	String
OrgType	組織架構類型	String
IsVirtual	是否是虛擬公司	Boolean
RepealRemark	撤銷原因	Text
CountryId	國家或地區代碼(資料來自CodeInfo表的CodeInfoId欄位)	String
MasterRate	雇主提繳比例	Decimal
GeneralCode	統一編號	String
LaborInsurNo	勞工保險證號:	String
HousingTaxNo	房屋稅藉編號	String
LaborInsureTypeNo	勞工保險業別代號	String
BusinessTaxNo	營利事業稅藉編號	String
HealthUnitNo	健保投保單位代號	String
ChkNo	保險證號檢查碼	String
ChargePerson	負責人	String
DisplayLever	顯示層級	String
LevelCode	公司低階碼	Int32
FloorCode	公司階層碼	String
Principal	公司負責人(資料來自Employee表的EmployeeId欄位)	Guid
PrincipalJobId	公司負責人職位(資料來自Job表的JobId欄位)	Guid
ERPDB	ERP資料庫代碼	String
Ooag004	T100營運據點	String
WorkCenterCode	T100歸屬法人編號	String
SN	微信组织架构id	Int32
BeginEndDate_BeginDate	開始結束日期_開始日期	DateTime
BeginEndDate_EndDate	開始結束日期_結束日期	DateTime

 * */
@Data
@Entity
@Table(name = "Corporation")
public class Corporation {
	@Id
    private UUID corporationId; //*CorporationId	公司ID	Guid
	
	private String shortName; //ShortName	公司簡稱	String
	
	private String enName; //EnName	英文名稱	String
}
