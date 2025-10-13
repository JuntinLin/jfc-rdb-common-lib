package com.jfc.rdb.hrm.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
/*欄位名稱	中文名稱	類型
*
SalaryKeyBigTypeId	參數類別Id(資料來自CodeInfo表的CodeInfoId欄位)	String
SalaryKeyTypeId	參數類型Id(資料來自CodeInfo表的CodeInfoId欄位)	String

ExternInfo	擴展資訊	String
KeyValue	參數值	Decimal
Remark	備註	Text

AssignReason	薪資項目設置.分配原因	String
OwnerId	薪資項目設置.所有者ID	String
CreateDate	薪資項目設置.創建日期	DateTime
LastModifiedDate	薪資項目設置.最後修改日期	DateTime
CreateBy	薪資項目設置.創建者(資料來自User表的UserId欄位)	Guid
LastModifiedBy	薪資項目設置.最後修改者(資料來自User表的UserId欄位)	Guid
Name	參數名稱	String
CorporationId	所屬公司(資料來自Corporation表的CorporationId欄位)	Guid
OrderDesc	項目類型	String
IsUse	是否使用	Boolean
IsSpecialDeduct	个人所得税专项附加扣除	Boolean
*/
@Data
@Entity
@Table(name = "SalaryFixed")
public class SalaryKey {
	@Id
    private UUID salaryKeyId; //SalaryKeyId	薪資項目索引ID	String
	private Integer keyId; //KeyId	參數編號	Int32
	private String dataField; //DataField	資料欄位名	String
	private Boolean flag; //Flag	薪資項目設置.是否有效	Boolean
}
