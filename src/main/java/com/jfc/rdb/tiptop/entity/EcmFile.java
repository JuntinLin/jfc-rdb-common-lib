package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the ECM_FILE database table.
 * 製程追蹤檔(ecm_file)
 */
@Entity
@Table(name="ECM_FILE")
@NamedQuery(name="EcmFile.findAll", query="SELECT e FROM EcmFile e")
public class EcmFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EcmFilePK id;

	private String ecm011;

	private String ecm014;

	private String ecm015;

	private Integer ecm02;

	private String ecm05;

	private BigDecimal ecm07;

	private BigDecimal ecm08;

	private BigDecimal ecm09;

	private BigDecimal ecm10;

	private String ecm11;

	private BigDecimal ecm12;

	private String ecm121;

	private BigDecimal ecm13;

	private BigDecimal ecm14;

	private BigDecimal ecm15;

	private BigDecimal ecm16;

	private BigDecimal ecm17;

	private BigDecimal ecm18;

	private BigDecimal ecm19;

	private BigDecimal ecm20;

	private BigDecimal ecm21;

	private BigDecimal ecm22;

	private BigDecimal ecm23;

	private BigDecimal ecm24;

	private String ecm25;

	private String ecm26;

	private BigDecimal ecm27;

	private BigDecimal ecm28;

	private BigDecimal ecm291;

	private BigDecimal ecm292;

	private BigDecimal ecm301;

	private BigDecimal ecm302;

	private BigDecimal ecm303;

	private BigDecimal ecm311;

	private BigDecimal ecm312;

	private BigDecimal ecm313;

	private BigDecimal ecm314;

	private BigDecimal ecm315;

	private BigDecimal ecm316;

	private BigDecimal ecm321;

	private BigDecimal ecm322;

	private BigDecimal ecm34;

	private BigDecimal ecm35;

	private BigDecimal ecm36;

	private BigDecimal ecm37;

	private BigDecimal ecm38;

	private BigDecimal ecm39;

	private BigDecimal ecm40;

	private BigDecimal ecm41;

	private BigDecimal ecm42;

	private String ecm43;

	private String ecm45;

	private BigDecimal ecm49;

	private Object ecm50;

	private Object ecm51;

	private String ecm52;

	private String ecm53;

	private String ecm54;

	private String ecm55;

	private String ecm56;

	private String ecm57;

	private String ecm58;

	private BigDecimal ecm59;

	private BigDecimal ecm60;

	private String ecm61;

	private BigDecimal ecm62;

	private BigDecimal ecm63;

	private BigDecimal ecm64;

	private BigDecimal ecm65;

	private String ecm66;

	private String ecm67;

	private String ecmacti;

	private Object ecmdate;

	private String ecmgrup;

	private String ecmlegal;

	private String ecmmodu;

	private String ecmorig;

	private String ecmoriu;

	private String ecmplant;

	private String ecmslk01;

	private BigDecimal ecmslk02;

	private BigDecimal ecmslk03;

	private BigDecimal ecmslk04;

	private String ecmuser;

	//bi-directional many-to-one association to ImaFile
	@ManyToOne
	//@JoinColumn(name="ECM03_PAR")
	@JoinColumns({
	    @JoinColumn(name = "ECM03_PAR", referencedColumnName = "ima01")
	})
	private ImaFile imaFile;

	//bi-directional many-to-one association to EcaFile
	@ManyToOne
	@JoinColumn(name="ECM06")
	private EcaFile ecaFile;

	//bi-directional many-to-one association to EcdFile
	@ManyToOne
	@JoinColumn(name="ECM04")
	private EcdFile ecdFile;

	//bi-directional many-to-one association to SfbFile
	@ManyToOne
	@JoinColumn(name = "ECM01", referencedColumnName = "SFB01", insertable = false, updatable = false)
	private SfbFile sfbFile;
	
	/*WIP量(ecm301+ecm302+ecm303-ecm311-ecm312-ecm313-ecm314-ecm316)
	 * ecm301	number(15,3)	良品轉入量       (+)	
	 * ecm302	number(15,3)	重工轉入量       (+)	
	 * ecm303	number(15,3)	工單轉入量       (+)	
	 * ecm311	number(15,3)	良品轉出量       (-)	
	 * ecm312	number(15,3)	重工轉出         (-)	
	 * ecm313	number(15,3)	當站報廢量       (-)	
	 * ecm314	number(15,3)	當站下線量(入庫) 	(-)	
	 * ecm315	number(15,3)	Bonus Qty        (-)	
	 * ecm316	number(15,3)	工單轉出量       (-)	
	 * */
	public BigDecimal getWip(){
		BigDecimal wip = new BigDecimal(0);
		wip = wip.add(ecm301);
		wip = wip.add(ecm302);
		wip = wip.add(ecm303);
		wip = wip.subtract(ecm311);
		wip = wip.subtract(ecm312);
		wip = wip.subtract(ecm313);
		wip = wip.subtract(ecm314);
		wip = wip.subtract(ecm316);		
		return wip;
	}
	
	public EcmFile() {
	}

	public EcmFilePK getId() {
		return this.id;
	}

	public void setId(EcmFilePK id) {
		this.id = id;
	}

	public String getEcm011() {
		return this.ecm011;
	}

	public void setEcm011(String ecm011) {
		this.ecm011 = ecm011;
	}

	public String getEcm014() {
		return this.ecm014;
	}

	public void setEcm014(String ecm014) {
		this.ecm014 = ecm014;
	}

	public String getEcm015() {
		return this.ecm015;
	}

	public void setEcm015(String ecm015) {
		this.ecm015 = ecm015;
	}

	public Integer getEcm02() {
		return this.ecm02;
	}

	public void setEcm02(Integer ecm02) {
		this.ecm02 = ecm02;
	}

	public String getEcm05() {
		return this.ecm05;
	}

	public void setEcm05(String ecm05) {
		this.ecm05 = ecm05;
	}

	public BigDecimal getEcm07() {
		return this.ecm07;
	}

	public void setEcm07(BigDecimal ecm07) {
		this.ecm07 = ecm07;
	}

	public BigDecimal getEcm08() {
		return this.ecm08;
	}

	public void setEcm08(BigDecimal ecm08) {
		this.ecm08 = ecm08;
	}

	public BigDecimal getEcm09() {
		return this.ecm09;
	}

	public void setEcm09(BigDecimal ecm09) {
		this.ecm09 = ecm09;
	}

	public BigDecimal getEcm10() {
		return this.ecm10;
	}

	public void setEcm10(BigDecimal ecm10) {
		this.ecm10 = ecm10;
	}

	public String getEcm11() {
		return this.ecm11;
	}

	public void setEcm11(String ecm11) {
		this.ecm11 = ecm11;
	}

	public BigDecimal getEcm12() {
		return this.ecm12;
	}

	public void setEcm12(BigDecimal ecm12) {
		this.ecm12 = ecm12;
	}

	public String getEcm121() {
		return this.ecm121;
	}

	public void setEcm121(String ecm121) {
		this.ecm121 = ecm121;
	}

	public BigDecimal getEcm13() {
		return this.ecm13;
	}

	public void setEcm13(BigDecimal ecm13) {
		this.ecm13 = ecm13;
	}

	public BigDecimal getEcm14() {
		return this.ecm14;
	}

	public void setEcm14(BigDecimal ecm14) {
		this.ecm14 = ecm14;
	}

	public BigDecimal getEcm15() {
		return this.ecm15;
	}

	public void setEcm15(BigDecimal ecm15) {
		this.ecm15 = ecm15;
	}

	public BigDecimal getEcm16() {
		return this.ecm16;
	}

	public void setEcm16(BigDecimal ecm16) {
		this.ecm16 = ecm16;
	}

	public BigDecimal getEcm17() {
		return this.ecm17;
	}

	public void setEcm17(BigDecimal ecm17) {
		this.ecm17 = ecm17;
	}

	public BigDecimal getEcm18() {
		return this.ecm18;
	}

	public void setEcm18(BigDecimal ecm18) {
		this.ecm18 = ecm18;
	}

	public BigDecimal getEcm19() {
		return this.ecm19;
	}

	public void setEcm19(BigDecimal ecm19) {
		this.ecm19 = ecm19;
	}

	public BigDecimal getEcm20() {
		return this.ecm20;
	}

	public void setEcm20(BigDecimal ecm20) {
		this.ecm20 = ecm20;
	}

	public BigDecimal getEcm21() {
		return this.ecm21;
	}

	public void setEcm21(BigDecimal ecm21) {
		this.ecm21 = ecm21;
	}

	public BigDecimal getEcm22() {
		return this.ecm22;
	}

	public void setEcm22(BigDecimal ecm22) {
		this.ecm22 = ecm22;
	}

	public BigDecimal getEcm23() {
		return this.ecm23;
	}

	public void setEcm23(BigDecimal ecm23) {
		this.ecm23 = ecm23;
	}

	public BigDecimal getEcm24() {
		return this.ecm24;
	}

	public void setEcm24(BigDecimal ecm24) {
		this.ecm24 = ecm24;
	}

	public String getEcm25() {
		return this.ecm25;
	}

	public void setEcm25(String ecm25) {
		this.ecm25 = ecm25;
	}

	public String getEcm26() {
		return this.ecm26;
	}

	public void setEcm26(String ecm26) {
		this.ecm26 = ecm26;
	}

	public BigDecimal getEcm27() {
		return this.ecm27;
	}

	public void setEcm27(BigDecimal ecm27) {
		this.ecm27 = ecm27;
	}

	public BigDecimal getEcm28() {
		return this.ecm28;
	}

	public void setEcm28(BigDecimal ecm28) {
		this.ecm28 = ecm28;
	}

	public BigDecimal getEcm291() {
		return this.ecm291;
	}

	public void setEcm291(BigDecimal ecm291) {
		this.ecm291 = ecm291;
	}

	public BigDecimal getEcm292() {
		return this.ecm292;
	}

	public void setEcm292(BigDecimal ecm292) {
		this.ecm292 = ecm292;
	}

	public BigDecimal getEcm301() {
		return this.ecm301;
	}

	public void setEcm301(BigDecimal ecm301) {
		this.ecm301 = ecm301;
	}

	public BigDecimal getEcm302() {
		return this.ecm302;
	}

	public void setEcm302(BigDecimal ecm302) {
		this.ecm302 = ecm302;
	}

	public BigDecimal getEcm303() {
		return this.ecm303;
	}

	public void setEcm303(BigDecimal ecm303) {
		this.ecm303 = ecm303;
	}

	public BigDecimal getEcm311() {
		return this.ecm311;
	}

	public void setEcm311(BigDecimal ecm311) {
		this.ecm311 = ecm311;
	}

	public BigDecimal getEcm312() {
		return this.ecm312;
	}

	public void setEcm312(BigDecimal ecm312) {
		this.ecm312 = ecm312;
	}

	public BigDecimal getEcm313() {
		return this.ecm313;
	}

	public void setEcm313(BigDecimal ecm313) {
		this.ecm313 = ecm313;
	}

	public BigDecimal getEcm314() {
		return this.ecm314;
	}

	public void setEcm314(BigDecimal ecm314) {
		this.ecm314 = ecm314;
	}

	public BigDecimal getEcm315() {
		return this.ecm315;
	}

	public void setEcm315(BigDecimal ecm315) {
		this.ecm315 = ecm315;
	}

	public BigDecimal getEcm316() {
		return this.ecm316;
	}

	public void setEcm316(BigDecimal ecm316) {
		this.ecm316 = ecm316;
	}

	public BigDecimal getEcm321() {
		return this.ecm321;
	}

	public void setEcm321(BigDecimal ecm321) {
		this.ecm321 = ecm321;
	}

	public BigDecimal getEcm322() {
		return this.ecm322;
	}

	public void setEcm322(BigDecimal ecm322) {
		this.ecm322 = ecm322;
	}

	public BigDecimal getEcm34() {
		return this.ecm34;
	}

	public void setEcm34(BigDecimal ecm34) {
		this.ecm34 = ecm34;
	}

	public BigDecimal getEcm35() {
		return this.ecm35;
	}

	public void setEcm35(BigDecimal ecm35) {
		this.ecm35 = ecm35;
	}

	public BigDecimal getEcm36() {
		return this.ecm36;
	}

	public void setEcm36(BigDecimal ecm36) {
		this.ecm36 = ecm36;
	}

	public BigDecimal getEcm37() {
		return this.ecm37;
	}

	public void setEcm37(BigDecimal ecm37) {
		this.ecm37 = ecm37;
	}

	public BigDecimal getEcm38() {
		return this.ecm38;
	}

	public void setEcm38(BigDecimal ecm38) {
		this.ecm38 = ecm38;
	}

	public BigDecimal getEcm39() {
		return this.ecm39;
	}

	public void setEcm39(BigDecimal ecm39) {
		this.ecm39 = ecm39;
	}

	public BigDecimal getEcm40() {
		return this.ecm40;
	}

	public void setEcm40(BigDecimal ecm40) {
		this.ecm40 = ecm40;
	}

	public BigDecimal getEcm41() {
		return this.ecm41;
	}

	public void setEcm41(BigDecimal ecm41) {
		this.ecm41 = ecm41;
	}

	public BigDecimal getEcm42() {
		return this.ecm42;
	}

	public void setEcm42(BigDecimal ecm42) {
		this.ecm42 = ecm42;
	}

	public String getEcm43() {
		return this.ecm43;
	}

	public void setEcm43(String ecm43) {
		this.ecm43 = ecm43;
	}

	public String getEcm45() {
		return this.ecm45;
	}

	public void setEcm45(String ecm45) {
		this.ecm45 = ecm45;
	}

	public BigDecimal getEcm49() {
		return this.ecm49;
	}

	public void setEcm49(BigDecimal ecm49) {
		this.ecm49 = ecm49;
	}

	public Object getEcm50() {
		return this.ecm50;
	}

	public void setEcm50(Object ecm50) {
		this.ecm50 = ecm50;
	}

	public Object getEcm51() {
		return this.ecm51;
	}

	public void setEcm51(Object ecm51) {
		this.ecm51 = ecm51;
	}

	public String getEcm52() {
		return this.ecm52;
	}

	public void setEcm52(String ecm52) {
		this.ecm52 = ecm52;
	}

	public String getEcm53() {
		return this.ecm53;
	}

	public void setEcm53(String ecm53) {
		this.ecm53 = ecm53;
	}

	public String getEcm54() {
		return this.ecm54;
	}

	public void setEcm54(String ecm54) {
		this.ecm54 = ecm54;
	}

	public String getEcm55() {
		return this.ecm55;
	}

	public void setEcm55(String ecm55) {
		this.ecm55 = ecm55;
	}

	public String getEcm56() {
		return this.ecm56;
	}

	public void setEcm56(String ecm56) {
		this.ecm56 = ecm56;
	}

	public String getEcm57() {
		return this.ecm57;
	}

	public void setEcm57(String ecm57) {
		this.ecm57 = ecm57;
	}

	public String getEcm58() {
		return this.ecm58;
	}

	public void setEcm58(String ecm58) {
		this.ecm58 = ecm58;
	}

	public BigDecimal getEcm59() {
		return this.ecm59;
	}

	public void setEcm59(BigDecimal ecm59) {
		this.ecm59 = ecm59;
	}

	public BigDecimal getEcm60() {
		return this.ecm60;
	}

	public void setEcm60(BigDecimal ecm60) {
		this.ecm60 = ecm60;
	}

	public String getEcm61() {
		return this.ecm61;
	}

	public void setEcm61(String ecm61) {
		this.ecm61 = ecm61;
	}

	public BigDecimal getEcm62() {
		return this.ecm62;
	}

	public void setEcm62(BigDecimal ecm62) {
		this.ecm62 = ecm62;
	}

	public BigDecimal getEcm63() {
		return this.ecm63;
	}

	public void setEcm63(BigDecimal ecm63) {
		this.ecm63 = ecm63;
	}

	public BigDecimal getEcm64() {
		return this.ecm64;
	}

	public void setEcm64(BigDecimal ecm64) {
		this.ecm64 = ecm64;
	}

	public BigDecimal getEcm65() {
		return this.ecm65;
	}

	public void setEcm65(BigDecimal ecm65) {
		this.ecm65 = ecm65;
	}

	public String getEcm66() {
		return this.ecm66;
	}

	public void setEcm66(String ecm66) {
		this.ecm66 = ecm66;
	}

	public String getEcm67() {
		return this.ecm67;
	}

	public void setEcm67(String ecm67) {
		this.ecm67 = ecm67;
	}

	public String getEcmacti() {
		return this.ecmacti;
	}

	public void setEcmacti(String ecmacti) {
		this.ecmacti = ecmacti;
	}

	public Object getEcmdate() {
		return this.ecmdate;
	}

	public void setEcmdate(Object ecmdate) {
		this.ecmdate = ecmdate;
	}

	public String getEcmgrup() {
		return this.ecmgrup;
	}

	public void setEcmgrup(String ecmgrup) {
		this.ecmgrup = ecmgrup;
	}

	public String getEcmlegal() {
		return this.ecmlegal;
	}

	public void setEcmlegal(String ecmlegal) {
		this.ecmlegal = ecmlegal;
	}

	public String getEcmmodu() {
		return this.ecmmodu;
	}

	public void setEcmmodu(String ecmmodu) {
		this.ecmmodu = ecmmodu;
	}

	public String getEcmorig() {
		return this.ecmorig;
	}

	public void setEcmorig(String ecmorig) {
		this.ecmorig = ecmorig;
	}

	public String getEcmoriu() {
		return this.ecmoriu;
	}

	public void setEcmoriu(String ecmoriu) {
		this.ecmoriu = ecmoriu;
	}

	public String getEcmplant() {
		return this.ecmplant;
	}

	public void setEcmplant(String ecmplant) {
		this.ecmplant = ecmplant;
	}

	public String getEcmslk01() {
		return this.ecmslk01;
	}

	public void setEcmslk01(String ecmslk01) {
		this.ecmslk01 = ecmslk01;
	}

	public BigDecimal getEcmslk02() {
		return this.ecmslk02;
	}

	public void setEcmslk02(BigDecimal ecmslk02) {
		this.ecmslk02 = ecmslk02;
	}

	public BigDecimal getEcmslk03() {
		return this.ecmslk03;
	}

	public void setEcmslk03(BigDecimal ecmslk03) {
		this.ecmslk03 = ecmslk03;
	}

	public BigDecimal getEcmslk04() {
		return this.ecmslk04;
	}

	public void setEcmslk04(BigDecimal ecmslk04) {
		this.ecmslk04 = ecmslk04;
	}

	public String getEcmuser() {
		return this.ecmuser;
	}

	public void setEcmuser(String ecmuser) {
		this.ecmuser = ecmuser;
	}

	public ImaFile getImaFile() {
		return this.imaFile;
	}

	public void setImaFile(ImaFile imaFile) {
		this.imaFile = imaFile;
	}

	public EcaFile getEcaFile() {
		return this.ecaFile;
	}

	public void setEcaFile(EcaFile ecaFile) {
		this.ecaFile = ecaFile;
	}

	public EcdFile getEcdFile() {
		return this.ecdFile;
	}

	public void setEcdFile(EcdFile ecdFile) {
		this.ecdFile = ecdFile;
	}

	public SfbFile getSfbFile() {
		return this.sfbFile;
	}

	public void setSfbFile(SfbFile sfbFile) {
		this.sfbFile = sfbFile;
	}
	
	@Override
	public String toString() {
		/* ecm01	varchar2(20)	工單編號
		 * ecm02	number(5)	工單型態	工單型態1 : 一般工單5 : 再加工工單7 : 廠外加工工單
		 * ecm03	number(5)	製程序號	製程序號料件被製作程序過程中的生產活動順序編號
		 * ecm06	varchar2(10)	工作站編號	工作站編號該生產程序/作業在何一工作站被生產
		 */
		return String.format("ECM_FILE[ecm01='%s', ecm02=%d, ecm03=%d, ecm06='%s', wip=%s]", this.id.getEcm01(), this.ecm02, this.id.getEcm03(), this.ecaFile.getEca01(), this.getWip());
	}

}