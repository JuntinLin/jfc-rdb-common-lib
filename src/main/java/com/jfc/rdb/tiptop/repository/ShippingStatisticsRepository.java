package com.jfc.rdb.tiptop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.OgaFile;

/**
 * 物流課出貨統計 KPI 查詢（實際出貨單口徑：OGA_FILE/OGB_FILE，非訂單口徑 OEA_FILE/OEB_FILE）
 */
@Repository
public interface ShippingStatisticsRepository extends JpaRepository<OgaFile, String> {

    /**
     * 依年月/國內外/區域分組的出貨金額統計
     * row[0]=ym(YYYY-MM), row[1]=country(國內/國外), row[2]=area(北區/中區/南區/''), row[3]=totalAmount
     */
    @Query(value = """
        SELECT
            TO_CHAR(oga.oga02, 'YYYY-MM') AS ym,
            CASE WHEN occ.occ21 = 'TW' THEN '國內' ELSE '國外' END AS country,
            CASE
                WHEN occ.occ21 = 'TW' AND occ.occ22 IN ('TW-01','TW-04','TW-05','TW-06','TW-07','TW-08','TW-09','TW-22') THEN '北區'
                WHEN occ.occ21 = 'TW' AND occ.occ22 IN ('TW-02','TW-10','TW-11','TW-12','TW-13','TW-14') THEN '中區'
                WHEN occ.occ21 = 'TW' AND occ.occ22 IN ('TW-03','TW-15','TW-16','TW-17','TW-18','TW-19','TW-20','TW-21','TW-23','TW-24','TW-25') THEN '南區'
                WHEN occ.occ21 = 'TW' THEN '南區'
                ELSE ''
            END AS area,
            NVL(SUM(oga.oga24 * ogb.ogb14), 0) AS totalAmount
        FROM OGA_FILE oga
        LEFT OUTER JOIN OGB_FILE ogb ON ogb.ogb01 = oga.oga01
        LEFT OUTER JOIN OCC_FILE occ ON occ.occ01 = oga.oga03
        WHERE oga.ogaconf = 'Y' AND oga.ogapost = 'Y' AND oga.oga09 = '2'
          AND oga.oga02 BETWEEN :startDate AND :endDate
        GROUP BY
            TO_CHAR(oga.oga02, 'YYYY-MM'),
            CASE WHEN occ.occ21 = 'TW' THEN '國內' ELSE '國外' END,
            CASE
                WHEN occ.occ21 = 'TW' AND occ.occ22 IN ('TW-01','TW-04','TW-05','TW-06','TW-07','TW-08','TW-09','TW-22') THEN '北區'
                WHEN occ.occ21 = 'TW' AND occ.occ22 IN ('TW-02','TW-10','TW-11','TW-12','TW-13','TW-14') THEN '中區'
                WHEN occ.occ21 = 'TW' AND occ.occ22 IN ('TW-03','TW-15','TW-16','TW-17','TW-18','TW-19','TW-20','TW-21','TW-23','TW-24','TW-25') THEN '南區'
                WHEN occ.occ21 = 'TW' THEN '南區'
                ELSE ''
            END
        ORDER BY 2, 3, 1
        """, nativeQuery = true)
    List<Object[]> findMonthlyShipmentStatsByCountryArea(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}
