package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
/**
 * 客訴處理單單頭檔(ohc_file)
 */
@Entity
@Table(name = "OHC_FILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OhcFile implements Serializable { 
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ohc01", length = 20)
    private String ohc01;    // 客訴單號

    @Column(name = "ohc02")
    @Temporal(TemporalType.DATE)
    private Date ohc02;      // 客訴日期

    @Column(name = "ohc03", length = 1)
    private String ohc03;    // 目前狀態 '0':未處理 '1':處理中 '2':結案

    @Column(name = "ohc04", length = 20)
    private String ohc04;    // 原出貨單號

    @Column(name = "ohc041", precision = 5)
    private BigDecimal ohc041;  // 原出貨單項次

    @Column(name = "ohc05")
    @Temporal(TemporalType.DATE)
    private Date ohc05;      // 原銷貨日期

    @Column(name = "ohc06", length = 10)
    private String ohc06;    // 客戶編號

    @Column(name = "ohc061", length = 40)
    private String ohc061;   // 客戶簡稱

    @Column(name = "ohc07", length = 20)
    private String ohc07;    // 發票號碼

    @Column(name = "ohc08", length = 40)
    private String ohc08;    // 產品編號

    @Column(name = "ohc081", length = 120)
    private String ohc081;   // 品名規格

    @Column(name = "ohc09", length = 20)
    private String ohc09;    // 客戶訂單

    @Column(name = "ohc10", length = 10)
    private String ohc10;    // 負責業務

    @Column(name = "ohc11", length = 10)
    private String ohc11;    // 處理人員

    @Column(name = "ohc12", length = 1)
    private String ohc12;    // No Use

    @Column(name = "ohc13")
    @Temporal(TemporalType.DATE)
    private Date ohc13;      // 處理期限

    @Column(name = "ohc14")
    @Temporal(TemporalType.DATE)
    private Date ohc14;      // 結案日期

    @Column(name = "ohc15", length = 4)
    private String ohc15;    // 幣別

    @Column(name = "ohc16", precision = 20, scale = 10)
    private BigDecimal ohc16;  // 匯率

    @Column(name = "ohc17", precision = 20, scale = 6)
    private BigDecimal ohc17;  // 單價

    @Column(name = "ohc18", precision = 15, scale = 3)
    private BigDecimal ohc18;  // 客訴數量

    @Column(name = "ohc181", precision = 15, scale = 3)
    private BigDecimal ohc181; // 補貨數量

    @Column(name = "ohc182", precision = 15, scale = 3)
    private BigDecimal ohc182; // 換貨數量

    @Column(name = "ohc183", precision = 15, scale = 3)
    private BigDecimal ohc183; // 銷貨退回

    @Column(name = "ohc184", precision = 20, scale = 6)
    private BigDecimal ohc184; // 折讓金額

    @Column(name = "ohc19", length = 1)
    private String ohc19;    // No Use

    @Column(name = "ohc20", length = 1)
    private String ohc20;    // No Use

    @Column(name = "ohc21", length = 1)
    private String ohc21;    // No Use

    @Column(name = "ohc22", length = 1)
    private String ohc22;    // No Use

    @Column(name = "ohc23", length = 1)
    private String ohc23;    // No Use

    @Column(name = "ohc24", length = 1)
    private String ohc24;    // No Use

    @Column(name = "ohc25", length = 1)
    private String ohc25;    // No Use

    @Column(name = "ohcconf", length = 1)
    private String ohcconf;  // 確認否

    @Column(name = "ohcuser", length = 10)
    private String ohcuser;  // 資料所有者

    @Column(name = "ohcgrup", length = 10)
    private String ohcgrup;  // 資料所有部門

    @Column(name = "ohcmodu", length = 10)
    private String ohcmodu;  // 資料修改者

    @Column(name = "ohcdate")
    @Temporal(TemporalType.DATE)
    private Date ohcdate;    // 最近修改日

    @Column(name = "ohcud01", length = 255)
    private String ohcud01;  // 自訂欄位-Textedit

    @Column(name = "ohcud02", length = 40)
    private String ohcud02;  // 自訂欄位-文字

    @Column(name = "ohcud03", length = 40)
    private String ohcud03;  // 自訂欄位-文字

    @Column(name = "ohcud04", length = 40)
    private String ohcud04;  // 自訂欄位-文字

    @Column(name = "ohcud05", length = 40)
    private String ohcud05;  // 自訂欄位-文字

    @Column(name = "ohcud06", length = 40)
    private String ohcud06;  // 自訂欄位-文字

    @Column(name = "ohcud07", precision = 15, scale = 3)
    private BigDecimal ohcud07;  // 自訂欄位-數值

    @Column(name = "ohcud08", precision = 15, scale = 3)
    private BigDecimal ohcud08;  // 自訂欄位-數值

    @Column(name = "ohcud09", precision = 15, scale = 3)
    private BigDecimal ohcud09;  // 自訂欄位-數值

    @Column(name = "ohcud10", precision = 10)
    private BigDecimal ohcud10;  // 自訂欄位-整數

    @Column(name = "ohcud11", precision = 10)
    private BigDecimal ohcud11;  // 自訂欄位-整數

    @Column(name = "ohcud12", precision = 10)
    private BigDecimal ohcud12;  // 自訂欄位-整數

    @Column(name = "ohcud13")
    @Temporal(TemporalType.DATE)
    private Date ohcud13;    // 自訂欄位-日期

    @Column(name = "ohcud14")
    @Temporal(TemporalType.DATE)
    private Date ohcud14;    // 自訂欄位-日期

    @Column(name = "ohcud15")
    @Temporal(TemporalType.DATE)
    private Date ohcud15;    // 自訂欄位-日期

    @Column(name = "ohcplant", length = 10)
    private String ohcplant; // 所屬營運中心

    @Column(name = "ohclegal", length = 10)
    private String ohclegal; // 所屬法人

    @Column(name = "ohcoriu", length = 10)
    private String ohcoriu;  // 資料建立者

    @Column(name = "ohcorig", length = 10)
    private String ohcorig;  // 資料建立部門

    @OneToMany(mappedBy = "ohcFile")
    private List<OhdFile> ohdFiles;
    
}