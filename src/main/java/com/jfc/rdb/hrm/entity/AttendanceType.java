package com.jfc.rdb.hrm.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*表名: AttendanceType(假勤類型)		
字段名称	中文名称	类型

CorporationId	所屬公司ID	Guid

ShortName	假勤簡稱	String
AttendanceUnitId	假勤單位ID	String
IsSalaryId	是否帶薪ID	String
IsAdjust	是否調休ID	String
Rate	調休係數	Decimal
IsAllownceAudit	是否需要審核（專為津貼）	Boolean
IsAssociateRank	是否與班次關聯（專為津貼）	Boolean
AssociateRanks	關聯的班次（專為津貼）（以;隔開）	Text
IsAssociateAbnormity	是否與異常關聯（專為津貼）	Boolean
AssociateAbnormitys	關聯的異常（專為津貼）	Text
IsAuditBegin	是否審核開始時間（專為津貼）	Boolean
BeginTime	開始時間（專為津貼）	String
InterzoneBegin	開始時間區間（專為津貼）	Decimal
InterzoneEnd	結束時間區間（專為津貼）	Decimal
IsAssociateOT	是否關聯加班（專為津貼）	Boolean
IsAssociateRT	是否關聯班次時間（專為津貼）	Boolean
RankAbnormityBegin	考勤異常計算最小分鐘數	Decimal
RankAbnormityEnd	考勤異常計算最大分鐘數	Decimal
Remark	假勤類型.備註	Text
SalaryPeriodId	計算週期ID	String

CreateDate	假勤類型.創建日期	DateTime
LastModifiedDate	假勤類型.最後修改日期	DateTime
CreateBy	假勤類型.創建者	Guid
LastModifiedBy	假勤類型.最後修改者	Guid
Flag	假勤類型.是否有效	Boolean
IsSystem	是否系統	Boolean
MaxSalaryHour	最大允許請假數量	Decimal
IsApplyId	出差是否需要申請Id	String
IsVisible	是否可見	Boolean
AssignReason	假勤類型.分配原因	String
OwnerId	假勤類型.所有者ID	String
InterzoneType	時間段以審核點起算	Boolean
AttendanceRankId	班次ID	String
IsGenerateData	是否生成節假日假勤類型資料	Boolean
IsUse	是否使用	Boolean
BeginDate	開始日期	Int32
EndDate	結束日期	Int32
NoItems	不扣全勤假勤項	Text
SexType	適用性別	String
DaysCoverATType	最多天數包含其它假勤項核算量	Text
HolidayOTTimes	假日類加班免稅時數	Decimal
IsCheckAdjustHours	是否扣除調休計畫時數	Boolean
IsShowReport	是否在報表中顯示	Boolean
DeductOhter	是否扣除班次以外時間	Boolean
PassRest	是否跳過休息日	Boolean
MinLeaveHours	最小核算量	Decimal
MinAuditHours	最小審核量	Decimal
IsShowEss	ESS中顯示	Boolean
ChangeAbsentType	轉換曠工類型	String
CalculateModeId	進位方式	String
Digits	小數位數	Int32
AssociateRanksName	關聯的班次名稱（專為津貼）（以;隔開）	Text
IsBeginEnd	是否按時段計算津貼（專為津貼）	Boolean
NewBeginTime	開始時間（專為津貼）	String
NewEndTime	結束時間（專為津貼）	String
IsContainsAbnormal	是否包含異常時數	Boolean
CountDayOff	是否包含班次外時段	Boolean

*/
@Data
@Entity
@Table(name = "AttendanceType")
public class AttendanceType {
	@Id
    private UUID attendanceTypeId; //AttendanceTypeId	假勤類型ID	String
	private String name; //Name	假勤名稱	String
	private String code; //Code	假勤編碼	String
	private String attendanceKindId; //AttendanceKindId	假勤項目ID	String
	private String typeTag; //TypeTag	冗余字段	Int32
	
}
