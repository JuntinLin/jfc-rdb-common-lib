package com.jfc.rdb.hrm.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
/*欄位名稱	中文名稱	類型
*
ApplyEmployeeId	申請人ID(資料來自Employee表的EmployeeId欄位)	Guid
	ApplyDate	申請日期	DateTime
	IsExecute	是否已執行	Boolean
	ApplyReason	申請原因	String
CorporationId	員工穩定薪資.公司ID(資料來自Corporation表的CorporationId欄位)	Guid
CreateDate	員工穩定薪資.創建日期	DateTime
LastModifiedDate	員工穩定薪資.最後修改日期	DateTime
CreateBy	員工穩定薪資.創建者(資料來自User表的UserId欄位)	Guid
LastModifiedBy	員工穩定薪資.最後修改者(資料來自User表的UserId欄位)	Guid
AssignReason	員工穩定薪資.分配原因	String
OwnerId	員工穩定薪資.所有者ID	String
FoundOperationDate	創建操作日期	DateTime
FoundUserId	創建用戶ID(資料來自User表的UserId欄位)	Guid
ApproveEmployeeId	審核人(資料來自Employee表的EmployeeId欄位)	Guid
ApproveEmployeeName	審核人姓名	String
ApproveDate	審核日期	DateTime
ApproveUserId	審核用戶ID(資料來自User表的UserId欄位)	Guid
ApproveOperationDate	審核操作日期	DateTime
SubmitUserId	提交用戶ID(資料來自User表的UserId欄位)	Guid
SubmitOperationDate	提交操作日期	DateTime
ConfirmUserId	歸檔用戶ID(資料來自User表的UserId欄位)	Guid
ConfirmOperationDate	歸檔日期	DateTime
RepealUserId	駁回用戶ID(資料來自User表的UserId欄位)	Guid
RepealOperationDate	駁回日期	DateTime
StateId	狀態(資料來自CodeInfo表的CodeInfoId欄位)	String
ApproveResultId	審核結果(資料來自CodeInfo表的CodeInfoId欄位)	String
Code	申請單編號	String
Name	申請單名稱	String
ApproveRemark	審核意見	String
VirObjectId	虛擬實體ID	String
IsEss	是否ESS相关	Boolean
IsEF	是否EF相关	Boolean
IsFromEss	是否Ess資料	Boolean
EssType	Ess單別	String
EssNo	Ess單號	String
*/
@Data
@Entity
@Table(name = "SalaryFixed")
public class SalaryFixed {
	@Id
    private UUID salaryFixedId; //SalaryFixedId	員工固定薪資id	Guid
	private String remark; //Remark	備註	Text
	
	@ManyToOne
    @JoinColumn(name = "EmployeeId")
	private Employee employee; //EmployeeId	員工(資料來自Employee表的EmployeeId欄位)	Guid
	
	private boolean flag; //Flag	員工穩定薪資.是否有效	Boolean
}
