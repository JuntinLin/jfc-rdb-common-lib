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

    // ========== 產業別統計（不分客戶，用於 getPeriodStatsByIndustry） ==========

    /**
     * 訂單金額 - 按產業別分組
     * row: industry, totalAmount
     */
    @Query(value = """
        SELECT oca.oca02 AS industry, NVL(SUM(oea.oea24 * oeb.oeb14), 0) AS totalAmount
        FROM OEA_FILE oea
        LEFT OUTER JOIN OEB_FILE oeb ON oeb.oeb01 = oea.oea01
        LEFT JOIN OCC_FILE occ ON occ.occ01 = oea.oea03
        LEFT JOIN OCA_FILE oca ON oca.oca01 = occ.occ03
        WHERE oea.oeaconf IN ('Y', 'N')
          AND oeb.oeb04 IS NOT NULL
          AND oea.oea02 BETWEEN :startDate AND :endDate
        GROUP BY oca.oca02
        """, nativeQuery = true)
    List<Object[]> findOrderAmountsByIndustry(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 出貨金額 - 按產業別分組
     * row: industry, totalAmount
     */
    @Query(value = """
        SELECT oca.oca02 AS industry, NVL(SUM(oga.oga24 * ogb.ogb14), 0) AS totalAmount
        FROM OGA_FILE oga
        LEFT OUTER JOIN OGB_FILE ogb ON ogb.ogb01 = oga.oga01
        LEFT JOIN OCC_FILE occ ON occ.occ01 = oga.oga03
        LEFT JOIN OCA_FILE oca ON oca.oca01 = occ.occ03
        WHERE oga.ogaconf = 'Y' AND oga.ogapost = 'Y' AND oga.oga09 = '2'
          AND oga.oga02 BETWEEN :startDate AND :endDate
        GROUP BY oca.oca02
        """, nativeQuery = true)
    List<Object[]> findShipmentAmountsByIndustry(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 應收金額 - 按產業別分組
     * row: industry, totalAmount
     */
    @Query(value = """
        SELECT oca.oca02 AS industry, NVL(SUM(omb.omb16), 0) AS totalAmount
        FROM OMB_FILE omb
        LEFT OUTER JOIN OMA_FILE oma ON oma.oma01 = omb.omb01
        LEFT JOIN OCC_FILE occ ON occ.occ01 = oma.oma03
        LEFT JOIN OCA_FILE oca ON oca.oca01 = occ.occ03
        WHERE oma.omaconf = 'Y' AND oma.omavoid = 'N' AND oma.oma00 = '12'
          AND oma.oma02 BETWEEN :startDate AND :endDate
        GROUP BY oca.oca02
        """, nativeQuery = true)
    List<Object[]> findInvoiceAmountsByIndustry(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    // ========== 產品別統計（不分客戶，用於 getPeriodStatsByProductType） ==========
    // 產品別 = IMZ_FILE.IMZ02 原始值（同「出貨預估」頁 ProductTypeFilter 的「類別」定義：
    // 油缸/氣缸/系統整合/系統整合-零件/電動缸/機械手整合/油封組/零件組/半成品/市販品/密封件類/雜項/其他）

    /**
     * 訂單金額 - 按產品別分組
     * row: productType, totalAmount
     */
    @Query(value = """
        SELECT NVL(imz.imz02, '未分類') AS productType, NVL(SUM(oea.oea24 * oeb.oeb14), 0) AS totalAmount
        FROM OEA_FILE oea
        LEFT OUTER JOIN OEB_FILE oeb ON oeb.oeb01 = oea.oea01
        LEFT JOIN IMA_FILE ima ON ima.ima01 = oeb.oeb04
        LEFT JOIN IMZ_FILE imz ON imz.imz01 = ima.ima06
        WHERE oea.oeaconf IN ('Y', 'N')
          AND oeb.oeb04 IS NOT NULL
          AND oea.oea02 BETWEEN :startDate AND :endDate
        GROUP BY imz.imz02
        """, nativeQuery = true)
    List<Object[]> findOrderAmountsByProductType(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 出貨金額 - 按產品別分組
     * row: productType, totalAmount
     */
    @Query(value = """
        SELECT NVL(imz.imz02, '未分類') AS productType, NVL(SUM(oga.oga24 * ogb.ogb14), 0) AS totalAmount
        FROM OGA_FILE oga
        LEFT OUTER JOIN OGB_FILE ogb ON ogb.ogb01 = oga.oga01
        LEFT JOIN IMA_FILE ima ON ima.ima01 = ogb.ogb04
        LEFT JOIN IMZ_FILE imz ON imz.imz01 = ima.ima06
        WHERE oga.ogaconf = 'Y' AND oga.ogapost = 'Y' AND oga.oga09 = '2'
          AND oga.oga02 BETWEEN :startDate AND :endDate
        GROUP BY imz.imz02
        """, nativeQuery = true)
    List<Object[]> findShipmentAmountsByProductType(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 應收金額 - 按產品別分組（OMB_FILE.OMB04 品名編號 -> IMA_FILE.IMA01，同訂單出貨應收統計_客戶.sql 參考寫法）
     * row: productType, totalAmount
     */
    @Query(value = """
        SELECT NVL(imz.imz02, '未分類') AS productType, NVL(SUM(omb.omb16), 0) AS totalAmount
        FROM OMB_FILE omb
        LEFT OUTER JOIN OMA_FILE oma ON oma.oma01 = omb.omb01
        LEFT JOIN IMA_FILE ima ON ima.ima01 = omb.omb04
        LEFT JOIN IMZ_FILE imz ON imz.imz01 = ima.ima06
        WHERE oma.omaconf = 'Y' AND oma.omavoid = 'N' AND oma.oma00 = '12'
          AND oma.oma02 BETWEEN :startDate AND :endDate
        GROUP BY imz.imz02
        """, nativeQuery = true)
    List<Object[]> findInvoiceAmountsByProductType(
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

    // ========== 訂單生產日期分析（重建版，規格見 docs 對應 brief） ==========
    // 全程唯讀 SELECT，絕不可 INSERT/UPDATE/DELETE/DDL（Tiptop 為正式生產 ERP）。
    // Stage A：依查詢類型取單（4 選一），每個查詢回傳同一組欄位順序，供 Service 端統一映射：
    //   [0]oeb01 [1]oeb03 [2]oea03(customerCode) [3]oea032(misc客戶名) [4]oea02(orderDate)
    //   [5]oeb04(partNo) [6]oeb12(quantity) [7]oeb24(shippedQty) [8]oebud15(designDate)
    //   [9]oeb15(promisedDate原始) [10]ta_oeb15(展延交貨日) [11]ta_oeb13(客戶確認日)
    //   [12]ta_oeb11(設計人員1) [13]ta_oeb12(設計人員2) [14]ta_oeb14(零件會驗)
    //   [15]oebud10(設計狀態) [16]oebud13(設計派工日) [17]oebud14(設計執行日)
    // 客戶名稱/料件資料/工單/發料/入庫/出貨日一律由 Stage B 批次查詢（見下）取得，
    // 避免 N+1 查詢，且客戶/料件缺碼不會讓整批查詢落空（外鍵斷鏈由 Service 端組裝異常清單）。

    /** Stage A（未出貨）：oeb70='N' 且訂單狀況為開立/已核准，且已出貨數量為 0，以訂單日期篩選 */
    @Query(value = """
        SELECT DISTINCT oeb.oeb01, oeb.oeb03, oea.oea03, oea.oea032, oea.oea02, oeb.oeb04,
            oeb.oeb12, oeb.oeb24, oeb.oebud15, oeb.oeb15, oeb.ta_oeb15, oeb.ta_oeb13,
            oeb.ta_oeb11, oeb.ta_oeb12, oeb.ta_oeb14, oeb.oebud10, oeb.oebud13, oeb.oebud14
        FROM oeb_file oeb
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        WHERE (oeb.oeb70 IS NULL OR oeb.oeb70 = 'N')
          AND oea.oea49 IN ('0', '1')
          AND oeb.oeb24 = 0
          AND oea.oea02 BETWEEN :startDate AND :endDate
        ORDER BY oea.oea02, oeb.oeb01, oeb.oeb03
        """, nativeQuery = true)
    List<Object[]> findOrderItemsUnshipped(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /** Stage A（已出貨）：一般出貨單(oga09='2')，排除作廢，以出貨日篩選；DISTINCT 避免同筆訂單多次出貨在區間內重複計入 */
    @Query(value = """
        SELECT DISTINCT oeb.oeb01, oeb.oeb03, oea.oea03, oea.oea032, oea.oea02, oeb.oeb04,
            oeb.oeb12, oeb.oeb24, oeb.oebud15, oeb.oeb15, oeb.ta_oeb15, oeb.ta_oeb13,
            oeb.ta_oeb11, oeb.ta_oeb12, oeb.ta_oeb14, oeb.oebud10, oeb.oebud13, oeb.oebud14
        FROM oga_file oga
        INNER JOIN ogb_file ogb ON ogb.ogb01 = oga.oga01
        INNER JOIN oeb_file oeb ON oeb.oeb01 = ogb.ogb31 AND oeb.oeb03 = ogb.ogb32
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        WHERE oga.oga09 = '2'
          AND oga.ogaconf IN ('N', 'Y')
          AND oga.oga55 IN ('0', '1')
          AND ogb.ogb31 IS NOT NULL
          AND oga.oga02 BETWEEN :startDate AND :endDate
        ORDER BY oea.oea02, oeb.oeb01, oeb.oeb03
        """, nativeQuery = true)
    List<Object[]> findOrderItemsShipped(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /** Stage A（出圖）：設計狀態=完成(4)，以設計完成日篩選 */
    @Query(value = """
        SELECT DISTINCT oeb.oeb01, oeb.oeb03, oea.oea03, oea.oea032, oea.oea02, oeb.oeb04,
            oeb.oeb12, oeb.oeb24, oeb.oebud15, oeb.oeb15, oeb.ta_oeb15, oeb.ta_oeb13,
            oeb.ta_oeb11, oeb.ta_oeb12, oeb.ta_oeb14, oeb.oebud10, oeb.oebud13, oeb.oebud14
        FROM oeb_file oeb
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        WHERE oeb.oebud10 = 4
          AND oeb.oebud15 BETWEEN :startDate AND :endDate
        ORDER BY oea.oea02, oeb.oeb01, oeb.oeb03
        """, nativeQuery = true)
    List<Object[]> findOrderItemsDesignReleased(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /** Stage A（工單開立）：工單未作廢，以工單輸入日篩選；DISTINCT 避免一張訂單項次對到多張子工單重複計入 */
    @Query(value = """
        SELECT DISTINCT oeb.oeb01, oeb.oeb03, oea.oea03, oea.oea032, oea.oea02, oeb.oeb04,
            oeb.oeb12, oeb.oeb24, oeb.oebud15, oeb.oeb15, oeb.ta_oeb15, oeb.ta_oeb13,
            oeb.ta_oeb11, oeb.ta_oeb12, oeb.ta_oeb14, oeb.oebud10, oeb.oebud13, oeb.oebud14
        FROM sfb_file sfb
        INNER JOIN oeb_file oeb ON oeb.oeb01 = sfb.sfb22 AND oeb.oeb03 = sfb.sfb221
        INNER JOIN oea_file oea ON oea.oea01 = oeb.oeb01
        WHERE sfb.sfb87 != 'X'
          AND sfb.sfb22 IS NOT NULL AND sfb.sfb221 IS NOT NULL
          AND sfb.sfb81 BETWEEN :startDate AND :endDate
        ORDER BY oea.oea02, oeb.oeb01, oeb.oeb03
        """, nativeQuery = true)
    List<Object[]> findOrderItemsWorkOrderOpened(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    // ---------- Stage B：批次補齊關聯資料（一次 IN 查詢取整批，避免 N+1） ----------

    /** 依訂單單號批次找已確認生產工單：row[0]=oeb01, [1]=oeb03, [2]=工單號(sfb01), [3]=工單開立日(sfb81) */
    @Query(value = """
        SELECT sfb.sfb22, sfb.sfb221, sfb.sfb01, sfb.sfb81
        FROM sfb_file sfb
        WHERE sfb.sfb22 IN :oeb01List
          AND sfb.sfbacti = 'Y' AND sfb.sfb87 = 'Y'
        """, nativeQuery = true)
    List<Object[]> findWorkOrdersByOebNos(@Param("oeb01List") List<String> oeb01List);

    /** 依工單號批次找最後發料日（未結案工單發料底稿來源，sfs單身+sfp單頭）：row[0]=工單號, [1]=MAX(扣帳日) */
    @Query(value = """
        SELECT sfs.sfs03 AS workOrderNo, MAX(sfp.sfp03) AS lastDate
        FROM sfs_file sfs
        JOIN sfp_file sfp ON sfp.sfp01 = sfs.sfs01
        WHERE sfs.sfs03 IN :workOrderNos
        GROUP BY sfs.sfs03
        """, nativeQuery = true)
    List<Object[]> findLastSendingDatesFromSfs(@Param("workOrderNos") List<String> workOrderNos);

    /** 依工單號批次找最後發料日（已結案工單料帳歷史來源，sfe）：row[0]=工單號, [1]=MAX(異動日) */
    @Query(value = """
        SELECT sfe.sfe01 AS workOrderNo, MAX(sfe.sfe04) AS lastDate
        FROM sfe_file sfe
        WHERE sfe.sfe01 IN :workOrderNos
        GROUP BY sfe.sfe01
        """, nativeQuery = true)
    List<Object[]> findLastSendingDatesFromSfe(@Param("workOrderNos") List<String> workOrderNos);

    /** 依工單號批次找最後入庫日（完工入庫，sfv單身+sfu單頭）：row[0]=工單號, [1]=MAX(入庫日) */
    @Query(value = """
        SELECT sfv.sfv11 AS workOrderNo, MAX(sfu.sfu02) AS lastDate
        FROM sfv_file sfv
        JOIN sfu_file sfu ON sfu.sfu01 = sfv.sfv01
        WHERE sfv.sfv11 IN :workOrderNos
        GROUP BY sfv.sfv11
        """, nativeQuery = true)
    List<Object[]> findLastInStockDatesByWorkOrders(@Param("workOrderNos") List<String> workOrderNos);

    /**
     * 依訂單單號批次找出貨紀錄（一般出貨單 oga09='2'，排除作廢）：
     * row[0]=oeb01(ogb31), [1]=oeb03(ogb32), [2]=MAX(出貨日)
     * ⚠️取最大值（"首次出貨日"變數名稱沿用舊系統命名，邏輯上是最後一次出貨日，勿改成 MIN）
     */
    @Query(value = """
        SELECT ogb.ogb31 AS oeb01, ogb.ogb32 AS oeb03, MAX(oga.oga02) AS lastDeliveryDate
        FROM ogb_file ogb
        JOIN oga_file oga ON oga.oga01 = ogb.ogb01
        WHERE oga.oga09 = '2'
          AND oga.ogaconf IN ('N', 'Y')
          AND ogb.ogb31 IN :oeb01List
        GROUP BY ogb.ogb31, ogb.ogb32
        """, nativeQuery = true)
    List<Object[]> findShipmentsByOebNos(@Param("oeb01List") List<String> oeb01List);

    /**
     * 依客戶代號批次找客戶名稱與負責業務（LEFT JOIN，不因缺碼影響其他客戶；缺碼由 Service 端組成資料異常清單）：
     * row[0]=客戶代號(occ01), [1]=客戶名稱(occ02), [2]=負責業務姓名(gen02)
     */
    @Query(value = """
        SELECT occ.occ01, occ.occ02, gen.gen02 AS salesmanName
        FROM occ_file occ
        LEFT JOIN gen_file gen ON gen.gen01 = occ.occ04
        WHERE occ.occ01 IN :customerCodes
        """, nativeQuery = true)
    List<Object[]> findCustomersByCodes(@Param("customerCodes") List<String> customerCodes);

    /**
     * 依料號批次找料件基本資料：row[0]=料號(ima01), [1]=品名(ima02), [2]=規格(ima021),
     * [3]=主分群碼(ima06), [4]=產品別(ima09), [5]=分群碼二(ima10)
     */
    @Query(value = """
        SELECT ima.ima01, ima.ima02, ima.ima021, ima.ima06, ima.ima09, ima.ima10
        FROM ima_file ima
        WHERE ima.ima01 IN :partNos
        """, nativeQuery = true)
    List<Object[]> findPartsByCodes(@Param("partNos") List<String> partNos);
}
