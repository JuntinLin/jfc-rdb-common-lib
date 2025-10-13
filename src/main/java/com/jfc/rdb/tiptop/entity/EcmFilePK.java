package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the ECM_FILE database table.
 * 
 */
@Embeddable
public class EcmFilePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String ecm01;//ecm01	varchar2(20)	工單編號

	private Integer ecm03;//ecm03	number(5)	製程序號	製程序號料件被製作程序過程中的生產活動順序編號

	private String ecm012;//ecm012	varchar2(10)	製程段號


	public EcmFilePK(String ecm01, Integer ecm03, String ecm012) {
		super();
		this.ecm01 = ecm01;
		this.ecm03 = ecm03;
		this.ecm012 = ecm012;
	}
	public EcmFilePK() {
	}
	public String getEcm01() {
		return this.ecm01;
	}
	public void setEcm01(String ecm01) {
		this.ecm01 = ecm01;
	}
	public long getEcm03() {
		return this.ecm03;
	}
	public void setEcm03(Integer ecm03) {
		this.ecm03 = ecm03;
	}
	public String getEcm012() {
		return this.ecm012;
	}
	public void setEcm012(String ecm012) {
		this.ecm012 = ecm012;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EcmFilePK)) {
			return false;
		}
		EcmFilePK castOther = (EcmFilePK)other;
		return 
			this.ecm01.equals(castOther.ecm01)
			&& (this.ecm03 == castOther.ecm03)
			&& this.ecm012.equals(castOther.ecm012);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ecm01.hashCode();
		hash = hash * prime + ((int) (this.ecm03 ^ (this.ecm03 >>> 32)));
		hash = hash * prime + this.ecm012.hashCode();
		
		return hash;
	}
}