package com.jfc.rdb.hrm.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
/*表名: AttendanceHolidayType(節假日類型)		

*/
@Data
@Entity
@Table(name = "AttendanceHolidayType")
public class AttendanceHolidayType {
	@Id
	private UUID attendanceHolidayTypeId; //AttendanceHolidayTypeId	節假日類型ID	String
	private String holidayTypeId; //HolidayTypeId	節假日類別ID(資料來自CodeInfo表的CodeInfoId欄位)	String
	private String holidayLegendId; //HolidayLegendId	節假日類型圖例ID(資料來自CodeInfo表的CodeInfoId欄位)	String
	private Boolean isSystem; //IsSystem	是否系統資料	Boolean
	private String remark; //Remark	備註	Text
	private LocalDateTime createDate; //CreateDate	節假日類型.創建日期	DateTime
	private LocalDateTime lastModifiedDate; //LastModifiedDate	節假日類型.最後修改日期	DateTime
	private String createBy; //CreateBy	節假日類型.創建者(資料來自User表的UserId欄位)	Guid
	private String lastModifiedBy; //LastModifiedBy	節假日類型.最後修改者(資料來自User表的UserId欄位)	Guid
	private String name; //Name	節假日類型名稱	String
	private Boolean flag; //Flag	節假日類型.是否有效	Boolean
	private String corporationId; //CorporationId	所屬公司(資料來自Corporation表的CorporationId欄位)	Guid
	private String code; //Code	節假日類型編	String
	private String assignReason; //AssignReason	節假日類型.分配原因	String
	private String ownerId; //OwnerId	節假日類型.所有者ID(資料來自User表的UserId欄位)	String
}
