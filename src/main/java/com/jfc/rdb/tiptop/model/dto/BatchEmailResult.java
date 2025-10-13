package com.jfc.rdb.tiptop.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 批量發送郵件结果DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchEmailResult {
	private int totalCount;
    private int successCount;
    private int failureCount;
    private List<FailureDetail> failures;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FailureDetail {
        private String customerCode;
        private String customerName;
        private String errorMessage;
    }
}
