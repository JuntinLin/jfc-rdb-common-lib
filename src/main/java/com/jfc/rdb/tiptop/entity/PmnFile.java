package com.jfc.rdb.tiptop.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PMN_FILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PmnFile {
	@EmbeddedId
	private PmnFilePK id;
	@Column(name = "pmn04")
	private String pmn04; // 料號

	@Column(name = "pmn16")
	private String pmn16; // 狀態碼

	@Column(name = "pmn011")
	private String pmn011; // 採購類型

	@Column(name = "pmn20", precision = 15, scale = 3)
	private BigDecimal pmn20; // 訂購數量

	@Column(name = "pmn50", precision = 15, scale = 3)
	private BigDecimal pmn50; // 已交數量

	@Column(name = "pmn55", precision = 15, scale = 3)
	private BigDecimal pmn55; // 驗退數量

	@ManyToOne
	@JoinColumn(name = "pmn01", referencedColumnName = "pmm01", insertable = false, updatable = false)
	private PmmFile pmm;
}
