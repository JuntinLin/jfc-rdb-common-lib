package com.jfc.rdb.tiptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.BmaFile;
import com.jfc.rdb.tiptop.entity.BmaFilePK;
@Repository
public interface BmaRepository extends JpaRepository<BmaFile, BmaFilePK> {
	// Find BmaFile entries by master item code
    List<BmaFile> findByIdBma01(String masterItemCode);
    
    // Find BmaFile entries by characteristic code
    List<BmaFile> findByIdBma06(String characteristicCode);
    
    // Find BmaFile entries by both master item code and characteristic code
    BmaFile findByIdBma01AndIdBma06(String masterItemCode, String characteristicCode);
    
    //countByIma09AndIma10
 // Count BmaFile entries where associated ImaFile has specific type and line
    @Query("SELECT COUNT(b) FROM BmaFile b " +
           "LEFT JOIN ImaFile i ON i.ima01 = b.id.bma01 " +
           "WHERE i.ima09 = :type AND i.ima10 = :line")
    long countByIma09AndIma10(String type, String line);
        
    // Find BmaFile entries where associated ImaFile has specific type and line
    @Query("SELECT b FROM BmaFile b " +
           "LEFT JOIN ImaFile i ON i.ima01 = b.id.bma01 " +
           "WHERE i.ima09 = :type AND i.ima10 = :line")
    List<BmaFile> findByIma09AndIma10(String type, String line);
    
 // ==================== New Methods for Knowledge Base Support ====================
    
    /**
     * Find all distinct master item codes from BMA table
     * This method is required by BomOwlExportService.getAllMasterItemCodes()
     * 
     * @return List of distinct master item codes
     */
    @Query("SELECT DISTINCT bma.id.bma01 FROM BmaFile bma ORDER BY bma.id.bma01")
    List<String> findDistinctMasterItemCodes();
    
    /**
     * Find all distinct master item codes with specific criteria
     * Used to filter master items by type and category
     * 
     * @param ima09 Item type (usually "S")
     * @param ima10 Item category (usually "130 HC") 
     * @return List of distinct master item codes matching criteria
     */
    @Query("SELECT DISTINCT bma.id.bma01 FROM BmaFile bma " +
           "LEFT JOIN ImaFile ima ON bma.id.bma01 = ima.ima01 " +
           "WHERE ima.ima09 = :ima09 AND ima.ima10 = :ima10 " +
           "ORDER BY bma.id.bma01")
    List<String> findDistinctMasterItemCodesByType(String ima09, String ima10);
    
    /**
     * Count total number of distinct master items
     * 
     * @return Count of distinct master items
     */
    @Query("SELECT COUNT(DISTINCT bma.id.bma01) FROM BmaFile bma")
    long countDistinctMasterItems();
    
    /**
     * Count distinct master items by type
     * 
     * @param ima09 Item type
     * @param ima10 Item category
     * @return Count of distinct master items matching criteria
     */
    @Query("SELECT COUNT(DISTINCT bma.id.bma01) FROM BmaFile bma " +
           "LEFT JOIN ImaFile ima ON bma.id.bma01 = ima.ima01 " +
           "WHERE ima.ima09 = :ima09 AND ima.ima10 = :ima10")
    long countDistinctMasterItemsByType(String ima09, String ima10);
    
    /**
     * Find master items that are hydraulic cylinders (codes starting with 3 or 4)
     * 
     * @return List of hydraulic cylinder master item codes
     */
    @Query("SELECT DISTINCT bma.id.bma01 FROM BmaFile bma " +
           "LEFT JOIN ImaFile ima ON bma.id.bma01 = ima.ima01 " +
           "WHERE ima.ima09 = 'S' AND ima.ima10 = '130 HC' " +
           "AND (bma.id.bma01 LIKE '3%' OR bma.id.bma01 LIKE '4%') " +
           "ORDER BY bma.id.bma01")
    List<String> findHydraulicCylinderMasterItems();
    
    /**
     * Count hydraulic cylinder master items
     * 
     * @return Count of hydraulic cylinder master items
     */
    @Query("SELECT COUNT(DISTINCT bma.id.bma01) FROM BmaFile bma " +
           "LEFT JOIN ImaFile ima ON bma.id.bma01 = ima.ima01 " +
           "WHERE ima.ima09 = 'S' AND ima.ima10 = '130 HC' " +
           "AND (bma.id.bma01 LIKE '3%' OR bma.id.bma01 LIKE '4%')")
    long countHydraulicCylinderMasterItems();
    
    /**
     * Find master items with BOM hierarchy (items that are both master and component)
     * 
     * @return List of master item codes that also appear as components
     */
    @Query("SELECT DISTINCT bma1.id.bma01 FROM BmaFile bma1 " +
           "WHERE EXISTS (" +
           "    SELECT 1 FROM BmbFile bmb " +
           "    WHERE bmb.id.bmb03 = bma1.id.bma01" +
           ") ORDER BY bma1.id.bma01")
    List<String> findMasterItemsWithHierarchy();
    
    /**
     * Find master items by series (for hydraulic cylinders)
     * 
     * @param series The series code (e.g., "10", "11", "12", "13")
     * @return List of master item codes matching the series
     */
    @Query("SELECT DISTINCT bma.id.bma01 FROM BmaFile bma " +
           "LEFT JOIN ImaFile ima ON bma.id.bma01 = ima.ima01 " +
           "WHERE ima.ima09 = 'S' AND ima.ima10 = '130 HC' " +
           "AND (bma.id.bma01 LIKE '3%' OR bma.id.bma01 LIKE '4%') " +
           "AND SUBSTRING(bma.id.bma01, 3, 2) = :series " +
           "ORDER BY bma.id.bma01")
    List<String> findHydraulicCylindersBySeries(String series);
    
    /**
     * Find master items by bore size range (for hydraulic cylinders)
     * 
     * @param minBore Minimum bore size (3-digit string, e.g., "050")
     * @param maxBore Maximum bore size (3-digit string, e.g., "100") 
     * @return List of master item codes within bore range
     */
    @Query("SELECT DISTINCT bma.id.bma01 FROM BmaFile bma " +
           "LEFT JOIN ImaFile ima ON bma.id.bma01 = ima.ima01 " +
           "WHERE ima.ima09 = 'S' AND ima.ima10 = '130 HC' " +
           "AND (bma.id.bma01 LIKE '3%' OR bma.id.bma01 LIKE '4%') " +
           "AND LENGTH(bma.id.bma01) >= 8 " +
           "AND SUBSTRING(bma.id.bma01, 6, 3) BETWEEN :minBore AND :maxBore " +
           "ORDER BY bma.id.bma01")
    List<String> findHydraulicCylindersByBoreRange(String minBore, String maxBore);
    
    /**
     * Get master items with component count statistics
     * 
     * @return List of objects containing master item code and component count
     */
    @Query("SELECT bma.id.bma01 as masterItemCode, COUNT(bmb.id.bmb03) as componentCount " +
           "FROM BmaFile bma " +
           "LEFT JOIN BmbFile bmb ON bmb.id.bmb01 = bma.id.bma01 AND bmb.id.bmb29 = bma.id.bma06 " +
           "LEFT JOIN ImaFile ima ON bma.id.bma01 = ima.ima01 " +
           "WHERE ima.ima09 = 'S' AND ima.ima10 = '130 HC' " +
           "GROUP BY bma.id.bma01 " +
           "ORDER BY bma.id.bma01")
    List<Object[]> findMasterItemsWithComponentCount();
    
    /**
     * Find recently created/updated master items
     * Note: This assumes there's a timestamp field. Adjust based on actual schema.
     * 
     * @return List of recently modified master item codes
     */
    @Query("SELECT DISTINCT bma.id.bma01 FROM BmaFile bma " +
           "LEFT JOIN ImaFile ima ON bma.id.bma01 = ima.ima01 " +
           "WHERE ima.ima09 = 'S' AND ima.ima10 = '130 HC' " +
           "ORDER BY bma.id.bma01")
    List<String> findRecentMasterItems();
}
