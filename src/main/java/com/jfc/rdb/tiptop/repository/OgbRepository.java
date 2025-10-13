package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfc.rdb.tiptop.entity.OgbFile;
import com.jfc.rdb.tiptop.entity.OgbFilePK;

public interface OgbRepository extends JpaRepository<OgbFile, OgbFilePK> {
	// ogb04 varchar2(40) 產品編號 產品編號 ima01(img01)
	// 2020-11-05 意昕提出 11302014015000 應建議採購未建議採購
	// 經查 出貨單T350-20040593 出貨數量13, 簽退數量13,
	// 暨「已出貨未簽收(findDeliveryUnsignupQty)」未扣除簽退數量(ogb51)
	//ogb12	number(15,3)	實際出貨數量	實際出貨數量 (依銷售單位)
	//ogb50	number(15,3)	累計簽收數量	開票性質
	//ogb51	number(15,3)	已簽退數量
	@Query("""
			SELECT COALESCE(SUM(o.ogb12) - SUM(o.ogb50) - SUM(o.ogb51), 0)
			FROM OgbFile o
			LEFT JOIN o.oga oga
			WHERE oga.ogaconf = 'Y'
			AND oga.oga09 = '2'
			AND oga.oga65 = 'Y'
			AND (o.ogb12 - o.ogb50 - o.ogb51) > 0
			AND o.ima.ima01 = :mano
			""")
	BigDecimal findUnsignupQuantity(@Param("mano") String mano);
	
	//出貨單單頭檔(oga_file)
    //oga09	varchar2(1)	單據別	單據別(1.出貨通知單 2.一般出貨單        3.無訂單出貨單 4.三角貿易出貨單        5.三角貿易出貨通知單        6.代採買出貨單        8.客戶驗收單        9.客戶驗退單        A.借貨出貨單
    //ogaconf	varchar2(1)	確認否/作廢碼	確認否/作廢碼 (Y/N/X) 欄位值:   N.未確認   X.作廢   Y.已確認 
    //ogapost	varchar2(1)	出貨扣帳否	出貨扣帳否 (Y.已出貨扣帳 N.尚未)
    //ogb04	varchar2(40)	產品編號	產品編號 ima01(img01)
    //ogb16	number(15,3)	數量	數量 (依庫存明細單位)
    //oga02	date	出貨日期
	@Query("""
			SELECT COALESCE(SUM(o.ogb16), 0)
			FROM OgbFile o
			LEFT JOIN o.oga oga
			WHERE oga.oga09 = '2'
			AND oga.ogaconf = 'Y'
			AND o.ima.ima01 = :mano
			AND oga.oga02 BETWEEN :beginDate AND :endDate
			""")
	BigDecimal findSaleAmount(@Param("mano") String mano, @Param("beginDate") Date beginDate,
			@Param("endDate") Date endDate);
	
	/**
     * 查詢特定客戶的出貨單明細
     */
    @Query(nativeQuery = true, value =
        "SELECT " +
        "    OGB01 AS documentNumber, " +           // 單據編號 ogb01	varchar2(20)	出貨單號	出貨單號 oga01
        "    OGA02 AS documentDate, " +             // 單據日期 oga02	date	出貨日期
        "    OGA23 AS currency, " +                 // 幣別 oga23	varchar2(4)	幣別	幣別 azi01
        "    OGA501 AS amount, " +                  // 金額	oga50	number(20,6)	原幣出貨金額	原幣出貨金額(未稅)
        											//		oga501	number(20,6)	本幣出貨金額	nouse  #No.9347
        "    OGB04||'-'||OGA01 AS description " +   // 描述 ogb04	varchar2(40)	產品編號	產品編號 ima01(img01)
        "FROM OGB_FILE " +
        "LEFT OUTER JOIN OGA_FILE on oga01 = ogb01" +
        "WHERE OGA03 = :customerCode " +            // 客戶代號
        " and ogaconf = 'Y' " + 
		" AND oga09 = '2' " + 						//oga09	varchar2(1)	單據別	單據別(1.出貨通知單 2.一般出貨單        3.無訂單出貨單 4.三角貿易出貨單        5.三角貿易出貨通知單        6.代採買出貨單        8.客戶驗收單        9.客戶驗退單        A.借貨出貨單
		" AND oga.oga65 = 'Y' " + 					//oga65	varchar2(1)	客戶出貨簽收否
		" AND (ogb12 - ogb50 - ogb51) > 0 " +	// 未轉應收
													//ogb12	number(15,3)	實際出貨數量	實際出貨數量 (依銷售單位)
													//ogb50	number(15,3)	累計簽收數量	開票性質
													//ogb51	number(15,3)	已簽退數量	
        " AND oga.ogaconf = 'Y' " +				//ogaconf	varchar2(1)	確認否/作廢碼	確認否/作廢碼 (Y/N/X) 欄位值:   N.未確認   X.作廢   Y.已確認 
        "ORDER BY OGA02 DESC"
    )
    List<Object[]> findShipmentDetailsByCustomer(@Param("customerCode") String customerCode);

}
