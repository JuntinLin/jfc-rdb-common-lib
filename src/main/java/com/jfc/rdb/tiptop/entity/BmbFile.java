package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BMB_FILE database table.
 * bmb01	varchar2(40)	主件料件編號
bmb02	number(5)	組合項次
bmb03	varchar2(40)	元件料件編號
bmb04	date	生效日期
bmb05	date	失效日期
bmb06	number(16,8)	組成用量
bmb07	number(16,8)	底數
bmb08	number(9,4)	損耗率
bmb09	varchar2(6)	作業編號
bmb10	varchar2(4)	發料單位
bmb10_fac	number(20,8)	「發料」對「料件庫存單位」換算率
bmb10_fac2	number(20,8)	「發料」對「料件成本單位」換算率
bmb11	varchar2(20)	工程圖號
bmb13	varchar2(10)	元件插件位置
bmb14	varchar2(1)	元件使用特性
bmb15	varchar2(1)	元件消耗特性
bmb16	varchar2(1)	取/替代特性
bmb17	varchar2(1)	特性旗標
bmb18	number(5)	元件投料時距
bmb19	varchar2(1)	工單開立展開選項
bmb20	number(5)	No Use
bmb21	varchar2(1)	No Use
bmb22	varchar2(1)	No Use
bmb23	number(9,4)	選中率
bmb24	varchar2(20)	工程變異單單號
bmb25	varchar2(10)	倉庫別
bmb26	varchar2(10)	存放位置
bmb27	varchar2(1)	元件是否軟體物件
bmb28	number(9,4)	發料誤差允許率
bmbmodu	varchar2(10)	單身最近一次資料更改者
bmbdate	date	單身最近一次修改日期
bmbcomm	varchar2(10)	修改指令來源
bmb29	varchar2(20)	特性代碼
bmb30	varchar2(1)	計算方式
bmb31	varchar2(1)	代買料否
bmb33	number(10)	款式BOM對應項次
bmbud01	varchar2(255)	自訂欄位-Textedit
bmbud02	varchar2(40)	自訂欄位-文字
bmbud03	varchar2(40)	自訂欄位-文字
bmbud04	varchar2(40)	自訂欄位-文字
bmbud05	varchar2(40)	自訂欄位-文字
bmbud06	varchar2(40)	自訂欄位-文字
bmbud07	number(15,3)	自訂欄位-數值
bmbud08	number(15,3)	自訂欄位-數值
bmbud09	number(15,3)	自訂欄位-數值
bmbud10	number(10)	自訂欄位-整數
bmbud11	number(10)	自訂欄位-整數
bmbud12	number(10)	自訂欄位-整數
bmbud13	date	自訂欄位-日期
bmbud14	date	自訂欄位-日期
bmbud15	date	自訂欄位-日期
bmb081	number(15,3)	固定損耗量
bmb082	number(9,4)	損耗批量
bmb36	varchar2(10)	PLM BOM項次
bmb37	varchar2(40)	PLM KEY
bmb34	varchar2(1)	服飾業顏色、尺寸屬性設置

 */
@Entity
@Table(name="BMB_FILE")
@NamedQuery(name="BmbFile.findAll", query="SELECT b FROM BmbFile b")
public class BmbFile implements Serializable {
	private static final long serialVersionUID = 1L;
	private BmbFilePK id;
	private Date bmb05;
	private BigDecimal bmb06;
	private BigDecimal bmb07;
	private BigDecimal bmb08;
	private BigDecimal bmb081;
	private BigDecimal bmb082;
	private String bmb09;
	private String bmb10;
	private BigDecimal bmb10Fac;
	private BigDecimal bmb10Fac2;
	private String bmb11;
	private String bmb13;
	private String bmb14;
	private String bmb15;
	private String bmb16;
	private String bmb17;
	private BigDecimal bmb18;
	private String bmb19;
	private BigDecimal bmb20;
	private String bmb21;
	private String bmb22;
	private BigDecimal bmb23;
	private String bmb24;
	private String bmb25;
	private String bmb26;
	private String bmb27;
	private BigDecimal bmb28;
	private String bmb30;
	private String bmb31;
	private BigDecimal bmb33;
	private String bmb34;
	private String bmb36;
	private String bmb37;
	private String bmbcomm;
	private Date bmbdate;
	private String bmbmodu;
	private String bmbud01;
	private String bmbud02;
	private String bmbud03;
	private String bmbud04;
	private String bmbud05;
	private String bmbud06;
	private BigDecimal bmbud07;
	private BigDecimal bmbud08;
	private BigDecimal bmbud09;
	private BigDecimal bmbud10;
	private BigDecimal bmbud11;
	private BigDecimal bmbud12;
	private Date bmbud13;
	private Date bmbud14;
	private Date bmbud15;
	private ImaFile imaFile;
	private BmaFile bmaFile;

	public BmbFile() {
	}


	@EmbeddedId
	public BmbFilePK getId() {
		return this.id;
	}

	public void setId(BmbFilePK id) {
		this.id = id;
	}


	@Temporal(TemporalType.DATE)
	public Date getBmb05() {
		return this.bmb05;
	}

	public void setBmb05(Date bmb05) {
		this.bmb05 = bmb05;
	}


	public BigDecimal getBmb06() {
		return this.bmb06;
	}

	public void setBmb06(BigDecimal bmb06) {
		this.bmb06 = bmb06;
	}


	public BigDecimal getBmb07() {
		return this.bmb07;
	}

	public void setBmb07(BigDecimal bmb07) {
		this.bmb07 = bmb07;
	}


	public BigDecimal getBmb08() {
		return this.bmb08;
	}

	public void setBmb08(BigDecimal bmb08) {
		this.bmb08 = bmb08;
	}


	public BigDecimal getBmb081() {
		return this.bmb081;
	}

	public void setBmb081(BigDecimal bmb081) {
		this.bmb081 = bmb081;
	}


	public BigDecimal getBmb082() {
		return this.bmb082;
	}

	public void setBmb082(BigDecimal bmb082) {
		this.bmb082 = bmb082;
	}


	public String getBmb09() {
		return this.bmb09;
	}

	public void setBmb09(String bmb09) {
		this.bmb09 = bmb09;
	}


	public String getBmb10() {
		return this.bmb10;
	}

	public void setBmb10(String bmb10) {
		this.bmb10 = bmb10;
	}


	@Column(name="BMB10_FAC")
	public BigDecimal getBmb10Fac() {
		return this.bmb10Fac;
	}

	public void setBmb10Fac(BigDecimal bmb10Fac) {
		this.bmb10Fac = bmb10Fac;
	}


	@Column(name="BMB10_FAC2")
	public BigDecimal getBmb10Fac2() {
		return this.bmb10Fac2;
	}

	public void setBmb10Fac2(BigDecimal bmb10Fac2) {
		this.bmb10Fac2 = bmb10Fac2;
	}


	public String getBmb11() {
		return this.bmb11;
	}

	public void setBmb11(String bmb11) {
		this.bmb11 = bmb11;
	}


	public String getBmb13() {
		return this.bmb13;
	}

	public void setBmb13(String bmb13) {
		this.bmb13 = bmb13;
	}


	public String getBmb14() {
		return this.bmb14;
	}

	public void setBmb14(String bmb14) {
		this.bmb14 = bmb14;
	}


	public String getBmb15() {
		return this.bmb15;
	}

	public void setBmb15(String bmb15) {
		this.bmb15 = bmb15;
	}


	public String getBmb16() {
		return this.bmb16;
	}

	public void setBmb16(String bmb16) {
		this.bmb16 = bmb16;
	}


	public String getBmb17() {
		return this.bmb17;
	}

	public void setBmb17(String bmb17) {
		this.bmb17 = bmb17;
	}


	public BigDecimal getBmb18() {
		return this.bmb18;
	}

	public void setBmb18(BigDecimal bmb18) {
		this.bmb18 = bmb18;
	}


	public String getBmb19() {
		return this.bmb19;
	}

	public void setBmb19(String bmb19) {
		this.bmb19 = bmb19;
	}


	public BigDecimal getBmb20() {
		return this.bmb20;
	}

	public void setBmb20(BigDecimal bmb20) {
		this.bmb20 = bmb20;
	}


	public String getBmb21() {
		return this.bmb21;
	}

	public void setBmb21(String bmb21) {
		this.bmb21 = bmb21;
	}


	public String getBmb22() {
		return this.bmb22;
	}

	public void setBmb22(String bmb22) {
		this.bmb22 = bmb22;
	}


	public BigDecimal getBmb23() {
		return this.bmb23;
	}

	public void setBmb23(BigDecimal bmb23) {
		this.bmb23 = bmb23;
	}


	public String getBmb24() {
		return this.bmb24;
	}

	public void setBmb24(String bmb24) {
		this.bmb24 = bmb24;
	}


	public String getBmb25() {
		return this.bmb25;
	}

	public void setBmb25(String bmb25) {
		this.bmb25 = bmb25;
	}


	public String getBmb26() {
		return this.bmb26;
	}

	public void setBmb26(String bmb26) {
		this.bmb26 = bmb26;
	}


	public String getBmb27() {
		return this.bmb27;
	}

	public void setBmb27(String bmb27) {
		this.bmb27 = bmb27;
	}


	public BigDecimal getBmb28() {
		return this.bmb28;
	}

	public void setBmb28(BigDecimal bmb28) {
		this.bmb28 = bmb28;
	}


	public String getBmb30() {
		return this.bmb30;
	}

	public void setBmb30(String bmb30) {
		this.bmb30 = bmb30;
	}


	public String getBmb31() {
		return this.bmb31;
	}

	public void setBmb31(String bmb31) {
		this.bmb31 = bmb31;
	}


	public BigDecimal getBmb33() {
		return this.bmb33;
	}

	public void setBmb33(BigDecimal bmb33) {
		this.bmb33 = bmb33;
	}


	public String getBmb34() {
		return this.bmb34;
	}

	public void setBmb34(String bmb34) {
		this.bmb34 = bmb34;
	}


	public String getBmb36() {
		return this.bmb36;
	}

	public void setBmb36(String bmb36) {
		this.bmb36 = bmb36;
	}


	public String getBmb37() {
		return this.bmb37;
	}

	public void setBmb37(String bmb37) {
		this.bmb37 = bmb37;
	}


	public String getBmbcomm() {
		return this.bmbcomm;
	}

	public void setBmbcomm(String bmbcomm) {
		this.bmbcomm = bmbcomm;
	}


	@Temporal(TemporalType.DATE)
	public Date getBmbdate() {
		return this.bmbdate;
	}

	public void setBmbdate(Date bmbdate) {
		this.bmbdate = bmbdate;
	}


	public String getBmbmodu() {
		return this.bmbmodu;
	}

	public void setBmbmodu(String bmbmodu) {
		this.bmbmodu = bmbmodu;
	}


	public String getBmbud01() {
		return this.bmbud01;
	}

	public void setBmbud01(String bmbud01) {
		this.bmbud01 = bmbud01;
	}


	public String getBmbud02() {
		return this.bmbud02;
	}

	public void setBmbud02(String bmbud02) {
		this.bmbud02 = bmbud02;
	}


	public String getBmbud03() {
		return this.bmbud03;
	}

	public void setBmbud03(String bmbud03) {
		this.bmbud03 = bmbud03;
	}


	public String getBmbud04() {
		return this.bmbud04;
	}

	public void setBmbud04(String bmbud04) {
		this.bmbud04 = bmbud04;
	}


	public String getBmbud05() {
		return this.bmbud05;
	}

	public void setBmbud05(String bmbud05) {
		this.bmbud05 = bmbud05;
	}


	public String getBmbud06() {
		return this.bmbud06;
	}

	public void setBmbud06(String bmbud06) {
		this.bmbud06 = bmbud06;
	}


	public BigDecimal getBmbud07() {
		return this.bmbud07;
	}

	public void setBmbud07(BigDecimal bmbud07) {
		this.bmbud07 = bmbud07;
	}


	public BigDecimal getBmbud08() {
		return this.bmbud08;
	}

	public void setBmbud08(BigDecimal bmbud08) {
		this.bmbud08 = bmbud08;
	}


	public BigDecimal getBmbud09() {
		return this.bmbud09;
	}

	public void setBmbud09(BigDecimal bmbud09) {
		this.bmbud09 = bmbud09;
	}


	public BigDecimal getBmbud10() {
		return this.bmbud10;
	}

	public void setBmbud10(BigDecimal bmbud10) {
		this.bmbud10 = bmbud10;
	}


	public BigDecimal getBmbud11() {
		return this.bmbud11;
	}

	public void setBmbud11(BigDecimal bmbud11) {
		this.bmbud11 = bmbud11;
	}


	public BigDecimal getBmbud12() {
		return this.bmbud12;
	}

	public void setBmbud12(BigDecimal bmbud12) {
		this.bmbud12 = bmbud12;
	}


	@Temporal(TemporalType.DATE)
	public Date getBmbud13() {
		return this.bmbud13;
	}

	public void setBmbud13(Date bmbud13) {
		this.bmbud13 = bmbud13;
	}


	@Temporal(TemporalType.DATE)
	public Date getBmbud14() {
		return this.bmbud14;
	}

	public void setBmbud14(Date bmbud14) {
		this.bmbud14 = bmbud14;
	}


	@Temporal(TemporalType.DATE)
	public Date getBmbud15() {
		return this.bmbud15;
	}

	public void setBmbud15(Date bmbud15) {
		this.bmbud15 = bmbud15;
	}


	//bi-directional many-to-one association to ImaFile
	@ManyToOne
	@JoinColumn(name="BMB03", insertable=false, updatable=false)
	public ImaFile getImaFile() {
		return this.imaFile;
	}

	public void setImaFile(ImaFile imaFile) {
		this.imaFile = imaFile;
	}


	//bi-directional many-to-one association to BmaFile
	@ManyToOne
	@JoinColumns({
	    @JoinColumn(name="BMB01", referencedColumnName="BMA01", insertable=false, updatable=false),
	    @JoinColumn(name="BMB29", referencedColumnName="BMA06", insertable=false, updatable=false)
	})
	public BmaFile getBmaFile() {
		return this.bmaFile;
	}

	public void setBmaFile(BmaFile bmaFile) {
		this.bmaFile = bmaFile;
	}

}