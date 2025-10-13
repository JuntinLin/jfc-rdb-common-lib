package com.jfc.rdb.tiptop.model.dto;

import java.math.BigDecimal;

import com.jfc.rdb.common.dto.AbstractDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ImaDTO extends AbstractDTO{
	private String ima01; //ima01	varchar2(40)	料件編號	料件編號料件存在系統中代表編號, 是唯一的
	private String ima02; //ima02	varchar2(120)	品名	品名規格描述該料件的品名規格, 如有需要進一步描述, 則可利用品名規格額外說明資料檔記錄
	private String ima021;	//ima021	varchar2(120)	規格	規格                   (97/08/18
	private String ima06; //	varchar2(10)	分群碼	分群碼0: 表由其他相關系統而來尚未分群當在料件基本資料建立時, 可利用分群碼來預設 相關欄位基本預設值
	private String ima08; //	varchar2(1)	來源碼	來源碼說明料件屬性, 用以勾劃出不同歸屬特性的料件, 以便在料件庫存異動及物料需求 ..等作業時, 提供作業管制方式或限制正確值 C/T/D/A/M/P/X/K/U/V/R/Z/S預設值 依分群碼類別預設 或 空白, 且結果需有一種來源碼C: 規格組件 (Configurable Product)T: 最後規格料件 (FAS Product)D: 特性料件 (Feature)A: 族群料件 (Family Product)M: 自製料件 (Make Product)P: 採購料件 (Purchase)X: 虛擬料件 (Phantom)K: 配件虛擬料件 (Kit)U: 自製大宗料件V: 採購大宗料件R: 在製途料件 (Routable)Z: 雜項料件 (Miscellaneous)S: 廠外加工料件 (Subcontract)
	private String ima09; //	varchar2(10)	其他分群碼 一
	private String ima10; //	varchar2(10)	其他分群碼 二
	private String ima11; //	varchar2(10)	其他分群碼 三
	private String ima12; //	varchar2(10)	其他分群碼 四
	private BigDecimal ima27; //	number(15,3)	安全庫存量	安全庫存量當在使用上, 希望在考量 MPS/MRP 產生計劃訂單(PLANNED ORDER)時, 在需求數量上能加上對庫存數量亦能維持一定的水準的庫存策略, 則可使用安全庫存量 或 安全庫存期間的作法安全庫存量是以一固定數量來達成上述庫存策略預設值 依分群碼類別預設 或 '0'
	private float ima46; //	number(15,3)	最少採購數量	最少採購數量如果該料件不為採購性料件時, 不需輸入儲存該料件採購時, 採購數量的最低水準數量; 若為'0'時, 表示不稽核此項預設值 依分群碼類別預設 或 '0'
    private java.util.Date ima73;//	date	最近入庫日	最近入庫日該料件最近一次庫存收貨日期系統維護

    private String ima571; //	varchar2(40)	主製程料件	製程料號生管系統將以ima571與sfb06讀取製程資料
    private Integer imaud10; //	number(10)	出圖單位 0:設計出圖 1:業務出圖
    private String imaacti;//	varchar2(1)	資料有效碼	資料有效碼系統維護
    
    private BigDecimal stockAmount;   //庫存量 ima.setStockAmount(this.findMatStock(ima.getIma01())); 
    private BigDecimal stockAmount_AE;//AE倉(現場倉_組立)庫存量
    private float stockAmount_noLotNo;
    //img23	varchar2(1)	是否為可用倉儲	是否為可用倉儲 提供使用者於倉庫管理時區別出可用料件 與不可用料件或報廢等倉儲管理正確值 'Y' 或 'N' Y: 可用倉儲 N: 不可用倉儲
    //private float avl_stk; //可用量 ima.setAvl_stk(this.findMatStock(ima.getIma01())); 同stockAmount
    private float unavl_stk; //不可用量
    private String prsna; //成品簡稱
    private BigDecimal sendingAmount;//工單發料
    private BigDecimal saleAmount;//銷售量
    private BigDecimal sendingAmount_1Year;//一年工單發料
    private BigDecimal saleAmount_1Year;//一年銷售量
    private BigDecimal sendingAmount_halfYear;//半年工單發料
    private BigDecimal saleAmount_halfYear;//半年銷售量
    
    private float averageCost;//平均單價 ccc23  number(20,6)	本月平均單價(a+b+c+d+e)
    private BigDecimal onOrder; //-受訂量 ima.setOnOrder(this.findOnOrder(ima.getIma01()));//-受訂量
    private BigDecimal onJob;//-工單備料量 ima.setOnJob(this.findOnJob(ima.getIma01()));//-工單備料量
    private float onJobLack;//-工單欠料量
    private BigDecimal onApply;//+請購量
    private BigDecimal onPurchase;//+採購量
    private BigDecimal onWip_Inner;//+工單在製量
    private BigDecimal onWip_Outsourcing;//+委外在製量
    private BigDecimal availableQuantity;//預計可用量 ima.setAvailableQuantity(ima.getStockAmount() - ima.getAi() + ima.getOoi());
    private BigDecimal suggestApplyQuantity;//建議請購量 ima.setActualQuantity(ima.getStockAmount() - ima.getOnOrder() - ima.getOnJob());//2017-01-17 意昕,實際可用量=庫存量-受訂量-工單備料量
    private BigDecimal actualQuantity;//實際可用量=庫存量-受訂量-工單備料量
    //ima.setAi(ima.getOnOrder() + ima.getOnJob());//保留量booking = 受訂量+工單備料量
    private BigDecimal ai; //保留量(Allocated Inventory)</strong>：客戶訂單展開後的零件需求量</p>
    //ima.setOoi(ima.getOnApply() + ima.getOnPurchase() + ima.getOnWip_Inner() + ima.getOnWip_Outsourcing());//在途量=請購量 pml+採購量 pmn+工單在製量+委外在製量
    private BigDecimal ooi; //在途量(On-Order Inventory)</strong>：零件已經外包、採購的數量扣除內製入庫量</p>
    private BigDecimal stockAmount_overAging180; //超過180天呆滯數量

}
