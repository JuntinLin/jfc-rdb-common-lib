package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.PmlFile;
import com.jfc.rdb.tiptop.entity.PmlFilePK;
@Repository
public interface PmlRepository extends JpaRepository<PmlFile, PmlFilePK> {
	
	/*請購單單身(pml_file)
    請購單單頭(pmk_file)
    pml04	varchar2(40)	料件編號	料件編號 ima01 請購料件編號
    pml16	varchar2(1)	狀況碼	狀況碼 正確值 0/1/2/6/7/8/9 X: 計劃訂單轉入 0: 開立(Open) 1: 已核准 2: 轉成採購單 6: 結案-正常 7: 結案-結長 8: 結案-結短 9: 作廢(Cancel)
    pml20	number(15,3)	訂購量	訂購量                           (97/07) 請購數量 單位為請購單位
    pml21	number(15,3)	已轉採購數量	已轉採購數量                     (97/07) 被轉成採購的累計數量 單位為請購單位
    pmk18	varchar2(1)	確認否	確認否(Y/N) 原'FOB條件'
    pmk25	varchar2(1)	狀況碼	狀況碼 正確值 0/1/2/6/9 X: 計劃訂單轉入 0: 開立(Open) 1: 已核准 2: 轉成採購單 6: 結案 9: 作廢 S: 送簽中               # NO.6686 R: 送簽退回 W: 抽單    
    */
    //2017-03-01 雅芳建議:未核准的請購單也應該計算在內，暨包含未確認(pmk18='N') & pmk = 'S'
	@Query("""
	        SELECT COALESCE(p.pml20 - p.pml21, 0) as applyQty 
	        FROM PmlFile p 
	        LEFT JOIN p.pmk pmk 
	        WHERE p.pml16 NOT IN ('2', '6', '7', '8', '9') 
	        AND pmk.pmk18 IN ('Y', 'N') 
	        AND p.pml04 = :mano
	        """)
	List<BigDecimal> findPurchaseApplyAmounts(@Param("mano") String mano);
}
