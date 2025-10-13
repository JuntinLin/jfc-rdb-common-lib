package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OEA_FILE")
public class OeaFile {
	String oea00;//	varchar2(1)	類別	"類別 0.合約       1.正常訂單   2.換貨訂單 3:出至境外倉 4:境外倉出貨 5:BU間銷售 6:調貨訂單     7:寄銷訂單  8.借貨出貨  9.借貨償價  換貨訂單為銷退不追訂單而再開立者,  換貨訂單不計入銷貨統計
    @Id
	String oea01;//	varchar2(20)	訂單單號/合約單號	
    java.util.Date oea02;//	date	訂單日期/合約日期	
    String oeaud02;// 	varchar2(40)	訂單時間
    String oea03; //	varchar2(10)	帳款客戶編號	"帳款客戶編號 occ01   MISC: 雜項客戶, 可輸入簡稱,統一編號"
    //private com.tiptop.db.entity.OCC occ;//客戶主檔(occ_file)
    String oea032; //	varchar2(40)	帳款客戶簡稱	
    String oea10; //	varchar2(30)	客戶訂單單號	
    String oea11; //	varchar2(1)	訂單來源	"訂單來源 (1.輸入 2.退補 3.合約轉入)         (4.估價單轉入            )         (5.報價單轉入            )         (6.多角代採買            )         (7.境外倉出貨單 no.7175  )
    String oea12; //	varchar2(20)	銷退單號/合約單號/估價單號	
    //String oea14; //	varchar2(10)	人員編號	人員編號   gen01
    //com.tiptop.db.entity.GEN genSalesman;
    @ManyToOne
    @JoinColumn(name = "oea14", referencedColumnName = "gen01")
    private GenFile salesman;
    String oea15; //	varchar2(10)	部門編號	部門編號   gem01
    private Float oea24;//	number(20,10)	匯率
    String oea43; //	varchar2(20)	交運方式
    String oea49; //	varchar2(1)	狀況碼	狀況碼: 0: 開立(Open) 1: 已核准 S: 送簽                 #No.6686 R: 送簽退回 W: 抽單 
    java.util.Date oea72; //	date	確認日	首次確認日 (第一次確認時更新)
    String oeacont; //	varchar2(8)	審核時間
    String oeaconf; //	varchar2(1)	確認否 oeaconf	varchar2(1)	確認否	"確認否 (Y/N/X)
    String oeaprsw; //	number(5)	訂單列印次數
    String oeauser; //	varchar2(10)	資料所有者
    //String oeaoriu; //	varchar2(10)	資料建立者	打單業務
    @ManyToOne
    @JoinColumn(name = "oeaoriu", referencedColumnName = "gen01")
    private GenFile creator;
    //com.tiptop.db.entity.GEN genCreator;//oeaoriu	varchar2(10)	資料建立者	打單業務
    //private String oea21;//	varchar2(4)	稅別	稅別       gec01
    //private Float oea211;//	number(9,4)	稅率	稅率   (由稅別檔預設,不可改)gec04
    //private String oea212;//	varchar2(1)	聯數	"聯數   (由稅別檔預設,不可改)gec05
    private String oea213;//	varchar2(1)	含稅否	含稅否 (由稅別檔預設,不可改)gec07
}
