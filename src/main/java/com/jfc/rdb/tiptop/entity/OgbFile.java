package com.jfc.rdb.tiptop.entity;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
* @author justin
出貨單身檔(ogb_file)
ogb05	varchar2(4)	銷售單位	銷售單位 (與訂單必須一致)(No-Entry)ima31
ogb05_fac	number(20,8)	銷售/庫存彙總單位換算率	銷售/庫存彙總單位換算率 (To ima25)
ogb06	varchar2(120)	品名規格	
ogb07	varchar2(10)	額外品名編號	額外品名編號  imc02,imc01=ogb04
ogb08	varchar2(10)	出貨營運中心編號	出貨營運中心編號  azp01
ogb09	varchar2(10)	出貨倉庫編號	出貨倉庫編號  imd01(img02)
ogb091	varchar2(10)	出貨儲位編號	出貨儲位編號  ime01(img03)
ogb092	varchar2(24)	出貨批號	出貨批號            img04   No.+024
ogb11	varchar2(40)	客戶產品編號	客戶產品編號 obk03,obk01=ogb04,obk02=oga


ogb14t	number(20,6)	原幣含稅金額	
ogb15	varchar2(4)	庫存明細單位由廠/倉/儲/批自動得出	庫存明細單位(img09)由廠/倉/儲/批自動得出
ogb15_fac	number(20,8)	銷售/庫存明細單位換算率	銷售/庫存明細單位換算率 (To ogb15)
ogb17	varchar2(1)	多倉儲批出貨否	多倉儲批出貨否 (Y/N)   Y:多倉儲批出貨, 需再輸入庫存異動明細     (本項次下再掛另一單身 ogc_file)
ogb18	number(15,3)	預計出貨數量	預計出貨數量 (依銷售單位)
ogb19	varchar2(1)	檢驗否	料號是否需要檢驗
ogb20	varchar2(1)	No Use	
ogb21	varchar2(1)	No Use	
ogb22	varchar2(1)	No Use	
ogb60	number(15,3)	已開發票數量	
ogb1005	varchar2(1)	作業方式	1-出貨,2-折扣

* 
*/

@Entity
@Table(name = "OGB_FILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OgbFile {
	@EmbeddedId
    private OgbFilePK id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ogb01")  // 映射到複合主鍵的 ogb01 欄位
    @JoinColumn(name = "ogb01", referencedColumnName = "oga01", 
                insertable = false, updatable = false)
    private OgaFile oga;
	
	
	//private String ogb04; //	varchar2(40)	產品編號	產品編號 ima01(img01)
	
	@ManyToOne
    @JoinColumn(name = "ogb04", referencedColumnName = "ima01")
    private ImaFile ima;
    //private String ogb31; //	varchar2(20)	訂單單號	訂單單號 oea01(oeb01)
    //private Integer ogb32; //	number(5)	訂單項次	訂單項次       oeb03
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ogb31", referencedColumnName = "oeb01", insertable = false, updatable = false),
        @JoinColumn(name = "ogb32", referencedColumnName = "oeb03", insertable = false, updatable = false)
    })
    private OebFile oeb;
    
    private BigDecimal ogb12; //	number(15,3)	實際出貨數量	實際出貨數量 (依銷售單位)
    private BigDecimal ogb13; //	number(20,6)	原幣單價	
    private BigDecimal ogb14; //	number(20,6)	原幣未稅金額	
    private BigDecimal ogb16; //	number(15,3)	數量	數量 (依庫存明細單位)
    private BigDecimal ogb50; //	number(15,3)	累計簽收數量
    private BigDecimal ogb51; //	number(15,3)	已簽退數量

}
