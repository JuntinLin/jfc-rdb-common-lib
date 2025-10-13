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
    
}


