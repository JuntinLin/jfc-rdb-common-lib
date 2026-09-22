package com.jfc.rdb.tiptop.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.ImaFile;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

@Repository
//public interface ImaRepository extends CrudRepository<ImaFile, String> {
public interface ImaRepository extends JpaRepository<ImaFile, String> {
	Optional<ImaFile> findById(String ima01);
	
	java.util.List<ImaFile> findByIma01Containing(String Ima01);
	
	//依據品名ima02 規格ima021查詢
	@Query("SELECT i FROM ImaFile i WHERE i.ima02 LIKE %?1% or i.ima021 LIKE %?1%")
	java.util.List<ImaFile> findByIma02Like(String title);
	
	@Query("""
			SELECT i from ImaFile i
			LEFT JOIN i.customer c
			WHERE  (:safeStock = 'all' OR 
			       (:safeStock = 'equalOne' AND i.ima27 = 1) OR 
			       (:safeStock = 'overOne' AND i.ima27 > 1))
			     AND (:groupCode IS NULL OR i.ima06 = :groupCode)
			     AND (:partNumber IS NULL OR i.ima01 LIKE CONCAT('%', :partNumber, '%'))
			     AND (:custCode IS NULL OR c.occ01 = :custCode)
			 ORDER BY i.ima01
			""")
	java.util.List<ImaFile> findByCriteria(
			@Param("safeStock") String safeStock,
	        @Param("groupCode") String groupCode,
	        @Param("partNumber") String partNumber,
	        @Param("custCode") String custCode);
	
	// Find items by name (partial match)
    List<ImaFile> findByIma02ContainingIgnoreCase(String itemName);
    
    // Find items by specification (partial match)
    List<ImaFile> findByIma021ContainingIgnoreCase(String itemSpec);
    
 // Find items by ima09 and ima10
    @Query("SELECT i FROM ImaFile i WHERE (:ima09 IS NULL OR i.ima09 = :ima09) AND (:ima10 IS NULL OR i.ima10 = :ima10)")
    java.util.List<ImaFile> findByIma09AndIma10(@Param("ima09") String ima09, @Param("ima10") String ima10);

    // Count items with specific type and line
    @Query("SELECT COUNT(i) FROM ImaFile i WHERE i.ima09 = :type AND i.ima10 = :line")
    long countByIma09AndIma10(String type, String line);

    /**
     * RFQ③ 歷史相似品比對——候選集：缸類分群碼白名單(ima10)，**全歷史（含客製 ima09=V/N/K）**。
     * 見 docs/RFQ/③施工brief_Forge提案定案.md（Nimbus 已核准，③ 原始設計）；
     * RFQ⑬ 修正：原本多加了 `ima09 IN ('S','T')` 只收標準品，君帆是客製廠，
     * 這條件會把 95%+ 的真實歷史品（客製 V）濾掉，候選池從應有的萬筆級別
     * 收斂到 74 筆。客製品的實績成本一樣真實、一樣可當相似度錨點，
     * S/N/V 只在 DTO 層當顯示標籤（見 SimilarItemDTO.standardClass），
     * 不在候選撈取這層過濾。只排「壞資料」（停用/作廢，ima_file 無明確狀態
     * 欄可用時不加此條件），不排客製。見 docs/RFQ/⑬候選池放寬含客製_task_brief.md。
     */
    @Query("SELECT i FROM ImaFile i WHERE i.ima10 IN :ima10Whitelist AND (i.imaacti IS NULL OR i.imaacti = 'Y')")
    List<ImaFile> findSimilarItemCandidates(@Param("ima10Whitelist") List<String> ima10Whitelist);

    /**
     * RFQ⑪ Phase 1 軸心/活塞桿——候選集：料號字首 2S016、2N016、2V016 開頭（SOP 確認：2=半成品
     * 產品大類，S/N/V=其它分群碼一：標準1/便品修改/特殊），全歷史（含客製，比照 ⑬，不再
     * 只收標準），型別看 ima02 品名前綴（AIR_/OIL_/HYD_），不在這層過濾。
     * 見 docs/RFQ/⑪-Phase1軸心_build_task_brief.md。
     */
    @Query("SELECT i FROM ImaFile i WHERE (i.ima01 LIKE '2S016%' OR i.ima01 LIKE '2N016%' OR i.ima01 LIKE '2V016%') "
            + "AND (i.imaacti IS NULL OR i.imaacti = 'Y')")
    List<ImaFile> findAxleCandidates();

    /**
     * RFQ⑤-A 市購件 price book 推導 job——市購品（ima08='P'）一階 BOM 範圍（AIR-S/OIL-S 全品線）。
     * 見 docs/RFQ/Forge回覆_⑤-A市購件規格樣態探勘.md：市購旗標用 ima08='P'（非 ima10 範圍，
     * 那混了自製結構件）；規格讀 ima021（非 ima02，後者無尺寸）；ima06 為次分類輔助。
     * row[0] partNo(ima01), row[1] name(ima02), row[2] spec(ima021), row[3] subCategory(ima06)
     */
    @Query(value = """
            SELECT DISTINCT i.ima01, i.ima02, i.ima021, i.ima06
            FROM bmb_file b
            JOIN ima_file m ON m.ima01 = b.bmb01
            JOIN ima_file i ON i.ima01 = b.bmb03
            WHERE m.ima10 IN ('140 AIR-S','130 OIL-S')
            AND (b.bmb05 IS NULL OR b.bmb05 >= SYSDATE)
            AND i.ima08 = 'P'
            """, nativeQuery = true)
    List<Object[]> findPurchasedItemsForPriceBook();

    /**
     * RFQ⑤-C 原料 NT/kg price book 推導 job——秤重原料件範圍。
     * 見 docs/RFQ/Forge回覆_⑤-C材質NTperKG前置探勘.md：`ima10='300'`本身不乾淨（混軸承/
     * 油封修理包/油壓配管等市購件），須另加 `ima06` 白名單只留真正秤重原料（銅棒/鋼板/
     * 鋁棒/鋁管/不鏽鋼圓棒等）。
     * row[0] partNo(ima01), row[1] name(ima02), row[2] spec(ima021), row[3] subCategory(ima06)
     */
    @Query(value = """
            SELECT i.ima01, i.ima02, i.ima021, i.ima06
            FROM ima_file i
            WHERE i.ima08 = 'P' AND i.ima10 = '300'
            AND i.ima06 IN ('301','302','303','305','307','308','310','312','315')
            """, nativeQuery = true)
    List<Object[]> findRawMaterialItemsForPriceBook();

}


