package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the IMA_FILE database table.
 * 
 */
@Entity
@Table(name = "IMA_FILE")
@NamedQuery(name = "ImaFile.findAll", query = "SELECT i FROM ImaFile i")
//public class ImaFile extends AbstractEntity implements Serializable {
public class ImaFile implements Serializable {	
	public ImaFile(String ima01, String ima02, String ima021) {
		super();
		this.ima01 = ima01;
		this.ima02 = ima02;
		this.ima021 = ima021;
	}

	private static final long serialVersionUID = 1L;
	//ima01	varchar2(40)	料件編號	料件編號料件存在系統中代表編號, 是唯一的
	//ima02	varchar2(120)	品名	品名規格描述該料件的品名規格, 如有需要進一步描述, 則可利用品名規格額外說明資料檔記錄
	//ima021	varchar2(120)	規格	規格                   (97/08/18

	@Id
	@Column(name = "ima01")
	private String ima01;

	private String ima02;

	private String ima021;

	private BigDecimal ima022;

	private String ima03;

	private String ima04;

	private String ima05;

	private String ima06;

	private String ima07;

	private String ima08;

	//SNV
	private String ima09;//ima09	varchar2(10)	其他分群碼 一	其他分群碼 一提供給使用者, 對料件分群/分類除在分群碼中定義外, 可提供給其它的定義方式, 以供 管理/匯集報表 使用將可分成 一/二/三/四 四個欄位以供交互定義使用使用者可自行定義預設值 依分群碼類別預設 或 空白

	//產品線 
	private String ima10;//ima10	varchar2(10)	其他分群碼 二	其他分群碼 二提供給使用者, 對料件分群/分類除在分群碼中定義外, 可提供給其它的定義方式, 以供 管理/匯集報表 使用將可分成 一/二/三/四 四個欄位以供交互定義使用使用者可自行定義預設值 依分群碼類別預設 或 空白

	private String ima100;

	private String ima1001;

	private String ima1002;

	private String ima1003;

	private String ima1004;

	private String ima1005;

	private String ima1006;

	private String ima1007;

	private String ima1008;

	private String ima1009;

	private String ima101;

	private String ima1010;

	private BigDecimal ima1011;

	private Object ima1012;

	private Object ima1013;

	private String ima1014;

	private Object ima1015;

	private String ima1016;

	private BigDecimal ima1017;

	private BigDecimal ima1018;

	private BigDecimal ima1019;

	private String ima102;

	private BigDecimal ima1020;

	private BigDecimal ima1021;

	private BigDecimal ima1022;

	private BigDecimal ima1023;

	private BigDecimal ima1024;

	private BigDecimal ima1025;

	private BigDecimal ima1026;

	private BigDecimal ima1027;

	private BigDecimal ima1028;

	private String ima1029;

	private String ima103;

	private String ima1030;

	private BigDecimal ima104;

	private String ima105;

	private String ima106;

	private String ima107;

	private String ima108;

	private String ima109;

	private String ima11;

	private String ima110;

	private String ima111;

	private String ima12;

	private String ima120;

	private BigDecimal ima121;

	private BigDecimal ima122;

	private BigDecimal ima123;

	private BigDecimal ima124;

	private BigDecimal ima125;

	private BigDecimal ima126;

	private BigDecimal ima127;

	private BigDecimal ima128;

	private BigDecimal ima129;

	private String ima13;

	private String ima130;

	private String ima131;

	private String ima132;

	private String ima1321;

	private String ima133;

	private String ima134;

	private String ima135;

	private String ima136;

	private String ima137;

	private String ima138;

	private String ima139;

	private String ima14;

	private String ima140;

	private Object ima1401;

	private String ima141;

	private BigDecimal ima142;

	private BigDecimal ima143;

	private String ima144;

	private String ima145;

	private String ima146;

	private String ima147;

	private BigDecimal ima148;

	private String ima149;

	private String ima1491;

	private String ima15;

	private String ima150;

	private String ima151;

	private String ima152;

	private BigDecimal ima153;

	private String ima154;

	private String ima155;

	private String ima156;

	private String ima157;

	private String ima158;

	private String ima159;

	private BigDecimal ima16;

	private String ima160;

	private String ima161;

	private String ima162;

	private String ima163;

	private String ima1631;

	private String ima164;

	private String ima1641;

	private String ima165;

	private String ima17;

	@Column(name = "IMA17_FAC")
	private BigDecimal ima17Fac;

	private BigDecimal ima171;

	private BigDecimal ima172;

	private BigDecimal ima18;

	private String ima19;

	private String ima1911;

	private String ima1912;

	private BigDecimal ima1913;

	private String ima1914;

	private String ima1915;

	private String ima1916;

	private String ima1919;

	private BigDecimal ima20;

	private String ima21;

	private BigDecimal ima22;

	private String ima23;

	private String ima24;

	private String ima25;

	private String ima251;

	private BigDecimal ima26;

	private BigDecimal ima261;

	private BigDecimal ima262;

	private BigDecimal ima27;//	number(15,3)	安全庫存量	安全庫存量當在使用上, 希望在考量 MPS/MRP 產生計劃訂單(PLANNED ORDER)時, 在需求數量上能加上對庫存數量亦能維持一定的水準的庫存策略, 則可使用安全庫存量 或 安全庫存期間的作法安全庫存量是以一固定數量來達成上述庫存策略預設值 依分群碼類別預設 或 '0'

	private BigDecimal ima271;

	private BigDecimal ima28;

	private Object ima29;

	private Object ima30;

	private String ima31;

	@Column(name = "IMA31_FAC")
	private BigDecimal ima31Fac;

	private BigDecimal ima32;

	private BigDecimal ima33;

	private String ima34;

	private String ima35;

	private String ima36;

	private String ima37;

	private BigDecimal ima38;

	private String ima39;

	private String ima391;

	private BigDecimal ima40;

	private BigDecimal ima41;

	private String ima42;

	private String ima43;

	private String ima44;

	@Column(name = "IMA44_FAC")
	private BigDecimal ima44Fac;

	private BigDecimal ima45;

	private BigDecimal ima46;

	private BigDecimal ima47;

	private BigDecimal ima48;

	private BigDecimal ima49;

	private BigDecimal ima491;

	private BigDecimal ima50;

	private BigDecimal ima51;

	private BigDecimal ima52;

	private BigDecimal ima53;

	private BigDecimal ima531;

	private Object ima532;

	private String ima54;

	private String ima55;

	@Column(name = "IMA55_FAC")
	private BigDecimal ima55Fac;

	private BigDecimal ima56;

	private BigDecimal ima561;

	private BigDecimal ima562;

	private BigDecimal ima57;

	private String ima571;

	private BigDecimal ima58;

	private BigDecimal ima59;

	private BigDecimal ima60;

	private BigDecimal ima601;

	private BigDecimal ima61;

	private BigDecimal ima62;

	private String ima63;

	@Column(name = "IMA63_FAC")
	private BigDecimal ima63Fac;

	private BigDecimal ima64;

	private BigDecimal ima641;

	private BigDecimal ima65;

	private BigDecimal ima66;

	private String ima67;

	private BigDecimal ima68;

	private BigDecimal ima69;

	private String ima70;

	private BigDecimal ima71;

	private BigDecimal ima72;

	private BigDecimal ima721;

	private java.util.Date ima73;

	private Object ima74;

	private String ima75;

	private String ima76;

	private BigDecimal ima77;

	private BigDecimal ima78;

	private BigDecimal ima79;

	private BigDecimal ima80;

	private BigDecimal ima81;

	private BigDecimal ima82;

	private BigDecimal ima83;

	private BigDecimal ima84;

	private BigDecimal ima85;

	private String ima851;

	private String ima852;

	private String ima853;

	private String ima86;

	@Column(name = "IMA86_FAC")
	private BigDecimal ima86Fac;

	private String ima87;

	private BigDecimal ima871;

	private String ima872;

	private BigDecimal ima873;

	private String ima874;

	private BigDecimal ima88;

	private Object ima881;

	private BigDecimal ima89;

	private BigDecimal ima90;

	private Object ima901;

	private Object ima902;

	private Object ima9021;

	private String ima903;

	private String ima904;

	private String ima905;

	private String ima906;

	private String ima907;

	private String ima908;

	private BigDecimal ima909;

	private BigDecimal ima91;

	private String ima910;

	private String ima911;

	private BigDecimal ima912;

	private String ima913;

	private String ima914;

	private String ima915;

	private String ima916;

	private BigDecimal ima917;

	private String ima918;

	private String ima919;

	private String ima92;

	private String ima920;

	private String ima921;

	private String ima922;

	private String ima923;

	private String ima924;

	private String ima925;

	private String ima926;

	private String ima927;

	private String ima928;

	private String ima929;

	private String ima93;

	private String ima930;

	private String ima931;

	private String ima932;

	private String ima933;

	private String ima934;

	private String ima94;

	private String ima940;

	private String ima941;

	private String ima943;

	private BigDecimal ima95;

	private BigDecimal ima96;

	private BigDecimal ima97;

	private BigDecimal ima98;

	private BigDecimal ima99;

	private String imaacti;

	private String imaag;

	private String imaag1;

	private Object imadate;

	private String imagrup;

	private String imamodu;

	private String imaorig;

	private String imaoriu;

	private String imaud01;

	@Column(insertable=false, updatable=false)
	private String imaud02; //客戶專用 客戶編號 cust_name

	private String imaud03;

	private String imaud04;

	private String imaud05;

	private String imaud06;

	private BigDecimal imaud07;

	private BigDecimal imaud08;

	private BigDecimal imaud09;

	private BigDecimal imaud10;

	private BigDecimal imaud11;

	private BigDecimal imaud12;

	private Object imaud13;

	private Object imaud14;

	private Object imaud15;

	private String imauser;

	// bi-directional many-to-one association to EcmFile
	@OneToMany(mappedBy = "imaFile")
	private List<EcmFile> ecmFiles;
	
	@OneToMany(mappedBy = "ima")
    private List<ImgFile> imgList;

	public ImaFile() {
	}
/*
	// 覆蓋父類的 getId 方法
    @Override
    public Long getId() {
        return null; // 或者可以根據需要返回轉換後的值
    }
	*/
	public String getIma01() {
		return this.ima01;
	}

	public void setIma01(String ima01) {
		this.ima01 = ima01;
	}

	public String getIma02() {
		return this.ima02;
	}

	public void setIma02(String ima02) {
		this.ima02 = ima02;
	}

	public String getIma021() {
		return this.ima021;
	}

	public void setIma021(String ima021) {
		this.ima021 = ima021;
	}

	public BigDecimal getIma022() {
		return this.ima022;
	}

	public void setIma022(BigDecimal ima022) {
		this.ima022 = ima022;
	}

	public String getIma03() {
		return this.ima03;
	}

	public void setIma03(String ima03) {
		this.ima03 = ima03;
	}

	public String getIma04() {
		return this.ima04;
	}

	public void setIma04(String ima04) {
		this.ima04 = ima04;
	}

	public String getIma05() {
		return this.ima05;
	}

	public void setIma05(String ima05) {
		this.ima05 = ima05;
	}

	public String getIma06() {
		return this.ima06;
	}

	public void setIma06(String ima06) {
		this.ima06 = ima06;
	}

	public String getIma07() {
		return this.ima07;
	}

	public void setIma07(String ima07) {
		this.ima07 = ima07;
	}

	public String getIma08() {
		return this.ima08;
	}

	public void setIma08(String ima08) {
		this.ima08 = ima08;
	}

	public String getIma09() {
		return this.ima09;
	}

	public void setIma09(String ima09) {
		this.ima09 = ima09;
	}

	public String getIma10() {
		return this.ima10;
	}

	public void setIma10(String ima10) {
		this.ima10 = ima10;
	}

	public String getIma100() {
		return this.ima100;
	}

	public void setIma100(String ima100) {
		this.ima100 = ima100;
	}

	public String getIma1001() {
		return this.ima1001;
	}

	public void setIma1001(String ima1001) {
		this.ima1001 = ima1001;
	}

	public String getIma1002() {
		return this.ima1002;
	}

	public void setIma1002(String ima1002) {
		this.ima1002 = ima1002;
	}

	public String getIma1003() {
		return this.ima1003;
	}

	public void setIma1003(String ima1003) {
		this.ima1003 = ima1003;
	}

	public String getIma1004() {
		return this.ima1004;
	}

	public void setIma1004(String ima1004) {
		this.ima1004 = ima1004;
	}

	public String getIma1005() {
		return this.ima1005;
	}

	public void setIma1005(String ima1005) {
		this.ima1005 = ima1005;
	}

	public String getIma1006() {
		return this.ima1006;
	}

	public void setIma1006(String ima1006) {
		this.ima1006 = ima1006;
	}

	public String getIma1007() {
		return this.ima1007;
	}

	public void setIma1007(String ima1007) {
		this.ima1007 = ima1007;
	}

	public String getIma1008() {
		return this.ima1008;
	}

	public void setIma1008(String ima1008) {
		this.ima1008 = ima1008;
	}

	public String getIma1009() {
		return this.ima1009;
	}

	public void setIma1009(String ima1009) {
		this.ima1009 = ima1009;
	}

	public String getIma101() {
		return this.ima101;
	}

	public void setIma101(String ima101) {
		this.ima101 = ima101;
	}

	public String getIma1010() {
		return this.ima1010;
	}

	public void setIma1010(String ima1010) {
		this.ima1010 = ima1010;
	}

	public BigDecimal getIma1011() {
		return this.ima1011;
	}

	public void setIma1011(BigDecimal ima1011) {
		this.ima1011 = ima1011;
	}

	public Object getIma1012() {
		return this.ima1012;
	}

	public void setIma1012(Object ima1012) {
		this.ima1012 = ima1012;
	}

	public Object getIma1013() {
		return this.ima1013;
	}

	public void setIma1013(Object ima1013) {
		this.ima1013 = ima1013;
	}

	public String getIma1014() {
		return this.ima1014;
	}

	public void setIma1014(String ima1014) {
		this.ima1014 = ima1014;
	}

	public Object getIma1015() {
		return this.ima1015;
	}

	public void setIma1015(Object ima1015) {
		this.ima1015 = ima1015;
	}

	public String getIma1016() {
		return this.ima1016;
	}

	public void setIma1016(String ima1016) {
		this.ima1016 = ima1016;
	}

	public BigDecimal getIma1017() {
		return this.ima1017;
	}

	public void setIma1017(BigDecimal ima1017) {
		this.ima1017 = ima1017;
	}

	public BigDecimal getIma1018() {
		return this.ima1018;
	}

	public void setIma1018(BigDecimal ima1018) {
		this.ima1018 = ima1018;
	}

	public BigDecimal getIma1019() {
		return this.ima1019;
	}

	public void setIma1019(BigDecimal ima1019) {
		this.ima1019 = ima1019;
	}

	public String getIma102() {
		return this.ima102;
	}

	public void setIma102(String ima102) {
		this.ima102 = ima102;
	}

	public BigDecimal getIma1020() {
		return this.ima1020;
	}

	public void setIma1020(BigDecimal ima1020) {
		this.ima1020 = ima1020;
	}

	public BigDecimal getIma1021() {
		return this.ima1021;
	}

	public void setIma1021(BigDecimal ima1021) {
		this.ima1021 = ima1021;
	}

	public BigDecimal getIma1022() {
		return this.ima1022;
	}

	public void setIma1022(BigDecimal ima1022) {
		this.ima1022 = ima1022;
	}

	public BigDecimal getIma1023() {
		return this.ima1023;
	}

	public void setIma1023(BigDecimal ima1023) {
		this.ima1023 = ima1023;
	}

	public BigDecimal getIma1024() {
		return this.ima1024;
	}

	public void setIma1024(BigDecimal ima1024) {
		this.ima1024 = ima1024;
	}

	public BigDecimal getIma1025() {
		return this.ima1025;
	}

	public void setIma1025(BigDecimal ima1025) {
		this.ima1025 = ima1025;
	}

	public BigDecimal getIma1026() {
		return this.ima1026;
	}

	public void setIma1026(BigDecimal ima1026) {
		this.ima1026 = ima1026;
	}

	public BigDecimal getIma1027() {
		return this.ima1027;
	}

	public void setIma1027(BigDecimal ima1027) {
		this.ima1027 = ima1027;
	}

	public BigDecimal getIma1028() {
		return this.ima1028;
	}

	public void setIma1028(BigDecimal ima1028) {
		this.ima1028 = ima1028;
	}

	public String getIma1029() {
		return this.ima1029;
	}

	public void setIma1029(String ima1029) {
		this.ima1029 = ima1029;
	}

	public String getIma103() {
		return this.ima103;
	}

	public void setIma103(String ima103) {
		this.ima103 = ima103;
	}

	public String getIma1030() {
		return this.ima1030;
	}

	public void setIma1030(String ima1030) {
		this.ima1030 = ima1030;
	}

	public BigDecimal getIma104() {
		return this.ima104;
	}

	public void setIma104(BigDecimal ima104) {
		this.ima104 = ima104;
	}

	public String getIma105() {
		return this.ima105;
	}

	public void setIma105(String ima105) {
		this.ima105 = ima105;
	}

	public String getIma106() {
		return this.ima106;
	}

	public void setIma106(String ima106) {
		this.ima106 = ima106;
	}

	public String getIma107() {
		return this.ima107;
	}

	public void setIma107(String ima107) {
		this.ima107 = ima107;
	}

	public String getIma108() {
		return this.ima108;
	}

	public void setIma108(String ima108) {
		this.ima108 = ima108;
	}

	public String getIma109() {
		return this.ima109;
	}

	public void setIma109(String ima109) {
		this.ima109 = ima109;
	}

	public String getIma11() {
		return this.ima11;
	}

	public void setIma11(String ima11) {
		this.ima11 = ima11;
	}

	public String getIma110() {
		return this.ima110;
	}

	public void setIma110(String ima110) {
		this.ima110 = ima110;
	}

	public String getIma111() {
		return this.ima111;
	}

	public void setIma111(String ima111) {
		this.ima111 = ima111;
	}

	public String getIma12() {
		return this.ima12;
	}

	public void setIma12(String ima12) {
		this.ima12 = ima12;
	}

	public String getIma120() {
		return this.ima120;
	}

	public void setIma120(String ima120) {
		this.ima120 = ima120;
	}

	public BigDecimal getIma121() {
		return this.ima121;
	}

	public void setIma121(BigDecimal ima121) {
		this.ima121 = ima121;
	}

	public BigDecimal getIma122() {
		return this.ima122;
	}

	public void setIma122(BigDecimal ima122) {
		this.ima122 = ima122;
	}

	public BigDecimal getIma123() {
		return this.ima123;
	}

	public void setIma123(BigDecimal ima123) {
		this.ima123 = ima123;
	}

	public BigDecimal getIma124() {
		return this.ima124;
	}

	public void setIma124(BigDecimal ima124) {
		this.ima124 = ima124;
	}

	public BigDecimal getIma125() {
		return this.ima125;
	}

	public void setIma125(BigDecimal ima125) {
		this.ima125 = ima125;
	}

	public BigDecimal getIma126() {
		return this.ima126;
	}

	public void setIma126(BigDecimal ima126) {
		this.ima126 = ima126;
	}

	public BigDecimal getIma127() {
		return this.ima127;
	}

	public void setIma127(BigDecimal ima127) {
		this.ima127 = ima127;
	}

	public BigDecimal getIma128() {
		return this.ima128;
	}

	public void setIma128(BigDecimal ima128) {
		this.ima128 = ima128;
	}

	public BigDecimal getIma129() {
		return this.ima129;
	}

	public void setIma129(BigDecimal ima129) {
		this.ima129 = ima129;
	}

	public String getIma13() {
		return this.ima13;
	}

	public void setIma13(String ima13) {
		this.ima13 = ima13;
	}

	public String getIma130() {
		return this.ima130;
	}

	public void setIma130(String ima130) {
		this.ima130 = ima130;
	}

	public String getIma131() {
		return this.ima131;
	}

	public void setIma131(String ima131) {
		this.ima131 = ima131;
	}

	public String getIma132() {
		return this.ima132;
	}

	public void setIma132(String ima132) {
		this.ima132 = ima132;
	}

	public String getIma1321() {
		return this.ima1321;
	}

	public void setIma1321(String ima1321) {
		this.ima1321 = ima1321;
	}

	public String getIma133() {
		return this.ima133;
	}

	public void setIma133(String ima133) {
		this.ima133 = ima133;
	}

	public String getIma134() {
		return this.ima134;
	}

	public void setIma134(String ima134) {
		this.ima134 = ima134;
	}

	public String getIma135() {
		return this.ima135;
	}

	public void setIma135(String ima135) {
		this.ima135 = ima135;
	}

	public String getIma136() {
		return this.ima136;
	}

	public void setIma136(String ima136) {
		this.ima136 = ima136;
	}

	public String getIma137() {
		return this.ima137;
	}

	public void setIma137(String ima137) {
		this.ima137 = ima137;
	}

	public String getIma138() {
		return this.ima138;
	}

	public void setIma138(String ima138) {
		this.ima138 = ima138;
	}

	public String getIma139() {
		return this.ima139;
	}

	public void setIma139(String ima139) {
		this.ima139 = ima139;
	}

	public String getIma14() {
		return this.ima14;
	}

	public void setIma14(String ima14) {
		this.ima14 = ima14;
	}

	public String getIma140() {
		return this.ima140;
	}

	public void setIma140(String ima140) {
		this.ima140 = ima140;
	}

	public Object getIma1401() {
		return this.ima1401;
	}

	public void setIma1401(Object ima1401) {
		this.ima1401 = ima1401;
	}

	public String getIma141() {
		return this.ima141;
	}

	public void setIma141(String ima141) {
		this.ima141 = ima141;
	}

	public BigDecimal getIma142() {
		return this.ima142;
	}

	public void setIma142(BigDecimal ima142) {
		this.ima142 = ima142;
	}

	public BigDecimal getIma143() {
		return this.ima143;
	}

	public void setIma143(BigDecimal ima143) {
		this.ima143 = ima143;
	}

	public String getIma144() {
		return this.ima144;
	}

	public void setIma144(String ima144) {
		this.ima144 = ima144;
	}

	public String getIma145() {
		return this.ima145;
	}

	public void setIma145(String ima145) {
		this.ima145 = ima145;
	}

	public String getIma146() {
		return this.ima146;
	}

	public void setIma146(String ima146) {
		this.ima146 = ima146;
	}

	public String getIma147() {
		return this.ima147;
	}

	public void setIma147(String ima147) {
		this.ima147 = ima147;
	}

	public BigDecimal getIma148() {
		return this.ima148;
	}

	public void setIma148(BigDecimal ima148) {
		this.ima148 = ima148;
	}

	public String getIma149() {
		return this.ima149;
	}

	public void setIma149(String ima149) {
		this.ima149 = ima149;
	}

	public String getIma1491() {
		return this.ima1491;
	}

	public void setIma1491(String ima1491) {
		this.ima1491 = ima1491;
	}

	public String getIma15() {
		return this.ima15;
	}

	public void setIma15(String ima15) {
		this.ima15 = ima15;
	}

	public String getIma150() {
		return this.ima150;
	}

	public void setIma150(String ima150) {
		this.ima150 = ima150;
	}

	public String getIma151() {
		return this.ima151;
	}

	public void setIma151(String ima151) {
		this.ima151 = ima151;
	}

	public String getIma152() {
		return this.ima152;
	}

	public void setIma152(String ima152) {
		this.ima152 = ima152;
	}

	public BigDecimal getIma153() {
		return this.ima153;
	}

	public void setIma153(BigDecimal ima153) {
		this.ima153 = ima153;
	}

	public String getIma154() {
		return this.ima154;
	}

	public void setIma154(String ima154) {
		this.ima154 = ima154;
	}

	public String getIma155() {
		return this.ima155;
	}

	public void setIma155(String ima155) {
		this.ima155 = ima155;
	}

	public String getIma156() {
		return this.ima156;
	}

	public void setIma156(String ima156) {
		this.ima156 = ima156;
	}

	public String getIma157() {
		return this.ima157;
	}

	public void setIma157(String ima157) {
		this.ima157 = ima157;
	}

	public String getIma158() {
		return this.ima158;
	}

	public void setIma158(String ima158) {
		this.ima158 = ima158;
	}

	public String getIma159() {
		return this.ima159;
	}

	public void setIma159(String ima159) {
		this.ima159 = ima159;
	}

	public BigDecimal getIma16() {
		return this.ima16;
	}

	public void setIma16(BigDecimal ima16) {
		this.ima16 = ima16;
	}

	public String getIma160() {
		return this.ima160;
	}

	public void setIma160(String ima160) {
		this.ima160 = ima160;
	}

	public String getIma161() {
		return this.ima161;
	}

	public void setIma161(String ima161) {
		this.ima161 = ima161;
	}

	public String getIma162() {
		return this.ima162;
	}

	public void setIma162(String ima162) {
		this.ima162 = ima162;
	}

	public String getIma163() {
		return this.ima163;
	}

	public void setIma163(String ima163) {
		this.ima163 = ima163;
	}

	public String getIma1631() {
		return this.ima1631;
	}

	public void setIma1631(String ima1631) {
		this.ima1631 = ima1631;
	}

	public String getIma164() {
		return this.ima164;
	}

	public void setIma164(String ima164) {
		this.ima164 = ima164;
	}

	public String getIma1641() {
		return this.ima1641;
	}

	public void setIma1641(String ima1641) {
		this.ima1641 = ima1641;
	}

	public String getIma165() {
		return this.ima165;
	}

	public void setIma165(String ima165) {
		this.ima165 = ima165;
	}

	public String getIma17() {
		return this.ima17;
	}

	public void setIma17(String ima17) {
		this.ima17 = ima17;
	}

	public BigDecimal getIma17Fac() {
		return this.ima17Fac;
	}

	public void setIma17Fac(BigDecimal ima17Fac) {
		this.ima17Fac = ima17Fac;
	}

	public BigDecimal getIma171() {
		return this.ima171;
	}

	public void setIma171(BigDecimal ima171) {
		this.ima171 = ima171;
	}

	public BigDecimal getIma172() {
		return this.ima172;
	}

	public void setIma172(BigDecimal ima172) {
		this.ima172 = ima172;
	}

	public BigDecimal getIma18() {
		return this.ima18;
	}

	public void setIma18(BigDecimal ima18) {
		this.ima18 = ima18;
	}

	public String getIma19() {
		return this.ima19;
	}

	public void setIma19(String ima19) {
		this.ima19 = ima19;
	}

	public String getIma1911() {
		return this.ima1911;
	}

	public void setIma1911(String ima1911) {
		this.ima1911 = ima1911;
	}

	public String getIma1912() {
		return this.ima1912;
	}

	public void setIma1912(String ima1912) {
		this.ima1912 = ima1912;
	}

	public BigDecimal getIma1913() {
		return this.ima1913;
	}

	public void setIma1913(BigDecimal ima1913) {
		this.ima1913 = ima1913;
	}

	public String getIma1914() {
		return this.ima1914;
	}

	public void setIma1914(String ima1914) {
		this.ima1914 = ima1914;
	}

	public String getIma1915() {
		return this.ima1915;
	}

	public void setIma1915(String ima1915) {
		this.ima1915 = ima1915;
	}

	public String getIma1916() {
		return this.ima1916;
	}

	public void setIma1916(String ima1916) {
		this.ima1916 = ima1916;
	}

	public String getIma1919() {
		return this.ima1919;
	}

	public void setIma1919(String ima1919) {
		this.ima1919 = ima1919;
	}

	public BigDecimal getIma20() {
		return this.ima20;
	}

	public void setIma20(BigDecimal ima20) {
		this.ima20 = ima20;
	}

	public String getIma21() {
		return this.ima21;
	}

	public void setIma21(String ima21) {
		this.ima21 = ima21;
	}

	public BigDecimal getIma22() {
		return this.ima22;
	}

	public void setIma22(BigDecimal ima22) {
		this.ima22 = ima22;
	}

	public String getIma23() {
		return this.ima23;
	}

	public void setIma23(String ima23) {
		this.ima23 = ima23;
	}

	public String getIma24() {
		return this.ima24;
	}

	public void setIma24(String ima24) {
		this.ima24 = ima24;
	}

	public String getIma25() {
		return this.ima25;
	}

	public void setIma25(String ima25) {
		this.ima25 = ima25;
	}

	public String getIma251() {
		return this.ima251;
	}

	public void setIma251(String ima251) {
		this.ima251 = ima251;
	}

	public BigDecimal getIma26() {
		return this.ima26;
	}

	public void setIma26(BigDecimal ima26) {
		this.ima26 = ima26;
	}

	public BigDecimal getIma261() {
		return this.ima261;
	}

	public void setIma261(BigDecimal ima261) {
		this.ima261 = ima261;
	}

	public BigDecimal getIma262() {
		return this.ima262;
	}

	public void setIma262(BigDecimal ima262) {
		this.ima262 = ima262;
	}

	public BigDecimal getIma27() {
		return this.ima27;
	}

	public void setIma27(BigDecimal ima27) {
		this.ima27 = ima27;
	}

	public BigDecimal getIma271() {
		return this.ima271;
	}

	public void setIma271(BigDecimal ima271) {
		this.ima271 = ima271;
	}

	public BigDecimal getIma28() {
		return this.ima28;
	}

	public void setIma28(BigDecimal ima28) {
		this.ima28 = ima28;
	}

	public Object getIma29() {
		return this.ima29;
	}

	public void setIma29(Object ima29) {
		this.ima29 = ima29;
	}

	public Object getIma30() {
		return this.ima30;
	}

	public void setIma30(Object ima30) {
		this.ima30 = ima30;
	}

	public String getIma31() {
		return this.ima31;
	}

	public void setIma31(String ima31) {
		this.ima31 = ima31;
	}

	public BigDecimal getIma31Fac() {
		return this.ima31Fac;
	}

	public void setIma31Fac(BigDecimal ima31Fac) {
		this.ima31Fac = ima31Fac;
	}

	public BigDecimal getIma32() {
		return this.ima32;
	}

	public void setIma32(BigDecimal ima32) {
		this.ima32 = ima32;
	}

	public BigDecimal getIma33() {
		return this.ima33;
	}

	public void setIma33(BigDecimal ima33) {
		this.ima33 = ima33;
	}

	public String getIma34() {
		return this.ima34;
	}

	public void setIma34(String ima34) {
		this.ima34 = ima34;
	}

	public String getIma35() {
		return this.ima35;
	}

	public void setIma35(String ima35) {
		this.ima35 = ima35;
	}

	public String getIma36() {
		return this.ima36;
	}

	public void setIma36(String ima36) {
		this.ima36 = ima36;
	}

	public String getIma37() {
		return this.ima37;
	}

	public void setIma37(String ima37) {
		this.ima37 = ima37;
	}

	public BigDecimal getIma38() {
		return this.ima38;
	}

	public void setIma38(BigDecimal ima38) {
		this.ima38 = ima38;
	}

	public String getIma39() {
		return this.ima39;
	}

	public void setIma39(String ima39) {
		this.ima39 = ima39;
	}

	public String getIma391() {
		return this.ima391;
	}

	public void setIma391(String ima391) {
		this.ima391 = ima391;
	}

	public BigDecimal getIma40() {
		return this.ima40;
	}

	public void setIma40(BigDecimal ima40) {
		this.ima40 = ima40;
	}

	public BigDecimal getIma41() {
		return this.ima41;
	}

	public void setIma41(BigDecimal ima41) {
		this.ima41 = ima41;
	}

	public String getIma42() {
		return this.ima42;
	}

	public void setIma42(String ima42) {
		this.ima42 = ima42;
	}

	public String getIma43() {
		return this.ima43;
	}

	public void setIma43(String ima43) {
		this.ima43 = ima43;
	}

	public String getIma44() {
		return this.ima44;
	}

	public void setIma44(String ima44) {
		this.ima44 = ima44;
	}

	public BigDecimal getIma44Fac() {
		return this.ima44Fac;
	}

	public void setIma44Fac(BigDecimal ima44Fac) {
		this.ima44Fac = ima44Fac;
	}

	public BigDecimal getIma45() {
		return this.ima45;
	}

	public void setIma45(BigDecimal ima45) {
		this.ima45 = ima45;
	}

	public BigDecimal getIma46() {
		return this.ima46;
	}

	public void setIma46(BigDecimal ima46) {
		this.ima46 = ima46;
	}

	public BigDecimal getIma47() {
		return this.ima47;
	}

	public void setIma47(BigDecimal ima47) {
		this.ima47 = ima47;
	}

	public BigDecimal getIma48() {
		return this.ima48;
	}

	public void setIma48(BigDecimal ima48) {
		this.ima48 = ima48;
	}

	public BigDecimal getIma49() {
		return this.ima49;
	}

	public void setIma49(BigDecimal ima49) {
		this.ima49 = ima49;
	}

	public BigDecimal getIma491() {
		return this.ima491;
	}

	public void setIma491(BigDecimal ima491) {
		this.ima491 = ima491;
	}

	public BigDecimal getIma50() {
		return this.ima50;
	}

	public void setIma50(BigDecimal ima50) {
		this.ima50 = ima50;
	}

	public BigDecimal getIma51() {
		return this.ima51;
	}

	public void setIma51(BigDecimal ima51) {
		this.ima51 = ima51;
	}

	public BigDecimal getIma52() {
		return this.ima52;
	}

	public void setIma52(BigDecimal ima52) {
		this.ima52 = ima52;
	}

	public BigDecimal getIma53() {
		return this.ima53;
	}

	public void setIma53(BigDecimal ima53) {
		this.ima53 = ima53;
	}

	public BigDecimal getIma531() {
		return this.ima531;
	}

	public void setIma531(BigDecimal ima531) {
		this.ima531 = ima531;
	}

	public Object getIma532() {
		return this.ima532;
	}

	public void setIma532(Object ima532) {
		this.ima532 = ima532;
	}

	public String getIma54() {
		return this.ima54;
	}

	public void setIma54(String ima54) {
		this.ima54 = ima54;
	}

	public String getIma55() {
		return this.ima55;
	}

	public void setIma55(String ima55) {
		this.ima55 = ima55;
	}

	public BigDecimal getIma55Fac() {
		return this.ima55Fac;
	}

	public void setIma55Fac(BigDecimal ima55Fac) {
		this.ima55Fac = ima55Fac;
	}

	public BigDecimal getIma56() {
		return this.ima56;
	}

	public void setIma56(BigDecimal ima56) {
		this.ima56 = ima56;
	}

	public BigDecimal getIma561() {
		return this.ima561;
	}

	public void setIma561(BigDecimal ima561) {
		this.ima561 = ima561;
	}

	public BigDecimal getIma562() {
		return this.ima562;
	}

	public void setIma562(BigDecimal ima562) {
		this.ima562 = ima562;
	}

	public BigDecimal getIma57() {
		return this.ima57;
	}

	public void setIma57(BigDecimal ima57) {
		this.ima57 = ima57;
	}

	public String getIma571() {
		return this.ima571;
	}

	public void setIma571(String ima571) {
		this.ima571 = ima571;
	}

	public BigDecimal getIma58() {
		return this.ima58;
	}

	public void setIma58(BigDecimal ima58) {
		this.ima58 = ima58;
	}

	public BigDecimal getIma59() {
		return this.ima59;
	}

	public void setIma59(BigDecimal ima59) {
		this.ima59 = ima59;
	}

	public BigDecimal getIma60() {
		return this.ima60;
	}

	public void setIma60(BigDecimal ima60) {
		this.ima60 = ima60;
	}

	public BigDecimal getIma601() {
		return this.ima601;
	}

	public void setIma601(BigDecimal ima601) {
		this.ima601 = ima601;
	}

	public BigDecimal getIma61() {
		return this.ima61;
	}

	public void setIma61(BigDecimal ima61) {
		this.ima61 = ima61;
	}

	public BigDecimal getIma62() {
		return this.ima62;
	}

	public void setIma62(BigDecimal ima62) {
		this.ima62 = ima62;
	}

	public String getIma63() {
		return this.ima63;
	}

	public void setIma63(String ima63) {
		this.ima63 = ima63;
	}

	public BigDecimal getIma63Fac() {
		return this.ima63Fac;
	}

	public void setIma63Fac(BigDecimal ima63Fac) {
		this.ima63Fac = ima63Fac;
	}

	public BigDecimal getIma64() {
		return this.ima64;
	}

	public void setIma64(BigDecimal ima64) {
		this.ima64 = ima64;
	}

	public BigDecimal getIma641() {
		return this.ima641;
	}

	public void setIma641(BigDecimal ima641) {
		this.ima641 = ima641;
	}

	public BigDecimal getIma65() {
		return this.ima65;
	}

	public void setIma65(BigDecimal ima65) {
		this.ima65 = ima65;
	}

	public BigDecimal getIma66() {
		return this.ima66;
	}

	public void setIma66(BigDecimal ima66) {
		this.ima66 = ima66;
	}

	public String getIma67() {
		return this.ima67;
	}

	public void setIma67(String ima67) {
		this.ima67 = ima67;
	}

	public BigDecimal getIma68() {
		return this.ima68;
	}

	public void setIma68(BigDecimal ima68) {
		this.ima68 = ima68;
	}

	public BigDecimal getIma69() {
		return this.ima69;
	}

	public void setIma69(BigDecimal ima69) {
		this.ima69 = ima69;
	}

	public String getIma70() {
		return this.ima70;
	}

	public void setIma70(String ima70) {
		this.ima70 = ima70;
	}

	public BigDecimal getIma71() {
		return this.ima71;
	}

	public void setIma71(BigDecimal ima71) {
		this.ima71 = ima71;
	}

	public BigDecimal getIma72() {
		return this.ima72;
	}

	public void setIma72(BigDecimal ima72) {
		this.ima72 = ima72;
	}

	public BigDecimal getIma721() {
		return this.ima721;
	}

	public void setIma721(BigDecimal ima721) {
		this.ima721 = ima721;
	}

	public java.util.Date getIma73() {
		return this.ima73;
	}

	public void setIma73(java.util.Date ima73) {
		this.ima73 = ima73;
	}

	public Object getIma74() {
		return this.ima74;
	}

	public void setIma74(Object ima74) {
		this.ima74 = ima74;
	}

	public String getIma75() {
		return this.ima75;
	}

	public void setIma75(String ima75) {
		this.ima75 = ima75;
	}

	public String getIma76() {
		return this.ima76;
	}

	public void setIma76(String ima76) {
		this.ima76 = ima76;
	}

	public BigDecimal getIma77() {
		return this.ima77;
	}

	public void setIma77(BigDecimal ima77) {
		this.ima77 = ima77;
	}

	public BigDecimal getIma78() {
		return this.ima78;
	}

	public void setIma78(BigDecimal ima78) {
		this.ima78 = ima78;
	}

	public BigDecimal getIma79() {
		return this.ima79;
	}

	public void setIma79(BigDecimal ima79) {
		this.ima79 = ima79;
	}

	public BigDecimal getIma80() {
		return this.ima80;
	}

	public void setIma80(BigDecimal ima80) {
		this.ima80 = ima80;
	}

	public BigDecimal getIma81() {
		return this.ima81;
	}

	public void setIma81(BigDecimal ima81) {
		this.ima81 = ima81;
	}

	public BigDecimal getIma82() {
		return this.ima82;
	}

	public void setIma82(BigDecimal ima82) {
		this.ima82 = ima82;
	}

	public BigDecimal getIma83() {
		return this.ima83;
	}

	public void setIma83(BigDecimal ima83) {
		this.ima83 = ima83;
	}

	public BigDecimal getIma84() {
		return this.ima84;
	}

	public void setIma84(BigDecimal ima84) {
		this.ima84 = ima84;
	}

	public BigDecimal getIma85() {
		return this.ima85;
	}

	public void setIma85(BigDecimal ima85) {
		this.ima85 = ima85;
	}

	public String getIma851() {
		return this.ima851;
	}

	public void setIma851(String ima851) {
		this.ima851 = ima851;
	}

	public String getIma852() {
		return this.ima852;
	}

	public void setIma852(String ima852) {
		this.ima852 = ima852;
	}

	public String getIma853() {
		return this.ima853;
	}

	public void setIma853(String ima853) {
		this.ima853 = ima853;
	}

	public String getIma86() {
		return this.ima86;
	}

	public void setIma86(String ima86) {
		this.ima86 = ima86;
	}

	public BigDecimal getIma86Fac() {
		return this.ima86Fac;
	}

	public void setIma86Fac(BigDecimal ima86Fac) {
		this.ima86Fac = ima86Fac;
	}

	public String getIma87() {
		return this.ima87;
	}

	public void setIma87(String ima87) {
		this.ima87 = ima87;
	}

	public BigDecimal getIma871() {
		return this.ima871;
	}

	public void setIma871(BigDecimal ima871) {
		this.ima871 = ima871;
	}

	public String getIma872() {
		return this.ima872;
	}

	public void setIma872(String ima872) {
		this.ima872 = ima872;
	}

	public BigDecimal getIma873() {
		return this.ima873;
	}

	public void setIma873(BigDecimal ima873) {
		this.ima873 = ima873;
	}

	public String getIma874() {
		return this.ima874;
	}

	public void setIma874(String ima874) {
		this.ima874 = ima874;
	}

	public BigDecimal getIma88() {
		return this.ima88;
	}

	public void setIma88(BigDecimal ima88) {
		this.ima88 = ima88;
	}

	public Object getIma881() {
		return this.ima881;
	}

	public void setIma881(Object ima881) {
		this.ima881 = ima881;
	}

	public BigDecimal getIma89() {
		return this.ima89;
	}

	public void setIma89(BigDecimal ima89) {
		this.ima89 = ima89;
	}

	public BigDecimal getIma90() {
		return this.ima90;
	}

	public void setIma90(BigDecimal ima90) {
		this.ima90 = ima90;
	}

	public Object getIma901() {
		return this.ima901;
	}

	public void setIma901(Object ima901) {
		this.ima901 = ima901;
	}

	public Object getIma902() {
		return this.ima902;
	}

	public void setIma902(Object ima902) {
		this.ima902 = ima902;
	}

	public Object getIma9021() {
		return this.ima9021;
	}

	public void setIma9021(Object ima9021) {
		this.ima9021 = ima9021;
	}

	public String getIma903() {
		return this.ima903;
	}

	public void setIma903(String ima903) {
		this.ima903 = ima903;
	}

	public String getIma904() {
		return this.ima904;
	}

	public void setIma904(String ima904) {
		this.ima904 = ima904;
	}

	public String getIma905() {
		return this.ima905;
	}

	public void setIma905(String ima905) {
		this.ima905 = ima905;
	}

	public String getIma906() {
		return this.ima906;
	}

	public void setIma906(String ima906) {
		this.ima906 = ima906;
	}

	public String getIma907() {
		return this.ima907;
	}

	public void setIma907(String ima907) {
		this.ima907 = ima907;
	}

	public String getIma908() {
		return this.ima908;
	}

	public void setIma908(String ima908) {
		this.ima908 = ima908;
	}

	public BigDecimal getIma909() {
		return this.ima909;
	}

	public void setIma909(BigDecimal ima909) {
		this.ima909 = ima909;
	}

	public BigDecimal getIma91() {
		return this.ima91;
	}

	public void setIma91(BigDecimal ima91) {
		this.ima91 = ima91;
	}

	public String getIma910() {
		return this.ima910;
	}

	public void setIma910(String ima910) {
		this.ima910 = ima910;
	}

	public String getIma911() {
		return this.ima911;
	}

	public void setIma911(String ima911) {
		this.ima911 = ima911;
	}

	public BigDecimal getIma912() {
		return this.ima912;
	}

	public void setIma912(BigDecimal ima912) {
		this.ima912 = ima912;
	}

	public String getIma913() {
		return this.ima913;
	}

	public void setIma913(String ima913) {
		this.ima913 = ima913;
	}

	public String getIma914() {
		return this.ima914;
	}

	public void setIma914(String ima914) {
		this.ima914 = ima914;
	}

	public String getIma915() {
		return this.ima915;
	}

	public void setIma915(String ima915) {
		this.ima915 = ima915;
	}

	public String getIma916() {
		return this.ima916;
	}

	public void setIma916(String ima916) {
		this.ima916 = ima916;
	}

	public BigDecimal getIma917() {
		return this.ima917;
	}

	public void setIma917(BigDecimal ima917) {
		this.ima917 = ima917;
	}

	public String getIma918() {
		return this.ima918;
	}

	public void setIma918(String ima918) {
		this.ima918 = ima918;
	}

	public String getIma919() {
		return this.ima919;
	}

	public void setIma919(String ima919) {
		this.ima919 = ima919;
	}

	public String getIma92() {
		return this.ima92;
	}

	public void setIma92(String ima92) {
		this.ima92 = ima92;
	}

	public String getIma920() {
		return this.ima920;
	}

	public void setIma920(String ima920) {
		this.ima920 = ima920;
	}

	public String getIma921() {
		return this.ima921;
	}

	public void setIma921(String ima921) {
		this.ima921 = ima921;
	}

	public String getIma922() {
		return this.ima922;
	}

	public void setIma922(String ima922) {
		this.ima922 = ima922;
	}

	public String getIma923() {
		return this.ima923;
	}

	public void setIma923(String ima923) {
		this.ima923 = ima923;
	}

	public String getIma924() {
		return this.ima924;
	}

	public void setIma924(String ima924) {
		this.ima924 = ima924;
	}

	public String getIma925() {
		return this.ima925;
	}

	public void setIma925(String ima925) {
		this.ima925 = ima925;
	}

	public String getIma926() {
		return this.ima926;
	}

	public void setIma926(String ima926) {
		this.ima926 = ima926;
	}

	public String getIma927() {
		return this.ima927;
	}

	public void setIma927(String ima927) {
		this.ima927 = ima927;
	}

	public String getIma928() {
		return this.ima928;
	}

	public void setIma928(String ima928) {
		this.ima928 = ima928;
	}

	public String getIma929() {
		return this.ima929;
	}

	public void setIma929(String ima929) {
		this.ima929 = ima929;
	}

	public String getIma93() {
		return this.ima93;
	}

	public void setIma93(String ima93) {
		this.ima93 = ima93;
	}

	public String getIma930() {
		return this.ima930;
	}

	public void setIma930(String ima930) {
		this.ima930 = ima930;
	}

	public String getIma931() {
		return this.ima931;
	}

	public void setIma931(String ima931) {
		this.ima931 = ima931;
	}

	public String getIma932() {
		return this.ima932;
	}

	public void setIma932(String ima932) {
		this.ima932 = ima932;
	}

	public String getIma933() {
		return this.ima933;
	}

	public void setIma933(String ima933) {
		this.ima933 = ima933;
	}

	public String getIma934() {
		return this.ima934;
	}

	public void setIma934(String ima934) {
		this.ima934 = ima934;
	}

	public String getIma94() {
		return this.ima94;
	}

	public void setIma94(String ima94) {
		this.ima94 = ima94;
	}

	public String getIma940() {
		return this.ima940;
	}

	public void setIma940(String ima940) {
		this.ima940 = ima940;
	}

	public String getIma941() {
		return this.ima941;
	}

	public void setIma941(String ima941) {
		this.ima941 = ima941;
	}

	public String getIma943() {
		return this.ima943;
	}

	public void setIma943(String ima943) {
		this.ima943 = ima943;
	}

	public BigDecimal getIma95() {
		return this.ima95;
	}

	public void setIma95(BigDecimal ima95) {
		this.ima95 = ima95;
	}

	public BigDecimal getIma96() {
		return this.ima96;
	}

	public void setIma96(BigDecimal ima96) {
		this.ima96 = ima96;
	}

	public BigDecimal getIma97() {
		return this.ima97;
	}

	public void setIma97(BigDecimal ima97) {
		this.ima97 = ima97;
	}

	public BigDecimal getIma98() {
		return this.ima98;
	}

	public void setIma98(BigDecimal ima98) {
		this.ima98 = ima98;
	}

	public BigDecimal getIma99() {
		return this.ima99;
	}

	public void setIma99(BigDecimal ima99) {
		this.ima99 = ima99;
	}

	public String getImaacti() {
		return this.imaacti;
	}

	public void setImaacti(String imaacti) {
		this.imaacti = imaacti;
	}

	public String getImaag() {
		return this.imaag;
	}

	public void setImaag(String imaag) {
		this.imaag = imaag;
	}

	public String getImaag1() {
		return this.imaag1;
	}

	public void setImaag1(String imaag1) {
		this.imaag1 = imaag1;
	}

	public Object getImadate() {
		return this.imadate;
	}

	public void setImadate(Object imadate) {
		this.imadate = imadate;
	}

	public String getImagrup() {
		return this.imagrup;
	}

	public void setImagrup(String imagrup) {
		this.imagrup = imagrup;
	}

	public String getImamodu() {
		return this.imamodu;
	}

	public void setImamodu(String imamodu) {
		this.imamodu = imamodu;
	}

	public String getImaorig() {
		return this.imaorig;
	}

	public void setImaorig(String imaorig) {
		this.imaorig = imaorig;
	}

	public String getImaoriu() {
		return this.imaoriu;
	}

	public void setImaoriu(String imaoriu) {
		this.imaoriu = imaoriu;
	}

	public String getImaud01() {
		return this.imaud01;
	}

	public void setImaud01(String imaud01) {
		this.imaud01 = imaud01;
	}

	public String getImaud02() {
		return this.imaud02;
	}

	public void setImaud02(String imaud02) {
		this.imaud02 = imaud02;
	}

	public String getImaud03() {
		return this.imaud03;
	}

	public void setImaud03(String imaud03) {
		this.imaud03 = imaud03;
	}

	public String getImaud04() {
		return this.imaud04;
	}

	public void setImaud04(String imaud04) {
		this.imaud04 = imaud04;
	}

	public String getImaud05() {
		return this.imaud05;
	}

	public void setImaud05(String imaud05) {
		this.imaud05 = imaud05;
	}

	public String getImaud06() {
		return this.imaud06;
	}

	public void setImaud06(String imaud06) {
		this.imaud06 = imaud06;
	}

	public BigDecimal getImaud07() {
		return this.imaud07;
	}

	public void setImaud07(BigDecimal imaud07) {
		this.imaud07 = imaud07;
	}

	public BigDecimal getImaud08() {
		return this.imaud08;
	}

	public void setImaud08(BigDecimal imaud08) {
		this.imaud08 = imaud08;
	}

	public BigDecimal getImaud09() {
		return this.imaud09;
	}

	public void setImaud09(BigDecimal imaud09) {
		this.imaud09 = imaud09;
	}

	public BigDecimal getImaud10() {
		return this.imaud10;
	}

	public void setImaud10(BigDecimal imaud10) {
		this.imaud10 = imaud10;
	}

	public BigDecimal getImaud11() {
		return this.imaud11;
	}

	public void setImaud11(BigDecimal imaud11) {
		this.imaud11 = imaud11;
	}

	public BigDecimal getImaud12() {
		return this.imaud12;
	}

	public void setImaud12(BigDecimal imaud12) {
		this.imaud12 = imaud12;
	}

	public Object getImaud13() {
		return this.imaud13;
	}

	public void setImaud13(Object imaud13) {
		this.imaud13 = imaud13;
	}

	public Object getImaud14() {
		return this.imaud14;
	}

	public void setImaud14(Object imaud14) {
		this.imaud14 = imaud14;
	}

	public Object getImaud15() {
		return this.imaud15;
	}

	public void setImaud15(Object imaud15) {
		this.imaud15 = imaud15;
	}

	public String getImauser() {
		return this.imauser;
	}

	public void setImauser(String imauser) {
		this.imauser = imauser;
	}

	public List<EcmFile> getEcmFiles() {
		return this.ecmFiles;
	}

	public void setEcmFiles(List<EcmFile> ecmFiles) {
		this.ecmFiles = ecmFiles;
	}

	public EcmFile addEcmFile(EcmFile ecmFile) {
		getEcmFiles().add(ecmFile);
		ecmFile.setImaFile(this);

		return ecmFile;
	}

	public EcmFile removeEcmFile(EcmFile ecmFile) {
		getEcmFiles().remove(ecmFile);
		ecmFile.setImaFile(null);

		return ecmFile;
	}
	
	@ManyToOne
    @JoinColumn(name = "imaud02", referencedColumnName = "occ01")
    private OccFile customer;

	@Override
	public String toString() {
		/* ima01 varchar2(40) 料件編號 料件編號料件存在系統中代表編號, 是唯一的 
		 * ima02 varchar2(120) 品名品名規格描述該料件的品名規格, 如有需要進一步描述, 則可利用品名規格額外說明資料檔記錄 
		 * ima021 varchar2(120) 規格 規格(97/08/18
		 */
		return String.format("IMA_FILE[ima01='%s', ima02='%s', ima021='%s']", ima01, ima02, ima021);
	}
}