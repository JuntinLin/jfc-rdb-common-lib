package com.jfc.rdb.tiptop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.SfbFile;
/**
 * 工單查詢 Repository
 */
@Repository
public interface WorkOrderRepository extends JpaRepository<SfbFile, String> {
    
    /**
     * 查詢工單基本資料
     */
    @Query(value = """
            SELECT 
                a.sfb01           AS workOrderNo,
                a.sfb02           AS workOrderType,
                a.sfb04           AS workOrderStatus,
                a.sfb05           AS partNumber,
                b.ima02           AS partName,
                b.ima021          AS partSpec,
                a.sfb08           AS productionQty,
                a.sfb09           AS completedQty,
                a.sfb12           AS scrapQty,
                (NVL(a.sfb08,0) - NVL(a.sfb09,0) - NVL(a.sfb12,0)) AS wipQty,
                a.sfb13           AS plannedStartDate,
                a.sfb15           AS plannedEndDate,
                a.sfb25           AS actualStartDate,
                a.sfb82           AS deptCode,
                c.gem02           AS deptName,
                a.sfb22           AS salesOrderNo,
                a.sfb221          AS salesOrderSeq,
                a.sfb96           AS remark
            FROM sfb_file a 
            LEFT JOIN ima_file b ON a.sfb05 = b.ima01 
            LEFT JOIN gem_file c ON a.sfb82 = c.gem01 
            WHERE a.sfb01 = :workOrderNo
            """, nativeQuery = true)
    List<Object[]> findWorkOrderByNo(@Param("workOrderNo") String workOrderNo);
    
    /**
     * 模糊查詢工單
     */
    @Query(value = """
            SELECT 
                a.sfb01           AS workOrderNo,
                a.sfb02           AS workOrderType,
                a.sfb04           AS workOrderStatus,
                a.sfb05           AS partNumber,
                b.ima02           AS partName,
                b.ima021          AS partSpec,
                a.sfb08           AS productionQty,
                a.sfb09           AS completedQty,
                a.sfb12           AS scrapQty,
                (NVL(a.sfb08,0) - NVL(a.sfb09,0) - NVL(a.sfb12,0)) AS wipQty,
                a.sfb13           AS plannedStartDate,
                a.sfb15           AS plannedEndDate,
                a.sfb25           AS actualStartDate,
                a.sfb82           AS deptCode,
                c.gem02           AS deptName,
                a.sfb22           AS salesOrderNo,
                a.sfb221          AS salesOrderSeq,
                a.sfb96           AS remark
            FROM sfb_file a 
            LEFT JOIN ima_file b ON a.sfb05 = b.ima01 
            LEFT JOIN gem_file c ON a.sfb82 = c.gem01 
            WHERE a.sfb01 LIKE :keyword
              AND a.sfbacti = 'Y'
            ORDER BY a.sfb01 DESC
            FETCH FIRST 100 ROWS ONLY
            """, nativeQuery = true)
    List<Object[]> searchWorkOrders(@Param("keyword") String keyword);
    
    /**
     * 查詢工單下階料/備料狀況
     */
    @Query(value = """
            SELECT 
                b.sfa01           AS workOrderNo,
                b.sfa03           AS componentPartNo,
                c.ima02           AS componentPartName,
                c.ima021          AS componentPartSpec,
                NVL(b.sfa05, 0)   AS requiredQty,
                NVL(b.sfa06, 0)   AS issuedQty,
                (NVL(b.sfa05, 0) - NVL(b.sfa06, 0)) AS pendingQty,
                NVL(d.stockQty, 0) AS stockQty,
                b.sfa12           AS issueUnit
            FROM sfa_file b 
            LEFT JOIN ima_file c ON b.sfa03 = c.ima01
            LEFT JOIN (
                SELECT img01, SUM(NVL(img10, 0)) AS stockQty 
                FROM img_file 
                WHERE img23 = 'Y'
                  AND img10 > 0
                  AND img02 NOT IN ('AM', 'AK', 'H')
                  AND LENGTH(img04) < 2
                GROUP BY img01
            ) d ON b.sfa03 = d.img01 
            WHERE b.sfa01 = :workOrderNo
              AND b.sfaacti = 'Y'
            ORDER BY b.sfa03
            """, nativeQuery = true)
    List<Object[]> findMaterialsByWorkOrder(@Param("workOrderNo") String workOrderNo);
    
    /**
     * 查詢工單製程進度
     */
    @Query(value = """
            SELECT 
                c.ecm01           AS workOrderNo,
                c.ecm03           AS routingSeq,
                c.ecm04           AS operationCode,
                c.ecm45           AS operationName,
                c.ecm06           AS workstationId,
                d.eca02           AS workstationName,
                NVL(c.ecm301, 0)  AS goodTransferIn,
                NVL(c.ecm302, 0)  AS reworkTransferIn,
                NVL(c.ecm303, 0)  AS orderTransferIn,
                NVL(c.ecm311, 0)  AS goodTransferOut,
                NVL(c.ecm312, 0)  AS reworkTransferOut,
                NVL(c.ecm313, 0)  AS scrapQty,
                NVL(c.ecm314, 0)  AS offlineQty,
                NVL(c.ecm316, 0)  AS orderTransferOut,
                (NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                 - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                 - NVL(c.ecm314,0) - NVL(c.ecm316,0)) AS wipQty
            FROM ecm_file c
            LEFT JOIN eca_file d ON c.ecm06 = d.eca01
            WHERE c.ecm01 = :workOrderNo
              AND c.ecmacti = 'Y'
            ORDER BY c.ecm03
            """, nativeQuery = true)
    List<Object[]> findRoutingsByWorkOrder(@Param("workOrderNo") String workOrderNo);
    
    /**
     * 查詢工單生產日報
     */
    @Query(value = """
            SELECT 
                a.shb01           AS transferNo,
                a.shb05           AS workOrderNo,
                a.shb06           AS routingSeq,
                a.shb081          AS operationCode,
                a.shb082          AS operationName,
                a.shb07           AS workCenter,
                a.shb09           AS machineNo,
                a.shb04           AS employeeNo,
                a.shb02           AS startDate,
                a.shb021          AS startTime,
                a.shb03           AS endDate,
                a.shb031          AS endTime,
                NVL(a.shb111, 0)  AS goodQty,
                NVL(a.shb112, 0)  AS scrapQty,
                NVL(a.shb113, 0)  AS reworkQty,
                NVL(a.shb114, 0)  AS offlineQty,
                NVL(a.shb032, 0)  AS laborHours,
                NVL(a.shb033, 0)  AS machineHours
            FROM shb_file a
            WHERE a.shb05 = :workOrderNo
              AND a.shbacti = 'Y'
            ORDER BY a.shb06, a.shb02
            """, nativeQuery = true)
    List<Object[]> findProductionReportsByWorkOrder(@Param("workOrderNo") String workOrderNo);
    
    /**
     * 查詢指定製程的生產日報
     */
    @Query(value = """
            SELECT 
                a.shb01           AS transferNo,
                a.shb05           AS workOrderNo,
                a.shb06           AS routingSeq,
                a.shb081          AS operationCode,
                a.shb082          AS operationName,
                a.shb07           AS workCenter,
                a.shb09           AS machineNo,
                a.shb04           AS employeeNo,
                a.shb02           AS startDate,
                a.shb021          AS startTime,
                a.shb03           AS endDate,
                a.shb031          AS endTime,
                NVL(a.shb111, 0)  AS goodQty,
                NVL(a.shb112, 0)  AS scrapQty,
                NVL(a.shb113, 0)  AS reworkQty,
                NVL(a.shb114, 0)  AS offlineQty,
                NVL(a.shb032, 0)  AS laborHours,
                NVL(a.shb033, 0)  AS machineHours
            FROM shb_file a
            WHERE a.shb05 = :workOrderNo
              AND a.shb06 = :routingSeq
              AND a.shbacti = 'Y'
            ORDER BY a.shb02
            """, nativeQuery = true)
    List<Object[]> findProductionReportsByWorkOrderAndRouting(
            @Param("workOrderNo") String workOrderNo,
            @Param("routingSeq") Integer routingSeq);

    /**
     * 員工報工工時統計（按員工匯總）
     *
     * row[0] employeeNo, row[1] employeeName,
     * row[2] reportCount, row[3] workOrderCount,
     * row[4] totalLaborHours, row[5] totalMachineHours,
     * row[6] totalGoodQty, row[7] totalScrapQty
     */
    @Query(value = """
            SELECT
                a.shb04             AS employeeNo,
                gen.gen02           AS employeeName,
                COUNT(a.shb01)      AS reportCount,
                COUNT(DISTINCT a.shb05) AS workOrderCount,
                ROUND(SUM(NVL(a.shb032, 0)) / 60, 2) AS totalLaborHours,
                ROUND(SUM(NVL(a.shb033, 0)) / 60, 2) AS totalMachineHours,
                SUM(NVL(a.shb111, 0)) AS totalGoodQty,
                SUM(NVL(a.shb112, 0)) AS totalScrapQty
            FROM shb_file a
            LEFT JOIN gen_file gen ON gen.gen01 = a.shb04
            WHERE a.shbacti = 'Y'
              AND a.shb03 BETWEEN :startDate AND :endDate
              AND (:employeeNo IS NULL OR a.shb04 = :employeeNo)
            GROUP BY a.shb04, gen.gen02
            ORDER BY totalLaborHours DESC
            """, nativeQuery = true)
    List<Object[]> findLaborHoursSummary(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("employeeNo") String employeeNo);

    /**
     * 員工產值統計（按員工匯總）
     *
     * 產值計算邏輯：
     * - 單一報工: ecb48 × shb111 (加工單價 × 良品轉出量)
     * - 多人報工: ecm311 × ecb48 × (shb032 / 同製程總工時)
     * - 單價來源: ecb_file (製程資料), 先用 ima01 再用 ima571 查找
     *
     * row[0] employeeNo, row[1] employeeName,
     * row[2] reportCount, row[3] workOrderCount,
     * row[4] totalLaborHours, row[5] totalMachineHours,
     * row[6] totalGoodQty, row[7] totalScrapQty,
     * row[8] totalOutputValue, row[9] noPriceCount
     */
    @Query(value = """
            SELECT
                shb.shb04                                       AS employeeNo,
                gen.gen02                                       AS employeeName,
                COUNT(shb.shb01)                                AS reportCount,
                COUNT(DISTINCT shb.shb05)                       AS workOrderCount,
                ROUND(SUM(NVL(shb.shb032, 0)) / 60, 2)         AS totalLaborHours,
                ROUND(SUM(NVL(shb.shb033, 0)) / 60, 2)         AS totalMachineHours,
                SUM(NVL(shb.shb111, 0))                         AS totalGoodQty,
                SUM(NVL(shb.shb112, 0))                         AS totalScrapQty,
                ROUND(SUM(
                    CASE
                        WHEN rt.reporter_count = 1 THEN
                            COALESCE(NULLIF(ecb1.ecb48, 0), NULLIF(ecb2.ecb48, 0), pmn.pmn31, 0) * NVL(shb.shb111, 0)
                        ELSE
                            CASE WHEN rt.total_time > 0 THEN
                                NVL(ecm.ecm311, 0)
                                * COALESCE(NULLIF(ecb1.ecb48, 0), NULLIF(ecb2.ecb48, 0), pmn.pmn31, 0)
                                * (NVL(shb.shb032, 0) / rt.total_time)
                            ELSE 0 END
                    END
                ), 2)                                           AS totalOutputValue,
                SUM(CASE WHEN COALESCE(NULLIF(ecb1.ecb48, 0), NULLIF(ecb2.ecb48, 0), pmn.pmn31) IS NULL
                         THEN 1 ELSE 0 END)                     AS noPriceCount
            FROM shb_file shb
            JOIN sfb_file sfb ON sfb.sfb01 = shb.shb05 AND sfb.sfbacti = 'Y'
            LEFT JOIN ima_file ima ON ima.ima01 = sfb.sfb05
            LEFT JOIN ecb_file ecb1 ON ecb1.ecb01 = ima.ima01
                                    AND ecb1.ecb02 = sfb.sfb06
                                    AND ecb1.ecb03 = shb.shb06
                                    AND ecb1.ecbacti = 'Y'
            LEFT JOIN ecb_file ecb2 ON ecb2.ecb01 = ima.ima571
                                    AND ecb2.ecb02 = sfb.sfb06
                                    AND ecb2.ecb03 = shb.shb06
                                    AND ecb2.ecbacti = 'Y'
                                    AND ecb1.ecb48 IS NULL
            LEFT JOIN ecm_file ecm ON ecm.ecm01 = shb.shb05
                                   AND ecm.ecm03 = shb.shb06
                                   AND ecm.ecmacti = 'Y'
            LEFT JOIN (
                SELECT n.pmn41, n.pmn46, MAX(n.pmn31) AS pmn31
                FROM pmn_file n
                JOIN pmm_file m ON m.pmm01 = n.pmn01 AND m.pmmacti = 'Y'
                WHERE n.pmn31 > 0
                GROUP BY n.pmn41, n.pmn46
            ) pmn ON pmn.pmn41 = shb.shb05 AND pmn.pmn46 = shb.shb06
            JOIN (
                SELECT
                    s.shb05,
                    s.shb06,
                    COUNT(s.shb01)         AS reporter_count,
                    SUM(NVL(s.shb032, 0))  AS total_time
                FROM shb_file s
                WHERE s.shbacti = 'Y'
                GROUP BY s.shb05, s.shb06
            ) rt ON rt.shb05 = shb.shb05 AND rt.shb06 = shb.shb06
            LEFT JOIN gen_file gen ON gen.gen01 = shb.shb04
            WHERE shb.shbacti = 'Y'
              AND shb.shb03 BETWEEN :startDate AND :endDate
              AND (:employeeNo IS NULL OR shb.shb04 = :employeeNo)
              AND gen.gen03 LIKE 'T234%'
            GROUP BY shb.shb04, gen.gen02, gen.gen03
            ORDER BY gen.gen03, shb.shb04
            """, nativeQuery = true)
    List<Object[]> findWorkerOutputSummary(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("employeeNo") String employeeNo);

    /**
     * ECB 訓練資料：所有有單價的製程資料（含標準工時特徵）
     * row[0] ecb01(料號), row[1] ecb06(作業編號), row[2] ecb48(加工單價),
     * row[3] ecb03(製程序號), row[4] ecb04(作業編號2/工作站),
     * row[5] ecb18(標準人工設置時間), row[6] ecb19(標準人工生產時間)
     */
    @Query(value = """
            SELECT ecb01, ecb06, ecb48, ecb03,
                   ecb04, NVL(ecb18, 0), NVL(ecb19, 0)
            FROM ecb_file
            WHERE ecbacti = 'Y' AND ecb48 > 0
            """, nativeQuery = true)
    List<Object[]> findEcbTrainingData();

    /**
     * 查詢無單價的 SHB 明細（用於 ML 預測）
     * row[0] shb05(工單), row[1] shb06(製程序), row[2] shb04(員工),
     * row[3] ima01(料號), row[4] ima571(主製程料號), row[5] shb081(作業編號),
     * row[6] shb111(良品數), row[7] shb032(工時)
     */
    @Query(value = """
            SELECT shb.shb05, shb.shb06, shb.shb04,
                   ima.ima01, ima.ima571, shb.shb081,
                   NVL(shb.shb111, 0), NVL(shb.shb032, 0)
            FROM shb_file shb
            JOIN sfb_file sfb ON sfb.sfb01 = shb.shb05 AND sfb.sfbacti = 'Y'
            LEFT JOIN ima_file ima ON ima.ima01 = sfb.sfb05
            LEFT JOIN ecb_file ecb1 ON ecb1.ecb01 = ima.ima01
                                    AND ecb1.ecb02 = sfb.sfb06
                                    AND ecb1.ecb03 = shb.shb06
                                    AND ecb1.ecbacti = 'Y'
            LEFT JOIN ecb_file ecb2 ON ecb2.ecb01 = ima.ima571
                                    AND ecb2.ecb02 = sfb.sfb06
                                    AND ecb2.ecb03 = shb.shb06
                                    AND ecb2.ecbacti = 'Y'
                                    AND ecb1.ecb48 IS NULL
            LEFT JOIN (
                SELECT n.pmn41, n.pmn46, MAX(n.pmn31) AS pmn31
                FROM pmn_file n
                JOIN pmm_file m ON m.pmm01 = n.pmn01 AND m.pmmacti = 'Y'
                WHERE n.pmn31 > 0
                GROUP BY n.pmn41, n.pmn46
            ) pmn ON pmn.pmn41 = shb.shb05 AND pmn.pmn46 = shb.shb06
            LEFT JOIN gen_file gen ON gen.gen01 = shb.shb04
            WHERE shb.shbacti = 'Y'
              AND shb.shb03 BETWEEN :startDate AND :endDate
              AND gen.gen03 LIKE 'T234%'
              AND COALESCE(NULLIF(ecb1.ecb48, 0), NULLIF(ecb2.ecb48, 0), pmn.pmn31) IS NULL
            """, nativeQuery = true)
    List<Object[]> findNoPriceSHB(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 個人報工明細（含單價來源）
     * row[0] shb01(移轉單號), row[1] shb05(工單), row[2] shb06(製程序),
     * row[3] shb03(完工日), row[4] shb081(作業編號), row[5] shb082(作業名稱),
     * row[6] ima01(料號), row[7] ima02(品名), row[8] ima571(主製程料號),
     * row[9] shb111(良品數), row[10] shb112(報廢數),
     * row[11] shb032(工時min), row[12] shb033(機時min),
     * row[13] ecb1_price(製程單價-自料號), row[14] ecb2_price(製程單價-主製程),
     * row[15] pmn_price(採購單價),
     * row[16] reporter_count, row[17] total_time, row[18] ecm311(良品轉出)
     */
    @Query(value = """
            SELECT shb.shb01, shb.shb05, shb.shb06,
                   shb.shb03, shb.shb081, shb.shb082,
                   ima.ima01, ima.ima02, ima.ima571,
                   NVL(shb.shb111, 0), NVL(shb.shb112, 0),
                   NVL(shb.shb032, 0), NVL(shb.shb033, 0),
                   ecb1.ecb48, ecb2.ecb48,
                   pmn.pmn31,
                   rt.reporter_count, rt.total_time, NVL(ecm.ecm311, 0)
            FROM shb_file shb
            JOIN sfb_file sfb ON sfb.sfb01 = shb.shb05 AND sfb.sfbacti = 'Y'
            LEFT JOIN ima_file ima ON ima.ima01 = sfb.sfb05
            LEFT JOIN ecb_file ecb1 ON ecb1.ecb01 = ima.ima01
                                    AND ecb1.ecb02 = sfb.sfb06
                                    AND ecb1.ecb03 = shb.shb06
                                    AND ecb1.ecbacti = 'Y'
            LEFT JOIN ecb_file ecb2 ON ecb2.ecb01 = ima.ima571
                                    AND ecb2.ecb02 = sfb.sfb06
                                    AND ecb2.ecb03 = shb.shb06
                                    AND ecb2.ecbacti = 'Y'
                                    AND ecb1.ecb48 IS NULL
            LEFT JOIN ecm_file ecm ON ecm.ecm01 = shb.shb05
                                   AND ecm.ecm03 = shb.shb06
                                   AND ecm.ecmacti = 'Y'
            LEFT JOIN (
                SELECT n.pmn41, n.pmn46, MAX(n.pmn31) AS pmn31
                FROM pmn_file n
                JOIN pmm_file m ON m.pmm01 = n.pmn01 AND m.pmmacti = 'Y'
                WHERE n.pmn31 > 0
                GROUP BY n.pmn41, n.pmn46
            ) pmn ON pmn.pmn41 = shb.shb05 AND pmn.pmn46 = shb.shb06
            JOIN (
                SELECT s.shb05, s.shb06,
                       COUNT(s.shb01) AS reporter_count,
                       SUM(NVL(s.shb032, 0)) AS total_time
                FROM shb_file s
                WHERE s.shbacti = 'Y'
                GROUP BY s.shb05, s.shb06
            ) rt ON rt.shb05 = shb.shb05 AND rt.shb06 = shb.shb06
            WHERE shb.shbacti = 'Y'
              AND shb.shb03 BETWEEN :startDate AND :endDate
              AND shb.shb04 = :employeeNo
            ORDER BY shb.shb03, shb.shb05, shb.shb06
            """, nativeQuery = true)
    List<Object[]> findWorkerOutputDetail(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("employeeNo") String employeeNo);

    /**
     * 組立課產值統計（按員工匯總）— 使用訂單單價
     *
     * 單價 = oeb13(訂單單價) × oea24(匯率)
     * 製造通知單(KSG)無單價時，找同料件最近一筆有單價的訂單
     * 排除作業 05-D5（噴漆）
     * 多人報工按工時分攤
     *
     * row[0] employeeNo, row[1] employeeName,
     * row[2] reportCount, row[3] workOrderCount,
     * row[4] totalLaborHours, row[5] totalMachineHours,
     * row[6] totalGoodQty, row[7] totalScrapQty,
     * row[8] totalOutputValue, row[9] noPriceCount
     */
    @Query(value = """
            SELECT
                shb.shb04                                       AS employeeNo,
                gen.gen02                                       AS employeeName,
                COUNT(shb.shb01)                                AS reportCount,
                COUNT(DISTINCT shb.shb05)                       AS workOrderCount,
                ROUND(SUM(NVL(shb.shb032, 0)) / 60, 2)         AS totalLaborHours,
                ROUND(SUM(NVL(shb.shb033, 0)) / 60, 2)         AS totalMachineHours,
                SUM(NVL(shb.shb111, 0))                         AS totalGoodQty,
                SUM(NVL(shb.shb112, 0))                         AS totalScrapQty,
                ROUND(SUM(
                    CASE
                        WHEN rt.reporter_count = 1 THEN
                            COALESCE(oeb.oeb13 * NVL(oea.oea24, 1), ksg_price.unit_price, 0) * NVL(shb.shb111, 0)
                        ELSE
                            CASE WHEN rt.total_time > 0 THEN
                                NVL(ecm.ecm311, 0)
                                * COALESCE(oeb.oeb13 * NVL(oea.oea24, 1), ksg_price.unit_price, 0)
                                * (NVL(shb.shb032, 0) / rt.total_time)
                            ELSE 0 END
                    END
                ), 2)                                           AS totalOutputValue,
                SUM(CASE WHEN COALESCE(oeb.oeb13, ksg_price.unit_price) IS NULL
                           OR COALESCE(oeb.oeb13, ksg_price.unit_price) = 0
                         THEN 1 ELSE 0 END)                     AS noPriceCount
            FROM shb_file shb
            JOIN sfb_file sfb ON sfb.sfb01 = shb.shb05 AND sfb.sfbacti = 'Y'
            LEFT JOIN oeb_file oeb ON oeb.oeb01 = sfb.sfb22 AND oeb.oeb03 = sfb.sfb221
            LEFT JOIN oea_file oea ON oea.oea01 = oeb.oeb01
            LEFT JOIN ksg_file ksg ON ksg.ksg01 = sfb.sfb91 AND ksg.ksg02 = sfb.sfb92
            LEFT JOIN (
                SELECT ob.oeb04, ob.oeb13 * NVL(oa.oea24, 1) AS unit_price, ob.oeb15,
                       ROW_NUMBER() OVER (PARTITION BY ob.oeb04 ORDER BY ob.oeb15 DESC) AS rn
                FROM oeb_file ob
                JOIN oea_file oa ON oa.oea01 = ob.oeb01
                WHERE ob.oeb13 > 0 AND SUBSTR(ob.oeb01, 2, 3) != '336'
            ) ksg_price ON ksg_price.oeb04 = sfb.sfb05 AND ksg_price.rn = 1
                        AND oeb.oeb01 IS NULL
            LEFT JOIN ecm_file ecm ON ecm.ecm01 = shb.shb05
                                   AND ecm.ecm03 = shb.shb06
                                   AND ecm.ecmacti = 'Y'
            JOIN (
                SELECT s.shb05, s.shb06,
                       COUNT(s.shb01) AS reporter_count,
                       SUM(NVL(s.shb032, 0)) AS total_time
                FROM shb_file s
                WHERE s.shbacti = 'Y'
                GROUP BY s.shb05, s.shb06
            ) rt ON rt.shb05 = shb.shb05 AND rt.shb06 = shb.shb06
            LEFT JOIN gen_file gen ON gen.gen01 = shb.shb04
            WHERE shb.shbacti = 'Y'
              AND shb.shb03 BETWEEN :startDate AND :endDate
              AND shb.shb081 != '05-D5'
              AND (:employeeNo IS NULL OR shb.shb04 = :employeeNo)
              AND gen.gen03 LIKE 'T235%'
            GROUP BY shb.shb04, gen.gen02, gen.gen03
            ORDER BY gen.gen03, shb.shb04
            """, nativeQuery = true)
    List<Object[]> findAssemblyOutputSummary(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("employeeNo") String employeeNo);

    /**
     * 組立課個人報工明細（含訂單單價 + KSG 備援）
     * row[0] shb01, row[1] shb05(工單), row[2] shb06(製程序),
     * row[3] shb03(完工日), row[4] shb081(作業編號), row[5] shb082(作業名稱),
     * row[6] ima01(料號), row[7] ima02(品名),
     * row[8] 訂單號(直接OEB或KSG), row[9] 項次,
     * row[10] 最終單價(含匯率), row[11] 單價來源(ORDER/KSG),
     * row[12] shb111(良品數), row[13] shb112(報廢數),
     * row[14] shb032(工時min), row[15] shb033(機時min),
     * row[16] reporter_count, row[17] total_time, row[18] ecm311,
     * row[19] occ01(客戶代號), row[20] occ02(客戶簡稱)
     */
    @Query(value = """
            SELECT shb.shb01, shb.shb05, shb.shb06,
                   shb.shb03, shb.shb081, shb.shb082,
                   ima.ima01, ima.ima02,
                   COALESCE(oeb.oeb01, ksg.ksg01),
                   COALESCE(oeb.oeb03, ksg.ksg02),
                   COALESCE(oeb.oeb13 * NVL(oea.oea24, 1), ksg_price.unit_price, 0),
                   CASE WHEN oeb.oeb01 IS NOT NULL THEN 'ORDER'
                        WHEN ksg_price.unit_price IS NOT NULL THEN 'KSG'
                        ELSE 'NONE' END,
                   NVL(shb.shb111, 0), NVL(shb.shb112, 0),
                   NVL(shb.shb032, 0), NVL(shb.shb033, 0),
                   rt.reporter_count, rt.total_time, NVL(ecm.ecm311, 0),
                   COALESCE(occ.occ01, ksg_occ.occ01),
                   COALESCE(occ.occ02, ksg_occ.occ02)
            FROM shb_file shb
            JOIN sfb_file sfb ON sfb.sfb01 = shb.shb05 AND sfb.sfbacti = 'Y'
            LEFT JOIN ima_file ima ON ima.ima01 = sfb.sfb05
            LEFT JOIN oeb_file oeb ON oeb.oeb01 = sfb.sfb22 AND oeb.oeb03 = sfb.sfb221
            LEFT JOIN oea_file oea ON oea.oea01 = oeb.oeb01
            LEFT JOIN occ_file occ ON occ.occ01 = oea.oea04
            LEFT JOIN ksg_file ksg ON ksg.ksg01 = sfb.sfb91 AND ksg.ksg02 = sfb.sfb92
            LEFT JOIN (
                SELECT ob.oeb04, ob.oeb13 * NVL(oa.oea24, 1) AS unit_price,
                       ob.oeb01 AS src_oeb01, oa.oea04 AS src_oea04,
                       ROW_NUMBER() OVER (PARTITION BY ob.oeb04 ORDER BY ob.oeb15 DESC) AS rn
                FROM oeb_file ob
                JOIN oea_file oa ON oa.oea01 = ob.oeb01
                WHERE ob.oeb13 > 0 AND SUBSTR(ob.oeb01, 2, 3) != '336'
            ) ksg_price ON ksg_price.oeb04 = sfb.sfb05 AND ksg_price.rn = 1
                        AND oeb.oeb01 IS NULL
            LEFT JOIN occ_file ksg_occ ON ksg_occ.occ01 = ksg_price.src_oea04
            LEFT JOIN ecm_file ecm ON ecm.ecm01 = shb.shb05
                                   AND ecm.ecm03 = shb.shb06
                                   AND ecm.ecmacti = 'Y'
            JOIN (
                SELECT s.shb05, s.shb06,
                       COUNT(s.shb01) AS reporter_count,
                       SUM(NVL(s.shb032, 0)) AS total_time
                FROM shb_file s
                WHERE s.shbacti = 'Y'
                GROUP BY s.shb05, s.shb06
            ) rt ON rt.shb05 = shb.shb05 AND rt.shb06 = shb.shb06
            WHERE shb.shbacti = 'Y'
              AND shb.shb03 BETWEEN :startDate AND :endDate
              AND shb.shb081 != '05-D5'
              AND shb.shb04 = :employeeNo
            ORDER BY shb.shb03, shb.shb05, shb.shb06
            """, nativeQuery = true)
    List<Object[]> findAssemblyOutputDetail(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("employeeNo") String employeeNo);

    /**
     * 加工課產能需求（按工作站組別/委外廠商彙總）
     *
     * WIP量 = ecm301+ecm302+ecm303-ecm311-ecm312-ecm313-ecm314-ecm316
     * 計畫量 = sfb08-ecm311-ecm312-ecm313-ecm314-ecm316
     * 指標 = WIP量×單價 + 計畫量×單價
     *
     * row[0] groupCode (eca03 部門代號 or ecm67 委外廠商)
     * row[1] groupName (gem02 部門名稱 or pmr02 廠商名稱)
     * row[2] groupType ('DEPT' or 'OUTSOURCE')
     * row[3] routingCount (製程數)
     * row[4] workOrderCount (工單數)
     * row[5] totalWipQty (WIP量合計)
     * row[6] totalPlanQty (計畫量合計)
     * row[7] totalWipValue (WIP金額)
     * row[8] totalPlanValue (計畫金額)
     */
    /**
     * 產能需求個別 ECM 明細（Java 端彙總 + 模糊匹配補價）
     * row[0] ecm01(工單), row[1] ecm03(製程序),
     * row[2] group_code, row[3] group_name, row[4] group_type,
     * row[5] wip_qty, row[6] plan_qty, row[7] unit_price(ECB),
     * row[8] ima01(料號), row[9] ima571, row[10] ecm04/shb081(作業編號)
     */
    @Query(value = """
            SELECT ecm.ecm01, TO_CHAR(ecm.ecm03),
                CASE WHEN NVL(ecm.ecm52, 'N') = 'Y'
                     THEN TO_CHAR(ecm.ecm67)
                     ELSE TO_CHAR(ecm.ecm06) END AS group_code,
                CASE WHEN NVL(ecm.ecm52, 'N') = 'Y'
                     THEN NVL(TO_CHAR(pmc.pmc03), TO_CHAR(ecm.ecm67))
                     ELSE TO_CHAR(eca.eca02) END AS group_name,
                CASE WHEN NVL(ecm.ecm52, 'N') = 'Y'
                     THEN 'OUTSOURCE'
                     ELSE 'DEPT' END AS group_type,
                GREATEST(NVL(ecm.ecm301,0)+NVL(ecm.ecm302,0)+NVL(ecm.ecm303,0)
                    -NVL(ecm.ecm311,0)-NVL(ecm.ecm312,0)-NVL(ecm.ecm313,0)
                    -NVL(ecm.ecm314,0)-NVL(ecm.ecm316,0), 0) AS wip_qty,
                GREATEST(
                    GREATEST(NVL(sfb.sfb08,0)-NVL(ecm.ecm311,0)-NVL(ecm.ecm312,0), 0)
                    - GREATEST(NVL(ecm.ecm301,0)+NVL(ecm.ecm302,0)+NVL(ecm.ecm303,0)
                    -NVL(ecm.ecm311,0)-NVL(ecm.ecm312,0)-NVL(ecm.ecm313,0)
                    -NVL(ecm.ecm314,0)-NVL(ecm.ecm316,0), 0)
                , 0) AS plan_qty,
                COALESCE(NULLIF(ecb1.ecb48, 0), NULLIF(ecb2.ecb48, 0), 0) AS unit_price,
                ima.ima01, ima.ima571, ecm.ecm04,
                CASE WHEN sfb.sfb22 IS NOT NULL OR p_sfb.sfb22 IS NOT NULL THEN 'ORDER'
                     WHEN sfb.sfb91 IS NOT NULL OR p_sfb.sfb91 IS NOT NULL THEN 'KSG'
                     ELSE 'WO' END AS source_type,
                COALESCE(oeb.ta_oeb15, oeb.oeb15, ksg.ksg04,
                    p_oeb.ta_oeb15, p_oeb.oeb15, p_ksg.ksg04,
                    sfb.sfb15) AS delivery_date
            FROM ecm_file ecm
            JOIN sfb_file sfb ON sfb.sfb01 = ecm.ecm01 AND sfb.sfbacti = 'Y'
                AND TRIM(TO_CHAR(sfb.sfb04)) IN ('1','2','3','4','5','6')
                AND sfb.sfb87 = 'Y'
            LEFT JOIN sfb_file p_sfb ON p_sfb.sfb01 = sfb.sfb86
            LEFT JOIN oeb_file oeb ON oeb.oeb01 = sfb.sfb22 AND oeb.oeb03 = sfb.sfb221
            LEFT JOIN ksg_file ksg ON ksg.ksg01 = sfb.sfb91 AND ksg.ksg02 = sfb.sfb92
            LEFT JOIN oeb_file p_oeb ON p_oeb.oeb01 = p_sfb.sfb22 AND p_oeb.oeb03 = p_sfb.sfb221
            LEFT JOIN ksg_file p_ksg ON p_ksg.ksg01 = p_sfb.sfb91 AND p_ksg.ksg02 = p_sfb.sfb92
            LEFT JOIN eca_file eca ON eca.eca01 = ecm.ecm06
            LEFT JOIN pmc_file pmc ON pmc.pmc01 = ecm.ecm67
            LEFT JOIN ima_file ima ON ima.ima01 = sfb.sfb05
            LEFT JOIN ecb_file ecb1 ON ecb1.ecb01 = ima.ima01
                AND ecb1.ecb02 = sfb.sfb06 AND ecb1.ecb03 = ecm.ecm03
                AND ecb1.ecbacti = 'Y'
            LEFT JOIN ecb_file ecb2 ON ecb2.ecb01 = ima.ima571
                AND ecb2.ecb02 = sfb.sfb06 AND ecb2.ecb03 = ecm.ecm03
                AND ecb2.ecbacti = 'Y' AND ecb1.ecb48 IS NULL
            WHERE ecm.ecmacti = 'Y'
                AND (eca.eca03 LIKE 'T234%' OR NVL(ecm.ecm52, 'N') = 'Y')
                AND NVL(sfb.sfb08,0) - NVL(ecm.ecm311,0) - NVL(ecm.ecm312,0) > 0
            """, nativeQuery = true)
    List<Object[]> findCapacityRows();

    /**
     * 產能資源清單（APS 用，不受工單活動篩選）：加工課全部工作站 + 曾用於委外製程的廠商
     * row[0] groupCode, row[1] groupName, row[2] groupType ('DEPT'/'OUTSOURCE'), row[3] deptCode
     */
    @Query(value = """
            SELECT eca.eca01 AS group_code, eca.eca02 AS group_name, 'DEPT' AS group_type, eca.eca03 AS dept_code
            FROM eca_file eca
            WHERE eca.eca03 LIKE 'T234%'
            UNION
            SELECT pmc.pmc01 AS group_code, NVL(pmc.pmc03, pmc.pmc01) AS group_name, 'OUTSOURCE' AS group_type, NULL AS dept_code
            FROM pmc_file pmc
            WHERE pmc.pmc01 IN (SELECT DISTINCT ecm67 FROM ecm_file WHERE ecm67 IS NOT NULL AND ecmacti = 'Y')
            """, nativeQuery = true)
    List<Object[]> findAllResourceGroups();

    /**
     * 排程甘特圖資料（APS Phase 1.2，現有 WIP 視覺化，非排程引擎輸出）
     * row[0] groupCode, row[1] groupType('DEPT'/'OUTSOURCE'), row[2] workOrderNo,
     * row[3] partNumber, row[4] partName, row[5] routingSeq, row[6] routingDesc,
     * row[7] startDate（第一站取sfb25實際開工日，後續站取前一站shb_file轉出時間）,
     * row[8] endDateProxy（交貨日代理值，COALESCE訂單/製造通知單交期，非真正工序完工日）,
     * row[9] wipQty, row[10] productionQty, row[11] workOrderStatus
     */
    @Query(value = """
            SELECT
                CASE WHEN NVL(ecm.ecm52, 'N') = 'Y' THEN TO_CHAR(ecm.ecm67) ELSE TO_CHAR(ecm.ecm06) END AS group_code,
                CASE WHEN NVL(ecm.ecm52, 'N') = 'Y' THEN 'OUTSOURCE' ELSE 'DEPT' END AS group_type,
                sfb.sfb01 AS work_order_no,
                sfb.sfb05 AS part_number,
                ima.ima02 AS part_name,
                TO_CHAR(ecm.ecm03) AS routing_seq,
                ecd.ecd02 AS routing_desc,
                CASE
                    WHEN ecm.ecm03 = (SELECT MIN(ecm03) FROM ecm_file WHERE ecm01 = sfb.sfb01 AND ecmacti = 'Y')
                    THEN sfb.sfb25
                    ELSE (
                        SELECT MAX(TO_DATE(TO_CHAR(shb03, 'YYYYMMDD') || NVL(shb031, '00:00'), 'YYYYMMDDHH24:MI'))
                        FROM shb_file
                        WHERE shb05 = sfb.sfb01
                          AND shb06 < ecm.ecm03
                          AND shb111 > 0
                          AND shbacti = 'Y'
                    )
                END AS start_date,
                COALESCE(oeb.ta_oeb15, oeb.oeb15, ksg.ksg04,
                    p_oeb.ta_oeb15, p_oeb.oeb15, p_ksg.ksg04,
                    sfb.sfb15) AS end_date_proxy,
                (NVL(ecm.ecm301,0) + NVL(ecm.ecm302,0) + NVL(ecm.ecm303,0)
                 - NVL(ecm.ecm311,0) - NVL(ecm.ecm312,0) - NVL(ecm.ecm313,0)
                 - NVL(ecm.ecm314,0) - NVL(ecm.ecm316,0)) AS wip_qty,
                sfb.sfb08 AS production_qty,
                sfb.sfb04 AS work_order_status
            FROM ecm_file ecm
            JOIN sfb_file sfb ON sfb.sfb01 = ecm.ecm01 AND sfb.sfbacti = 'Y'
            LEFT JOIN eca_file eca ON eca.eca01 = ecm.ecm06
            LEFT JOIN ima_file ima ON ima.ima01 = sfb.sfb05
            LEFT JOIN ecd_file ecd ON ecd.ecd01 = ecm.ecm04
            LEFT JOIN oeb_file oeb ON oeb.oeb01 = sfb.sfb22 AND oeb.oeb03 = sfb.sfb221
            LEFT JOIN ksg_file ksg ON ksg.ksg01 = sfb.sfb91 AND ksg.ksg02 = sfb.sfb92
            LEFT JOIN sfb_file p_sfb ON p_sfb.sfb01 = sfb.sfb86
            LEFT JOIN oeb_file p_oeb ON p_oeb.oeb01 = p_sfb.sfb22 AND p_oeb.oeb03 = p_sfb.sfb221
            LEFT JOIN ksg_file p_ksg ON p_ksg.ksg01 = p_sfb.sfb91 AND p_ksg.ksg02 = p_sfb.sfb92
            WHERE ecm.ecmacti = 'Y'
                AND sfb.sfb04 IN ('4','5','6')
                AND (eca.eca03 LIKE 'T234%' OR NVL(ecm.ecm52, 'N') = 'Y')
                AND (NVL(ecm.ecm301,0) + NVL(ecm.ecm302,0) + NVL(ecm.ecm303,0)
                     - NVL(ecm.ecm311,0) - NVL(ecm.ecm312,0) - NVL(ecm.ecm313,0)
                     - NVL(ecm.ecm314,0) - NVL(ecm.ecm316,0)) > 0
            ORDER BY sfb.sfb01, ecm.ecm03
            """, nativeQuery = true)
    List<Object[]> findScheduleBoardRows();

    /**
     * 產能需求明細（按工作站組別或委外廠商）
     * row[0] ecm01(工單), row[1] ecm03(製程序), row[2] ecm06(工作站),
     * row[3] sfb05(料號), row[4] ima02(品名),
     * row[5] sfb08(生產數量), row[6] wip_qty, row[7] plan_qty,
     * row[8] unit_price, row[9] wip_value, row[10] plan_value,
     * row[11] sfb04(狀態), row[12] ecm45(作業名稱),
     * row[13] oeb01(訂單號), row[14] ecm04(作業編號), row[15] ima571,
     * row[16] delivery_date, row[17] ecm67(委外廠商), row[18] source_type,
     * row[19] oea032(客戶簡稱)
     */
    @Query(value = """
            SELECT ecm.ecm01, ecm.ecm03, ecm.ecm06,
                sfb.sfb05, ima.ima02,
                NVL(sfb.sfb08, 0),
                GREATEST(NVL(ecm.ecm301,0)+NVL(ecm.ecm302,0)+NVL(ecm.ecm303,0)
                    -NVL(ecm.ecm311,0)-NVL(ecm.ecm312,0)-NVL(ecm.ecm313,0)
                    -NVL(ecm.ecm314,0)-NVL(ecm.ecm316,0), 0) AS wip_qty,
                GREATEST(
                    GREATEST(NVL(sfb.sfb08,0)-NVL(ecm.ecm311,0)-NVL(ecm.ecm312,0), 0)
                    - GREATEST(NVL(ecm.ecm301,0)+NVL(ecm.ecm302,0)+NVL(ecm.ecm303,0)
                    -NVL(ecm.ecm311,0)-NVL(ecm.ecm312,0)-NVL(ecm.ecm313,0)
                    -NVL(ecm.ecm314,0)-NVL(ecm.ecm316,0), 0)
                , 0) AS plan_qty,
                COALESCE(NULLIF(ecb1.ecb48, 0), NULLIF(ecb2.ecb48, 0), 0) AS unit_price,
                ROUND(GREATEST(NVL(ecm.ecm301,0)+NVL(ecm.ecm302,0)+NVL(ecm.ecm303,0)
                    -NVL(ecm.ecm311,0)-NVL(ecm.ecm312,0)-NVL(ecm.ecm313,0)
                    -NVL(ecm.ecm314,0)-NVL(ecm.ecm316,0), 0)
                    * COALESCE(NULLIF(ecb1.ecb48, 0), NULLIF(ecb2.ecb48, 0), 0), 0) AS wip_value,
                ROUND((GREATEST(NVL(sfb.sfb08,0)-NVL(ecm.ecm311,0)-NVL(ecm.ecm312,0), 0)
                    - GREATEST(NVL(ecm.ecm301,0)+NVL(ecm.ecm302,0)+NVL(ecm.ecm303,0)
                    -NVL(ecm.ecm311,0)-NVL(ecm.ecm312,0)-NVL(ecm.ecm313,0)
                    -NVL(ecm.ecm314,0)-NVL(ecm.ecm316,0), 0))
                    * COALESCE(NULLIF(ecb1.ecb48, 0), NULLIF(ecb2.ecb48, 0), 0), 0) AS plan_value,
                sfb.sfb04, ecm.ecm45,
                COALESCE(sfb.sfb22, p_sfb.sfb22),
                ecm.ecm04, ima.ima571,
                COALESCE(oeb.ta_oeb15, oeb.oeb15, ksg.ksg04,
                    p_oeb.ta_oeb15, p_oeb.oeb15, p_ksg.ksg04,
                    sfb.sfb15) AS delivery_date,
                ecm.ecm67,
                CASE WHEN sfb.sfb22 IS NOT NULL OR p_sfb.sfb22 IS NOT NULL THEN 'ORDER'
                     WHEN sfb.sfb91 IS NOT NULL OR p_sfb.sfb91 IS NOT NULL THEN 'KSG'
                     ELSE 'WO' END AS source_type,
                oea.oea032 AS customer_name
            FROM ecm_file ecm
            JOIN sfb_file sfb ON sfb.sfb01 = ecm.ecm01 AND sfb.sfbacti = 'Y'
                AND TRIM(TO_CHAR(sfb.sfb04)) IN ('1','2','3','4','5','6')
                AND sfb.sfb87 = 'Y'
            LEFT JOIN ima_file ima ON ima.ima01 = sfb.sfb05
            LEFT JOIN eca_file eca ON eca.eca01 = ecm.ecm06
            LEFT JOIN ecb_file ecb1 ON ecb1.ecb01 = ima.ima01
                AND ecb1.ecb02 = sfb.sfb06 AND ecb1.ecb03 = ecm.ecm03
                AND ecb1.ecbacti = 'Y'
            LEFT JOIN ecb_file ecb2 ON ecb2.ecb01 = ima.ima571
                AND ecb2.ecb02 = sfb.sfb06 AND ecb2.ecb03 = ecm.ecm03
                AND ecb2.ecbacti = 'Y' AND ecb1.ecb48 IS NULL
            LEFT JOIN oeb_file oeb ON oeb.oeb01 = sfb.sfb22 AND oeb.oeb03 = sfb.sfb221
            LEFT JOIN ksg_file ksg ON ksg.ksg01 = sfb.sfb91 AND ksg.ksg02 = sfb.sfb92
            LEFT JOIN sfb_file p_sfb ON p_sfb.sfb01 = sfb.sfb86
            LEFT JOIN oeb_file p_oeb ON p_oeb.oeb01 = p_sfb.sfb22 AND p_oeb.oeb03 = p_sfb.sfb221
            LEFT JOIN ksg_file p_ksg ON p_ksg.ksg01 = p_sfb.sfb91 AND p_ksg.ksg02 = p_sfb.sfb92
            LEFT JOIN oea_file oea ON TRIM(oea.oea01) = TRIM(COALESCE(sfb.sfb22, p_sfb.sfb22))
            WHERE ecm.ecmacti = 'Y'
                AND NVL(sfb.sfb08,0) - NVL(ecm.ecm311,0) - NVL(ecm.ecm312,0) > 0
                AND (
                    (TO_CHAR(ecm.ecm06) = :groupCode AND NVL(ecm.ecm52, 'N') != 'Y')
                    OR (TO_CHAR(ecm.ecm67) = :groupCode AND NVL(ecm.ecm52, 'N') = 'Y')
                )
            ORDER BY delivery_date NULLS LAST, ecm.ecm01, ecm.ecm03
            """, nativeQuery = true)
    List<Object[]> findCapacityDetail(@Param("groupCode") String groupCode);

    /**
     * 加工課各工作站歷史產值（按月彙總）
     * 用於計算每週產能上限
     * row[0] workstationCode (ecm06), row[1] workstationName (eca02),
     * row[2] outputMonth (TRUNC(shb03,'MM')), row[3] totalOutputValue
     */
    @Query(value = """
            SELECT
                TO_CHAR(ecm.ecm06)                              AS workstationCode,
                eca.eca02                                        AS workstationName,
                TRUNC(shb.shb03, 'MM')                           AS outputMonth,
                ROUND(SUM(
                    CASE
                        WHEN rt.reporter_count = 1 THEN
                            COALESCE(NULLIF(ecb1.ecb48, 0), NULLIF(ecb2.ecb48, 0), pmn.pmn31, 0)
                            * NVL(shb.shb111, 0)
                        ELSE
                            CASE WHEN rt.total_time > 0 THEN
                                NVL(ecm.ecm311, 0)
                                * COALESCE(NULLIF(ecb1.ecb48, 0), NULLIF(ecb2.ecb48, 0), pmn.pmn31, 0)
                                * (NVL(shb.shb032, 0) / rt.total_time)
                            ELSE 0 END
                    END
                ), 2)                                            AS totalOutputValue
            FROM shb_file shb
            JOIN ecm_file ecm ON ecm.ecm01 = shb.shb05
                              AND ecm.ecm03 = shb.shb06
                              AND ecm.ecmacti = 'Y'
            JOIN eca_file eca ON eca.eca01 = ecm.ecm06
            JOIN sfb_file sfb ON sfb.sfb01 = shb.shb05 AND sfb.sfbacti = 'Y'
            LEFT JOIN ima_file ima ON ima.ima01 = sfb.sfb05
            LEFT JOIN ecb_file ecb1 ON ecb1.ecb01 = ima.ima01
                                    AND ecb1.ecb02 = sfb.sfb06
                                    AND ecb1.ecb03 = shb.shb06
                                    AND ecb1.ecbacti = 'Y'
            LEFT JOIN ecb_file ecb2 ON ecb2.ecb01 = ima.ima571
                                    AND ecb2.ecb02 = sfb.sfb06
                                    AND ecb2.ecb03 = shb.shb06
                                    AND ecb2.ecbacti = 'Y'
                                    AND ecb1.ecb48 IS NULL
            LEFT JOIN (
                SELECT n.pmn41, n.pmn46, MAX(n.pmn31) AS pmn31
                FROM pmn_file n
                JOIN pmm_file m ON m.pmm01 = n.pmn01 AND m.pmmacti = 'Y'
                WHERE n.pmn31 > 0
                GROUP BY n.pmn41, n.pmn46
            ) pmn ON pmn.pmn41 = shb.shb05 AND pmn.pmn46 = shb.shb06
            JOIN (
                SELECT s.shb05, s.shb06,
                       COUNT(s.shb01)         AS reporter_count,
                       SUM(NVL(s.shb032, 0))  AS total_time
                FROM shb_file s
                WHERE s.shbacti = 'Y'
                GROUP BY s.shb05, s.shb06
            ) rt ON rt.shb05 = shb.shb05 AND rt.shb06 = shb.shb06
            WHERE shb.shbacti = 'Y'
              AND shb.shb03 BETWEEN :startDate AND :endDate
              AND eca.eca03 LIKE 'T234%'
            GROUP BY TO_CHAR(ecm.ecm06), eca.eca02, TRUNC(shb.shb03, 'MM')
            ORDER BY TO_CHAR(ecm.ecm06), TRUNC(shb.shb03, 'MM')
            """, nativeQuery = true)
    List<Object[]> findWorkstationOutputHistory(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}
