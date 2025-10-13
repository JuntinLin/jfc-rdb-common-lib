package com.jfc.rdb.tiptop.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OMA_FILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmaFile {
	private String oma00; //	varchar2(2)	帳款類別	"帳款類別 (1*:應收帳款, 2*:待抵帳款 3*:其他) 10.待抵帳扣費用(流通) 11.訂金應收, 12.出貨應收, 13.尾款應收, 14.雜項應收, 15.訂金/押金(流通), 16.代退款應收, 17.應收(流通), 18.儲值卡訂金(流通), 19.代收, 21.退貨折讓待抵, 22.雜項待抵, 23.預收(訂金),24.暫收(溢收), 25.折扣, 26.預收(流通), 27.代收應返, 28.代退, 31.其他應收(不列入應收帳款)"
    @Id
	private String oma01;//	varchar2(20)	帳款編號	"帳款編號 11.訂金號碼       21.待抵單號  12.INVOICE#       22.待抵單號  13.INVOICE#2      23.待抵單號  14.應收單號       24.待抵單號"
    private java.util.Date oma02;//	date	帳款日期	
    private String oma03;//	varchar2(10)	帳款客戶編號	帳款客戶編號  occ01  MISC: 雜項客戶, 可輸入簡稱
    private String oma032;//	varchar2(40)	帳款客戶簡稱	
    private String oma04;//	varchar2(10)	發票客戶編號	發票客戶編號(9605 No use,請由發票檔維護)
    private String oma042;//	varchar2(20)	發票客戶統一編號	發票客戶統一編號(9605 No use)
    private String oma043;//	varchar2(80)	發票客戶全名	發票客戶全名(9605 No use)
    private String oma044;//	varchar2(255)	發票客戶地址	發票客戶地址(9605 No use)
    private String oma05;//	varchar2(5)	發票別	發票別 (自動開發票時將會依本欄位取簿號)  預設'1',輸入時不可空白
    private String oma06;//	varchar2(10)	客戶分類	帳款分類 xx
    private String oma07;//	varchar2(1)	出貨是否計入未開發票的銷貨待驗收入	出貨是否計入未開發票的銷貨待驗收入 (Y/N)
    private String oma08;//	varchar2(1)	1.內銷 2.外銷  3.視同外銷	1.內銷 2.外銷  3.視同外銷->no.A047
    private java.util.Date oma09;//	date	發票日期/折讓單日期	
    private String oma10;//	varchar2(20)	發票號碼	發票號碼 (於開立發票後更新)ome01
    private java.util.Date oma11;//	date	應收款日/應扣抵日	        
    //private String oma14;//	varchar2(10)	人員編號	人員編號  gen01
    @ManyToOne    
    @JoinColumn(name = "oma14", referencedColumnName = "gen01")
    private GenFile salesman;
    private String oma18; //	varchar2(24)	科目編號	科目編號 11.應收帳款科目   21.應付銷退款科目 12.應收帳款科目   22.待抵科目 13.應收帳款科目   23.預收科目 14.應收帳款科目   24.溢收科目
    private String oma15;//	varchar2(10)	部門編號	部門編號  gem01
    //com.tiptop.db.entity.GEN genSalesman;
    private String oma16;//	varchar2(20)	訂單單號/出貨單號	訂單單號/出貨單號oea01/oga01 11.訂單單號       21.退貨單號  12.出貨單號       22.參考單號  13.出貨單號       23.原帳單號  14.參考單號       24.沖帳單號
    private String oma23;//	varchar2(4)	幣別	幣別   azi01
    private Float oma24;//	number(20,10)	匯率	匯率 (更改時將更新單身本幣單價金額)
    private Float oma54t;//	number(20,6)	原幣應收含稅金額 / 待抵含稅
    private Float oma55;//	number(20,6)	原幣已沖帳金額
    private Float oma56;//	number(20,6)	本幣應收未稅金額 / 待抵未稅	
    private Float oma56x;//	number(20,6)	本幣應收稅額  / 待抵稅額	本幣應收稅額     / 待抵稅額
    private BigDecimal oma56t;//	number(20,6)	本幣應收含稅金額 / 待抵含稅
    private Float oma57;//	number(20,6)	本幣已沖帳金額
    private Float oma59;//	number(20,6)	本幣發票未稅金額
    private Float oma59x;//	number(20,6)	本幣發票稅額
    private Float oma59t;//	number(20,6)	本幣發票含稅金額
    private BigDecimal oma61; //	number(20,6)	本幣未沖金額
    private String omaconf;//	varchar2(1)	確認否	確認否 (Y/N)
    private String omavoid;//	varchar2(1)	作廢否	作廢否 (Y/N)
    private String omavoid2;//	varchar2(10)	作廢理由碼	作廢理由碼 oak01,oak03='2'
    private Integer omaprsw;//	number(5)	列印次數	
    private String omauser;//	varchar2(10)	資料所有者	
    private String omagrup;//	varchar2(10)	資料所有部門	
    private String omamodu;//	varchar2(10)	資料修改者	
    private java.util.Date omadate;//	date	最近修改日	
    private String oma64;//	varchar2(1)	狀況碼	
    private String omamksg;//	varchar2(1)	簽核否？	
}
