package com.jfc.rdb.hrm.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*表名: AttendanceOTResult(實際加班管理)		
字段名称	中文名称	类型

IsAdjusted	是否已經調休	Boolean

AttendanceRankId	班次ID	String
BeginDate	開始日期	DateTime
BeginTime	開始時間	String
EndDate	結束日期	DateTime
EndTime	結束時間	String
Hours	加班時數	Decimal
IsManual	是否手動錄入	Boolean
IsConfirm	是否封存	Boolean
Remark	備註	Text

FoundOperationDate	處理操作日期	DateTime
FoundUserId	處理操作人ID	Guid
ApproveDate	審核日期	DateTime
ApproveEmployeeId	審核人ID	Guid
ApproveEmployeeName	審核人名稱	String

ApproveRemark	審核批註	Text
ApproveOperationDate	審核操作日期	DateTime
ApproveUserId	審核操作人ID	Guid
RepealOperationDate	撤銷操作日期	DateTime
RepealUserId	撤銷操作人ID	Guid
CreateDate	加班結果.創建日期	DateTime
LastModifiedDate	加班結果.最後修改日期	DateTime
CreateBy	加班結果.創建者	Guid
LastModifiedBy	加班結果.最後修改者	Guid
CorporationId	加班結果.公司ID	Guid
Flag	加班結果.是否有效	Boolean
AssignReason	加班結果.分配原因	String
OwnerId	加班結果.所有者ID	String
VirObjectId	虛擬實體輔助欄位	String
PlanBeginDate	計畫開始日期	DateTime
PlanBeginTime	計畫開始時間	String
PlanEndDate	計畫結束日期	DateTime
PlanEndTime	計畫結束時間	String
PlanHours	計畫加班時數	Decimal


HourBalance	加班時間差額	Decimal
IsMinusDinner	是否扣除就餐	Boolean
DinnerTimesInfo	就餐信息	String
AfterDinnerHours	扣除就餐後加班時間	Decimal
ClockingHours	刷卡時數	Decimal
ValidBeginTime	實際有效開始時間	String
ValidEndTime	實際有效結束時間	String
SubmitOperationDate	提交操作日期	DateTime
SubmitUserId	提交操作人	Guid
ConfirmOperationDate	歸檔操作日期	DateTime
ConfirmUserId	歸檔操作人	Guid
ValidBeginDate	實際有效開始日期	DateTime
ValidEndDate	實際有效結束日期	DateTime
OTAdjustHours	調休時數	Decimal
OldOTDivHours	原加班分段時數	String
IsDispose	是否處理	Boolean
IsPlanAdjust	是否計畫調休	Boolean
IsEss	是否ESS相关	Boolean
IsEF	是否EF相关	Boolean
AttendanceOTParameterId	加班參數ID	Guid
OvertimeKindId	加班種類	String
ESS_SheetNo	ESS單號	String
CardBeginTime	刷卡開始時間	DateTime
CardEndTime	刷卡結束時間	DateTime
IsHRRepealESS	是否HR撤銷ESS單據(已廢除)	Boolean
IsFromEss	是否Ess資料	Boolean
EssType	Ess單別	String
EssNo	Ess單號	String
TOILPlanDetailID	積借休明細Id	Guid
TOILDivHours	積借休明細Id	String
*/

@Data
@Entity
@Table(name = "AttendanceOTResult")
public class AttendanceOTResult {
	@Id
    private UUID attendanceOTResultId; //AttendanceOTResultId	員工加班結果ID	 Guid
	private Date date; //Date	加班日期	DateTime
	private String attendanceOverTimeInfoId; //AttendanceOverTimeInfoId	員工加班計畫明細Id	String
	private UUID employeeId; //EmployeeId	員工ID	Guid
	private UUID attendanceOTPlanId; //AttendanceOTPlanId	加班計畫ID	Guid
	private String attendanceTypeId; //AttendanceTypeId	加班類型ID	String
	
	private BigDecimal workOTOne; //WorkOTOne	平日加班分段一	Decimal
	private BigDecimal workOTSec; //WorkOTSec	平日加班分段二	Decimal
	private BigDecimal workOTThree; //WorkOTThree	平日加班分段三	Decimal
	
	private BigDecimal dayoffOTOne; //DayoffOTOne	休息日加班分段一	Decimal
	private BigDecimal dayoffOTSec; //DayoffOTSec	休息日加班分段二	Decimal
	private BigDecimal dayoffOTThree; //DayoffOTThree	休息日加班分段三	Decimal

	private BigDecimal holidayDutyFreeBase; //HolidayDutyFreeBase	假日類加班免稅基數	Decimal
	private BigDecimal holidayOTOne; //HolidayOTOne	假日加班分段一	Decimal
	private BigDecimal holidayOTSec; //HolidayOTSec	假日加班分段二	Decimal
	private BigDecimal holidayOTThree; //HolidayOTThree	假日加班分段三	Decimal
	
	private BigDecimal festivalDutyFreeBase; //FestivalDutyFreeBase	節日類加班免稅基數	Decimal
	private BigDecimal festivalOTOne; //FestivalOTOne	節日加班分段一	Decimal
	private BigDecimal festivalOTSec; //FestivalOTSec	節日加班分段二	Decimal
	private BigDecimal festivalOTThree; //FestivalOTThree	節日加班分段三	Decimal
	
	private BigDecimal otAdjustHours; //OTAdjustHours	調休時數	Decimal
	private String approveResultId; //ApproveResultId	審核結果ＩＤ	String
	private String stateId; //StateId	狀態ID	String
}
