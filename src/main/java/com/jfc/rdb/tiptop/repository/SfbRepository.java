package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.SfbFile;

@Repository
public interface SfbRepository extends JpaRepository<SfbFile, String> {
	// sfb01 varchar2(20) 工單編號
	Optional<SfbFile> findById(String sfb01);

	// sfb22 varchar2(20) 訂單編號/預測工單編號 訂單編號/預測工單編號 儲存該工單之指定生產供給的訂單單號
	java.util.List<SfbFile> findBySfb22(String sfb22);

	/*
	 * sfb08 number(15,3) 生產數量 生產數量 預計投入生產的數量，單位為料件生產單位 sfb09 number(15,3) 完工入庫數量
	 * 完工入庫數量 儲存該工單已完工入庫數量 sfb12 number(15,3) 報廢數量 報廢數量 儲存該工單報廢數量
	 */
	@Query("""
			SELECT COALESCE(SUM(s.sfb08) - SUM(s.sfb09) - SUM(s.sfb12), 0)
			FROM SfbFile s
			WHERE s.sfb04 != '8'
			AND s.sfb05 = :mano
			AND s.sfb87 = 'Y'
			""")
	BigDecimal findWipInnerAmount(@Param("mano") String mano);

	/**
	 * RFQ⑰-②-1 改錨工單實際——該料號最近一張「完工入庫/結案」(sfb04 IN '7','8') 工單，
	 * 其實際發料明細(sfs_file)當材料成本錨點來源，取代 BmbFile 理論用量。
	 * sfb87='Y' 排壞（比照既有 {@link #findWipInnerAmount} 慣例）；sfb01 工單編號格式含日期
	 * （如 T511-17060739），DESC 排序近似取最近一張。查無則回傳空（呼叫端 fallback 回現有
	 * BmbFile 理論用量路徑，不是新的失敗狀態）。
	 */
	@Query(value = "SELECT * FROM (SELECT s.* FROM sfb_file s WHERE s.sfb05 = :partNo "
			+ "AND s.sfb04 IN ('7','8') AND s.sfb87 = 'Y' ORDER BY s.sfb01 DESC) WHERE ROWNUM = 1",
			nativeQuery = true)
	Optional<SfbFile> findLatestCompletedWorkOrder(@Param("partNo") String partNo);

	//sfb02	number(5)	工單型態	"工單型態 儲存該工單所屬類別型態,正確值 1/2/5/7/11/12/13/15,
    //  1: 一般工單 / 5: 再加工工單 / 7: 委外工單 / 8: 重工委外工單 #Add By Snow 
    //  11: 拆件式工單 / 13: 預測工單 / 15: 試產工單
    //sfb04	varchar2(1)	工單狀態	"工單狀態 儲存該工單目前處理階段狀況,正確值 1/2/3/4/5/6/7/8
    //  1: 確認生產工單(firm plan) / 2: 工單已發放,料表尚未列印 / 3: 工單已發放,料表已列印 / 4: 工單已發料 / 5: 在製過程中
    //  6: 工單已完工,進入F.Q.C / 7: 完工入庫 / 8: 結案
    //sfb08	number(15,3)	生產數量	"生產數量,預計投入生產的數量，單位為料件生產單位"
    //sfb09	number(15,3)	完工入庫數量	"完工入庫數量,儲存該工單已完工入庫數量"
	@Query("""
			SELECT COALESCE(SUM(s.sfb08) - SUM(s.sfb09), 0)
			FROM SfbFile s
			WHERE s.sfb02 = 7
			AND s.sfb04 != '8'
			AND s.sfb05 = :mano
			AND s.sfb87 = 'Y'
			""")
	BigDecimal findWipOutsourcingAmount(@Param("mano") String mano);
	
	// sfb08 number(15,3) 生產數量   - planned production quantity
	// sfb09 number(15,3) 完工入庫數量 - completed/stored quantity
	// A work order is considered finished when sfb09 and sfb08 are both non-null
	// and the completed quantity (sfb09) has met or exceeded the planned quantity (sfb08).
	@Query("""
	        SELECT COUNT(s) > 0
	        FROM SfbFile s
	        WHERE s.sfb01 = :sfb01
	        AND s.sfb08 IS NOT NULL
	        AND s.sfb09 IS NOT NULL
	        AND s.sfb09 >= s.sfb08
	        """)
	boolean isFinished(@Param("sfb01") String sfb01);
}
