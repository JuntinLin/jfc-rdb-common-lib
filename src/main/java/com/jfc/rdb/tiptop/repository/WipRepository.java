package com.jfc.rdb.tiptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.EcmFile;
import com.jfc.rdb.tiptop.entity.EcmFilePK;
/**
 * WIP 查詢 Repository
 */
@Repository
public interface WipRepository extends JpaRepository<EcmFile, EcmFilePK> {
	/**
	 * ecm301	number(15,3)	良品轉入量       (+)	WIP量(ecm301+ecm302+ecm303-ecm311-ecm312-ecm313-ecm314-ecm316)
ecm302	number(15,3)	重工轉入量       (+)	
ecm303	number(15,3)	工單轉入量       (+)	
ecm311	number(15,3)	良品轉出量       (-)	
ecm312	number(15,3)	重工轉出         (-)	
ecm313	number(15,3)	當站報廢量       (-)	
ecm314	number(15,3)	當站下線量(入庫) (-)	
ecm315	number(15,3)	Bonus Qty        (-)	
ecm316	number(15,3)	工單轉出量       (-)	

     * 查詢各工作站 WIP 彙總
     * * 包含各分類工單數量統計
     * 對應原始 SQL:
     * SELECT ecm06 AS 工作站編號, eca02 AS 工作站說明,
     *        COUNT(DISTINCT sfb01) AS 工單數,
     *        SUM(ecm301+ecm302+ecm303-ecm311-ecm312-ecm313-ecm314-ecm316) AS 總WIP數量
     * FROM sfb_file a
     * INNER JOIN ecm_file c ON a.sfb01 = c.ecm01
     * LEFT JOIN eca_file d ON c.ecm06 = d.eca01
     * WHERE a.sfb04 IN ('4','5','6')
     *   AND a.sfbacti = 'Y'
     *   AND c.ecmacti = 'Y'
     * GROUP BY ecm06, eca02
     * HAVING SUM(...) > 0
     * ORDER BY 總WIP數量 DESC
     */
    @Query(value = """
            SELECT 
                c.ecm06 AS workstationId,
                d.eca02 AS workstationName,
                COUNT(DISTINCT a.sfb01) AS workOrderCount,
                SUM(NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                    - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                    - NVL(c.ecm314,0) - NVL(c.ecm316,0)) AS totalWipQuantity,
                COUNT(DISTINCT CASE 
                    WHEN a.sfb22 IS NOT NULL OR p.sfb22 IS NOT NULL 
                    THEN a.sfb01 
                END) AS salesOrderCount,
                COUNT(DISTINCT CASE 
                    WHEN (a.sfb22 IS NULL AND p.sfb22 IS NULL) 
                         AND (a.sfb91 IS NOT NULL OR p.sfb91 IS NOT NULL)
                    THEN a.sfb01 
                END) AS manufactureNoticeCount,
                COUNT(DISTINCT CASE 
                    WHEN (a.sfb22 IS NULL AND p.sfb22 IS NULL) 
                         AND (a.sfb91 IS NULL AND p.sfb91 IS NULL)
                    THEN a.sfb01 
                END) AS otherCount
            FROM sfb_file a
            INNER JOIN ecm_file c ON a.sfb01 = c.ecm01
            LEFT JOIN eca_file d ON c.ecm06 = d.eca01
            LEFT JOIN sfb_file p ON a.sfb86 = p.sfb01
            WHERE a.sfb04 IN ('4', '5', '6')
              AND a.sfbacti = 'Y'
              AND c.ecmacti = 'Y'
              AND NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                       - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                       - NVL(c.ecm314,0) - NVL(c.ecm316,0) > 0
            GROUP BY c.ecm06, d.eca02
            HAVING SUM(NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                       - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                       - NVL(c.ecm314,0) - NVL(c.ecm316,0)) > 0
            ORDER BY totalWipQuantity DESC
            """, nativeQuery = true)
    List<Object[]> findWipSummary();
    
    @Query(value = """
            SELECT 
                c.ecm06 AS workstationId,
                d.eca02 AS workstationName,
                COUNT(DISTINCT a.sfb01) AS workOrderCount,
                SUM(NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                    - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                    - NVL(c.ecm314,0) - NVL(c.ecm316,0)) AS totalWipQuantity,
                COUNT(DISTINCT CASE 
                    WHEN a.sfb22 IS NOT NULL OR p.sfb22 IS NOT NULL 
                    THEN a.sfb01 
                END) AS salesOrderCount,
                COUNT(DISTINCT CASE 
                    WHEN (a.sfb22 IS NULL AND p.sfb22 IS NULL) 
                         AND (a.sfb91 IS NOT NULL OR p.sfb91 IS NOT NULL)
                    THEN a.sfb01 
                END) AS manufactureNoticeCount,
                COUNT(DISTINCT CASE 
                    WHEN (a.sfb22 IS NULL AND p.sfb22 IS NULL) 
                         AND (a.sfb91 IS NULL AND p.sfb91 IS NULL)
                    THEN a.sfb01 
                END) AS otherCount
            FROM sfb_file a
            left outer join oeb_file b on a.sfb22 = b.oeb01 and a.sfb221 = b.oeb03
            LEFT OUTER JOIN ksg_file g ON a.sfb91 = g.ksg01 AND a.sfb92 = g.ksg02
            INNER JOIN ecm_file c ON a.sfb01 = c.ecm01
            LEFT JOIN eca_file d ON c.ecm06 = d.eca01
            LEFT JOIN sfb_file p ON a.sfb86 = p.sfb01 --sfb86	varchar2(20)	母工單號碼
            -- 關聯銷售訂單
            left outer join oeb_file pb on p.sfb22 = pb.oeb01 and p.sfb221 = pb.oeb03 
            -- 關聯製造通知單 (請注意：ksg_file 的日期欄位通常是 ksg04，ksg02 通常是項次)
    		LEFT OUTER JOIN ksg_file pg ON p.sfb91 = pg.ksg01 AND p.sfb92 = pg.ksg02
            WHERE a.sfb04 IN ('4', '5', '6')
              AND a.sfbacti = 'Y'
              AND c.ecmacti = 'Y'
              AND NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                       - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                       - NVL(c.ecm314,0) - NVL(c.ecm316,0) > 0
              AND (:estimatedStartDateBefore IS NULL OR 
                   CASE 
                       WHEN c.ecm03 = (SELECT MIN(ecm03) FROM ecm_file WHERE ecm01 = a.sfb01 AND ecmacti = 'Y')
                       THEN a.sfb25
                       ELSE (
                           SELECT MAX(TO_DATE(TO_CHAR(shb03, 'YYYYMMDD') || NVL(shb031, '00:00'), 'YYYYMMDDHH24:MI'))
                           FROM shb_file
                           WHERE shb05 = a.sfb01
                             AND shb06 < c.ecm03
                             AND shb111 > 0
                             AND shbacti = 'Y'
                       )
                   END <= TO_DATE(:estimatedStartDateBefore, 'YYYY-MM-DD'))
              AND (:deliveryDateBefore IS NULL OR 
                   NVL(COALESCE(b.ta_oeb15, pb.ta_oeb15), COALESCE(b.oeb15, g.ksg04, pb.oeb15, pg.ksg04)) <= TO_DATE(:deliveryDateBefore, 'YYYY-MM-DD'))
            GROUP BY c.ecm06, d.eca02
            HAVING SUM(NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                       - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                       - NVL(c.ecm314,0) - NVL(c.ecm316,0)) > 0
            ORDER BY totalWipQuantity DESC
            """, nativeQuery = true)
    List<Object[]> findWipSummaryWithDateFilter(
            @Param("estimatedStartDateBefore") String estimatedStartDateBefore,
            @Param("deliveryDateBefore") String deliveryDateBefore);
    
    /**
     * 查詢指定工作站的製程明細
     * 包含工單資訊、製程資訊、WIP數量明細
     * 
     * 分類邏輯：
     * 1) 合約訂單工單: sfb22 != null 或 母工單(sfb86)的 sfb22 != null
     * 2) 製造通知單工單: sfb91 != null 或 母工單(sfb86)的 sfb91 != null
     * 3) 其他工單: sfb22 = null AND sfb91 = null (本工單和母工單都為空)
     */
    @Query(value = """
            SELECT 
                a.sfb01 AS workOrderNo,	-- row[0]
                a.sfb02 AS workOrderType,
                a.sfb04 AS workOrderStatus,
                a.sfb05 AS partNumber,
                i.ima02 AS partName,
                c.ecm03 AS routingSeq,	-- row[5]                
                e.ecd02 AS routingDesc,
                -- 新增：實際開工日邏輯
    			CASE 
        			WHEN c.ecm03 = (SELECT MIN(ecm03) FROM ecm_file WHERE ecm01 = a.sfb01 AND ecmacti = 'Y')
        			then a.sfb25
       		 	-- THEN (SELECT MAX(sfp.sfp03) FROM sfq_file sfq left outer join sfp_file sfp on sfp.sfp01 = sfq.sfq01 WHERE sfq.sfq02 = a.sfb01) -- 第一站：取該工單最後發料日
        			ELSE (
            			SELECT MAX(TO_DATE(TO_CHAR(shb03, 'YYYYMMDD') || NVL(shb031, '00:00'), 'YYYYMMDDHH24:MI')) -- 假設 tc_srg10 為製程移轉單的過帳/轉出日，請依實際 Table 調整
            			FROM shb_file -- 製程移轉單單頭
            			WHERE shb05 = a.sfb01             -- 同一工單
             		 	AND shb06 < c.ecm03             -- 序號小於目前製程
             		 	AND shb111 > 0                  -- 有良品轉出數量
              			AND shbacti = 'Y'               -- 資料有效
		          )
		      	END AS estimatedStartDate,
                a.sfb08 AS productionQty,
                a.sfb09 AS completedQty,
                NVL(c.ecm301, 0) AS goodTransferIn,  -- row[10]
                NVL(c.ecm302, 0) AS reworkTransferIn,	
                NVL(c.ecm303, 0) AS orderTransferIn,
                NVL(c.ecm311, 0) AS goodTransferOut,
                NVL(c.ecm312, 0) AS reworkTransferOut,
                NVL(c.ecm313, 0) AS scrapQty, -- row[15]
                NVL(c.ecm314, 0) AS offlineQty,	-- row[15]
                NVL(c.ecm316, 0) AS orderTransferOut,
                (NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                 - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                 - NVL(c.ecm314,0) - NVL(c.ecm316,0)) AS wipQuantity,	-- row[18]
                a.sfb22 AS salesOrderNo,
                a.sfb221 AS salesOrderSeq,		-- row[20]
                a.sfb91 AS manufactureNoticeNo,	-- row[21]
                a.sfb92 AS manufactureNoticeSeq,	
                a.sfb86 AS sourceWorkOrderNo,	-- row[23]
                p.sfb22 AS parentSalesOrderNo,
                p.sfb221 AS parentSalesOrderSeq,--row[25]
                p.sfb91 AS parentManufactureNoticeNo,	
                p.sfb92 AS parentManufactureNoticeSeq,	
                -- 使用 COALESCE 優先取銷售訂單日期，若無則取製造通知單日期
                COALESCE(b.oeb15, g.ksg04,pb.oeb15, pg.ksg04) AS promisedDeliveryDate,	-- row[28]
                COALESCE(b.ta_oeb15, pb.ta_oeb15) AS extendedDeliveryDate -- row[29]
            FROM sfb_file a
            left outer join oeb_file b on a.sfb22 = b.oeb01 and a.sfb221 = b.oeb03
            LEFT OUTER JOIN ksg_file g ON a.sfb91 = g.ksg01 AND a.sfb92 = g.ksg02
            INNER JOIN ecm_file c ON a.sfb01 = c.ecm01
            LEFT JOIN eca_file d ON c.ecm06 = d.eca01
            LEFT JOIN ecd_file e ON c.ecm04 = e.ecd01
            LEFT JOIN ima_file i ON a.sfb05 = i.ima01
            LEFT JOIN sfb_file p ON a.sfb86 = p.sfb01 --sfb86	varchar2(20)	母工單號碼
            -- 關聯銷售訂單
            left outer join oeb_file pb on p.sfb22 = pb.oeb01 and p.sfb221 = pb.oeb03 
            -- 關聯製造通知單 (請注意：ksg_file 的日期欄位通常是 ksg04，ksg02 通常是項次)
    		LEFT OUTER JOIN ksg_file pg ON p.sfb91 = pg.ksg01 AND p.sfb92 = pg.ksg02
            WHERE c.ecm06 = :workstationId
              AND a.sfb04 IN ('4', '5', '6')
              AND a.sfbacti = 'Y'
              AND c.ecmacti = 'Y'
              AND (NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                   - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                   - NVL(c.ecm314,0) - NVL(c.ecm316,0)) > 0
            ORDER BY a.sfb01, c.ecm03
            """, nativeQuery = true)
    List<Object[]> findWipDetailByWorkstation(@Param("workstationId") String workstationId);
    
    @Query(value = """
            SELECT 
                a.sfb01 AS workOrderNo,	-- row[0]
                a.sfb02 AS workOrderType,
                a.sfb04 AS workOrderStatus,
                a.sfb05 AS partNumber,
                i.ima02 AS partName,
                c.ecm03 AS routingSeq,	-- row[5]                
                e.ecd02 AS routingDesc,
                -- 新增：實際開工日邏輯
    			CASE 
        			WHEN c.ecm03 = (SELECT MIN(ecm03) FROM ecm_file WHERE ecm01 = a.sfb01 AND ecmacti = 'Y')
        			then a.sfb25
       		 	-- THEN (SELECT MAX(sfp.sfp03) FROM sfq_file sfq left outer join sfp_file sfp on sfp.sfp01 = sfq.sfq01 WHERE sfq.sfq02 = a.sfb01) -- 第一站：取該工單最後發料日
        			ELSE (
            			SELECT MAX(TO_DATE(TO_CHAR(shb03, 'YYYYMMDD') || NVL(shb031, '00:00'), 'YYYYMMDDHH24:MI')) -- 假設 tc_srg10 為製程移轉單的過帳/轉出日，請依實際 Table 調整
            			FROM shb_file -- 製程移轉單單頭
            			WHERE shb05 = a.sfb01             -- 同一工單
             		 	AND shb06 < c.ecm03             -- 序號小於目前製程
             		 	AND shb111 > 0                  -- 有良品轉出數量
              			AND shbacti = 'Y'               -- 資料有效
		          )
		      	END AS estimatedStartDate,
                a.sfb08 AS productionQty,
                a.sfb09 AS completedQty,
                NVL(c.ecm301, 0) AS goodTransferIn,  -- row[10]
                NVL(c.ecm302, 0) AS reworkTransferIn,	
                NVL(c.ecm303, 0) AS orderTransferIn,
                NVL(c.ecm311, 0) AS goodTransferOut,
                NVL(c.ecm312, 0) AS reworkTransferOut,
                NVL(c.ecm313, 0) AS scrapQty, -- row[15]
                NVL(c.ecm314, 0) AS offlineQty,	-- row[15]
                NVL(c.ecm316, 0) AS orderTransferOut,
                (NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                 - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                 - NVL(c.ecm314,0) - NVL(c.ecm316,0)) AS wipQuantity,	-- row[18]
                a.sfb22 AS salesOrderNo,
                a.sfb221 AS salesOrderSeq,		-- row[20]
                a.sfb91 AS manufactureNoticeNo,	-- row[21]
                a.sfb92 AS manufactureNoticeSeq,	
                a.sfb86 AS sourceWorkOrderNo,	-- row[23]
                p.sfb22 AS parentSalesOrderNo,
                p.sfb221 AS parentSalesOrderSeq,--row[25]
                p.sfb91 AS parentManufactureNoticeNo,	
                p.sfb92 AS parentManufactureNoticeSeq,	
                -- 使用 COALESCE 優先取銷售訂單日期，若無則取製造通知單日期
                COALESCE(b.oeb15, g.ksg04,pb.oeb15, pg.ksg04) AS promisedDeliveryDate,	-- row[28]
                COALESCE(b.ta_oeb15, pb.ta_oeb15) AS extendedDeliveryDate, -- row[29]
                d.eca02 as eca02 -- eca02	varchar2(80)	說明	說明簡述工作站特性說明
            FROM sfb_file a
            left outer join oeb_file b on a.sfb22 = b.oeb01 and a.sfb221 = b.oeb03
            LEFT OUTER JOIN ksg_file g ON a.sfb91 = g.ksg01 AND a.sfb92 = g.ksg02
            INNER JOIN ecm_file c ON a.sfb01 = c.ecm01
            LEFT JOIN eca_file d ON c.ecm06 = d.eca01 --工作站基本資料(eca_file)
            LEFT JOIN ecd_file e ON c.ecm04 = e.ecd01 --作業資料(ecd_file)
            LEFT JOIN ima_file i ON a.sfb05 = i.ima01
            LEFT JOIN sfb_file p ON a.sfb86 = p.sfb01 --sfb86	varchar2(20)	母工單號碼
            -- 關聯銷售訂單
            left outer join oeb_file pb on p.sfb22 = pb.oeb01 and p.sfb221 = pb.oeb03 
            -- 關聯製造通知單 (請注意：ksg_file 的日期欄位通常是 ksg04，ksg02 通常是項次)
    		LEFT OUTER JOIN ksg_file pg ON p.sfb91 = pg.ksg01 AND p.sfb92 = pg.ksg02
            WHERE c.ecm06 = :workstationId
              AND a.sfb04 IN ('4', '5', '6')
              AND a.sfbacti = 'Y'
              AND c.ecmacti = 'Y'
              AND (NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                   - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                   - NVL(c.ecm314,0) - NVL(c.ecm316,0)) > 0
              AND (:estimatedStartDateBefore IS NULL OR 
                   CASE 
                       WHEN c.ecm03 = (SELECT MIN(ecm03) FROM ecm_file WHERE ecm01 = a.sfb01 AND ecmacti = 'Y')
                       THEN a.sfb25
                       ELSE (
                           SELECT MAX(TO_DATE(TO_CHAR(shb03, 'YYYYMMDD') || NVL(shb031, '00:00'), 'YYYYMMDDHH24:MI'))
                           FROM shb_file
                           WHERE shb05 = a.sfb01
                             AND shb06 < c.ecm03
                             AND shb111 > 0
                             AND shbacti = 'Y'
                       )
                   END <= TO_DATE(:estimatedStartDateBefore, 'YYYY-MM-DD'))
              AND (:deliveryDateBefore IS NULL OR 
                   NVL(COALESCE(b.ta_oeb15, pb.ta_oeb15), COALESCE(b.oeb15, g.ksg04, pb.oeb15, pg.ksg04)) <= TO_DATE(:deliveryDateBefore, 'YYYY-MM-DD'))
            ORDER BY a.sfb01, c.ecm03
            """, nativeQuery = true)
    List<Object[]> findWipDetailByWorkstationWithDateFilter(
    		@Param("workstationId") String workstationId,
            @Param("estimatedStartDateBefore") String estimatedStartDateBefore,
            @Param("deliveryDateBefore") String deliveryDateBefore);
    
    /**
     * 查詢指定工單在指定工作站的製程明細
     */
    @Query(value = """
            SELECT 
                a.sfb01 AS workOrderNo,
                a.sfb02 AS workOrderType,
                a.sfb04 AS workOrderStatus,
                a.sfb05 AS partNumber,
                i.ima02 AS partName,
                c.ecm03 AS routingSeq,
                e.ecd02 AS routingDesc,
                a.sfb08 AS productionQty,
                a.sfb09 AS completedQty,
                NVL(c.ecm301, 0) AS goodTransferIn,
                NVL(c.ecm302, 0) AS reworkTransferIn,
                NVL(c.ecm303, 0) AS orderTransferIn,
                NVL(c.ecm311, 0) AS goodTransferOut,
                NVL(c.ecm312, 0) AS reworkTransferOut,
                NVL(c.ecm313, 0) AS scrapQty,
                NVL(c.ecm314, 0) AS offlineQty,
                NVL(c.ecm316, 0) AS orderTransferOut,
                (NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                 - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                 - NVL(c.ecm314,0) - NVL(c.ecm316,0)) AS wipQuantity,
                a.sfb22 AS salesOrderNo,
                a.sfb221 AS salesOrderSeq,
                a.sfb91 AS manufactureNoticeNo,
                a.sfb86 AS sourceWorkOrderNo,
                p.sfb22 AS parentSalesOrderNo,
                p.sfb91 AS parentManufactureNoticeNo
            FROM sfb_file a
            INNER JOIN ecm_file c ON a.sfb01 = c.ecm01
            LEFT JOIN eca_file d ON c.ecm06 = d.eca01
            LEFT JOIN ecd_file e ON c.ecm04 = e.ecd01
            LEFT JOIN ima_file i ON a.sfb05 = i.ima01
            LEFT JOIN sfb_file p ON a.sfb86 = p.sfb01
            WHERE c.ecm06 = :workstationId
              AND a.sfb01 = :workOrderNo
              AND a.sfbacti = 'Y'
              AND c.ecmacti = 'Y'
            ORDER BY c.ecm03
            """, nativeQuery = true)
    List<Object[]> findWipDetailByWorkstationAndWorkOrder(
            @Param("workstationId") String workstationId,
            @Param("workOrderNo") String workOrderNo);
}
