package com.jfc.rdb.hrm.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 閒置時間計算 - 回應 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkingDaysResponse {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /** 日期類型說明 (開工日 / 發料日) */
    private String dateTypeDesc;

    /** 工作天數 */
    private long workingDays;

    /** 非工作天數（假日 + 節日 + 休息日） */
    private long nonWorkingDays;

    /** 總日曆天數 */
    private long calendarDays;

    /** 閒置工作天數（= 工作天數） */
    private long idleDays;

    /** 工作日明細（可選） */
    private List<DayDetail> details;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DayDetail {
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;
        private String dayOfWeek;
        private String holidayTypeName;
        private String holidayTypeCode;
    }

}
