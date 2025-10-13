package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * 庫存資料明細檔(img_file)
*/
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "IMG_FILE")
@NamedQuery(name = "ImgFile.findAll", query = "SELECT i FROM ImgFile i")
public class ImgFile implements Serializable {
    private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    private ImgFilePK id;
	//private String img01; //	varchar2(40)	料件編號	料件編號 儲存存放地點的料件編號
    //private String img02; //	varchar2(10)	倉庫編號	倉庫編號 儲存料件所在之倉庫編號
    //private String img03; //	varchar2(10)	儲位	儲位 儲存料件所在之儲位
    //private String img04; //	varchar2(24)	批號	批號(進貨) 儲存料件所在之進貨批號
    private String img05; //	varchar2(20)	參考號碼	參考號碼 其值可能為驗收單號.採購單號等進貨單 系統維護
    private Integer img06; //	number(5)	參考序號	參考序號 其值可能為驗收單項次.採購單項次等進貨單 之項次 系統維護
    private String img07; //	varchar2(4)	採購單位/生產單位	採購單位/生產單位(收料單位) 系統維護
    private Integer img08; //	number(15,3)	收貨數量	收貨數量(進貨數量) 單位使用採購/生產單位 系統維護
    private String img09; //	varchar2(4)	庫存單位	庫存單位 img之單位
    private Float img10; //	number(15,3)	庫存數量	庫存數量 儲存料件在某倉儲下之庫存總數量
    private java.util.Date img14;//	date	最近一次盤點日期	最近一次盤點日期 儲存料件倉儲最近一次盤點日期 系統維護
    private java.util.Date img15;//	date	最近一次收料日期	最近一次收料日期 儲存料件倉儲最近一次收料日期 系統維護
    private java.util.Date img16;//	date	最近一次發料日期	最近一次發料日期 儲存料件倉儲最近一次發料日期 系統維護
    private java.util.Date img17;//	date	最近一次異動日期	最近一次異動日期 儲存料件倉儲最近一之進出料日期 系統維護
    private String img23; //	varchar2(1)	是否為可用倉儲	是否為可用倉儲 提供使用者於倉庫管理時區別出可用料件 與不可用料件或報廢等倉儲管理　　　　　 正確值 'Y' 或 'N' Y: 可用倉儲 N: 不可用倉儲

    @ManyToOne
    @JoinColumn(name = "img01", referencedColumnName = "ima01", 
            insertable = false, updatable = false)
    private ImaFile ima;
}
