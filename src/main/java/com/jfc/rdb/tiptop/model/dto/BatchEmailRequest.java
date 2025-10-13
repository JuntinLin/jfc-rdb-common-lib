package com.jfc.rdb.tiptop.model.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 批量发送邮件请求DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchEmailRequest {
    private List<String> customerCodes;
    private String accountNumber;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate cutoffDate;
}