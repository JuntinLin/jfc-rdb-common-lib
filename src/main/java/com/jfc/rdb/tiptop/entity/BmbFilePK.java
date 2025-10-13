package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the BMB_FILE database table.
 * bmb01	varchar2(40)	主件料件編號	主件料件編號儲存該產品結構組合的主件料件編號。主件料件編號需在料件基本資料主檔中。
bmb02	number(5)	組合項次	組合項次儲存該產品結構組合的順序項次。可作為設定元件料件在該產品結構組合中的用料順序。
bmb03	varchar2(40)	元件料件編號	元件料件編號儲存該產品結構組合的項次中使用的元件料件編號。元件料件編號需在料件基本資料主檔中。
bmb04	date	生效日期	生效日期儲存該產品結構組合的項次的有效期間之起始日期。
bmb29	varchar2(20)	特性代碼

 */
@Embeddable
public class BmbFilePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private String bmb01; //bmb01	varchar2(40)	主件料件編號
	private long bmb02; //bmb02	number(5)	組合項次
	private String bmb03; //bmb03	varchar2(40)	元件料件編號
	private java.util.Date bmb04;
	private String bmb29;

	public BmbFilePK() {
	}

	public String getBmb01() {
		return this.bmb01;
	}
	public void setBmb01(String bmb01) {
		this.bmb01 = bmb01;
	}

	public long getBmb02() {
		return this.bmb02;
	}
	public void setBmb02(long bmb02) {
		this.bmb02 = bmb02;
	}

	public String getBmb03() {
		return this.bmb03;
	}
	public void setBmb03(String bmb03) {
		this.bmb03 = bmb03;
	}

	@Temporal(TemporalType.DATE)
	public java.util.Date getBmb04() {
		return this.bmb04;
	}
	public void setBmb04(java.util.Date bmb04) {
		this.bmb04 = bmb04;
	}

	public String getBmb29() {
		return this.bmb29;
	}
	public void setBmb29(String bmb29) {
		this.bmb29 = bmb29;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BmbFilePK)) {
			return false;
		}
		BmbFilePK castOther = (BmbFilePK)other;
		return 
			this.bmb01.equals(castOther.bmb01)
			&& (this.bmb02 == castOther.bmb02)
			&& this.bmb03.equals(castOther.bmb03)
			&& this.bmb04.equals(castOther.bmb04)
			&& this.bmb29.equals(castOther.bmb29);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.bmb01.hashCode();
		hash = hash * prime + ((int) (this.bmb02 ^ (this.bmb02 >>> 32)));
		hash = hash * prime + this.bmb03.hashCode();
		hash = hash * prime + this.bmb04.hashCode();
		hash = hash * prime + this.bmb29.hashCode();
		
		return hash;
	}
}