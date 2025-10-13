package com.jfc.rdb.tiptop.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
* @author justin
* 出貨單單頭檔(oga_file)
oga021	date	結關日期	
oga022	date	裝船日期	

oga033	varchar2(20)	帳款客戶統一編號	
oga04	varchar2(10)	送貨客戶編號	送貨客戶編號   occ1
oga044	varchar2(10)	送貨地址碼	送貨地址碼   空白: 使用送貨客戶送貨地址   Code: 使用送貨客戶其它地址   MISC: 臨時輸入送貨地址 (oap_file)
oga05	varchar2(5)	發票別	發票別(自動發票開窗時將會依本欄位取簿號)   預設'1',輸入時不可空白,'X'表示不開發票
oga06	varchar2(1)	修改版本	
oga07	varchar2(1)	出貨是否計入未開發票的銷貨待驗收入	出貨是否計入未開發票的銷貨待驗收入 (Y/N)
oga08	varchar2(1)	1.內銷 2.外銷  3.視同外銷	1.內銷 2.外銷  3.視同外銷->no.A047 欄位值:   1.內銷   2.外銷   3.視同外銷(合約系統使用) 
oga10	varchar2(20)	帳單編號	帳單編號 oma01
oga11	date	應收款日	
oga12	date	容許票據到期日	
oga13	varchar2(10)	科目分類碼	
oga14	varchar2(10)	人員編號	人員編號 gen01
oga15	varchar2(10)	部門編號	部門編號 gem01
ogaprsw	number(5)	列印次數	
ogauser	varchar2(10)	資料所有者	
ogagrup	varchar2(10)	資料所有部門	
ogamodu	varchar2(10)	資料修改者	
ogadate	date	最近修改日	


* 
*/

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OGA_FILE")
@NamedQuery(name = "OgaFile.findAll", query = "SELECT o FROM OgaFile o")
public class OgaFile {
	@Id
	private String oga01; //	varchar2(20)	出貨單號/出通單號	
	
	// 添加反向關係（如果需要）
    @OneToMany(mappedBy = "oga", fetch = FetchType.LAZY)
    private java.util.List<OgbFile> ogbFiles;
	
	private String oga00; //	varchar2(1)	出貨別	出貨別  1.正常出貨:沖正常訂單  2.換貨出貨:沖換貨訂單  3.出至境外倉  4.境外倉出貨  5.BU 間銷售  6.調貨訂單  7.寄銷訂單  A.借貨出貨   B.借貨償價 欄位值:   1.一般出貨   2.換貨出貨   3.出至境外倉   4.境外倉出貨   5.BU間銷售   6.調貨出貨   7.寄銷出貨    A.借貨出貨    B.借貨償價
    private String oga011; //	varchar2(20)	出貨通知單號	出貨通知單號 oga01,oga09='1'
    private java.util.Date oga02;//	date	出貨日期	
    private java.util.Date oga021;//	date	指定交貨日/結關日期
    private String oga03;//	varchar2(10)	帳款客戶編號	帳款客戶編號 occ01   MISC: 雜項客戶, 可輸入簡稱,統一編號
    private String oga032;//	varchar2(40)	帳款客戶簡稱	
    private String oga09;//	varchar2(1)	單據別	單據別(1.出貨通知單 2.一般出貨單        3.無訂單出貨單 4.三角貿易出貨單        5.三角貿易出貨通知單        6.代採買出貨單        8.客戶驗收單        9.客戶驗退單        A.借貨出貨單
    private String oga16;//	varchar2(20)	訂單單號	訂單單號 oea01   有預收訂金時, 本單號一定要輸入   有預收訂金時, 一張出貨僅可沖一張訂單
    private BigDecimal oga24;//	number(20,10)	匯率
    private String ogaconf; //	varchar2(1)	確認否/作廢碼	確認否/作廢碼 (Y/N/X) 欄位值:   N.未確認   X.作廢   Y.已確認 
    private String ogapost; //	varchar2(1)	出貨扣帳否	出貨扣帳否 (Y.已出貨扣帳 N.尚未)
    private String oga55; //	varchar2(1)	狀況碼	No use 欄位值:   0.開立   1.已核准   9.作廢   R.送簽退回   S.送簽中   W.抽單 
    private String ogamksg; //	varchar2(1)	簽核
    private String oga65; //	varchar2(1)	客戶出貨簽收否
    private String ogaoriu; //	varchar2(10)	資料建立者
    private String ogaud05; //	varchar2(40)	自訂欄位-文字	ogaud05_2 自動開立簽收單, ogaud05_3 隨貨附發票
    
}
