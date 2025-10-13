package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the BMA_FILE database table.
 * bma01	varchar2(40)	主件料件編號	主件料件編號 儲存該產品結構組合的主件料件編號。 主件料件編號需在料件基本資料主檔中。
bma02	varchar2(20)	最後工程變異單單號	最後工程變異單單號 儲存該產品結構組合的最近一次工程變異單 （ECN）單號。 系統維護。
bma03	date	最近工程變異日期	最近工程變異日期 儲存該產品結構組合的最近一次工程變異日 期。 系統維護。
bma04	varchar2(10)	組合模式參考編號	組合模式參考編號 儲存該產品結構組合的分類模式代號。 使用者可自行定義  如 1001-電焊車架。
bma05	date	發放日期	發放日期  010507 add No.B474 有發放日期,工單及ECN才可使用
bmauser	varchar2(10)	資料所有者	資料所有者 系統維護。
bmagrup	varchar2(10)	資料所有群	資料所有群 系統維護。
bmamodu	varchar2(10)	資料更改者	資料更改者 系統維護。
bmadate	date	最近修改日	最近修改日 系統維護。
bmaacti	varchar2(1)	資料有效碼	資料有效碼 系統維護。
bma06	varchar2(20)	特性代碼	
bma07	varchar2(1)	配方否	
bmaicd01	varchar2(40)	來源單號	
bma08	varchar2(10)	資料來源	
bma09	number(10)	拋轉次數	
bma10	varchar2(1)	狀態碼	
bmaud01	varchar2(255)	自訂欄位-Textedit	
bmaud02	varchar2(40)	自訂欄位-文字	
bmaud03	varchar2(40)	自訂欄位-文字	
bmaud04	varchar2(40)	自訂欄位-文字	
bmaud05	varchar2(40)	自訂欄位-文字	
bmaud06	varchar2(40)	自訂欄位-文字	
bmaud07	number(15,3)	自訂欄位-數值	
bmaud08	number(15,3)	自訂欄位-數值	
bmaud09	number(15,3)	自訂欄位-數值	
bmaud10	number(10)	自訂欄位-整數	
bmaud11	number(10)	自訂欄位-整數	
bmaud12	number(10)	自訂欄位-整數	
bmaud13	date	自訂欄位-日期	
bmaud14	date	自訂欄位-日期	
bmaud15	date	自訂欄位-日期	
bmaoriu	varchar2(10)	資料建立者	
bmaorig	varchar2(10)	資料建立部門	

 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="BMA_FILE")
@NamedQuery(name="BmaFile.findAll", query="SELECT b FROM BmaFile b")
public class BmaFile implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private BmaFilePK id;
	private String bma02;
	private Date bma03;
	private String bma04;
	private Date bma05;
	private String bma07;
	private String bma08;
	private BigDecimal bma09;
	private String bma10;
	private String bmaacti;
	private Date bmadate;
	private String bmagrup;
	private String bmaicd01;
	private String bmamodu;
	private String bmaorig;
	private String bmaoriu;
	private String bmaud01;
	private String bmaud02;
	private String bmaud03;
	private String bmaud04;
	private String bmaud05;
	private String bmaud06;
	private BigDecimal bmaud07;
	private BigDecimal bmaud08;
	private BigDecimal bmaud09;
	private BigDecimal bmaud10;
	private BigDecimal bmaud11;
	private BigDecimal bmaud12;
	private Date bmaud13;
	private Date bmaud14;
	private Date bmaud15;
	private String bmauser;
	
	
	@ManyToOne
	@JoinColumn(name = "bma01", referencedColumnName = "ima01", insertable = false, updatable = false)
	private ImaFile imaFile;
	
	@OneToMany(mappedBy = "bmaFile")
	private List<BmbFile> bmbFiles;
}