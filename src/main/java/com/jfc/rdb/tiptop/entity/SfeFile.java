package com.jfc.rdb.tiptop.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *工單料帳歷史檔(sfe_file)
 * @author justin
 * 
sfe24	varchar2(20)	異動命令	
sfe25	varchar2(10)	異動人	
sfe26	varchar2(10)	替代碼	
sfe27	varchar2(40)	被替代料號	post to abx system flag'Y':確認'N':未確認
sfe28	number(5)	單據項次	
sfe91	varchar2(1)	No Use	
sfe92	varchar2(1)	No Use	
sfe93	varchar2(1)	No Use	
sfe94	varchar2(1)	No Use	
sfe95	varchar2(1)	No Use	
sfe30	varchar2(4)	單位一	
sfe31	number(20,8)	單位一換算率(與生產單位)	
sfe32	number(15,3)	單位一數量	
sfe33	varchar2(4)	單位二	
sfe34	number(20,8)	單位二換算率(與生產單位)	
sfe35	number(15,3)	單位二數量	
sfe930	varchar2(10)	成本中心	
sfe36	varchar2(24)	廠牌	
sfeplant	varchar2(10)	所屬營運中心	
sfelegal	varchar2(10)	所屬法人	
sfe012	varchar2(10)	製程段號	
sfe013	number(5)	製程序	
sfe014	varchar2(23)	Run Card	
sfeud01	varchar2(255)	自訂欄位-Textedit	
sfeud02	varchar2(40)	自訂欄位-文字	
sfeud03	varchar2(40)	自訂欄位-文字	
sfeud04	varchar2(40)	自訂欄位-文字	
sfeud05	varchar2(40)	自訂欄位-文字	
sfeud06	varchar2(40)	自訂欄位-文字	
sfeud07	number(15,3)	自訂欄位-數值	
sfeud08	number(15,3)	自訂欄位-數值	
sfeud09	number(15,3)	自訂欄位-數值	
sfeud10	number(10)	自訂欄位-整數	
sfeud11	number(10)	自訂欄位-整數	
sfeud12	number(10)	自訂欄位-整數	
sfeud13	date	自訂欄位-日期	
sfeud14	date	自訂欄位-日期	
sfeud15	date	自訂欄位-日期	
sfe37	varchar2(10)	理由碼	

 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SFE_FILE")
public class SfeFile {
	private String sfe01;//	varchar2(40)	工單單號	工單編號儲存該筆異動的工單編號(ASR此欄是料號(asri210,asri220))
    @Id
	private String sfe02;//	varchar2(20)	異動單據編號	異動單據編號儲存該筆異動的異動單據號碼異動單據可為發料單、退料單號、超領單號
    private String sfe03;//	varchar2(20)	PBI NO(Picking Batch ID)	料表批號儲存異動工單所屬的料表批號
    private java.util.Date sfe04;//	date	異動日期	異動日期儲存該筆異動日期
    private String sfe05;//	varchar2(8)	異動時間	異動時間儲存該筆異動日期的時間
    private String sfe06;//	varchar2(1)	異動別	異動別0. 工單下階料件報廢       -- 領料系統1.工單發料                -- 發料系統2. 完工扣帳 - 消耗料件    -- 發料系統3. 工單超領               -- 發料系統4. 工單退料               -- 發料系統5. 工單下階料件報廢       -- 發料系統6. 庫存移轉               -- 領料系統7. 完工扣帳               -- 領料系統8. 工單超領               -- 領料系統9. 工單退料               -- 領料系統A. 工單發料 - 代買料件    -- 發料系統B. 工單退料 - 代買料件    -- 發料系統系統維護
    private String sfe07;//	varchar2(40)	異動料件編號	異動料件編號儲存該筆異動的料件編號
    private String sfe08;//	varchar2(10)	異動倉庫別	異動倉庫別儲存該筆異動來源的倉庫編號
    private String sfe09;//	varchar2(10)	異動儲位	異動儲位儲存該筆異動來源的儲位
    private String sfe10;//	varchar2(24)	異動批號	異動批號儲存該筆異動來源的批號
    private String sfe11;//	varchar2(255)	備註	
    private String sfe14; //	varchar2(6)	作業編號	作業編號儲存該筆異動的作業編號
    private String sfe15; //	varchar2(10)	工作站編號	工作站編號儲存該筆異動的工作站編號
    private BigDecimal  sfe16; //	number(15,3)	異動數量	異動數量儲存該筆異動的異動數量
    private String sfe17; //	varchar2(4)	異動單位	異動單位儲存該筆異動的異動單位使用工單備料檔上的單位
    private Float sfe18; //	number(20,6)	直接材料成本	
    private Float sfe19; //	number(20,6)	間接材料成本	
    private Float sfe20; //	number(20,6)	直接人工成本	
    private Float sfe21; //	number(20,6)	間接人工成本	
    private Float sfe22; //	number(20,6)	廠外加工材料成本	
    private Float sfe23; //	number(20,6)	廠外加工人工成本	
}
