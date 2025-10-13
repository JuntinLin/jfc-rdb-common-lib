package com.jfc.rdb.tiptop.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReminderEmailRequest {
	private String customerCode;
    private String accountNumber;
    private LocalDate cutoffDate;
}
