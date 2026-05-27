package com.jfc.rdb.tiptop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.OebFile;
import com.jfc.rdb.tiptop.entity.OebFilePK;
/*
 * The Order-to-Cash process describes the entire workflow, from the moment a customer places an order to the final receipt of payment (cash).
 * The three stages you mentioned—Order, Shipment, and Invoice—are the core operational steps within the O2C cycle
 * */
@Repository
public interface O2CRepository extends JpaRepository<OebFile, OebFilePK> {
	
	// ========== 月度客戶統計查詢 (用於 runMonthlyStatistics) ==========
	// (A) 訂單金額統計 (Order Amount)
    @Query(value = """
        SELECT
            oea03 AS customerCode,
            occ02 AS customerName,
            TO_CHAR(oea02, 'YYYY-MM') AS statMonth,
            SUM(oea24 * oeb14) AS totalAmount
        FROM OEA_FILE
        LEFT OUTER JOIN OEB_FILE ON OEB01 = OEA01
        LEFT OUTER JOIN OCC_FILE ON OCC01 = OEA03
        WHERE OEACONF IN ('Y', 'N') AND OEB04 IS NOT NULL
          AND OEA02 BETWEEN :startDate AND :endDate
        GROUP BY OEA03, OCC02, TO_CHAR(OEA02, 'YYYY-MM')
        """, nativeQuery = true)
    List<Object[]> findMonthlyOrderAmounts(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // (B) 出貨金額統計 (Shipment Amount)
    @Query(value = """
        SELECT
            OGA03 AS customerCode,
            OCC02 AS customerName,
            TO_CHAR(OGA02, 'YYYY-MM') AS statMonth,
            SUM(OGA24 * OGB14) AS totalAmount
        FROM OGA_FILE
        LEFT OUTER JOIN OGB_FILE ON OGB01 = OGA01
        LEFT OUTER JOIN OCC_FILE ON OCC01 = OGA03
        WHERE OGACONF = 'Y' AND OGAPOST = 'Y' AND OGA09 = '2'
          AND OGA02 BETWEEN :startDate AND :endDate
        GROUP BY OGA03, OCC02, TO_CHAR(OGA02, 'YYYY-MM')
        """, nativeQuery = true)
    List<Object[]> findMonthlyShipmentAmounts(
    		@Param("startDate") Date startDate
    		, @Param("endDate") Date endDate);

    // (C) 發票金額統計 (Invoice/A/R Amount)
    @Query(value = """
        SELECT
            OMA03 AS customerCode,
            OCC02 AS customerName,
            TO_CHAR(OMA02, 'YYYY-MM') AS statMonth,
            SUM(OMB16) AS totalAmount
        FROM OMB_FILE
        LEFT OUTER JOIN OMA_FILE ON OMA01 = OMB01
        LEFT OUTER JOIN OCC_FILE ON OCC01 = OMA03
        WHERE OMACONF = 'Y' AND OMAVOID = 'N' AND OMA00 = '12'
          AND OMA02 BETWEEN :startDate AND :endDate
        GROUP BY OMA03, OCC02, TO_CHAR(OMA02, 'YYYY-MM')
        """, nativeQuery = true)
    List<Object[]> findMonthlyInvoiceAmounts(
    		@Param("startDate") Date startDate
    		, @Param("endDate") Date endDate);
    
 // ========== 業務員客戶統計查詢 (用於 getSalesmanSummary) ==========
    /**
     * 查詢特定業務員在特定區間的訂單總額 (Order Amount)
     */
    @Query(value = """
        SELECT
            T.customerCode,
            T.customerName,
            SUM(T.totalAmount) AS totalAmount
        FROM (
            SELECT
                OEA03 AS customerCode,
                CASE WHEN OEA03 = 'MISC' THEN OEA032 ELSE OCC02 END AS customerName,
                (OEA24 * OEB14) AS totalAmount
            FROM OEA_FILE
            LEFT OUTER JOIN OEB_FILE ON OEB01 = OEA01
            LEFT OUTER JOIN OCC_FILE ON OCC01 = OEA03
            WHERE OEACONF IN ('Y', 'N')
              AND OEB04 IS NOT NULL
              AND OEA02 BETWEEN :startDate AND :endDate
              AND (OCC04 = :salesmanId OR (OEA03 = 'MISC' AND OEA14 = :salesmanId))
        ) T
        GROUP BY T.customerCode, T.customerName
        """, nativeQuery = true)
    List<Object[]> findTotalOrderAmountsBySalesman(
            @Param("salesmanId") String salesmanId, 
            @Param("startDate") Date startDate, 
            @Param("endDate") Date endDate);

    /**
     * 查詢特定業務員在特定區間的出貨總額 (Shipment Amount)
     */
    @Query(value = """
        SELECT
            T.customerCode,
            T.customerName,
            SUM(T.totalAmount) AS totalAmount
        FROM (
            SELECT
                OGA03 AS customerCode,
                CASE WHEN OGA03 = 'MISC' THEN OGA032 ELSE OCC02 END AS customerName,
                (OGA24 * OGB14) AS totalAmount
            FROM OGA_FILE
            LEFT OUTER JOIN OGB_FILE ON OGB01 = OGA01
            LEFT OUTER JOIN OCC_FILE ON OCC01 = OGA03
            WHERE OGACONF = 'Y' AND OGAPOST = 'Y' AND OGA09 = '2'
              AND OGA02 BETWEEN :startDate AND :endDate
              AND (OCC04 = :salesmanId OR (OGA03 = 'MISC' AND OGA14 = :salesmanId))
        ) T
        GROUP BY T.customerCode, T.customerName
        """, nativeQuery = true)
    List<Object[]> findTotalShipmentAmountsBySalesman(
            @Param("salesmanId") String salesmanId, 
            @Param("startDate") Date startDate, 
            @Param("endDate") Date endDate);

    /**
     * 查詢特定業務員在特定區間的應收總額 (Invoice/A/R Amount)
     */
    @Query(value = """
        SELECT
            T.customerCode,
            T.customerName,
            SUM(T.totalAmount) AS totalAmount
        FROM (
            SELECT
                OMA03 AS customerCode,
                CASE WHEN OMA03 = 'MISC' THEN OMA032 ELSE OCC02 END AS customerName,
                OMB16 AS totalAmount
            FROM OMB_FILE
            LEFT OUTER JOIN OMA_FILE ON OMA01 = OMB01
            LEFT OUTER JOIN OCC_FILE ON OCC01 = OMA03
            WHERE OMACONF = 'Y' AND OMAVOID = 'N' AND OMA00 = '12'
              AND OMA02 BETWEEN :startDate AND :endDate
              AND (OCC04 = :salesmanId OR (OMA03 = 'MISC' AND OMA14 = :salesmanId))
        ) T
        GROUP BY T.customerCode, T.customerName
        """, nativeQuery = true)
    List<Object[]> findTotalInvoiceAmountsBySalesman(
            @Param("salesmanId") String salesmanId, 
            @Param("startDate") Date startDate, 
            @Param("endDate") Date endDate);
    
// ========== Dashboard 儀表板統計查詢 (用於 getDashboardStats) ==========
    
    /**
     * 查詢指定區間的訂單總額 (不分客戶)
     */
    @Query(value = """
        SELECT NVL(SUM(OEA24 * OEB14), 0) AS totalAmount
        FROM OEA_FILE
        LEFT OUTER JOIN OEB_FILE ON OEB01 = OEA01
        WHERE OEACONF IN ('Y', 'N') 
          AND OEB04 IS NOT NULL
          AND OEA02 BETWEEN :startDate AND :endDate
        """, nativeQuery = true)
    Object findTotalOrderAmount(
            @Param("startDate") Date startDate, 
            @Param("endDate") Date endDate);

    /**
     * 查詢指定區間的出貨總額 (不分客戶)
     */
    @Query(value = """
        SELECT NVL(SUM(OGA24 * OGB14), 0) AS totalAmount
        FROM OGA_FILE
        LEFT OUTER JOIN OGB_FILE ON OGB01 = OGA01
        WHERE OGACONF = 'Y' AND OGAPOST = 'Y' AND OGA09 = '2'
          AND OGA02 BETWEEN :startDate AND :endDate
        """, nativeQuery = true)
    Object findTotalShipmentAmount(
            @Param("startDate") Date startDate, 
            @Param("endDate") Date endDate);

    /**
     * 查詢指定區間的應收總額 (不分客戶)
     */
    @Query(value = """
        SELECT NVL(SUM(OMB16), 0) AS totalAmount
        FROM OMB_FILE
        LEFT OUTER JOIN OMA_FILE ON OMA01 = OMB01
        WHERE OMACONF = 'Y' AND OMAVOID = 'N' AND OMA00 = '12'
          AND OMA02 BETWEEN :startDate AND :endDate
        """, nativeQuery = true)
    Object findTotalInvoiceAmount(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    // ========== 客戶訂單/出貨分類統計 ==========

    /**
     * 客戶訂單統計（按客戶+產品類別）
     * row: customerCode, customerName, salesman, industry, category, itemCount, amount
     */
    @Query(value = """
        SELECT
            oea.oea03 AS customerCode,
            CASE WHEN oea.oea03 = 'MISC' THEN oea.oea032 ELSE occ.occ02 END AS customerName,
            gen.gen02 AS salesman,
            oca.oca02 AS industry,
            CASE
                WHEN imz.imz02 = '油缸' THEN '油缸'
                WHEN imz.imz02 = '氣缸' THEN '氣缸'
                WHEN imz.imz02 IN ('系統整合', '系統整合-零件') THEN '系統'
                ELSE '其他'
            END AS category,
            COUNT(*) AS itemCount,
            SUM(NVL(oea.oea24, 1) * NVL(oeb.oeb14, 0)) AS amount
        FROM oeb_file oeb
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        LEFT JOIN occ_file occ ON occ.occ01 = oea.oea03
        LEFT JOIN gen_file gen ON gen.gen01 = occ.occ04
        LEFT JOIN oca_file oca ON oca.oca01 = occ.occ03
        LEFT JOIN ima_file ima ON ima.ima01 = oeb.oeb04
        LEFT JOIN imz_file imz ON imz.imz01 = ima.ima06
        WHERE oea.oeaconf IN ('Y', 'N')
          AND oeb.oeb04 IS NOT NULL
          AND oea.oea02 BETWEEN :startDate AND :endDate
        GROUP BY oea.oea03,
                 CASE WHEN oea.oea03 = 'MISC' THEN oea.oea032 ELSE occ.occ02 END,
                 gen.gen02, oca.oca02,
                 CASE WHEN imz.imz02 = '油缸' THEN '油缸'
                      WHEN imz.imz02 = '氣缸' THEN '氣缸'
                      WHEN imz.imz02 IN ('系統整合', '系統整合-零件') THEN '系統'
                      ELSE '其他' END
        ORDER BY amount DESC
        """, nativeQuery = true)
    List<Object[]> findOrderStatByCustomer(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 客戶出貨統計（按客戶+產品類別，已過帳）
     * row: customerCode, customerName, salesman, industry, category, itemCount, amount
     */
    @Query(value = """
        SELECT
            oga.oga03 AS customerCode,
            CASE WHEN oga.oga03 = 'MISC' THEN oga.oga032 ELSE occ.occ02 END AS customerName,
            gen.gen02 AS salesman,
            oca.oca02 AS industry,
            CASE
                WHEN imz.imz02 = '油缸' THEN '油缸'
                WHEN imz.imz02 = '氣缸' THEN '氣缸'
                WHEN imz.imz02 IN ('系統整合', '系統整合-零件') THEN '系統'
                ELSE '其他'
            END AS category,
            COUNT(*) AS itemCount,
            SUM(NVL(oga.oga24, 1) * NVL(ogb.ogb14, 0)) AS amount
        FROM ogb_file ogb
        INNER JOIN oga_file oga ON oga.oga01 = ogb.ogb01
        LEFT JOIN occ_file occ ON occ.occ01 = oga.oga03
        LEFT JOIN gen_file gen ON gen.gen01 = occ.occ04
        LEFT JOIN oca_file oca ON oca.oca01 = occ.occ03
        LEFT JOIN ima_file ima ON ima.ima01 = ogb.ogb04
        LEFT JOIN imz_file imz ON imz.imz01 = ima.ima06
        WHERE oga.ogaconf = 'Y'
          AND oga.ogapost = 'Y'
          AND oga.oga09 = '2'
          AND oga.oga02 BETWEEN :startDate AND :endDate
        GROUP BY oga.oga03,
                 CASE WHEN oga.oga03 = 'MISC' THEN oga.oga032 ELSE occ.occ02 END,
                 gen.gen02, oca.oca02,
                 CASE WHEN imz.imz02 = '油缸' THEN '油缸'
                      WHEN imz.imz02 = '氣缸' THEN '氣缸'
                      WHEN imz.imz02 IN ('系統整合', '系統整合-零件') THEN '系統'
                      ELSE '其他' END
        ORDER BY amount DESC
        """, nativeQuery = true)
    List<Object[]> findShipmentStatByCustomer(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 客戶入庫統計（按客戶+產品類別）
     * 資料來源：sfu_file（入庫單頭）+ sfv_file（入庫單身）
     * 日期篩選：sfu02（入庫日期）
     * 金額 = sfv09（入庫量）× oea24（匯率）× oeb13（單價）
     * row: customerCode, customerName, salesman, industry, category, itemCount, amount
     */
    @Query(value = """
        SELECT
            oea.oea03 AS customerCode,
            CASE WHEN oea.oea03 = 'MISC' THEN oea.oea032 ELSE occ.occ02 END AS customerName,
            gen.gen02 AS salesman,
            oca.oca02 AS industry,
            CASE
                WHEN imz.imz02 = '油缸' THEN '油缸'
                WHEN imz.imz02 = '氣缸' THEN '氣缸'
                WHEN imz.imz02 IN ('系統整合', '系統整合-零件') THEN '系統'
                ELSE '其他'
            END AS category,
            COUNT(*) AS itemCount,
            SUM(NVL(sfv.sfv09, 0) * NVL(oea.oea24, 1) * NVL(oeb.oeb13, 0)) AS amount
        FROM sfv_file sfv
        INNER JOIN sfu_file sfu ON sfu.sfu01 = sfv.sfv01
        INNER JOIN sfb_file sfb ON sfb.sfb01 = sfv.sfv11
        LEFT JOIN oeb_file oeb ON oeb.oeb01 = sfb.sfb22 AND oeb.oeb03 = sfb.sfb221
        LEFT JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        LEFT JOIN occ_file occ ON occ.occ01 = oea.oea03
        LEFT JOIN gen_file gen ON gen.gen01 = occ.occ04
        LEFT JOIN oca_file oca ON oca.oca01 = occ.occ03
        LEFT JOIN ima_file ima ON ima.ima01 = sfv.sfv04
        LEFT JOIN imz_file imz ON imz.imz01 = ima.ima06
        WHERE sfu.sfuconf = 'Y'
          AND sfu.sfupost = 'Y'
          AND NOT (sfb.sfb22 IS NULL AND sfb.sfb91 IS NULL)
          AND oea.oea03 IS NOT NULL
          AND sfu.sfu02 BETWEEN :startDate AND :endDate
        GROUP BY oea.oea03,
                 CASE WHEN oea.oea03 = 'MISC' THEN oea.oea032 ELSE occ.occ02 END,
                 gen.gen02, oca.oca02,
                 CASE WHEN imz.imz02 = '油缸' THEN '油缸'
                      WHEN imz.imz02 = '氣缸' THEN '氣缸'
                      WHEN imz.imz02 IN ('系統整合', '系統整合-零件') THEN '系統'
                      ELSE '其他' END
        ORDER BY amount DESC
        """, nativeQuery = true)
    List<Object[]> findStockInStatByCustomer(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    // ========== 明細查詢 ==========

    /**
     * 客戶訂單明細
     * row: orderNo, customerCode, customerName, partNo, partName, partSpec,
     *      productType, productLine, deliveryDate, bore, stroke, quantity, amount
     */
    @Query(value = """
        SELECT
            oeb.oeb01 || '-' || oeb.oeb03 AS orderNo,
            oea.oea03 AS customerCode,
            CASE WHEN oea.oea03 = 'MISC' THEN oea.oea032 ELSE occ.occ02 END AS customerName,
            oeb.oeb04 AS partNo,
            ima.ima02 AS partName,
            ima.ima021 AS partSpec,
            ima.ima09 || ' ' || imz.imz02 AS productType,
            SUBSTR(oeb.oeb04, 3, 2) AS productLine,
            COALESCE(oeb.ta_oeb15, oeb.oeb15) AS deliveryDate,
            ima.ima35 AS bore,
            ima.ima36 AS stroke,
            oeb.oeb12 AS quantity,
            NVL(oea.oea24, 1) * NVL(oeb.oeb14, 0) AS amount
        FROM oeb_file oeb
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        LEFT JOIN occ_file occ ON occ.occ01 = oea.oea03
        LEFT JOIN ima_file ima ON ima.ima01 = oeb.oeb04
        LEFT JOIN imz_file imz ON imz.imz01 = ima.ima06
        WHERE oea.oeaconf IN ('Y', 'N')
          AND oeb.oeb04 IS NOT NULL
          AND oea.oea03 = :customerCode
          AND oea.oea02 BETWEEN :startDate AND :endDate
        ORDER BY oeb.oeb01, oeb.oeb03
        """, nativeQuery = true)
    List<Object[]> findOrderDetailByCustomer(
            @Param("customerCode") String customerCode,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 客戶入庫明細
     * row: orderNo, workOrderNo, stockInDate, customerCode, customerName, partNo, partName, partSpec,
     *      productType, productLine, deliveryDate, bore, stroke, quantity, amount
     */
    @Query(value = """
        SELECT
            sfb.sfb22 || '-' || sfb.sfb221 AS orderNo,
            sfv.sfv11 AS workOrderNo,
            sfu.sfu02 AS stockInDate,
            oea.oea03 AS customerCode,
            CASE WHEN oea.oea03 = 'MISC' THEN oea.oea032 ELSE occ.occ02 END AS customerName,
            sfv.sfv04 AS partNo,
            ima.ima02 AS partName,
            ima.ima021 AS partSpec,
            ima.ima09 || ' ' || imz.imz02 AS productType,
            SUBSTR(sfv.sfv04, 3, 2) AS productLine,
            COALESCE(oeb.ta_oeb15, oeb.oeb15) AS deliveryDate,
            ima.ima35 AS bore,
            ima.ima36 AS stroke,
            sfv.sfv09 AS quantity,
            NVL(sfv.sfv09, 0) * NVL(oea.oea24, 1) * NVL(oeb.oeb13, 0) AS amount
        FROM sfv_file sfv
        INNER JOIN sfu_file sfu ON sfu.sfu01 = sfv.sfv01
        INNER JOIN sfb_file sfb ON sfb.sfb01 = sfv.sfv11
        LEFT JOIN oeb_file oeb ON oeb.oeb01 = sfb.sfb22 AND oeb.oeb03 = sfb.sfb221
        LEFT JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        LEFT JOIN occ_file occ ON occ.occ01 = oea.oea03
        LEFT JOIN ima_file ima ON ima.ima01 = sfv.sfv04
        LEFT JOIN imz_file imz ON imz.imz01 = ima.ima06
        WHERE sfu.sfuconf = 'Y'
          AND sfu.sfupost = 'Y'
          AND oea.oea03 = :customerCode
          AND sfu.sfu02 BETWEEN :startDate AND :endDate
        ORDER BY sfu.sfu02, sfv.sfv11
        """, nativeQuery = true)
    List<Object[]> findStockInDetailByCustomer(
            @Param("customerCode") String customerCode,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 客戶出貨明細
     * row: shipmentNo, customerCode, customerName, partNo, partName, partSpec,
     *      productType, productLine, productShortName, bore, stroke, quantity, amount
     */
    @Query(value = """
        SELECT
            ogb.ogb01 || '-' || ogb.ogb03 AS shipmentNo,
            oga.oga03 AS customerCode,
            CASE WHEN oga.oga03 = 'MISC' THEN oga.oga032 ELSE occ.occ02 END AS customerName,
            ogb.ogb04 AS partNo,
            ima.ima02 AS partName,
            ima.ima021 AS partSpec,
            ima.ima09 || ' ' || imz.imz02 AS productType,
            SUBSTR(ogb.ogb04, 3, 2) AS productLine,
            SUBSTR(ima.ima02, 1, 5) AS productShortName,
            ima.ima35 AS bore,
            ima.ima36 AS stroke,
            ogb.ogb12 AS quantity,
            NVL(oga.oga24, 1) * NVL(ogb.ogb14, 0) AS amount
        FROM ogb_file ogb
        INNER JOIN oga_file oga ON oga.oga01 = ogb.ogb01
        LEFT JOIN occ_file occ ON occ.occ01 = oga.oga03
        LEFT JOIN ima_file ima ON ima.ima01 = ogb.ogb04
        LEFT JOIN imz_file imz ON imz.imz01 = ima.ima06
        WHERE oga.ogaconf = 'Y'
          AND oga.ogapost = 'Y'
          AND oga.oga09 = '2'
          AND oga.oga03 = :customerCode
          AND oga.oga02 BETWEEN :startDate AND :endDate
        ORDER BY ogb.ogb01, ogb.ogb03
        """, nativeQuery = true)
    List<Object[]> findShipmentDetailByCustomer(
            @Param("customerCode") String customerCode,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    // ========== 訂單生產日期分析 ==========

    /**
     * 訂單生產時間線查詢（未出貨：以約定交貨日篩選）
     *
     * row[0] orderNo, [1] customerCode, [2] customerName, [3] partNo,
     * [4] partName, [5] partSpec, [6] spl(ima09), [7] productType(imz02),
     * [8] orderDate, [9] designDate(oebud15), [10] promisedDate(oeb15),
     * [11] workOrderNo, [12] woCreateDate(sfb81),
     * [13] lastSendingDate, [14] lastStockInDate, [15] firstDeliveryDate,
     * [16] quantity, [17] shippedQty, [18] amount,
     * [19] salesman, [20] designer, [21] bore, [22] stroke
     */
    @Query(value = """
        SELECT
            oeb.oeb01 || '-' || oeb.oeb03 AS orderNo,
            oea.oea03 AS customerCode,
            CASE WHEN oea.oea03 = 'MISC' THEN oea.oea032 ELSE occ.occ02 END AS customerName,
            oeb.oeb04 AS partNo,
            ima.ima02 AS partName,
            ima.ima021 AS partSpec,
            ima.ima09 AS spl,
            imz.imz02 AS productType,
            oea.oea02 AS orderDate,
            oeb.oebud15 AS designDate,
            COALESCE(oeb.ta_oeb15, oeb.oeb15) AS promisedDate,
            sfb.sfb01 AS workOrderNo,
            sfb.sfb81 AS woCreateDate,
            (SELECT MAX(sfp.sfp03) FROM sfp_file sfp WHERE sfp.sfp01 = sfb.sfb01) AS lastSendingDate,
            (SELECT MAX(sfu.sfu02) FROM sfv_file sfv2 JOIN sfu_file sfu ON sfu.sfu01 = sfv2.sfv01 WHERE sfv2.sfv11 = sfb.sfb01) AS lastStockInDate,
            (SELECT MIN(oga.oga02) FROM ogb_file ogb2 JOIN oga_file oga ON oga.oga01 = ogb2.ogb01
             WHERE ogb2.ogb31 = oeb.oeb01 AND ogb2.ogb32 = oeb.oeb03
               AND oga.ogaconf = 'Y' AND oga.oga09 = '2') AS firstDeliveryDate,
            oeb.oeb12 AS quantity,
            oeb.oeb24 AS shippedQty,
            NVL(oea.oea24, 1) * NVL(oeb.oeb14, 0) AS amount,
            gen.gen02 AS salesman,
            TRIM(oeb.ta_oeb11) || ' ' || TRIM(oeb.ta_oeb12) AS designer,
            ima.ima35 AS bore,
            ima.ima36 AS stroke
        FROM oeb_file oeb
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        LEFT JOIN occ_file occ ON occ.occ01 = oea.oea03
        LEFT JOIN gen_file gen ON gen.gen01 = occ.occ04
        LEFT JOIN ima_file ima ON ima.ima01 = oeb.oeb04
        LEFT JOIN imz_file imz ON imz.imz01 = ima.ima06
        LEFT JOIN sfb_file sfb ON sfb.sfb22 = oeb.oeb01 AND sfb.sfb221 = oeb.oeb03
                                  AND sfb.sfbacti = 'Y' AND sfb.sfb87 = 'Y'
        WHERE oea.oeaconf IN ('Y', 'N')
          AND oeb.oeb04 IS NOT NULL
          AND (oeb.oeb70 IS NULL OR oeb.oeb70 != 'Y')
          AND oeb.oeb12 - oeb.oeb24 > 0
          AND COALESCE(oeb.ta_oeb15, oeb.oeb15) BETWEEN :startDate AND :endDate
        ORDER BY oea.oea02, oeb.oeb01, oeb.oeb03
        """, nativeQuery = true)
    List<Object[]> findManufacturingAnalysisUnshipped(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 訂單生產時間線查詢（已出貨：以出貨日篩選）
     */
    @Query(value = """
        SELECT
            oeb.oeb01 || '-' || oeb.oeb03 AS orderNo,
            oea.oea03 AS customerCode,
            CASE WHEN oea.oea03 = 'MISC' THEN oea.oea032 ELSE occ.occ02 END AS customerName,
            oeb.oeb04 AS partNo,
            ima.ima02 AS partName,
            ima.ima021 AS partSpec,
            ima.ima09 AS spl,
            imz.imz02 AS productType,
            oea.oea02 AS orderDate,
            oeb.oebud15 AS designDate,
            COALESCE(oeb.ta_oeb15, oeb.oeb15) AS promisedDate,
            sfb.sfb01 AS workOrderNo,
            sfb.sfb81 AS woCreateDate,
            (SELECT MAX(sfp.sfp03) FROM sfp_file sfp WHERE sfp.sfp01 = sfb.sfb01) AS lastSendingDate,
            (SELECT MAX(sfu.sfu02) FROM sfv_file sfv2 JOIN sfu_file sfu ON sfu.sfu01 = sfv2.sfv01 WHERE sfv2.sfv11 = sfb.sfb01) AS lastStockInDate,
            delivery.firstDeliveryDate,
            oeb.oeb12 AS quantity,
            oeb.oeb24 AS shippedQty,
            NVL(oea.oea24, 1) * NVL(oeb.oeb14, 0) AS amount,
            gen.gen02 AS salesman,
            TRIM(oeb.ta_oeb11) || ' ' || TRIM(oeb.ta_oeb12) AS designer,
            ima.ima35 AS bore,
            ima.ima36 AS stroke
        FROM oeb_file oeb
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        LEFT JOIN occ_file occ ON occ.occ01 = oea.oea03
        LEFT JOIN gen_file gen ON gen.gen01 = occ.occ04
        LEFT JOIN ima_file ima ON ima.ima01 = oeb.oeb04
        LEFT JOIN imz_file imz ON imz.imz01 = ima.ima06
        LEFT JOIN sfb_file sfb ON sfb.sfb22 = oeb.oeb01 AND sfb.sfb221 = oeb.oeb03
                                  AND sfb.sfbacti = 'Y' AND sfb.sfb87 = 'Y'
        INNER JOIN (
            SELECT ogb.ogb31, ogb.ogb32, MIN(oga.oga02) AS firstDeliveryDate
            FROM ogb_file ogb
            JOIN oga_file oga ON oga.oga01 = ogb.ogb01
            WHERE oga.ogaconf = 'Y' AND oga.oga09 = '2'
            GROUP BY ogb.ogb31, ogb.ogb32
        ) delivery ON delivery.ogb31 = oeb.oeb01 AND delivery.ogb32 = oeb.oeb03
        WHERE oea.oeaconf IN ('Y', 'N')
          AND oeb.oeb04 IS NOT NULL
          AND delivery.firstDeliveryDate BETWEEN :startDate AND :endDate
        ORDER BY oea.oea02, oeb.oeb01, oeb.oeb03
        """, nativeQuery = true)
    List<Object[]> findManufacturingAnalysisShipped(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}
