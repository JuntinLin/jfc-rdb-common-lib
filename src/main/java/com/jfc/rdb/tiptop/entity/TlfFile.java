package com.jfc.rdb.tiptop.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *異動記錄檔(tlf_file)
 * @author justin
 tlf12	number(20,8)	異動數量單位與異動目的數量單位轉換率	異動數量單位與異動目的數量單位轉換率儲存異動數量單位與異動目的數量單位之間的轉換率
tlf13	varchar2(20)	異動命令代號	異動命令代號儲存異動命令代號
tlf14	varchar2(10)	異動原因	異動原因儲存異動原因異動原因包括退貨原因、報廢原因
tlf15	varchar2(24)	借方會計科目	借方會計科目 (成本計算後更新)
tlf16	varchar2(24)	貸方會計科目	貸方會計科目 (成本計算後更新)
tlf17	varchar2(255)	備註	
tlf18	number(15,3)	異動後總庫存量	異動後總庫存量儲存異動後該料件總庫存數量數量單位為該料件庫存單位（料件主檔）
tlf20	varchar2(10)	專案號碼	專案號碼儲存異動有關的專案號碼
tlf21	number(20,6)	成會異動成本	成會異動成本 (成會計算後更新)
tlf211	date	成會計算日期	成會計算日期 (成本計算後更新)
tlf212	varchar2(8)	成會計算時間	成會計算時間 (成本計算後更新)
tlf60	number(20,8)	異動單據單位對庫存單位之換算率	異動單據單位對庫存單位(ima_file)之換算率
tlf63	number(10)	No Use	
tlf64	varchar2(20)	手冊編號	手冊編號  (A050)
tlf65	varchar2(20)	傳票編號	
tlf66	varchar2(1)	多倉出貨 Flag	No use
tlf901	varchar2(10)	成本庫別	成本庫別             97/08/23default=imd09

*/
@Data
@Entity
@Table(name="TLF_FILE")
@NamedQuery(name="TlfFile.findAll", query="SELECT s FROM TlfFile s")
public class TlfFile implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "tlf01", length = 40)
	private String tlf01;//	varchar2(40)	異動料件編號	異動料件編號儲存異動料件--異動經由何處所發生之資料(來源)--------
    private Integer tlf02;//	number(5)	來源狀況	來源狀況儲存發生異動的來源狀況，其分類如下00: 調整／盤點   05:一階段調整   06:二階段調整   07:營運中心二階段調整10: 採購    11: 一般     採購單    14: 消耗性   採購單    16: 資材性   採購單    60: 委外加工 採購單20: 檢驗區25: F.Q.C.30: 退貨    31:退貨補貨    32:退貨扣款40: 報廢50: 倉庫    55: 一階段倉庫間調撥    56: 二階段倉庫間調撥    57: 營運中心間調撥60: 工單    61 :一般工單    62 :最後裝配工單    63 :再加工工單    64 :委外工單    65 :拆件式工單    66 :多營運中心工單70: 銷單    71: 製造管理    72: 銷售管理       722:出貨項次修改       723:出貨項次刪除       724:出貨項次新增       725:出貨整張刪除       731:銷退入庫       732:銷退入庫項次修改       733:銷退入庫項次刪除       734:銷退入庫項次新增       735:銷退入庫整張刪除80: 同業90: 雜項100: MRB   101: 接受   102: 退貨補貨   103: 退貨扣款   104: 報廢   105: 分類   106: 再處理   107: 下不為例110:再加工120:其它(如已分攤製費)
    private String tlf020;//	varchar2(10)	異動來源營運中心編號	營運中心編號
    private String tlf021;//	varchar2(10)	倉庫別	倉庫別儲存發生異動的來源倉庫別視發生異動的來源狀況是否與庫存有關，而決定是否需儲存倉庫別
    private String tlf022;//	varchar2(10)	儲位	儲位儲存發生異動的來源儲位視發生異動的來源狀況是否與庫存有關，而決定是否需儲存儲位
    private String tlf023;//	varchar2(24)	批號	批號 （庫存批號）儲存發生異動的來源庫存批號視發生異動的來源狀況是否與庫存有關，而決定是否需儲存庫存批號
    private Float tlf024;//	number(15,3)	異動後庫存數量	異動後庫存數量儲存發生異動的來源異動後庫存數量視發生異動的來源狀況是否與庫存有關，而決定是否需儲存異動後庫存數量
    private String tlf025;//	varchar2(4)	異動後庫存數量單位	異動後庫存數量單位儲存發生異動的來源異動後庫存數量單位視發生異動的來源狀況是否與庫存有關，而決定是否需儲存異動後庫存數量單位
    private String tlf026;//	varchar2(20)	單據編號	單據編號儲存發生異動的來源異動單據編號視發生異動的來源狀況是否與單據有關，而決定是否需儲存異動單據，可能為採購單、驗收單、退貨單、報廢單、調撥單、發料單借料單....等
    private Integer tlf027;//	number(5)	單據項次	單據項次儲存發生異動的來源異動單據明細項次視發生異動的來源狀況是否與單據有關，而決定是否需儲存異動單據明細項次資料，如該類單據無項次則其值應為'NULL'--異動至何處所發生之資料(目的)----------
    private Integer tlf03;//	number(5)	目的狀況	目的狀況儲存發生異動的目的狀況，其分類如下00: 調整／盤點10: 採購    11: 一般     採購單    12: 委外加工 採購單    13: 退貨補貨 採購單    14: 消耗性   採購單    15: 服務性   採購單    16: 資材性   採購單    17: 多營運中心   採購單    18: 委外代買 採購單20: 檢驗區25: F.Q.C.30: 退貨    31:退貨補貨    32:退貨扣款40: 報廢50: 倉庫    55: 倉庫間調撥60: 工單    61 :一般工單    62 :最後裝配工單    63 :再加工工單    64 :委外工單    65 :拆件式工單    66 :多營運中心工單70: 銷單    71: 製造管理    72: 銷售管理       722:出貨項次修改       723:出貨項次刪除       724:出貨項次新增       725:出貨整張刪除       731:銷退入庫       732:銷退入庫項次修改       733:銷退入庫項次刪除       734:銷退入庫項次新增       735:銷退入庫整張刪除80: 同業90: 雜項100: MRB   101: 接受   102: 退貨補貨   103: 退貨扣款   104: 報廢   105: 分類   106: 再處理   107: 下不為例110:再加工120:其它(如已分攤製費)
    private String tlf030;//	varchar2(10)	異動目的營運中心編號	營運中心編號
    private String tlf031;//	varchar2(10)	倉庫別	倉庫別儲存發生異動的目的倉庫別視發生異動的目的狀況是否與庫存有關，而決定是否需儲存倉庫別
    private String tlf032;//	varchar2(10)	儲位	儲位儲存發生異動的目的儲位視發生異動的目的狀況是否與庫存有關，而決定是否需儲存儲位
    private String tlf033;//	varchar2(24)	批號	批號 （庫存批號）儲存發生異動的目的庫存批號視發生異動的目的狀況是否與庫存有關，而決定是否需儲存庫存批號
    private Float tlf034;//	number(15,3)	異動後庫存數量	異動後庫存數量儲存發生異動的目的異動後庫存數量視發生異動的目的狀況是否與庫存有關，而決定是否需儲存異動後庫存數量
    private String tlf035;//	varchar2(4)	異動後庫存數量單位	異動後庫存數量單位儲存發生異動的目的異動後庫存數量單位視發生異動的目的狀況是否與庫存有關，而決定是否需儲存異動後庫存數量單位
    private String tlf036;//	varchar2(20)	單據編號	單據編號(參考號碼)儲存發生異動的目的異動單據編號視發生異動的目的狀況是否與單據有關，而決定是否需儲存異動單據，可能為採購單、驗收單、退貨單、報廢單、調撥單、發料單借料單....等
    private Integer tlf037;//	number(5)	單據項次	單據項次儲存發生異動的目的異動單據明細項次視發生異動的目的狀況是否與單據有關，而決定是否需儲存異動單據明細項次資料，如該類單據無項次則其值應為'NULL'--異動資料------------------------------
    private String tlf04;//	varchar2(10)	工作站	工作站儲存發生異動的工作站
    private String tlf05;//	varchar2(6)	作業編號	作業編號儲存發生異動的作業編號
    private java.util.Date tlf06;//	date	單據扣帳日期	
    private java.util.Date tlf07;//	date	執行扣帳日期	異動資料產生日期儲存異動資料產生日期
    private String tlf08;//	varchar2(8)	異動資料產生時間	異動資料產生時間 (時:分:秒)儲存異動資料產生時間
    private String tlf09;//	varchar2(10)	異動資料發出者	異動資料發出者儲存異動資料產生者
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tlf09", referencedColumnName = "gen01", insertable = false, updatable = false)
    com.jfc.rdb.tiptop.entity.GenFile tlf09Staff; //異動者
    private Float tlf10;//	number(15,3)	異動數量	異動數量儲存異動數量
    private String tlf11;//	varchar2(4)	異動數量單位	異動數量單位儲存異動數量單位
    private String tlf19;//	varchar2(10)	異動廠商/客戶編號/部門編號	異動廠商/客戶編號/部門編號儲存異動有關的廠商/客戶編號
    private String tlf61;//	varchar2(5)	單別	單別 (=tlf905[1,3])          97/10/27
    private String tlf62;//	varchar2(40)	工單單號	(ASR此欄是料號(asri210,asri220))
    private String tlf902;//	varchar2(10)	倉庫	倉庫                 97/06/18
    private String tlf903;//	varchar2(10)	儲位	儲位                 97/06/18
    private String tlf904;//	varchar2(24)	批號	批號                 97/06/18
    private String tlf905;//	varchar2(20)	單號	單號                 97/06/18
    private Integer tlf906;//	number(5)	項次	項次                 97/06/18
    private Integer tlf907;//	number(5)	入出庫碼	入出庫碼 (1:入庫   -1:出庫   0:其它    

    //private String salesReturnMsg;//銷貨退回單資訊
    //private com.tiptop.db.entity.OHB ohb;//銷退單
    //private com.tiptop.db.entity.OGB ogb;//出貨單、驗退單
	
}
