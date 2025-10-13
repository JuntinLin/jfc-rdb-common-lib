package com.jfc.rdb.tiptop.model.dto;

import com.jfc.rdb.common.dto.AbstractDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EcaDTO extends AbstractDTO {
	private String eca01;//工作站編號	工作站編號工作場所編號, 可能為 一製造營運中心/一廠房/一工作場所
	
	private String eca02;//說明	說明簡述工作站特性說明
	
	private String eca03;//工作站所屬部門別	工作站所屬部門別需存在公司部門檔內
	
	 // 部門相關資訊
    private String departmentName;     // 部門名稱 (gem02)
    private String departmentFullName; // 部門全稱 (gem03)
}
