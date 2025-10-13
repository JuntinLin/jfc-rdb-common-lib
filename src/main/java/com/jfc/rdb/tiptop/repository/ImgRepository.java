package com.jfc.rdb.tiptop.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.ImgFile;
import com.jfc.rdb.tiptop.entity.ImgFilePK;

@Repository
public interface ImgRepository extends JpaRepository<ImgFile, ImgFilePK> {
	 @Query("SELECT COALESCE(SUM(i.img10), 0) FROM ImgFile i " +
	           "WHERE i.img23 = 'Y' AND i.img10 > 0 " +
	           "AND i.id.img02 NOT IN ('AM', 'AK', 'H') " +
	           "AND i.id.img01 = :mano")
	 BigDecimal findMatStock(@Param("mano") String mano);
	 
	 @Query("""
		       SELECT COALESCE(SUM(i.img10), 0) 
		       FROM ImgFile i 
		       WHERE i.img23 = 'Y' 
		       AND i.img10 > 0 
		       AND i.id.img02 = :warehouse 
		       AND i.id.img01 = :mano
		       """)
	 BigDecimal findMatStockByWarehouse(
		       @Param("mano") String mano, 
		       @Param("warehouse") String warehouse
		   );
}
