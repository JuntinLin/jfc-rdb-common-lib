package com.jfc.rdb.hrm.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 閒置時間計算 - 請求 DTO
 * Tiptop 工單呼叫時傳入開工日或發料日
 */
@Data
public class WorkingDaysRequest {
	
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /** 結束日期，未傳時預設為今天 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /** 日期類型: START_WORK(開工日), MATERIAL_ISSUE(發料日) */
    private DateType dateType;

    public enum DateType {
        /** 開工日 */
        START_WORK,
        /** 發料日 */
        MATERIAL_ISSUE
    }

}
