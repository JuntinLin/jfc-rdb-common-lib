package com.jfc.rdb.tiptop.model.dto;

import lombok.Data;

@Data
public class ComplaintDTO {
    private String complaintNo;        // ohc01 客訴單號
    //private LocalDate complaintDate;   // ohc02 客訴日期
    private String complaintDate;
    private String status;             // ohc03 目前狀態
    private String customerCode;       // ohc06 客戶編號
    private String customerName;       // ohc061 客戶簡稱
    private String productCode;        // ohc08 產品編號
    private String productName;        // ohc081 品名規格
    private String productSpec;        // ima.ima021
    private String handler;            // ohc11 處理人員
    private String handlerName;        // gen02 處理人員姓名
    private String complaintContent;   // 客訴內容說明
    private String investigationResult; // 調查結果
    private String responsibilityType;  // 責任類型
    private String responsibleDept;     // 責任單位
    private String responsiblePerson;   // 責任人
    private String improvementPlan;     // 處理對策及改善對策
}
