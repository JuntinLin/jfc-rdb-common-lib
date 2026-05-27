package com.jfc.rdb.hrm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
/*
 * 表名: AttendanceCalendar(企業行事曆)		
字段名称	中文名称	类型
*AttendanceCalendarId	行事曆ID	Guid
CorporationId	所屬公司ID(資料來自Corporation表的CorporationId欄位)	Guid
CalendarType	行事曆類別	Int32
AttendanceSpellId	輪班組ID(資料來自AttendanceSpell表的AttendanceSpellId欄位)	Guid
EmployeeId	員工ID(資料來自Employee表的EmployeeId欄位)	Guid
Date	日期	DateTime
FiscalYearId	年度ID(資料來自FiscalYear表的FiscalYearId欄位)	Guid
MonthId	月份ID(資料來自CodeInfo表的CodeInfoId欄位)	String
Week	星期	String
AttendanceHolidayTypeId	節假日類型ID(資料來自AttendanceHolidayType表的AttendanceHolidayTypeId欄位)	String
IsConfirm	是否批准	Boolean
Remark	備註	Text
CreateDate	企業行事曆.創建日期	DateTime
LastModifiedDate	企業行事曆.最後修改日期	DateTime
CreateBy	企業行事曆.創建者(資料來自User表的UserId欄位)	Guid
LastModifiedBy	企業行事曆.最後修改者(資料來自User表的UserId欄位)	Guid
Flag	企業行事曆.是否有效	Boolean
VirObjectId	虛擬實體輔助欄位	String
YearId	年度(資料來自CodeInfo表的CodeInfoId欄位)	String
AssignReason	分配原因	String
OwnerId	擁有者ID(資料來自User表的UserId欄位)	String
*/
@Data
@Entity
@Table(name = "AttendanceCalendar")
public class AttendanceCalendar {
	@Id
    private UUID attendanceCalendarId; //AttendanceCalendarId	行事曆ID	Guid
	private UUID corporationId; //CorporationId	所屬公司ID(資料來自Corporation表的CorporationId欄位)	Guid
	private Integer calendarType; //CalendarType	行事曆類別	Int32
	private UUID attendanceSpellId; //AttendanceSpellId	輪班組ID(資料來自AttendanceSpell表的AttendanceSpellId欄位)	Guid
	private UUID employeeId; //EmployeeId	員工ID(資料來自Employee表的EmployeeId欄位)	Guid
	private LocalDateTime date; //Date	日期	DateTime
	private UUID fiscalYearId; //FiscalYearId	年度ID(資料來自FiscalYear表的FiscalYearId欄位)	Guid
	private String monthId; //MonthId	月份ID(資料來自CodeInfo表的CodeInfoId欄位)	String
	private String week; //Week	星期	String
	
	//private String attendanceHolidayTypeId; //AttendanceHolidayTypeId	節假日類型ID(資料來自AttendanceHolidayType表的AttendanceHolidayTypeId欄位)	String
	@ManyToOne
	@JoinColumn(name = "attendanceHolidayTypeId")
	private AttendanceHolidayType attendanceHolidayType;
	
	private Boolean isConfirm; //IsConfirm	是否批准	Boolean
	private String remark; //Remark	備註	Text
	private LocalDateTime createDate; //CreateDate	企業行事曆.創建日期	DateTime
	private LocalDateTime lastModifiedDate; //LastModifiedDate	企業行事曆.最後修改日期	DateTime
	private String createBy; //CreateBy	企業行事曆.創建者(資料來自User表的UserId欄位)	Guid
	private String lastModifiedBy; //LastModifiedBy	企業行事曆.最後修改者(資料來自User表的UserId欄位)	Guid
	private Boolean flag; //Flag	企業行事曆.是否有效	Boolean
	private String virObjectId; //VirObjectId	虛擬實體輔助欄位	String
	private String yearId; //YearId	年度(資料來自CodeInfo表的CodeInfoId欄位)	String
	private String assignReason; //AssignReason	分配原因	String
	private String ownerId; //OwnerId	擁有者ID(資料來自User表的UserId欄位)	String

}
