package com.jfc.rdb.tiptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.SfbFile;
/**
 * BOM 樹狀結構查詢 Repository
 */
@Repository
public interface BomTreeRepository extends JpaRepository<SfbFile, String> {
    
    /**
     * 查詢工單基本資料 (用於樹狀圖根節點)
     */
    @Query(value = """
            SELECT 
                a.sfb01           AS workOrderNo,
                a.sfb04           AS workOrderStatus,
                a.sfb05           AS partNumber,
                b.ima02           AS partName,
                b.ima021          AS partSpec,
                NVL(a.sfb08, 0)   AS productionQty,
                NVL(a.sfb09, 0)   AS completedQty,
                NVL(a.sfb12, 0)   AS scrapQty,
                (NVL(a.sfb08,0) - NVL(a.sfb09,0) - NVL(a.sfb12,0)) AS wipQty,
                a.sfb13           AS plannedStartDate,
                a.sfb15           AS plannedEndDate,
                a.sfb25           AS actualStartDate,
                b.ima06           AS unit,
                a.sfb22 AS salesOrderNo,
                a.sfb221 AS salesOrderSeq		-- row[14]
            FROM sfb_file a 
            LEFT JOIN ima_file b ON a.sfb05 = b.ima01 
            WHERE a.sfb01 = :workOrderNo
            """, nativeQuery = true)
    List<Object[]> findWorkOrderForBomTree(@Param("workOrderNo") String workOrderNo);
    
    /**
     * 模糊搜尋工單
     */
    @Query(value = """
            SELECT 
                a.sfb01           AS workOrderNo,
                a.sfb04           AS workOrderStatus,
                a.sfb05           AS partNumber,
                b.ima02           AS partName,
                b.ima021          AS partSpec,
                NVL(a.sfb08, 0)   AS productionQty,
                NVL(a.sfb09, 0)   AS completedQty,
                (NVL(a.sfb08,0) - NVL(a.sfb09,0) - NVL(a.sfb12,0)) AS wipQty,
                a.sfb13           AS plannedStartDate,
                a.sfb15           AS plannedEndDate
            FROM sfb_file a 
            LEFT JOIN ima_file b ON a.sfb05 = b.ima01 
            WHERE a.sfb01 LIKE :keyword
              AND a.sfbacti = 'Y'
            ORDER BY a.sfb01 DESC
            FETCH FIRST 100 ROWS ONLY
            """, nativeQuery = true)
    List<Object[]> searchWorkOrders(@Param("keyword") String keyword);
    
    /**
     * 查詢工單下階料/BOM (包含來源判斷資訊)
     */
    @Query(value = """
            SELECT 
                b.sfa01           AS workOrderNo,		--sfa01		工單編號	工單編號 sfb01
                b.sfa03           AS componentPartNo,	--sfa03		料件編號	料件編號 ima01儲存該工單所屬下階料件編號，應為被發放投入生產的料件編號
                c.ima02           AS componentPartName,	--ima02		品名	品名規格描述該料件的品名規格, 如有需要進一步描述, 則可利用品名規格額外說明資料檔記錄
                c.ima021          AS componentPartSpec,	--ima021		規格	規格                   (97/08/18
                NVL(b.sfa05, 0)   AS requiredQty,		--sfa05		應發數量	應發數量儲存該工單備料料件，使用者經過修改後，決定的備料量；開始時，應與原發數量相同
                NVL(b.sfa06, 0)   AS issuedQty,			--sfa06		已發數量	已發數量儲存該工單備料料件，已經被發料的數量；
                (NVL(b.sfa05, 0) - NVL(b.sfa06, 0)) AS pendingQty,
                NVL(d.stockQty, 0) AS stockQty,
                b.sfa12           AS issueUnit,			--sfa12		發料單位	發料單位儲存該工單備料料件的發料單位，將由產品產品結構產生而來
                c.ima08           AS partAttribute,		--ima08		來源碼	來源碼說明料件屬性, 用以勾劃出不同歸屬特性的料件, 以便在料件庫存異動及物料需求 ..等作業時, 提供作業管制方式或限制正確值 C/T/D/A/M/P/X/K/U/V/R/Z/S預設值 依分群碼類別預設 或 空白, 且結果需有一種來源碼C: 規格組件 (Configurable Product)T: 最後規格料件 (FAS Product)D: 特性料件 (Feature)A: 族群料件 (Family Product)M: 自製料件 (Make Product)P: 採購料件 (Purchase)X: 虛擬料件 (Phantom)K: 配件虛擬料件 (Kit)U: 自製大宗料件V: 採購大宗料件R: 在製途料件 (Routable)Z: 雜項料件 (Miscellaneous)S: 廠外加工料件 (Subcontract)
                e.sfb01           AS childWorkOrderNo,
                e.sfb08           AS childProductionQty,
                e.sfb09           AS childCompletedQty,
                e.sfb13           AS childPlannedStart,
                e.sfb15           AS childPlannedEnd,
                f.pmn01           AS purchaseOrderNo,	--pmn01		採購單號	採購單號 pmm01
                f.pmn33           AS poDeliveryDate		--pmn33		原始交貨日期	原始交貨日期 預定供應廠商發貨日期 本欄位為輸入時的原始確定的交貨日期
            FROM sfa_file b 
            LEFT JOIN ima_file c ON b.sfa03 = c.ima01
            LEFT JOIN (
                SELECT img01, SUM(NVL(img10, 0)) AS stockQty --img01		料件編號	料件編號 儲存存放地點的料件編號
                FROM img_file -- 庫存資料明細檔(img_file)
                WHERE img23 = 'Y'	--img23		是否為可用倉儲	是否為可用倉儲 提供使用者於倉庫管理時區別出可用料件 與不可用料件或報廢等倉儲管理　　　　　 正確值 'Y' 或 'N' Y: 可用倉儲 N: 不可用倉儲
                  AND img10 > 0		--img10		庫存數量	庫存數量 儲存料件在某倉儲下之庫存總數量
                  AND img02 NOT IN ('AM', 'AK', 'H')	--img02		倉庫編號	倉庫編號 儲存料件所在之倉庫編號
                  AND LENGTH(img04) < 2	--img04		批號	批號(進貨) 儲存料件所在之進貨批號
                GROUP BY img01
            ) d ON b.sfa03 = d.img01 
            LEFT JOIN sfb_file e ON b.sfa01 = e.sfb89	 --sfb89	varchar2(20)	上階工單單號
            	AND e.sfb05 = b.sfa03 --sfb05		料件編號	料件編號   ima01 儲存該工單將投入生產料件
                AND e.sfbacti = 'Y'	--sfbacti		資料有效碼	資料有效碼 系統維護
                AND e.sfb87 NOT IN ('X')	--sfb87		確認否	確認否(Y/N/X)
            LEFT JOIN (
            	select * from (
                SELECT pmn04, pmn01, pmn33,
            	ROW_NUMBER() OVER (PARTITION BY pmn04 ORDER BY pmn33 ASC) as rn                
                FROM pmn_file 	--採購單單身(pmn_file)
                LEFT JOIN pmm_file ON pmn01 = pmm01
                LEFT JOIN pml_file ON pmn24 = pml01 and pmn25 = pml02	--請購單單身(pml_file)
                WHERE pmm18 = 'Y'		--確認碼(Y/N/X) X.作廢         (97/07)
              	  AND pmmacti = 'Y'
              	  AND pml24 = :salesOrderNo AND pml25 =:salesOrderSeq 
                -- ORDER BY pmn13
                -- FETCH FIRST 1 ROWS ONLY
                ) where rn = 1
            ) f ON b.sfa03 = f.pmn04	--pmn04		料件編號	料件編號 ima01 採購料件編號 使用者有輸入詢價單號時所輸入的料件需存在 於所輸入的詢價單號
            WHERE b.sfa01 = :workOrderNo
              AND b.sfaacti = 'Y'
            ORDER BY b.sfa03
            """, nativeQuery = true)
    List<Object[]> findBomChildrenByWorkOrder(
    		@Param("workOrderNo") String workOrderNo, 
    		@Param("salesOrderNo") String salesOrderNo, 
    		@Param("salesOrderSeq") Integer salesOrderSeq);
    
    /**
     * 查詢工單製程進度 (用於甘特圖)
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
                NVL(c.ecm311, 0)  AS goodTransferOut,
                (NVL(c.ecm301,0) + NVL(c.ecm302,0) + NVL(c.ecm303,0) 
                 - NVL(c.ecm311,0) - NVL(c.ecm312,0) - NVL(c.ecm313,0) 
                 - NVL(c.ecm314,0) - NVL(c.ecm316,0)) AS routingWip,
                c.ecm08           AS plannedStartDate,
                c.ecm09           AS plannedEndDate,
                e.actualStartDate AS actualStartDate,
                e.actualEndDate   AS actualEndDate
            FROM ecm_file c
            LEFT JOIN eca_file d ON c.ecm06 = d.eca01
            LEFT JOIN (
                SELECT 
                    shb05, shb06,
                    MIN(shb02) AS actualStartDate,
                    MAX(shb03) AS actualEndDate
                FROM shb_file
                WHERE shbacti = 'Y'
                GROUP BY shb05, shb06
            ) e ON c.ecm01 = e.shb05 AND c.ecm03 = e.shb06
            WHERE c.ecm01 = :workOrderNo
              AND c.ecmacti = 'Y'
            ORDER BY c.ecm03
            """, nativeQuery = true)
    List<Object[]> findRoutingsForGantt(@Param("workOrderNo") String workOrderNo);
    
    /**
     * 查詢採購單資訊 (用於採購類型節點的甘特圖)
     * 交貨量 =>未交=訂購  - (已交 - 驗退量)       (pmn20 - (pmn50 - pmn55) 報表或查詢作業的已交量= 交貨量-驗退量
     */
    @Query(value = """
    		select * from (
            SELECT 
                a.pmn01           AS purchaseOrderNo,
                a.pmn03           AS purchaseSeq,
                a.pmn04           AS partNo,
                b.ima02           AS partName,
                NVL(a.pmn20, 0)   AS orderQty,		--採購量
                NVL(a.pmn50, 0)   AS receivedQty, 	--交貨量
                (NVL(a.pmn20, 0) - (NVL(a.pmn50, 0) - NVL(a.pmn55, 0))) AS pendingQty,
                c.pmm04           AS orderDate,
                a.pmn33           AS deliveryDate,
                a.pmn36           AS confirmDeliveryDate,
                c.pmm09           AS vendorNo,
                d.pmc03           AS vendorName
            FROM pmn_file a
            LEFT JOIN ima_file b ON a.pmn04 = b.ima01
            LEFT JOIN pmm_file c ON a.pmn01 = c.pmm01
            LEFT JOIN pmc_file d ON c.pmm09 = d.pmc01
            LEFT JOIN pml_file e ON a.pmn24 = e.pml01 and a.pmn25 = e.pml02	--請購單
            WHERE a.pmn04 = :partNo
              AND c.pmm18 = 'Y'		--確認碼(Y/N/X) X.作廢         (97/07)
              AND c.pmmacti = 'Y'
              --AND (a.pob12 - NVL(a.pob17, 0)) > 0
              AND e.pml24 = :salesOrderNo AND e.pml25 =:salesOrderSeq 
             ORDER BY a.pmn33
            -- FETCH FIRST 1 ROWS ONLY
            ) where ROWNUM <= 1
            """, nativeQuery = true)
    List<Object[]> findPurchaseOrderByPart(
    		@Param("partNo") String partNo, 
    		@Param("salesOrderNo") String salesOrderNo, 
    		@Param("salesOrderSeq") Integer salesOrderSeq);
    
    /**
     * 批次查詢多個料件的庫存
     */
    @Query(value = """
            SELECT 
                img01 AS partNo,
                SUM(NVL(img10, 0)) AS stockQty
            FROM img_file
            WHERE img01 IN :partNos
              AND img23 = 'Y'
              AND img10 > 0
              AND img02 NOT IN ('AM', 'AK', 'H')
              AND LENGTH(img04) < 2 --無批號
            GROUP BY img01
            """, nativeQuery = true)
    List<Object[]> findStockByPartNos(@Param("partNos") List<String> partNos);
    
    /**
     * 查詢料件屬性 (判斷製造/採購)
     * ima08	varchar2(1)	來源碼	來源碼說明料件屬性, 用以勾劃出不同歸屬特性的料件, 以便在料件庫存異動及物料需求 ..等作業時, 
     * 提供作業管制方式或限制正確值 C/T/D/A/M/P/X/K/U/V/R/Z/S預設值 依分群碼類別預設 或 空白, 且結果需有一種來源碼
     * C: 規格組件 (Configurable Product)
     * T: 最後規格料件 (FAS Product)
     * D: 特性料件 (Feature)A: 族群料件 (Family Product)
     * M: 自製料件 (Make Product)
     * P: 採購料件 (Purchase)
     * X: 虛擬料件 (Phantom)K: 配件虛擬料件 (Kit)U: 自製大宗料件V: 採購大宗料件R: 在製途料件 (Routable)Z: 雜項料件 (Miscellaneous)S: 廠外加工料件 (Subcontract)

     */
    @Query(value = """
            SELECT 
                ima01 AS partNo,
                ima08 AS partAttribute, -- M: 自製料件 (Make Product)/P: 採購料件 (Purchase)
                ima02 AS partName,
                ima021 AS partSpec,
                ima06 AS unit
            FROM ima_file
            WHERE ima01 = :partNo
            """, nativeQuery = true)
    List<Object[]> findPartAttribute(@Param("partNo") String partNo);
    
    /**
     * 遞迴查詢下階工單 (用於展開製造類型節點)
     * sfb04			工單狀態 儲存該工單目前處理階段狀況 正確值 1/2/3/4/5/6/7/8
     *  1: 確認生產工單(firm plan)
     *  2: 工單已發放,料表尚未列印
     *  3: 工單已發放,料表已列印
     *  4: 工單已發料 
     *  5: 在製過程中
     *  6: 工單已完工,進入F.Q.C 
     *  7: 完工入庫 8: 結案 
		sfb87	varchar2(1)	確認否	確認否(Y/N/X)

     */
    @Query(value = """
    		select * from (
            SELECT 
                a.sfb01           AS workOrderNo,
                a.sfb04           AS workOrderStatus,
                a.sfb05           AS partNumber,
                b.ima02           AS partName,
                b.ima021          AS partSpec,
                NVL(a.sfb08, 0)   AS productionQty,
                NVL(a.sfb09, 0)   AS completedQty,
                (NVL(a.sfb08,0) - NVL(a.sfb09,0) - NVL(a.sfb12,0)) AS wipQty,
                a.sfb13           AS plannedStartDate,
                a.sfb15           AS plannedEndDate,
                b.ima06           AS unit
            FROM sfb_file a 
            LEFT JOIN ima_file b ON a.sfb05 = b.ima01 
            WHERE a.sfb05 = :partNo
              AND a.sfbacti = 'Y'
              AND a.sfb87 NOT IN ('X')
              AND a.sfb22 = :salesOrderNo
            ORDER BY a.sfb01 DESC
            --FETCH FIRST 1 ROWS ONLY
            )where ROWNUM <= 1
            """, nativeQuery = true)
    List<Object[]> findChildWorkOrder(
            @Param("partNo") String partNo, 
            @Param("salesOrderNo") String salesOrderNo);
    
    
}
