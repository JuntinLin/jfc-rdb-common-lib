package com.jfc.rdb.tiptop.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.OccFile;

@Repository
public interface OccRepository extends JpaRepository<OccFile, String> {
    
    /**
     * 根據客戶編號查詢客戶資訊
     * 
     * @param occ01 客戶編號
     * @return 客戶實體
     */
    Optional<OccFile> findByOcc01(String occ01);
    
    /**
     * 根據客戶簡稱進行模糊查詢
     * 
     * @param name 客戶簡稱關鍵字
     * @return 符合條件的客戶列表
     */
    List<OccFile> findByOcc02ContainingIgnoreCase(String name);
    
    /**
     * 根據客戶分類查詢
     * 
     * @param occ03 客戶分類
     * @return 符合條件的客戶列表
     */
    List<OccFile> findByOcc03(String occ03);
    
    /**
     * 根據業務員編號查詢其負責的客戶
     * 
     * @param salesmanCode 業務員編號
     * @return 該業務員負責的客戶列表
     */
    @Query("SELECT o FROM OccFile o WHERE o.salesman.gen01 = :salesmanCode")
    List<OccFile> findBySalesmanCode(@Param("salesmanCode") String salesmanCode);
    
    /**
     * 查詢該業務員負責的、且有指定特性的客戶
     * 
     * @param salesmanCode 業務員編號
     * @param customerType 客戶類型
     * @return 符合條件的客戶列表
     */
    @Query("SELECT o FROM OccFile o WHERE o.salesman.gen01 = :salesmanCode AND o.occ03 = :customerType")
    List<OccFile> findBySalesmanCodeAndCustomerType(
        @Param("salesmanCode") String salesmanCode, 
        @Param("customerType") String customerType
    );
    
    /**
     * 查詢所有有效客戶（可自定義條件）
     * 
     * @return 有效客戶列表
     */
    @Query("SELECT o FROM OccFile o WHERE o.occ01 IS NOT NULL ORDER BY o.occ01")
    List<OccFile> findAllActiveCustomers();
    
    /**
     * 根據國別查詢客戶
     * 
     * @param countryCode 國別編號
     * @return 符合條件的客戶列表
     */
    List<OccFile> findByOcc21(String countryCode);
    
    /**
     * 查詢有績效倍率設定的客戶
     * 
     * @param minRate 最小倍率
     * @return 符合條件的客戶列表
     */
    @Query("SELECT o FROM OccFile o WHERE o.occud07 >= :minRate")
    List<OccFile> findCustomersWithPerformanceRateAbove(@Param("minRate") Float minRate);
    
    /**
     * 查詢客戶信用額度統計資料
     * 包含信用額度、應收帳款、出貨未轉應收、出貨通知單、訂單未轉出貨等數據
     */
    @Query(nativeQuery = true, value =
    		"select " +
    		"	occ01 AS customerCode, " +	// 客戶代號
    		"	occ02 AS customerName, " + 	// 客戶名稱
    		"NVL(occ63, 0) AS creditLimit, " +	// 信用額度
    		"occ631 AS creditCurrency, " +		// 信用幣別
    		"NVL((select sum(oma61) " +			// 4.應收帳款
    		"	from oma_file " +
    		"	WHERE omavoid = 'N' " +
    		"	AND oma61 > 0 " +
    		"	AND oma03 = occ01), 0) as receivables, " +
    		"NVL((select sum(oeb13*(oeb12-oeb23-oeb24+oeb25-oeb26)*(1+oea211/100)) " +	//5.訂單未出貨, 約定交貨日在本月底以前
    		"	from oeb_file  " +
    		"	left outer join oea_file on oea01 = oeb01 " +
    		"	where oeb15 <= :cutoffDate " +
    		"	and oeb70 ='N' " +
    		"	and oeb12-oeb23-oeb24+oeb25-oeb26 > 0 " +
    		"	and oea03 = occ01), 0) as thisMonthUnshippedOrders, " +
    		"NVL((select sum((ogb12-ogb50-ogb51)*ogb13*(1+oga211/100)) " +		// 6.出貨未轉應收金額
    		"	from ogb_file " +
    		"	left outer join oga_file on oga01 = ogb01 " +
    		"	where ogaconf != 'X' and (oga65='Y' and oga09='2') " +
    		"	and ogb12  > ogb50 + ogb51  " +
    		"	and oga03 = occ01), 0) as unprocessedShipments, " +
    		"NVL((select sum(ogb12 * ogb13*(1+oga211/100)) " +				// 出貨通知單金額
    		"	from ogb_file " +
    		"	left outer join oga_file on oga01 = ogb01 " +
    		"	left outer join oeb_file on oeb01 = ogb31 and oeb03 = ogb32 " +
    		"	where oga011 is null and ogaconf = 'Y' and oga09 = '1' " +
    		"	and oeb12-oeb24+oeb25-oeb26 > 0 " +
    		"	and oga03 = occ01), 0) as shipmentNotices,	" +
    		"NVL((select sum(oeb13*(oeb12-oeb23-oeb24+oeb25-oeb26)*(1+oea211/100)) " +	// 訂單未轉出貨金額
    		"	from oeb_file " +
    		"	left outer join oea_file on oea01 = oeb01 " +
    		"	where oeb70 ='N' " +
    		"	and oeb12-oeb23-oeb24+oeb25-oeb26 > 0 " +
    		"	and oea03 = occ01), 0) as unshippedOrders, " +	 
    		"    GEN01 AS salesmanCode, " +             // 業務員代號
            "    GEN02 AS salesmanName " +              // 業務員姓名
    	"from occ_file " +
    	"LEFT JOIN gen_file ON gen01 = occ04 "	+
    	"where occ63 > 0 " +
    	"and NVL((select sum(oeb13*(oeb12-oeb23-oeb24+oeb25-oeb26)) " +
    	"		from oeb_file " +
    	"		left outer join oea_file on oea01 = oeb01 " +
    	"		where oeb15 <= :cutoffDate " +
    	"		and oeb70 ='N' " +
    	"		and oeb12-oeb23-oeb24+oeb25-oeb26 > 0 " +
    	"		and oea03 = occ01), 0) > 0 " +
    	"order by occ01"
        )
        List<Object[]> findCreditLimitsNative(@Param("cutoffDate") LocalDate cutoffDate);
    
}