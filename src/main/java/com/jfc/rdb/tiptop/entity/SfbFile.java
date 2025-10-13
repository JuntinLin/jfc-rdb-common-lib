package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the SFB_FILE database table.
 * 工單檔(sfb_file)
 */
@Entity
@Table(name="SFB_FILE")
@NamedQuery(name="SfbFile.findAll", query="SELECT s FROM SfbFile s")
public class SfbFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String sfb01;//工單編號

	private int sfb02;//工單型態	工單型態 儲存該工單所屬類別型態 正確值 1/2/5/7/11/12/13/15 1: 一般工單 5: 再加工工單 7: 委外工單 8: 重工委外工單 #Add By Snow 11: 拆件式工單 13: 預測工單 15: 試產工單 

	private String sfb03;//在製品會計科目	在製品會計科目  aag01
	
	private String sfb04;//工單狀態	工單狀態 儲存該工單目前處理階段狀況 正確值 1/2/3/4/5/6/7/8 1: 確認生產工單(firm plan) 2: 工單已發放,料表尚未列印 3: 工單已發放,料表已列印 4: 工單已發料 5: 在製過程中 6: 工單已完工,進入F.Q.C 7: 完工入庫 8: 結案 
	
	private String sfb05;//料件編號	料件編號   ima01 儲存該工單將投入生產料件
	
	private String sfb06;//使用製程編號	使用製程編號  ecu01 儲存該工單將投入生產料件時所用的製程編 號
	
	private String sfb07;//版本	版本 儲存工單投入生產的料件版本

	private Object sfb071;

	private BigDecimal sfb08;//生產數量	生產數量 預計投入生產的數量，單位為料件生產單位
	
	private BigDecimal sfb081;//已發料套數	
	
	private BigDecimal sfb09;//完工入庫數量	完工入庫數量 儲存該工單已完工入庫數量

	private BigDecimal sfb10;

	private String sfb100;

	private String sfb1001;

	private String sfb1002;

	private String sfb1003;

	private BigDecimal sfb101;

	private String sfb102;

	private BigDecimal sfb103;

	private String sfb104;

	private BigDecimal sfb11;

	private BigDecimal sfb111;

	private BigDecimal sfb12;

	private BigDecimal sfb121;

	private BigDecimal sfb122;

	private Object sfb13;

	private String sfb14;

	private Object sfb15;

	private String sfb16;

	private BigDecimal sfb17;

	private Object sfb18;

	private String sfb19;

	private Object sfb20;

	private String sfb21;

	private String sfb22;//訂單編號/預測工單編號	訂單編號/預測工單編號 儲存該工單之指定生產供給的訂單單號
	
	private BigDecimal sfb221;//訂單項次/預測工單項次	訂單項次/預測工單項次 儲存該工單之指定生產供給的訂單項次

	private String sfb222;

	private String sfb223;

	private String sfb224;

	private String sfb23;

	private String sfb24;

	private Object sfb25;

	private Object sfb251;

	private String sfb26;

	private String sfb27;

	private String sfb271;

	private String sfb28;

	private String sfb29;

	private String sfb30;

	private String sfb31;

	private String sfb32;

	private String sfb33;

	private BigDecimal sfb34;

	private String sfb35;

	private Object sfb36;

	private Object sfb37;

	private Object sfb38;

	private String sfb39;

	private BigDecimal sfb40;

	private String sfb41;

	private BigDecimal sfb42;

	private String sfb43;

	private String sfb44;

	private String sfb50;

	private String sfb51;

	private Object sfb81;

	private String sfb82;

	private String sfb85;

	private String sfb86;

	private String sfb87;

	private String sfb88;

	private String sfb89;

	private String sfb91;

	private String sfb919;

	private BigDecimal sfb92;

	private String sfb93;

	private String sfb94;

	private String sfb95;

	private String sfb96;

	private String sfb97;

	private String sfb98;

	private String sfb99;

	private String sfbacti;

	private Object sfbdate;

	private String sfbgrup;

	private String sfblegal;

	private String sfbmksg;

	private String sfbmodu;

	private String sfborig;

	private String sfboriu;

	private String sfbplant;

	private String sfbud01;

	private String sfbud02;

	private String sfbud03;

	private String sfbud04;

	private String sfbud05;

	private String sfbud06;

	private BigDecimal sfbud07;

	private BigDecimal sfbud08;

	private BigDecimal sfbud09;

	private BigDecimal sfbud10;

	private BigDecimal sfbud11;

	private BigDecimal sfbud12;

	private Object sfbud13;

	private Object sfbud14;

	private Object sfbud15;

	private String sfbuser;

	@Column(name="TA_SFB01")
	private String taSfb01;

	@Column(name="TA_SFB02")
	private String taSfb02;

	@Column(name="TA_SFB03")
	private String taSfb03;

	@Column(name="TA_SFB04")
	private String taSfb04;

	@Column(name="TA_SFB05")
	private String taSfb05;

	@Column(name="TA_SFB06")
	private String taSfb06;

	@Column(name="TA_SFB07")
	private String taSfb07;

	@Column(name="TA_SFB08")
	private String taSfb08;

	@Column(name="TA_SFB09")
	private String taSfb09;

	@Column(name="TA_SFB10")
	private String taSfb10;

	@Column(name="TA_SFB11")
	private String taSfb11;

	@Column(name="TA_SFB12")
	private String taSfb12;

	@Column(name="TA_SFB13")
	private String taSfb13;

	//bi-directional many-to-one association to EcmFile
	@OneToMany(mappedBy="sfbFile")
	private List<EcmFile> ecmFiles;

	public SfbFile() {
	}

	public String getSfb01() {
		return this.sfb01;
	}

	public void setSfb01(String sfb01) {
		this.sfb01 = sfb01;
	}

	public int getSfb02() {
		return this.sfb02;
	}

	public void setSfb02(int sfb02) {
		this.sfb02 = sfb02;
	}

	public String getSfb03() {
		return this.sfb03;
	}

	public void setSfb03(String sfb03) {
		this.sfb03 = sfb03;
	}

	public String getSfb04() {
		return this.sfb04;
	}

	public void setSfb04(String sfb04) {
		this.sfb04 = sfb04;
	}

	public String getSfb05() {
		return this.sfb05;
	}

	public void setSfb05(String sfb05) {
		this.sfb05 = sfb05;
	}

	public String getSfb06() {
		return this.sfb06;
	}

	public void setSfb06(String sfb06) {
		this.sfb06 = sfb06;
	}

	public String getSfb07() {
		return this.sfb07;
	}

	public void setSfb07(String sfb07) {
		this.sfb07 = sfb07;
	}

	public Object getSfb071() {
		return this.sfb071;
	}

	public void setSfb071(Object sfb071) {
		this.sfb071 = sfb071;
	}

	public BigDecimal getSfb08() {
		return this.sfb08;
	}

	public void setSfb08(BigDecimal sfb08) {
		this.sfb08 = sfb08;
	}

	public BigDecimal getSfb081() {
		return this.sfb081;
	}

	public void setSfb081(BigDecimal sfb081) {
		this.sfb081 = sfb081;
	}

	public BigDecimal getSfb09() {
		return this.sfb09;
	}

	public void setSfb09(BigDecimal sfb09) {
		this.sfb09 = sfb09;
	}

	public BigDecimal getSfb10() {
		return this.sfb10;
	}

	public void setSfb10(BigDecimal sfb10) {
		this.sfb10 = sfb10;
	}

	public String getSfb100() {
		return this.sfb100;
	}

	public void setSfb100(String sfb100) {
		this.sfb100 = sfb100;
	}

	public String getSfb1001() {
		return this.sfb1001;
	}

	public void setSfb1001(String sfb1001) {
		this.sfb1001 = sfb1001;
	}

	public String getSfb1002() {
		return this.sfb1002;
	}

	public void setSfb1002(String sfb1002) {
		this.sfb1002 = sfb1002;
	}

	public String getSfb1003() {
		return this.sfb1003;
	}

	public void setSfb1003(String sfb1003) {
		this.sfb1003 = sfb1003;
	}

	public BigDecimal getSfb101() {
		return this.sfb101;
	}

	public void setSfb101(BigDecimal sfb101) {
		this.sfb101 = sfb101;
	}

	public String getSfb102() {
		return this.sfb102;
	}

	public void setSfb102(String sfb102) {
		this.sfb102 = sfb102;
	}

	public BigDecimal getSfb103() {
		return this.sfb103;
	}

	public void setSfb103(BigDecimal sfb103) {
		this.sfb103 = sfb103;
	}

	public String getSfb104() {
		return this.sfb104;
	}

	public void setSfb104(String sfb104) {
		this.sfb104 = sfb104;
	}

	public BigDecimal getSfb11() {
		return this.sfb11;
	}

	public void setSfb11(BigDecimal sfb11) {
		this.sfb11 = sfb11;
	}

	public BigDecimal getSfb111() {
		return this.sfb111;
	}

	public void setSfb111(BigDecimal sfb111) {
		this.sfb111 = sfb111;
	}

	public BigDecimal getSfb12() {
		return this.sfb12;
	}

	public void setSfb12(BigDecimal sfb12) {
		this.sfb12 = sfb12;
	}

	public BigDecimal getSfb121() {
		return this.sfb121;
	}

	public void setSfb121(BigDecimal sfb121) {
		this.sfb121 = sfb121;
	}

	public BigDecimal getSfb122() {
		return this.sfb122;
	}

	public void setSfb122(BigDecimal sfb122) {
		this.sfb122 = sfb122;
	}

	public Object getSfb13() {
		return this.sfb13;
	}

	public void setSfb13(Object sfb13) {
		this.sfb13 = sfb13;
	}

	public String getSfb14() {
		return this.sfb14;
	}

	public void setSfb14(String sfb14) {
		this.sfb14 = sfb14;
	}

	public Object getSfb15() {
		return this.sfb15;
	}

	public void setSfb15(Object sfb15) {
		this.sfb15 = sfb15;
	}

	public String getSfb16() {
		return this.sfb16;
	}

	public void setSfb16(String sfb16) {
		this.sfb16 = sfb16;
	}

	public BigDecimal getSfb17() {
		return this.sfb17;
	}

	public void setSfb17(BigDecimal sfb17) {
		this.sfb17 = sfb17;
	}

	public Object getSfb18() {
		return this.sfb18;
	}

	public void setSfb18(Object sfb18) {
		this.sfb18 = sfb18;
	}

	public String getSfb19() {
		return this.sfb19;
	}

	public void setSfb19(String sfb19) {
		this.sfb19 = sfb19;
	}

	public Object getSfb20() {
		return this.sfb20;
	}

	public void setSfb20(Object sfb20) {
		this.sfb20 = sfb20;
	}

	public String getSfb21() {
		return this.sfb21;
	}

	public void setSfb21(String sfb21) {
		this.sfb21 = sfb21;
	}

	public String getSfb22() {
		return this.sfb22;
	}

	public void setSfb22(String sfb22) {
		this.sfb22 = sfb22;
	}

	public BigDecimal getSfb221() {
		return this.sfb221;
	}

	public void setSfb221(BigDecimal sfb221) {
		this.sfb221 = sfb221;
	}

	public String getSfb222() {
		return this.sfb222;
	}

	public void setSfb222(String sfb222) {
		this.sfb222 = sfb222;
	}

	public String getSfb223() {
		return this.sfb223;
	}

	public void setSfb223(String sfb223) {
		this.sfb223 = sfb223;
	}

	public String getSfb224() {
		return this.sfb224;
	}

	public void setSfb224(String sfb224) {
		this.sfb224 = sfb224;
	}

	public String getSfb23() {
		return this.sfb23;
	}

	public void setSfb23(String sfb23) {
		this.sfb23 = sfb23;
	}

	public String getSfb24() {
		return this.sfb24;
	}

	public void setSfb24(String sfb24) {
		this.sfb24 = sfb24;
	}

	public Object getSfb25() {
		return this.sfb25;
	}

	public void setSfb25(Object sfb25) {
		this.sfb25 = sfb25;
	}

	public Object getSfb251() {
		return this.sfb251;
	}

	public void setSfb251(Object sfb251) {
		this.sfb251 = sfb251;
	}

	public String getSfb26() {
		return this.sfb26;
	}

	public void setSfb26(String sfb26) {
		this.sfb26 = sfb26;
	}

	public String getSfb27() {
		return this.sfb27;
	}

	public void setSfb27(String sfb27) {
		this.sfb27 = sfb27;
	}

	public String getSfb271() {
		return this.sfb271;
	}

	public void setSfb271(String sfb271) {
		this.sfb271 = sfb271;
	}

	public String getSfb28() {
		return this.sfb28;
	}

	public void setSfb28(String sfb28) {
		this.sfb28 = sfb28;
	}

	public String getSfb29() {
		return this.sfb29;
	}

	public void setSfb29(String sfb29) {
		this.sfb29 = sfb29;
	}

	public String getSfb30() {
		return this.sfb30;
	}

	public void setSfb30(String sfb30) {
		this.sfb30 = sfb30;
	}

	public String getSfb31() {
		return this.sfb31;
	}

	public void setSfb31(String sfb31) {
		this.sfb31 = sfb31;
	}

	public String getSfb32() {
		return this.sfb32;
	}

	public void setSfb32(String sfb32) {
		this.sfb32 = sfb32;
	}

	public String getSfb33() {
		return this.sfb33;
	}

	public void setSfb33(String sfb33) {
		this.sfb33 = sfb33;
	}

	public BigDecimal getSfb34() {
		return this.sfb34;
	}

	public void setSfb34(BigDecimal sfb34) {
		this.sfb34 = sfb34;
	}

	public String getSfb35() {
		return this.sfb35;
	}

	public void setSfb35(String sfb35) {
		this.sfb35 = sfb35;
	}

	public Object getSfb36() {
		return this.sfb36;
	}

	public void setSfb36(Object sfb36) {
		this.sfb36 = sfb36;
	}

	public Object getSfb37() {
		return this.sfb37;
	}

	public void setSfb37(Object sfb37) {
		this.sfb37 = sfb37;
	}

	public Object getSfb38() {
		return this.sfb38;
	}

	public void setSfb38(Object sfb38) {
		this.sfb38 = sfb38;
	}

	public String getSfb39() {
		return this.sfb39;
	}

	public void setSfb39(String sfb39) {
		this.sfb39 = sfb39;
	}

	public BigDecimal getSfb40() {
		return this.sfb40;
	}

	public void setSfb40(BigDecimal sfb40) {
		this.sfb40 = sfb40;
	}

	public String getSfb41() {
		return this.sfb41;
	}

	public void setSfb41(String sfb41) {
		this.sfb41 = sfb41;
	}

	public BigDecimal getSfb42() {
		return this.sfb42;
	}

	public void setSfb42(BigDecimal sfb42) {
		this.sfb42 = sfb42;
	}

	public String getSfb43() {
		return this.sfb43;
	}

	public void setSfb43(String sfb43) {
		this.sfb43 = sfb43;
	}

	public String getSfb44() {
		return this.sfb44;
	}

	public void setSfb44(String sfb44) {
		this.sfb44 = sfb44;
	}

	public String getSfb50() {
		return this.sfb50;
	}

	public void setSfb50(String sfb50) {
		this.sfb50 = sfb50;
	}

	public String getSfb51() {
		return this.sfb51;
	}

	public void setSfb51(String sfb51) {
		this.sfb51 = sfb51;
	}

	public Object getSfb81() {
		return this.sfb81;
	}

	public void setSfb81(Object sfb81) {
		this.sfb81 = sfb81;
	}

	public String getSfb82() {
		return this.sfb82;
	}

	public void setSfb82(String sfb82) {
		this.sfb82 = sfb82;
	}

	public String getSfb85() {
		return this.sfb85;
	}

	public void setSfb85(String sfb85) {
		this.sfb85 = sfb85;
	}

	public String getSfb86() {
		return this.sfb86;
	}

	public void setSfb86(String sfb86) {
		this.sfb86 = sfb86;
	}

	public String getSfb87() {
		return this.sfb87;
	}

	public void setSfb87(String sfb87) {
		this.sfb87 = sfb87;
	}

	public String getSfb88() {
		return this.sfb88;
	}

	public void setSfb88(String sfb88) {
		this.sfb88 = sfb88;
	}

	public String getSfb89() {
		return this.sfb89;
	}

	public void setSfb89(String sfb89) {
		this.sfb89 = sfb89;
	}

	public String getSfb91() {
		return this.sfb91;
	}

	public void setSfb91(String sfb91) {
		this.sfb91 = sfb91;
	}

	public String getSfb919() {
		return this.sfb919;
	}

	public void setSfb919(String sfb919) {
		this.sfb919 = sfb919;
	}

	public BigDecimal getSfb92() {
		return this.sfb92;
	}

	public void setSfb92(BigDecimal sfb92) {
		this.sfb92 = sfb92;
	}

	public String getSfb93() {
		return this.sfb93;
	}

	public void setSfb93(String sfb93) {
		this.sfb93 = sfb93;
	}

	public String getSfb94() {
		return this.sfb94;
	}

	public void setSfb94(String sfb94) {
		this.sfb94 = sfb94;
	}

	public String getSfb95() {
		return this.sfb95;
	}

	public void setSfb95(String sfb95) {
		this.sfb95 = sfb95;
	}

	public String getSfb96() {
		return this.sfb96;
	}

	public void setSfb96(String sfb96) {
		this.sfb96 = sfb96;
	}

	public String getSfb97() {
		return this.sfb97;
	}

	public void setSfb97(String sfb97) {
		this.sfb97 = sfb97;
	}

	public String getSfb98() {
		return this.sfb98;
	}

	public void setSfb98(String sfb98) {
		this.sfb98 = sfb98;
	}

	public String getSfb99() {
		return this.sfb99;
	}

	public void setSfb99(String sfb99) {
		this.sfb99 = sfb99;
	}

	public String getSfbacti() {
		return this.sfbacti;
	}

	public void setSfbacti(String sfbacti) {
		this.sfbacti = sfbacti;
	}

	public Object getSfbdate() {
		return this.sfbdate;
	}

	public void setSfbdate(Object sfbdate) {
		this.sfbdate = sfbdate;
	}

	public String getSfbgrup() {
		return this.sfbgrup;
	}

	public void setSfbgrup(String sfbgrup) {
		this.sfbgrup = sfbgrup;
	}

	public String getSfblegal() {
		return this.sfblegal;
	}

	public void setSfblegal(String sfblegal) {
		this.sfblegal = sfblegal;
	}

	public String getSfbmksg() {
		return this.sfbmksg;
	}

	public void setSfbmksg(String sfbmksg) {
		this.sfbmksg = sfbmksg;
	}

	public String getSfbmodu() {
		return this.sfbmodu;
	}

	public void setSfbmodu(String sfbmodu) {
		this.sfbmodu = sfbmodu;
	}

	public String getSfborig() {
		return this.sfborig;
	}

	public void setSfborig(String sfborig) {
		this.sfborig = sfborig;
	}

	public String getSfboriu() {
		return this.sfboriu;
	}

	public void setSfboriu(String sfboriu) {
		this.sfboriu = sfboriu;
	}

	public String getSfbplant() {
		return this.sfbplant;
	}

	public void setSfbplant(String sfbplant) {
		this.sfbplant = sfbplant;
	}

	public String getSfbud01() {
		return this.sfbud01;
	}

	public void setSfbud01(String sfbud01) {
		this.sfbud01 = sfbud01;
	}

	public String getSfbud02() {
		return this.sfbud02;
	}

	public void setSfbud02(String sfbud02) {
		this.sfbud02 = sfbud02;
	}

	public String getSfbud03() {
		return this.sfbud03;
	}

	public void setSfbud03(String sfbud03) {
		this.sfbud03 = sfbud03;
	}

	public String getSfbud04() {
		return this.sfbud04;
	}

	public void setSfbud04(String sfbud04) {
		this.sfbud04 = sfbud04;
	}

	public String getSfbud05() {
		return this.sfbud05;
	}

	public void setSfbud05(String sfbud05) {
		this.sfbud05 = sfbud05;
	}

	public String getSfbud06() {
		return this.sfbud06;
	}

	public void setSfbud06(String sfbud06) {
		this.sfbud06 = sfbud06;
	}

	public BigDecimal getSfbud07() {
		return this.sfbud07;
	}

	public void setSfbud07(BigDecimal sfbud07) {
		this.sfbud07 = sfbud07;
	}

	public BigDecimal getSfbud08() {
		return this.sfbud08;
	}

	public void setSfbud08(BigDecimal sfbud08) {
		this.sfbud08 = sfbud08;
	}

	public BigDecimal getSfbud09() {
		return this.sfbud09;
	}

	public void setSfbud09(BigDecimal sfbud09) {
		this.sfbud09 = sfbud09;
	}

	public BigDecimal getSfbud10() {
		return this.sfbud10;
	}

	public void setSfbud10(BigDecimal sfbud10) {
		this.sfbud10 = sfbud10;
	}

	public BigDecimal getSfbud11() {
		return this.sfbud11;
	}

	public void setSfbud11(BigDecimal sfbud11) {
		this.sfbud11 = sfbud11;
	}

	public BigDecimal getSfbud12() {
		return this.sfbud12;
	}

	public void setSfbud12(BigDecimal sfbud12) {
		this.sfbud12 = sfbud12;
	}

	public Object getSfbud13() {
		return this.sfbud13;
	}

	public void setSfbud13(Object sfbud13) {
		this.sfbud13 = sfbud13;
	}

	public Object getSfbud14() {
		return this.sfbud14;
	}

	public void setSfbud14(Object sfbud14) {
		this.sfbud14 = sfbud14;
	}

	public Object getSfbud15() {
		return this.sfbud15;
	}

	public void setSfbud15(Object sfbud15) {
		this.sfbud15 = sfbud15;
	}

	public String getSfbuser() {
		return this.sfbuser;
	}

	public void setSfbuser(String sfbuser) {
		this.sfbuser = sfbuser;
	}

	public String getTaSfb01() {
		return this.taSfb01;
	}

	public void setTaSfb01(String taSfb01) {
		this.taSfb01 = taSfb01;
	}

	public String getTaSfb02() {
		return this.taSfb02;
	}

	public void setTaSfb02(String taSfb02) {
		this.taSfb02 = taSfb02;
	}

	public String getTaSfb03() {
		return this.taSfb03;
	}

	public void setTaSfb03(String taSfb03) {
		this.taSfb03 = taSfb03;
	}

	public String getTaSfb04() {
		return this.taSfb04;
	}

	public void setTaSfb04(String taSfb04) {
		this.taSfb04 = taSfb04;
	}

	public String getTaSfb05() {
		return this.taSfb05;
	}

	public void setTaSfb05(String taSfb05) {
		this.taSfb05 = taSfb05;
	}

	public String getTaSfb06() {
		return this.taSfb06;
	}

	public void setTaSfb06(String taSfb06) {
		this.taSfb06 = taSfb06;
	}

	public String getTaSfb07() {
		return this.taSfb07;
	}

	public void setTaSfb07(String taSfb07) {
		this.taSfb07 = taSfb07;
	}

	public String getTaSfb08() {
		return this.taSfb08;
	}

	public void setTaSfb08(String taSfb08) {
		this.taSfb08 = taSfb08;
	}

	public String getTaSfb09() {
		return this.taSfb09;
	}

	public void setTaSfb09(String taSfb09) {
		this.taSfb09 = taSfb09;
	}

	public String getTaSfb10() {
		return this.taSfb10;
	}

	public void setTaSfb10(String taSfb10) {
		this.taSfb10 = taSfb10;
	}

	public String getTaSfb11() {
		return this.taSfb11;
	}

	public void setTaSfb11(String taSfb11) {
		this.taSfb11 = taSfb11;
	}

	public String getTaSfb12() {
		return this.taSfb12;
	}

	public void setTaSfb12(String taSfb12) {
		this.taSfb12 = taSfb12;
	}

	public String getTaSfb13() {
		return this.taSfb13;
	}

	public void setTaSfb13(String taSfb13) {
		this.taSfb13 = taSfb13;
	}

	public List<EcmFile> getEcmFiles() {
		return this.ecmFiles;
	}

	public void setEcmFiles(List<EcmFile> ecmFiles) {
		this.ecmFiles = ecmFiles;
	}

	public EcmFile addEcmFile(EcmFile ecmFile) {
		getEcmFiles().add(ecmFile);
		ecmFile.setSfbFile(this);

		return ecmFile;
	}

	public EcmFile removeEcmFile(EcmFile ecmFile) {
		getEcmFiles().remove(ecmFile);
		ecmFile.setSfbFile(null);

		return ecmFile;
	}

}