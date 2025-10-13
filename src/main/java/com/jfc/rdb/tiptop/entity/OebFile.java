package com.jfc.rdb.tiptop.entity;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "OEB_FILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OebFile {
	@EmbeddedId
    private OebFilePK id;
	private String oeb04; //	varchar2(40)	產品編號	產品編號 ima01
	private String oeb05; //	varchar2(4)	銷售單位	銷售單位 ima31
	private BigDecimal oeb05_fac; //	number(20,8)	銷售/庫存單位換算率	銷售/庫存單位換算率 (To ima25)
	private String oeb06; //	varchar2(120)	品名規格	
	private BigDecimal oeb12; //	number(15,3)	數量	
	private BigDecimal oeb13; //	number(20,6)	單價	
    BigDecimal oeb14; //	number(20,6)	未稅金額	
    BigDecimal oeb14t;//	number(20,6)	含稅金額
    java.util.Date oeb15; //	date	約定交貨日	
    java.util.Date oeb16; //	date	排定交貨日	

    BigDecimal oeb23; //	number(15,3)	待出貨數量	待出貨數量 (已出貨通知或出貨未扣帳)
    BigDecimal oeb24; //	number(15,3)	已出貨數量	
    BigDecimal oeb25; //	number(15,3)	已銷退數量	已銷退數量(須再換貨出貨)
    BigDecimal oeb26; //	number(15,3)	被結案數量	"被結案數量 ==>(未交量=訂單量-已出貨+已銷退-被結案)"
    String oeb70; //	varchar2(1)	結案否	結案否 (Y/N)
    java.util.Date oeb70d; //	date	結案日期	
    String ta_oeb01; //	varchar2(255)	內部備註	No.0000127481_06_M006 160615 By TSD.Dinner
    String ta_oeb02; //	varchar2(5)	工作壓	No.0000127481_06_M006 160615 By TSD.Dinner
    String ta_oeb03; //	varchar2(5)	測試壓	No.0000127481_06_M006 160615 By TSD.Dinner
    String ta_oeb04; //	varchar2(20)	底漆	No.0000127481_06_M006 160615 By TSD.Dinner
    String ta_oeb05; //	varchar2(20)	面漆	No.0000127481_06_M006 160615 By TSD.Dinner
    String ta_oeb06; //	varchar2(20)	漆種	No.0000127481_06_M006 160615 By TSD.Dinner
    String ta_oeb07; //	varchar2(1)	年約	"No.0000127481_06_M006 160615 By TSD.Dinner Y/N"
    String ta_oeb08; //	varchar2(1)	舊品送回	"No.0000127481_06_M006 160615 By TSD.Dinner Y/N"
    String ta_oeb09; //	varchar2(1)	附測試照片	"No.0000127481_06_M006 160615 By TSD.Dinner Y/N"
    String ta_oeb10; //	varchar2(1)	標籤	"No.0000127481_06_M006 160615 By TSD.Dinner A:貼標籤 B:釘銘牌 C:氣動刻印 D:不貼標籤銘牌 E:雷射刻印"
    String ta_oeb11; //	varchar2(10)	設計人員代號	No.0000127481_06_M005 160718 By TSD.nick add
    String ta_oeb12; //	varchar2(40)	設計人員姓名	No.0000127481_06_M005 160718 By TSD.nick add
    java.util.Date ta_oeb13; //date 設計確認日期
    String ta_oeb14; //varchar2(1) 零件會驗
    private boolean fqc;//FQC
    private Integer oebud10; //	number(10)	設計狀態碼  -1.免出圖 0.未指派 1.已指派 2.確認中 3.執行中 4.完成
    private java.util.Date oebud13; //	date	派工日期
    private java.util.Date oebud14; //	date	執行日期
    private java.util.Date oebud15; //	date	完成日期
    Integer oebud12; // sfb40 number(5)	優先順序	"優先順序儲存該工單派工的優先順序正確值 應不小於零其值愈小者順序愈高"
    @ManyToOne
    @JoinColumn(name = "oeb01", referencedColumnName = "oea01",insertable=false, updatable=false)
    private OeaFile oea;
}
