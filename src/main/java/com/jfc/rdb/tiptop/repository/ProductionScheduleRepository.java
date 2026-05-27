package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.OebFile;
import com.jfc.rdb.tiptop.entity.OebFilePK;
/**
 * 生產進度表 Repository
 */
@Repository
public interface ProductionScheduleRepository extends JpaRepository<OebFile, OebFilePK> {
    
    /**
     * 查詢客戶下拉清單 (有未交訂單的客戶，優先等級依過去12個月訂單金額排名)
     */
    @Query(value = """
            WITH customer_order_amount AS (
                SELECT 
                    a.oea03 AS customerNo,
                    SUM(NVL(a.oea24, 1) * NVL(b.oeb14, 0)) AS totalAmount
                FROM oea_file a
                JOIN oeb_file b ON a.oea01 = b.oeb01
                WHERE a.oeaconf IN ('Y', 'N')
                  AND b.oeb04 IS NOT NULL
                  AND a.oea02 >= ADD_MONTHS(SYSDATE, -12)
                GROUP BY a.oea03
            ),
            customer_rank AS (
                SELECT 
                    customerNo,
                    totalAmount,
                    ROW_NUMBER() OVER (ORDER BY totalAmount DESC) AS priority
                FROM customer_order_amount
            ),
            undelivered_count AS (
                SELECT 
                    a.oea03 AS customerNo,
                    COUNT(*) AS cnt
                FROM oea_file a
                JOIN oeb_file b ON a.oea01 = b.oeb01
                WHERE a.oeaconf = 'Y'
                	and b.oeb12 - b.oeb24 > 0  -- 未出貨數量		
                	and a.oea49 in ('0', '1', 'S') 
                	and not(b.oeb70 = 'Y')
                GROUP BY a.oea03
            )
            SELECT 
                u.customerNo,
                c.occ02 AS shortName,
                c.occ18 AS fullName,
                NVL(r.priority, 999) AS priority,
                u.cnt AS undeliveredCount
            FROM undelivered_count u
            JOIN occ_file c ON u.customerNo = c.occ01
            LEFT JOIN customer_rank r ON u.customerNo = r.customerNo
            ORDER BY NVL(r.priority, 999), u.customerNo
            """, nativeQuery = true)
    List<Object[]> findCustomerSelectList();
    
    /**
     * 查詢未交訂單清單
     * oebud12: 優先順序	0.普通件 50.急件 70.特急件 90.務必出貨
     */
    @Query(value = """
            SELECT 
                b.oeb01               AS orderNo,
                b.oeb03               AS orderSeq,
                a.oea03               AS customerNo,
                c.occ02               AS customerShortName,
                b.oeb04               AS partNo,
                d.ima02               AS partName,
                d.ima021              AS partSpec,
                j.imz02               AS productType,
                d.ima09	              AS modelType,
                a.oea02               AS orderDate,
                b.oeb15               AS promisedDeliveryDate,
                b.ta_oeb15            AS extendedDeliveryDate, -- 1. 新增此查詢欄位
                NVL(b.oeb12, 0)       AS orderQty,
                NVL(b.oeb24, 0)       AS shippedQty,
                (NVL(b.oeb12, 0) - NVL(b.oeb24, 0)) AS undeliveredQty,
                e.gen02               AS salesmanName,
                f.gen02               AS creatorName,
                a.oea02               AS orderCreateTime,
                a.oea72               AS confirmDate,
                NVL(b.oebud12, 99)    AS priority, --優先順序	0.普通件 50.急件 70.特急件 90.務必出貨
                b.ta_oeb14            AS partInspection,
                b.oebud13             AS designAssignDate,
                b.oebud10             AS designStatus, --設計狀態碼	-1.免出圖 0.未指派 1.已指派 2.確認中 3.執行中 4.完成
                b.ta_oeb12            AS designerName,
                b.oebud15             AS drawingDate,
                h.sfb01               AS workOrderNo,
                i.tc_pmo01               AS mergeWorkOrderNo
            FROM oea_file a
            JOIN oeb_file b ON a.oea01 = b.oeb01
            LEFT JOIN occ_file c ON a.oea03 = c.occ01
            LEFT JOIN ima_file d ON b.oeb04 = d.ima01
            LEFT JOIN gen_file e ON c.occ04 = e.gen01
            LEFT JOIN gen_file f ON a.oeaoriu = f.gen01	 --資料建立者
            -- LEFT JOIN gen_file g ON b.oebud11 = g.gen01
            LEFT JOIN sfb_file h ON b.oeb01 = h.sfb22 AND b.oeb03 = h.sfb221 AND h.sfbacti = 'Y'
            LEFT JOIN tc_pmo_file i ON (b.oeb01 || '-' || b.oeb03) = i.tc_pmo05 and i.tc_pmo02 = 0 --i.tc_pmo02 = 0 means MO
            left join imz_file j on d.ima06 = j.imz01
            left join azf_file k on d.ima10 = k.azf01 and k.azf02 = 'E'
            WHERE  a.oeaconf = 'Y'
                	and b.oeb12 - b.oeb24 > 0  -- 未出貨數量		
                	and a.oea49 in ('0', '1', 'S') 
                	and not(b.oeb70 = 'Y')
              AND (:customerNo IS NULL OR a.oea03 = :customerNo)
              AND (:orderNo IS NULL OR b.oeb01 LIKE :orderNo)
              AND (:workOrderNo IS NULL OR h.sfb01 LIKE :workOrderNo)
            ORDER BY NVL(b.oebud12, 99), b.oeb15, b.oeb01, b.oeb03
            """, nativeQuery = true)
    List<Object[]> findUndeliveredOrders(
            @Param("customerNo") String customerNo,
            @Param("orderNo") String orderNo,
            @Param("workOrderNo") String workOrderNo);
    
    /**
     * 查詢訂單的出貨明細
     */
    @Query(value = """
            SELECT 
                b.ogb01               AS shipmentNo,
                b.ogb03               AS shipmentSeq,
                NVL(b.ogb12, 0)       AS shippedQty,
                a.oga02               AS shipmentDate,
                NVL(b.ogb50, 0)       AS signedQty,
                a.oga65               AS customerSigned
            FROM oga_file a
            JOIN ogb_file b ON a.oga01 = b.ogb01
            WHERE b.ogb31 = :orderNo
              AND b.ogb32 = :orderSeq
              AND a.ogaconf = 'Y'
              AND a.oga09 = '2' --出貨單
            ORDER BY a.oga02
            """, nativeQuery = true)
    List<Object[]> findShipmentDetails(
            @Param("orderNo") String orderNo,
            @Param("orderSeq") Integer orderSeq);
    
    /**
     * 查詢料件庫存量 (排除特定倉庫，無批號)
     */
    @Query(value = """
            SELECT NVL(SUM(img10), 0) AS stockQty
            FROM img_file
            WHERE img01 = :partNo
              AND img23 = 'Y'
              AND img10 > 0
              AND img02 NOT IN ('AM', 'AK', 'H')
              AND LENGTH(img04) < 2
            """, nativeQuery = true)
    BigDecimal findStockQty(@Param("partNo") String partNo);
    
    /**
     * 查詢工單製程進度 (用於 MOChart)
     */
    @Query(value = """
            SELECT 
                a.sfb01               AS workOrderNo,
                a.sfb05               AS partNo,
                b.ima02               AS partName,
                NVL(a.sfb08, 0)       AS productionQty,
                NVL(a.sfb09, 0)       AS completedQty,
                (NVL(a.sfb08, 0) - NVL(a.sfb09, 0) - NVL(a.sfb12, 0)) AS wipQty,
                c.ecm03               AS routingSeq,
                c.ecm04               AS operationCode,
                c.ecm45               AS operationName,
                c.ecm06               AS workstationId,
                d.eca02               AS workstationName,
                NVL(c.ecm301, 0)      AS goodTransferIn,
                NVL(c.ecm311, 0)      AS goodTransferOut,
                (NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                 - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                 - NVL(c.ecm314,0) - NVL(c.ecm316,0)) AS routingWip
            FROM sfb_file a
            LEFT JOIN ima_file b ON a.sfb05 = b.ima01
            LEFT JOIN ecm_file c ON a.sfb01 = c.ecm01 AND c.ecmacti = 'Y'
            LEFT JOIN eca_file d ON c.ecm06 = d.eca01
            WHERE a.sfb01 = :workOrderNo
              AND a.sfbacti = 'Y'
            ORDER BY c.ecm03
            """, nativeQuery = true)
    List<Object[]> findWorkOrderRoutingProgress(@Param("workOrderNo") String workOrderNo);

}
