package com.jfc.rdb.tiptop.entity;

/*
 * 部門名稱資料(gem_file)
 * */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GEM_FILE")
public class GemFile {
	@Id
	@Column(name = "gem01", length = 10)
	private String gem01; // 部門編號

	@Column(name = "gem02", length = 80)
	private String gem02; // 部門名稱

	@Column(name = "gem03", length = 80)
	private String gem03; // 部門全稱
}
