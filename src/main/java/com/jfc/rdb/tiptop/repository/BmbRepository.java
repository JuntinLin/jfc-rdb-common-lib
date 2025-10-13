package com.jfc.rdb.tiptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.BmaFile;
import com.jfc.rdb.tiptop.entity.BmbFile;
import com.jfc.rdb.tiptop.entity.BmbFilePK;

@Repository
public interface BmbRepository extends JpaRepository<BmbFile, BmbFilePK> {
	// Find BmbFile entries by BmaFile (master item)
	List<BmbFile> findByBmaFile(BmaFile bmaFile);

	// Find BmbFile entries by master item code
	List<BmbFile> findByIdBmb01(String masterItemCode);

	// Find BmbFile entries by component item code
	List<BmbFile> findByIdBmb03(String componentItemCode);

	// Find BmbFile entries by master item code and item sequence
	BmbFile findByIdBmb01AndIdBmb02(String masterItemCode, long itemSequence);

	// Find BmbFile entries by characteristic code
	List<BmbFile> findByIdBmb29(String characteristicCode);

	// Find BmbFile entries where associated ImaFile (through BmaFile) has specific
	// type and line
	@Query("SELECT bmb FROM BmbFile bmb "
			+ "LEFT JOIN BmaFile bma ON bma.id.bma01 = bmb.id.bmb01 AND bma.id.bma06 = bmb.id.bmb29 "
			+ "LEFT JOIN ImaFile ima ON ima.ima01 = bma.id.bma01 " + "WHERE ima.ima09 = :type AND ima.ima10 = :line")
	List<BmbFile> findByIma09AndIma10(String type, String line);
	
	//the distinct component count
	@Query("SELECT COUNT(DISTINCT bmb.id.bmb03) FROM BmbFile bmb " +
		       "WHERE bmb.id.bmb01 IN (" +
		       "  SELECT ima.ima01 FROM ImaFile ima " +
		       "  WHERE ima.ima09 = :type AND ima.ima10 = :line" +
		       ")")
	long countDistinctComponentsByMasterItemTypeAndLine(@Param("type") String type, @Param("line") String line);
	
	// counting BOM relationships
	@Query("SELECT COUNT(bmb) FROM BmbFile bmb " + "WHERE bmb.id.bmb01 IN (" + "  SELECT ima.ima01 FROM ImaFile ima "
			+ "  WHERE ima.ima09 = :type AND ima.ima10 = :line" + ")")
	long countBomRelationshipsByMaterItemTypeAndLine(@Param("type") String type, @Param("line") String line);
}
