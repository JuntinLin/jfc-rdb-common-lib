package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the ECD_FILE database table.
 * 作業資料(ecd_file)
 */
@Entity
@Table(name="ECD_FILE")
@NamedQuery(name="EcdFile.findAll", query="SELECT e FROM EcdFile e")
public class EcdFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String ecd01;//作業編號	作業編號為料件被製作程序過程中的生產活動編號
	
	private String ecd02;//作業說明	作業說明簡述作業特性說明

	private Object ecd03;

	private Object ecd04;

	private String ecd05;

	private String ecd06;

	private String ecd07;

	private BigDecimal ecd08;

	private BigDecimal ecd09;

	private String ecd10;

	private String ecd11;

	private BigDecimal ecd12;

	private BigDecimal ecd13;

	private BigDecimal ecd14;

	private String ecd15;

	private BigDecimal ecd16;

	private BigDecimal ecd17;

	private BigDecimal ecd18;

	private BigDecimal ecd19;

	private BigDecimal ecd20;

	private BigDecimal ecd21;

	private String ecd22;

	private String ecd23;

	private BigDecimal ecd24;

	private BigDecimal ecd25;

	private BigDecimal ecd26;

	private String ecdacti;

	private Object ecddate;

	private String ecdgrup;

	private String ecdicd01;

	private String ecdmodu;

	private String ecdorig;

	private String ecdoriu;

	private String ecdslk01;

	private String ecduser;

	//bi-directional many-to-one association to EcmFile
	@OneToMany(mappedBy="ecdFile")
	private List<EcmFile> ecmFiles;

	public EcdFile() {
	}

	public String getEcd01() {
		return this.ecd01;
	}

	public void setEcd01(String ecd01) {
		this.ecd01 = ecd01;
	}

	public String getEcd02() {
		return this.ecd02;
	}

	public void setEcd02(String ecd02) {
		this.ecd02 = ecd02;
	}

	public Object getEcd03() {
		return this.ecd03;
	}

	public void setEcd03(Object ecd03) {
		this.ecd03 = ecd03;
	}

	public Object getEcd04() {
		return this.ecd04;
	}

	public void setEcd04(Object ecd04) {
		this.ecd04 = ecd04;
	}

	public String getEcd05() {
		return this.ecd05;
	}

	public void setEcd05(String ecd05) {
		this.ecd05 = ecd05;
	}

	public String getEcd06() {
		return this.ecd06;
	}

	public void setEcd06(String ecd06) {
		this.ecd06 = ecd06;
	}

	public String getEcd07() {
		return this.ecd07;
	}

	public void setEcd07(String ecd07) {
		this.ecd07 = ecd07;
	}

	public BigDecimal getEcd08() {
		return this.ecd08;
	}

	public void setEcd08(BigDecimal ecd08) {
		this.ecd08 = ecd08;
	}

	public BigDecimal getEcd09() {
		return this.ecd09;
	}

	public void setEcd09(BigDecimal ecd09) {
		this.ecd09 = ecd09;
	}

	public String getEcd10() {
		return this.ecd10;
	}

	public void setEcd10(String ecd10) {
		this.ecd10 = ecd10;
	}

	public String getEcd11() {
		return this.ecd11;
	}

	public void setEcd11(String ecd11) {
		this.ecd11 = ecd11;
	}

	public BigDecimal getEcd12() {
		return this.ecd12;
	}

	public void setEcd12(BigDecimal ecd12) {
		this.ecd12 = ecd12;
	}

	public BigDecimal getEcd13() {
		return this.ecd13;
	}

	public void setEcd13(BigDecimal ecd13) {
		this.ecd13 = ecd13;
	}

	public BigDecimal getEcd14() {
		return this.ecd14;
	}

	public void setEcd14(BigDecimal ecd14) {
		this.ecd14 = ecd14;
	}

	public String getEcd15() {
		return this.ecd15;
	}

	public void setEcd15(String ecd15) {
		this.ecd15 = ecd15;
	}

	public BigDecimal getEcd16() {
		return this.ecd16;
	}

	public void setEcd16(BigDecimal ecd16) {
		this.ecd16 = ecd16;
	}

	public BigDecimal getEcd17() {
		return this.ecd17;
	}

	public void setEcd17(BigDecimal ecd17) {
		this.ecd17 = ecd17;
	}

	public BigDecimal getEcd18() {
		return this.ecd18;
	}

	public void setEcd18(BigDecimal ecd18) {
		this.ecd18 = ecd18;
	}

	public BigDecimal getEcd19() {
		return this.ecd19;
	}

	public void setEcd19(BigDecimal ecd19) {
		this.ecd19 = ecd19;
	}

	public BigDecimal getEcd20() {
		return this.ecd20;
	}

	public void setEcd20(BigDecimal ecd20) {
		this.ecd20 = ecd20;
	}

	public BigDecimal getEcd21() {
		return this.ecd21;
	}

	public void setEcd21(BigDecimal ecd21) {
		this.ecd21 = ecd21;
	}

	public String getEcd22() {
		return this.ecd22;
	}

	public void setEcd22(String ecd22) {
		this.ecd22 = ecd22;
	}

	public String getEcd23() {
		return this.ecd23;
	}

	public void setEcd23(String ecd23) {
		this.ecd23 = ecd23;
	}

	public BigDecimal getEcd24() {
		return this.ecd24;
	}

	public void setEcd24(BigDecimal ecd24) {
		this.ecd24 = ecd24;
	}

	public BigDecimal getEcd25() {
		return this.ecd25;
	}

	public void setEcd25(BigDecimal ecd25) {
		this.ecd25 = ecd25;
	}

	public BigDecimal getEcd26() {
		return this.ecd26;
	}

	public void setEcd26(BigDecimal ecd26) {
		this.ecd26 = ecd26;
	}

	public String getEcdacti() {
		return this.ecdacti;
	}

	public void setEcdacti(String ecdacti) {
		this.ecdacti = ecdacti;
	}

	public Object getEcddate() {
		return this.ecddate;
	}

	public void setEcddate(Object ecddate) {
		this.ecddate = ecddate;
	}

	public String getEcdgrup() {
		return this.ecdgrup;
	}

	public void setEcdgrup(String ecdgrup) {
		this.ecdgrup = ecdgrup;
	}

	public String getEcdicd01() {
		return this.ecdicd01;
	}

	public void setEcdicd01(String ecdicd01) {
		this.ecdicd01 = ecdicd01;
	}

	public String getEcdmodu() {
		return this.ecdmodu;
	}

	public void setEcdmodu(String ecdmodu) {
		this.ecdmodu = ecdmodu;
	}

	public String getEcdorig() {
		return this.ecdorig;
	}

	public void setEcdorig(String ecdorig) {
		this.ecdorig = ecdorig;
	}

	public String getEcdoriu() {
		return this.ecdoriu;
	}

	public void setEcdoriu(String ecdoriu) {
		this.ecdoriu = ecdoriu;
	}

	public String getEcdslk01() {
		return this.ecdslk01;
	}

	public void setEcdslk01(String ecdslk01) {
		this.ecdslk01 = ecdslk01;
	}

	public String getEcduser() {
		return this.ecduser;
	}

	public void setEcduser(String ecduser) {
		this.ecduser = ecduser;
	}

	public List<EcmFile> getEcmFiles() {
		return this.ecmFiles;
	}

	public void setEcmFiles(List<EcmFile> ecmFiles) {
		this.ecmFiles = ecmFiles;
	}

	public EcmFile addEcmFile(EcmFile ecmFile) {
		getEcmFiles().add(ecmFile);
		ecmFile.setEcdFile(this);

		return ecmFile;
	}

	public EcmFile removeEcmFile(EcmFile ecmFile) {
		getEcmFiles().remove(ecmFile);
		ecmFile.setEcdFile(null);

		return ecmFile;
	}

}