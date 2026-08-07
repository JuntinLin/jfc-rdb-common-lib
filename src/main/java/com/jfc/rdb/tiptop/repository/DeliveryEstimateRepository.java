package com.jfc.rdb.tiptop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.OebFile;
import com.jfc.rdb.tiptop.entity.OebFilePK;

/**
 * 出貨預估統計 Repository
 * 
 * 提供出貨預估相關的資料庫查詢
 */
@Repository
public interface DeliveryEstimateRepository extends JpaRepository<OebFile, OebFilePK> {
    
    /**
     * 查詢未出貨訂單
     * oeb12	number(15,3)	數量
     * oeb24	number(15,3)	已出貨數量
     * oea49	varchar2(1)	狀況碼	狀況碼: 0: 開立(Open) 1: 已核准 S: 送簽                 #No.6686 R: 送簽退回 W: 抽單 
     * oeaconf	varchar2(1)	確認否	確認否 (Y/N/X) 
     * oeb70	varchar2(1)	結案否	結案否 (Y/N)
     * oeb15	date	約定交貨日
     * ta_oeb15	date	展延交期
     * oebud10	number(10)	設計狀態碼	-1.免出圖 0.未指派 1.已指派 2.確認中 3.執行中 4.完成
     * 
     * 條件:
     * - 未出貨數量 > 0 (oeb12 - oeb24 > 0)
     * - 訂單狀態為有效 (oea49 in 0, 1, S)
     * - 訂單已確認 (oeaconf = Y)
     * - 非取消項目 (oeb70 != Y)
     * - 約定交貨日在指定範圍內 (oeb15 between startDate and endDate)
     * 
     * @param startDate 起始日期
     * @param endDate 結束日期
     * @return 未出貨訂單列表
     */
    @Query(value = """
        SELECT 
            oeb.oeb01,
            oeb.oeb03,	-- 項次
            oea.oea03,	-- 帳款客戶編號 occ01   MISC: 雜項客戶, 可輸入簡稱,統一編號
            oea.oea032,	-- 帳款客戶簡稱
            imz.imz02 as productType, --分群碼
            ima.ima09 as spl, --分群碼1 row[5]
            ima.ima02,
            ima.ima021,
            oea.oea02,
            oeb.oeb15,
            oeb.oeb12,	--row[10]
            oeb.oeb24,	--row[11]
            oeb.oeb13,	--row[12]
            COALESCE(stk.stockAmount, 0) as stockAmount,
            oeb.oebud10, 
            oeb.oebud15,
            sfb.sfb01, 	--row[16]
            gen.gen02 as creator,
            oea.oea24,	--匯率 row[18]
            COALESCE(sfb.sfb09, 0) as sfb09, --row[19]
            COALESCE(sfb.sfb08, 0) as sfb08,
            pmo.tc_pmo01 AS mergeWorkOrderNo,	--row[21]
            sfw.sfw01,
            oeb.ta_oeb15 AS extendedDeliveryDate, -- row[23]
            ogb.ogb12 as ogb12, -- row[24] ogb12	number(15,3)	實際出貨數量
            sales.gen02 as salesman,
            occ.occ03 as custIndustry,    -- row[26] 行業別
            occ.occ21 as custCountry,     -- row[27] 國別
            occ.occud06 as custUd06,      -- row[28] 自訂欄位06
            occ03 || '-' || oca02 as customerCategory,  -- row[29]
            occ21 || '-' || geb02 as countryName,       -- row[30]
            occ.occ04 as salesmanCode                    -- row[31] 負責業務員編號
        FROM oeb_file oeb
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        LEFT JOIN occ_file occ on occ.occ01 = oea.oea03
        LEFT JOIN oca_file oca on occ.occ03 = oca.oca01
        LEFT JOIN geb_file geb on occ.occ21 = geb.geb01
        LEFT JOIN gen_file sales on sales.gen01 = occ.occ04
        LEFT JOIN ima_file ima ON ima.ima01 = oeb.oeb04
        LEFT JOIN imz_file imz ON ima.ima06 = imz.imz01
        LEFT JOIN gen_file gen ON gen.gen01 = oea.oeaoriu
        LEFT JOIN sfb_file sfb ON sfb.sfb22 = oeb.oeb01 AND sfb.sfb221 = oeb.oeb03 AND sfb.sfbacti = 'Y' AND sfb.sfb87 = 'Y'
        LEFT JOIN tc_pmo_file pmo ON (oeb.oeb01 || '-' || oeb.oeb03) = pmo.tc_pmo05 AND pmo.tc_pmo01 LIKE 'T51%' and pmo.tc_pmo02 = 0
        LEFT JOIN (
    		SELECT MIN(sfw01) as sfw01, sfw03
    		FROM sfw_file
    		GROUP BY sfw03
    	) sfw ON sfw.sfw03 LIKE '%' || oeb.oeb01 || '%'
        LEFT JOIN (
            SELECT img01, SUM(img10) as stockAmount
            FROM img_file
            WHERE img23 = 'Y' AND img10 > 0
            GROUP BY img01
        ) stk ON stk.img01 = oeb.oeb04
        LEFT JOIN (
    		select ogb31, ogb32, sum(ogb12) as ogb12
			from ogb_file
			left outer join oga_file on oga01 = ogb01
			WHERE ogaconf = 'Y' AND oga09 = '2'
			group by ogb31, ogb32
        )ogb on ogb.ogb31 = oeb.oeb01 and ogb.ogb32 = oeb.oeb03
        WHERE oeb.oeb12 - oeb.oeb24 > 0
          AND oea.oea49 IN ('0', '1', 'S')
          AND oea.oeaconf = 'Y'
          AND (oeb.oeb70 IS NULL OR oeb.oeb70 != 'Y')
          AND COALESCE(oeb.ta_oeb15, oeb.oeb15) BETWEEN :startDate AND :endDate
        ORDER BY COALESCE(oeb.ta_oeb15, oeb.oeb15), oeb.oeb01, oeb.oeb03
        """, nativeQuery = true)
    List<Object[]> findUndeliveryOrders(
            @Param("startDate") Date startDate, 
            @Param("endDate") Date endDate);
    
    /**
     * 查詢尚未出圖的未出貨訂單（不限約定交貨日範圍）
     *
     * 設計狀態碼 oebud10:
     *   -1.免出圖  0.未指派  1.已指派  2.確認中  3.執行中  4.完成
     *
     * 「尚未出圖」= oebud10 NOT IN (-1, 4)，即非免出圖且非完成
     *
     * 回傳欄位與 findUndeliveryOrders 相同（row[0]~row[27]），
     * 可直接複用 DeliveryEstimateService.mapToOrderItemDTO()
     *
     * @return 尚未出圖的未出貨訂單列表
     */
    @Query(value = """
        SELECT
            oeb.oeb01,
            oeb.oeb03,
            oea.oea03,
            oea.oea032,
            imz.imz02 as productType,
            ima.ima09 as spl,
            ima.ima02,
            ima.ima021,
            oea.oea02,
            oeb.oeb15,
            oeb.oeb12,
            oeb.oeb24,
            oeb.oeb13,
            COALESCE(stk.stockAmount, 0) as stockAmount,
            oeb.oebud10,
            oeb.oebud15,
            sfb.sfb01,
            gen.gen02 as creator,
            oea.oea24,
            COALESCE(sfb.sfb09, 0) as sfb09,
            COALESCE(sfb.sfb08, 0) as sfb08,
            pmo.tc_pmo01 AS mergeWorkOrderNo,
            sfw.sfw01,
            oeb.ta_oeb15 AS extendedDeliveryDate,
            ogb.ogb12 as ogb12,
            sales.gen02 as salesman,
            occ.occ03 as custIndustry,
            occ.occ21 as custCountry,
            occ.occud06 as custUd06,
            occ03 || '-' || oca02 as customerCategory,
            occ21 || '-' || geb02 as countryName
        FROM oeb_file oeb
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        LEFT JOIN occ_file occ ON occ.occ01 = oea.oea03
        LEFT JOIN oca_file oca ON occ.occ03 = oca.oca01
        LEFT JOIN geb_file geb ON occ.occ21 = geb.geb01
        LEFT JOIN gen_file sales ON sales.gen01 = occ.occ04
        LEFT JOIN ima_file ima ON ima.ima01 = oeb.oeb04
        LEFT JOIN imz_file imz ON ima.ima06 = imz.imz01
        LEFT JOIN gen_file gen ON gen.gen01 = oea.oeaoriu
        LEFT JOIN sfb_file sfb ON sfb.sfb22 = oeb.oeb01 AND sfb.sfb221 = oeb.oeb03 AND sfb.sfbacti = 'Y' AND sfb.sfb87 = 'Y'
        LEFT JOIN tc_pmo_file pmo ON (oeb.oeb01 || '-' || oeb.oeb03) = pmo.tc_pmo05 AND pmo.tc_pmo01 LIKE 'T51%' AND pmo.tc_pmo02 = 0
        LEFT JOIN (
            SELECT MIN(sfw01) as sfw01, sfw03
            FROM sfw_file
            GROUP BY sfw03
        ) sfw ON sfw.sfw03 LIKE '%' || oeb.oeb01 || '%'
        LEFT JOIN (
            SELECT img01, SUM(img10) as stockAmount
            FROM img_file
            WHERE img23 = 'Y' AND img10 > 0
            GROUP BY img01
        ) stk ON stk.img01 = oeb.oeb04
        LEFT JOIN (
            SELECT ogb31, ogb32, SUM(ogb12) as ogb12
            FROM ogb_file
            LEFT OUTER JOIN oga_file ON oga01 = ogb01
            WHERE ogaconf = 'Y' AND oga09 = '2'
            GROUP BY ogb31, ogb32
        ) ogb ON ogb.ogb31 = oeb.oeb01 AND ogb.ogb32 = oeb.oeb03
        WHERE oeb.oeb12 - oeb.oeb24 > 0
          AND oea.oea49 IN ('0', '1', 'S')
          AND oea.oeaconf = 'Y'
          AND (oeb.oeb70 IS NULL OR oeb.oeb70 != 'Y')
          AND oeb.oebud10 IS NOT NULL
          AND oeb.oebud10 NOT IN (-1, 4)
        ORDER BY oea.oea02, oeb.oeb01, oeb.oeb03
        """, nativeQuery = true)
    List<Object[]> findUndesignOrders();

    /**
     * 查詢已出貨未簽收
     * 
     * 條件:
     * - 單據已確認 (ogaconf = Y)
     * - 單據別為一般出貨單 (oga09 = 2)
     * - 需要客戶簽收 (oga65 = Y)
     * - 未簽收數量 > 0 (ogb12 - ogb50 > 0)
     * 
     * @return 已出貨未簽收列表
     */
    @Query(value = """
        SELECT 
            ogb.ogb01,
            ogb.ogb03,
            oga.oga03,
            oga.oga032,
            imz.imz02 as productType,
            ima.ima09 as spl, --分群碼1  row[5]
            ima.ima02,
            ima.ima021,
            oga.oga02,
            ogb.ogb12,	--row[9]
            ogb.ogb50,	--row[10]累計簽收數量
            ogb.ogb13,	--row[11]原幣單價
            oga.oga24,	--row[12]匯率
            NVL(ogb.ogb51, 0)	--row[13]累計銷退數量
        FROM ogb_file ogb
        INNER JOIN oga_file oga ON oga.oga01 = ogb.ogb01
        LEFT JOIN ima_file ima ON ima.ima01 = ogb.ogb04
        LEFT JOIN imz_file imz ON ima.ima06 = imz.imz01
        WHERE oga.ogaconf = 'Y'
          AND oga.oga09 = '2'
          AND oga.oga65 = 'Y'
          AND ogb.ogb12 - ogb.ogb50 - NVL(ogb.ogb51, 0) > 0
          AND oga.oga02 BETWEEN :startDate AND :endDate
        ORDER BY oga.oga02 DESC, ogb.ogb01, ogb.ogb03
        """, nativeQuery = true)
    List<Object[]> findUnsignedShipments(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
    
    /**
     * 查詢客戶排行列表
     * 
     * @return 客戶排行列表
     */
    @Query(value = """
        SELECT 
            occ.occ01 as compno,
            occ.occ02 as name,
            occ.occ031 as sname,
            COALESCE(rank.priority, 999) as priority,
            COALESCE(cnt.orderCount, 0) as orderCount
        FROM occ_file occ
        LEFT JOIN (
            SELECT oea03, COUNT(*) as orderCount
            FROM oea_file 
            WHERE oea49 IN ('0', '1', 'S') AND oeaconf = 'Y'
            GROUP BY oea03
        ) cnt ON cnt.oea03 = occ.occ01
        LEFT JOIN (
            SELECT customer_code, priority
            FROM customer_rank
        ) rank ON rank.customer_code = occ.occ01
        WHERE cnt.orderCount > 0
        ORDER BY priority, orderCount DESC
        """, nativeQuery = true)
    List<Object[]> findCustomerRankList();

    // ========== 月度管道統計專用查詢 ==========

    /**
     * 查詢全部未出貨訂單（不限交貨日期），供月度管道統計使用
     * 欄位順序與 findUndeliveryOrders 相同
     */
    @Query(value = """
        SELECT
            oeb.oeb01,
            oeb.oeb03,
            oea.oea03,
            oea.oea032,
            imz.imz02 as productType,
            ima.ima09 as spl,
            ima.ima02,
            ima.ima021,
            oea.oea02,
            oeb.oeb15,
            oeb.oeb12,
            oeb.oeb24,
            oeb.oeb13,
            COALESCE(stk.stockAmount, 0) as stockAmount,
            oeb.oebud10,
            oeb.oebud15,
            sfb.sfb01,
            gen.gen02 as creator,
            oea.oea24,
            COALESCE(sfb.sfb09, 0) as sfb09,
            COALESCE(sfb.sfb08, 0) as sfb08,
            pmo.tc_pmo01 AS mergeWorkOrderNo,
            sfw.sfw01,
            oeb.ta_oeb15 AS extendedDeliveryDate,
            ogb.ogb12 as ogb12,
            sales.gen02 as salesman,
            occ.occ03 as custIndustry,
            occ.occ21 as custCountry,
            occ.occud06 as custUd06,
            occ03 || '-' || oca02 as customerCategory,
            occ21 || '-' || geb02 as countryName,
            occ.occ04 as salesmanCode,
            COALESCE(oeb.ta_oeb15, oeb.oeb15) AS effectiveDelivery
        FROM oeb_file oeb
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        LEFT JOIN occ_file occ on occ.occ01 = oea.oea03
        LEFT JOIN oca_file oca on occ.occ03 = oca.oca01
        LEFT JOIN geb_file geb on occ.occ21 = geb.geb01
        LEFT JOIN gen_file sales on sales.gen01 = occ.occ04
        LEFT JOIN ima_file ima ON ima.ima01 = oeb.oeb04
        LEFT JOIN imz_file imz ON ima.ima06 = imz.imz01
        LEFT JOIN gen_file gen ON gen.gen01 = oea.oeaoriu
        LEFT JOIN sfb_file sfb ON sfb.sfb22 = oeb.oeb01 AND sfb.sfb221 = oeb.oeb03 AND sfb.sfbacti = 'Y' AND sfb.sfb87 = 'Y'
        LEFT JOIN tc_pmo_file pmo ON (oeb.oeb01 || '-' || oeb.oeb03) = pmo.tc_pmo05 AND pmo.tc_pmo01 LIKE 'T51%' and pmo.tc_pmo02 = 0
        LEFT JOIN (
            SELECT MIN(sfw01) as sfw01, sfw03
            FROM sfw_file
            GROUP BY sfw03
        ) sfw ON sfw.sfw03 LIKE '%' || oeb.oeb01 || '%'
        LEFT JOIN (
            SELECT img01, SUM(img10) as stockAmount
            FROM img_file
            WHERE img23 = 'Y' AND img10 > 0
            GROUP BY img01
        ) stk ON stk.img01 = oeb.oeb04
        LEFT JOIN (
            select ogb31, ogb32, sum(ogb12) as ogb12
            from ogb_file
            left outer join oga_file on oga01 = ogb01
            WHERE ogaconf = 'Y' AND oga09 = '2'
            group by ogb31, ogb32
        )ogb on ogb.ogb31 = oeb.oeb01 and ogb.ogb32 = oeb.oeb03
        WHERE oeb.oeb12 - oeb.oeb24 > 0
          AND oea.oea49 IN ('0', '1', 'S')
          AND oea.oeaconf = 'Y'
          AND (oeb.oeb70 IS NULL OR oeb.oeb70 != 'Y')
        ORDER BY COALESCE(oeb.ta_oeb15, oeb.oeb15), oeb.oeb01, oeb.oeb03
        """, nativeQuery = true)
    List<Object[]> findAllUndeliveryOrders();

    /**
     * 空油壓缸入庫金額（按入庫月份彙總）
     * ima06 IN ('130','140') 油壓缸+空壓缸
     * row[0] yearMonth (YYYY-MM), row[1] amount
     */
    @Query(value = """
        SELECT
            TO_CHAR(TRUNC(sfu.sfu02, 'MM'), 'YYYY-MM') AS yearMonth,
            NVL(SUM(sfv.sfv09 * NVL(oea.oea24, 1) * NVL(oeb.oeb13, 0)), 0) AS amount
        FROM sfv_file sfv
        INNER JOIN sfu_file sfu ON sfu.sfu01 = sfv.sfv01
        INNER JOIN sfb_file sfb ON sfb.sfb01 = sfv.sfv11
        INNER JOIN oeb_file oeb ON oeb.oeb01 = sfb.sfb22 AND oeb.oeb03 = sfb.sfb221
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        INNER JOIN ima_file ima ON ima.ima01 = sfv.sfv04
        WHERE sfu.sfuconf = 'Y' AND sfu.sfupost = 'Y'
          AND ima.ima06 IN ('130', '140')
          AND sfb.sfb22 IS NOT NULL
          AND sfu.sfu02 BETWEEN :startDate AND :endDate
        GROUP BY TRUNC(sfu.sfu02, 'MM')
        ORDER BY TRUNC(sfu.sfu02, 'MM')
        """, nativeQuery = true)
    List<Object[]> findCylinderInStockByMonth(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 出貨金額（按出貨月份彙總）
     * row[0] yearMonth (YYYY-MM), row[1] amount
     */
    @Query(value = """
        SELECT
            TO_CHAR(TRUNC(oga.oga02, 'MM'), 'YYYY-MM') AS yearMonth,
            NVL(SUM(oga.oga24 * ogb.ogb14), 0) AS amount
        FROM oga_file oga
        LEFT OUTER JOIN ogb_file ogb ON ogb.ogb01 = oga.oga01
        WHERE oga.ogaconf = 'Y' AND oga.ogapost = 'Y' AND oga.oga09 = '2'
          AND oga.oga02 BETWEEN :startDate AND :endDate
        GROUP BY TRUNC(oga.oga02, 'MM')
        ORDER BY TRUNC(oga.oga02, 'MM')
        """, nativeQuery = true)
    List<Object[]> findShipmentAmountByMonth(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 已出貨未簽收金額（按出貨月份彙總）
     * (ogb12 - ogb50 - ogb51) * ogb13 * oga24
     * row[0] yearMonth (YYYY-MM), row[1] amount
     */
    @Query(value = """
        SELECT
            TO_CHAR(TRUNC(oga.oga02, 'MM'), 'YYYY-MM') AS yearMonth,
            NVL(SUM((ogb.ogb12 - ogb.ogb50 - NVL(ogb.ogb51, 0)) * ogb.ogb13 * oga.oga24), 0) AS amount
        FROM ogb_file ogb
        INNER JOIN oga_file oga ON oga.oga01 = ogb.ogb01
        WHERE oga.ogaconf = 'Y'
          AND oga.oga09 = '2'
          AND oga.oga65 = 'Y'
          AND ogb.ogb12 - ogb.ogb50 - NVL(ogb.ogb51, 0) > 0
          AND oga.oga02 BETWEEN :startDate AND :endDate
        GROUP BY TRUNC(oga.oga02, 'MM')
        ORDER BY TRUNC(oga.oga02, 'MM')
        """, nativeQuery = true)
    List<Object[]> findUnsignedShipmentAmountByMonth(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 銷貨收入（應收帳款/開票）金額（按開票月份彙總）
     * SUM(OMB16) 本幣金額
     * row[0] yearMonth (YYYY-MM), row[1] amount
     */
    @Query(value = """
        SELECT
            TO_CHAR(TRUNC(oma.oma02, 'MM'), 'YYYY-MM') AS yearMonth,
            NVL(SUM(omb.omb16), 0) AS amount
        FROM omb_file omb
        LEFT OUTER JOIN oma_file oma ON oma.oma01 = omb.omb01
        WHERE oma.omaconf = 'Y' AND oma.omavoid = 'N' AND oma.oma00 = '12'
          AND oma.oma02 BETWEEN :startDate AND :endDate
        GROUP BY TRUNC(oma.oma02, 'MM')
        ORDER BY TRUNC(oma.oma02, 'MM')
        """, nativeQuery = true)
    List<Object[]> findInvoiceAmountByMonth(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}
