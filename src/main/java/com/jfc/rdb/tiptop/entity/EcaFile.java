package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the ECA_FILE database table.
 * 工作站基本資料(eca_file)
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ECA_FILE")
@NamedQuery(name="EcaFile.findAll", query="SELECT e FROM EcaFile e")
public class EcaFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eca01;//工作站編號	工作站編號工作場所編號, 可能為 一製造營運中心/一廠房/一工作場所
	
	private String eca02;//說明	說明簡述工作站特性說明
	
	private String eca03;//工作站所屬部門別	工作站所屬部門別需存在公司部門檔內
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eca03", referencedColumnName = "gem01", insertable = false, updatable = false)
    private GemFile department;    // 關聯部門資料
	
	private String eca04;//工作站型態	工作站型態正確值為 0/1/20: 正常1: 廠外加工2: 廠內加工   廠內加工與廠外加工之差別, 在於廠內   加工除了要負擔廠內加工成本外, 尚需   負擔廠內加工製造費用

	private String eca05;//	倉庫別	

	@Column(name="ECA05_WAR")
	private String eca05War;//	存放位置	

	private String eca06; //產能型態	產能型態是用以表示工作站產能的瓶頸是來自人工產能或機器產能正確值為 1/21:機器產能2:人工產能

	private String eca07;	//班次次數

	private BigDecimal eca08;

	private BigDecimal eca09;

	private BigDecimal eca10;

	private BigDecimal eca11;

	private BigDecimal eca12;

	private BigDecimal eca13;

	private String eca14;

	private String eca15;

	private BigDecimal eca16;

	private String eca17;

	private BigDecimal eca18;

	private String eca19;

	private BigDecimal eca20;

	private BigDecimal eca201;

	private String eca21;

	private String eca211;

	private BigDecimal eca22;

	private String eca23;

	private BigDecimal eca24;

	private String eca25;

	private BigDecimal eca26;

	private String eca27;

	private BigDecimal eca28;

	private String eca29;

	private BigDecimal eca30;

	private BigDecimal eca301;

	private String eca31;

	private String eca311;

	private BigDecimal eca32;

	private String eca33;

	private BigDecimal eca34;

	private String eca35;

	private BigDecimal eca36;

	private String eca37;

	private BigDecimal eca38;

	private String eca39;

	private BigDecimal eca40;

	private String eca41;

	private BigDecimal eca42;

	private String eca43;

	private BigDecimal eca44;

	private String eca45;

	private BigDecimal eca46;

	private String eca47;

	private BigDecimal eca50;

	private BigDecimal eca51;

	private BigDecimal eca52;

	private BigDecimal eca53;

	private String eca54;

	private String eca55;

	private BigDecimal eca60;

	private BigDecimal eca61;

	private BigDecimal eca62;

	private BigDecimal eca63;

	private String ecaacti;

	private Object ecadate;

	private String ecagrup;

	private String ecamodu;

	private String ecaorig;

	private String ecaoriu;

	private String ecauser;

	//bi-directional many-to-one association to EcmFile
	@OneToMany(mappedBy="ecaFile")
	private List<EcmFile> ecmFiles;

	

}