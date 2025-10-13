package com.jfc.rdb.hrm.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*表名: AttendanceRollcall(點名管理)		
字段名称	中文名称	类型

BeginTime	班次開始時間	DateTime
EndTime	班次結束時間	DateTime



QuartersHours	實際在崗時數	Decimal
QuartersHoursUnit	實際在崗計算單位	String
IsConfirm	是否封存	Boolean
OperationDate	操作日期	DateTime
UserId	操作人	Guid
Recover	是否覆蓋該資料	Boolean
Remark	備註	Text
CreateDate	點名結果.創建日期	DateTime
LastModifiedDate	點名結果.最後修改日期	DateTime
CreateBy	點名結果.創建者	Guid
LastModifiedBy	點名結果.最後修改者	Guid
CorporationId	點名結果.公司ID	Guid

AssignReason	點名結果.分配原因	String
OwnerId	點名結果.所有者ID	String
VirObjectId	虛擬實體輔助欄位	String
ActualBeginTime	實際開始時間	DateTime
ActualEndTime	實際結束時間	DateTime
Count	核算量	Int32
DailyCards	當天的刷卡記錄	String
EmpRankCards	當天有效刷卡記錄	String
CollectBegin	刷卡開始時間	DateTime
CollectEnd	刷卡結束時間	DateTime
IsAbnormal	是否異常資料	Boolean
AttendanceRollcallDetail.AttendanceRollcallDetailId	點名明細.點名明細ID	Guid
AttendanceRollcallDetail.AttendanceRankRestId	點名明細.班段ID	String
AttendanceRollcallDetail.EmployeeId	點名明細.員工ID	Guid
AttendanceRollcallDetail.AttendanceTypeId	點名明細.假勤類型ID	String
AttendanceRollcallDetail.RestCode	點名明細.班段編號	Int32
AttendanceRollcallDetail.RestName	點名明細.班段名稱	String
AttendanceRollcallDetail.RestBeginTime	點名明細.班段開始時間	DateTime
AttendanceRollcallDetail.RestEndTime	點名明細.班段結束時間	DateTime
AttendanceRollcallDetail.CallBeginTime	點名明細.實際開始時間	DateTime
AttendanceRollcallDetail.CallEndTime	點名明細.實際結束時間	DateTime
AttendanceRollcallDetail.Count	點名明細.核算次數	Int32
AttendanceRollcallDetail.Hours	點名明細.核算量	Decimal
AttendanceRollcallDetail.QuartersHours	點名明細.實際在崗時數	Decimal
AttendanceRollcallDetail.QuartersHoursUnit	點名明細.在崗時數單位	String
AttendanceRollcallDetail.CreateDate	點名明細.創建日期	DateTime
AttendanceRollcallDetail.LastModifiedDate	點名明細.最後修改日期	DateTime
AttendanceRollcallDetail.CreateBy	點名明細.創建者	Guid
AttendanceRollcallDetail.LastModifiedBy	點名明細.最後修改者	Guid
AttendanceRollcallDetail.Flag	點名明細.是否有效	Boolean
AttendanceRollcallDetail.AttendanceRankId	班次ID	String
AttendanceRollcallDetail.CorporationId	所属公司ID	Guid
AttendanceRollcallDetail.CostCenterId	成本中心	Guid
AttendanceRollcallCollect.AttendanceRollcallCollectId	點名刷卡明細.刷卡數據ID	Guid
AttendanceRollcallCollect.MachineId	點名刷卡明細.考勤機ID	Guid
AttendanceRollcallCollect.CardId	點名刷卡明細.考勤卡ID	Guid
AttendanceRollcallCollect.EmployeeId	點名刷卡明細.員工ID	Guid
AttendanceRollcallCollect.Date	點名刷卡明細	DateTime
AttendanceRollcallCollect.IsManual	點名刷卡明細.是否手工錄入	Boolean
AttendanceRollcallCollect.CreateDate	創建日期	DateTime
AttendanceRollcallCollect.LastModifiedDate	最後修改日期	DateTime
AttendanceRollcallCollect.CreateBy	創建者	Guid
AttendanceRollcallCollect.LastModifiedBy	最後修改人	Guid
AttendanceRollcallCollect.Flag	識別字	Boolean
AttendanceRollcallCollect.AttendanceRankId	班次ID	String
AttendanceRollcallCollect.ClassCode	功能碼	String
*/
@Data
@Entity
@Table(name = "AttendanceRollcall")
public class AttendanceRollcall {
	@Id
    private UUID attendanceRollcallId; //AttendanceRollcallId	點名結果ID	Guid
	private UUID EmployeeId; //EmployeeId	員工ID	Guid
	private Date date; //Date	日期	DateTime
	private String attendanceTypeId; //AttendanceTypeId	假勤類型ID	String
	private String attendanceRankId; //AttendanceRankId	班次ID	String
	private BigDecimal hours; //Hours	核算量	Decimal
	private Boolean flag; //Flag	點名結果.是否有效	Boolean
}
