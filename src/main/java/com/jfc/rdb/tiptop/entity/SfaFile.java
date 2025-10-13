package com.jfc.rdb.tiptop.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
* @author justin
備料檔(sfa_file)

sfa04	number(15,3)	原發數量	原發數量儲存該工單備料料件，由產品結構產生而來之原有數量；在與生產數量對比上使用標準用量加上損耗率，延展而來
sfa064	number(15,3)	盤損數量	盤損數量儲存該工單備料料件，盤點時的減少數量
sfa065	number(15,3)	委外代買量	委外代買量儲存該工單備料料件，委外代買數量
sfa066	number(15,3)	委外代買已交量	委外代買已交量儲存該工單備料料件，委外代買已交數量
sfa07	number(15,3)	欠料量	No Use (for GP5.2發料改善專案)
sfa09	number(5)	前置時間調整	前置時間調整(天)表示自工單起始生產日算起,需多少時間備料若為正值表示多少天後若為負值表示多少天前
sfa10	varchar2(5)	前置時間調整	前置時間調整(時:分)儲存經備料前置調整後，對該日的時間調整若為正值表示多少時間後若為負值表示多少時間前量產系統專用
sfa11	varchar2(1)	旗標	旗標儲存備料料件的來源特性正確值 E/U/V/R/N E: 消耗性料件U: 大宗自製料件V: 大宗採購料件R: 在製途料件N: 除外一般料件--------單位換算率----------------------
sfa13	number(20,8)	發料單位/庫存單位換算率	發料單位/庫存單位換算率ex.發料單位=kg 庫存單位=g 1000/1=1000(換算率)
sfa14	varchar2(4)	成本單位	成本單位儲存該工單備料料件的發料單位，將由 [料件主檔] 產生而來
sfa15	number(20,8)	成本單位/材料成本檔成本單位之換算率	成本單位/材料成本檔成本單位之換算率ex.成本單位=g 成本單位=kg 1/1000=0.001(換算率)
sfa16	number(16,8)	標準單位用量	標準單位用量 (Standard QPA)儲存該工單備料料件的標準單位用量，由BOM產品結構產生而來系統於備料時自動維護,不能修改
sfa161	number(16,8)	實際單位用量 (Actual QPA)	實際單位用量 (Actual QPA)系統若使用損耗率, 則本用量會加上損耗率系統若不用損耗率, 則本用量不加上損耗率系統若不用損耗率, 則本用量等於標準用量.系統於備料時自動維護,不能修改
sfa25	number(15,3)	未備料量	未備料量儲存該工單備料料件的應發數量大於 [料件主檔] 的可被備料量，而又無其他可替代料的數量；亦即未能備料量
sfa26	varchar2(1)	替代碼	替代碼儲存該工單備料的料件為原始組合的下階料件或採用取／替代料件正確值 0/1/2/3/4/S/U0: 原始料件, 不可被取替代1: 新料, 有舊料可取代 (取代後,轉為'3')2: 主料, 有副料可替代 (替代後,轉為'4')3: 新料, 已經被取代4: 主料, 已經被替代5: 主料, 可做SET替代(SET替代號,轉為'6')                     #Add By 養生20036: 主料, 已做SET替代 #Add By 養生2003U: 舊料(取代料件)S: 副料(替代料件)T: SET替代料         #Add BY 養生2003
sfa28	number(15,6)	替代率	替代率當 sfa26='012345'時,本欄位=1當 sfa26='S/U'  時,本欄位儲存替代率當 sfa26='T'時,儲存替代料替代量bob07               #Add BY 養生2003
sfa29	varchar2(40)	上階料號	
sfa30	varchar2(10)	指定倉庫	
sfa31	varchar2(10)	指定儲位	
*/
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SFA_FILE")
public class SfaFile {
	@EmbeddedId
    private SfaFilePK id;
	//private String sfa01;//	varchar2(20)	工單編號	工單編號 sfb01
	@ManyToOne
    @JoinColumn(name = "sfa01", referencedColumnName = "sfb01", 
            insertable = false, updatable = false)
    private SfbFile sfb; //工單sfb01 <= sfa01 
    private int sfa02;//	number(5)	工單型態	工單型態 (同sfb02)
    //private String sfa03;//	varchar2(40)	料件編號	料件編號 ima01儲存該工單所屬下階料件編號，應為被發放投入生產的料件編號
    @ManyToOne
    @JoinColumn(name = "sfa03", referencedColumnName = "ima01", 
            insertable = false, updatable = false)
    private ImaFile ima;
    // sfa08 multiple id
    //private String sfa08;//	varchar2(6)	作業編號	作業編號儲存該工單備料料件，預期投入生產的作業所屬作業編號；可由[工單檔]指定的製程編號及產品結構而得
    private String sfa12;//	varchar2(4)	發料單位	發料單位儲存該工單備料料件的發料單位，將由產品產品結構產生而來
    private Float sfa16;//	number(16,8)	標準單位用量	標準單位用量 (Standard QPA)儲存該工單備料料件的標準單位用量，由BOM產品結構產生而來系統於備料時自動維護,不能修改
    private Float sfa161;//	number(16,8)	實際單位用量 (Actual QPA)	實際單位用量 (Actual QPA)系統若使用損耗率, 則本用量會加上損耗率系統若不用損耗率, 則本用量不加上損耗率系統若不用損耗率, 則本用量等於標準用量.系統於備料時自動維護,不能修改
    private String sfa27;//	varchar2(40)	被替代料號	被替代料號當 sfa26='012345T'時,欄位儲存料號同sfa03當 sfa26='S/U'  時,本欄位儲存被替代料號
    //private String sfa012;//	varchar2(10)	製程段號 multiple id
    private int sfa013;//	number(5)	製程序
    private Float sfa05;//	number(15,3)	應發數量	應發數量儲存該工單備料料件，使用者經過修改後，決定的備料量；開始時，應與原發數量相同
    private Float sfa06;//	number(15,3)	已發數量	已發數量儲存該工單備料料件，已經被發料的數量；
    private Float sfa061;//	number(15,3)	已領數量	已領數量儲存該工單備料料件，已經被領料的數量；
    private Float sfa062;//	number(15,3)	超領數量	超領數量儲存該工單備料料件，被超領料的數量
    private Float sfa063;//	number(15,3)	報廢數量	報廢數量儲存該工單備料料件，被報廢料的數量
    private Integer sfaud12;// number( 10)	發料狀態碼	1.缺料 2.有採購單 4.有子工單 8.有製造通知單
}
