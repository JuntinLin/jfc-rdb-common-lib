package com.jfc.rdb.tiptop.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.Data;

@Data

public class AccountDetailDTO {
	private String billDate; // oma02 帳款日期
	private String dueDate; // oma11 應收款日/應扣抵日
	private String billNumber; // oma01 帳款編號
	private String billType; // oma00 帳款類別
	private BigDecimal originalAmount; // oma56t 本幣應收含稅金額
	private BigDecimal outstandingAmount; // oma61 本幣未沖金額
	private String invoiceNumber; // oma10 發票號碼
	private String salesman; // oma14 業務人員

	public AccountDetailDTO(Date oma02, // 與 o.oma02 型別相符
			Date oma11, // 與 o.oma11 型別相符
			String oma01, // ...
			String oma00, BigDecimal oma56t, BigDecimal oma61, String oma10, String salesmanGen02) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date1 = oma02.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    this.billDate = (oma02 != null) ? date1.format(formatter) : null;
	    date1 = oma11.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    this.dueDate = (oma11 != null) ? date1.format(formatter) : null;
		this.billNumber = oma01; //帳款編號
		this.billType= oma00; // oma00 帳款類別
		this.originalAmount = oma56t; // oma56t 本幣應收含稅金額
		this.outstandingAmount = oma61; // oma61 本幣未沖金額
		this.invoiceNumber = oma10; // oma10 發票號碼
		this.salesman = salesmanGen02; // oma14 業務人員
	}
}
