package com.jfc.rdb.tiptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jfc.rdb.tiptop.entity.OhcFile;
/*客訴處理單單頭檔(ohc_file)
 * */
public interface OhcRepository extends JpaRepository<OhcFile, String>, JpaSpecificationExecutor<OhcFile> {
	List<OhcFile> findByOhc03(String status);
	
	@Query(value = """
            SELECT * FROM (
                SELECT a.*, ROWNUM rnum FROM (
                    SELECT * FROM ohc_file 
                    WHERE (?1 IS NULL OR ohc03 = ?1)
                    AND (?2 IS NULL OR ohc06 LIKE ?2 || '%')
                    ORDER BY ohc01
                ) a WHERE ROWNUM <= ?4
            ) WHERE rnum > ?3
            """, 
           nativeQuery = true)
    List<OhcFile> findAllWithPaging(
        String status, 
        String customerCode, 
        int offset, 
        int limit
    );

    @Query(value = """
            SELECT COUNT(*) FROM ohc_file 
            WHERE (?1 IS NULL OR ohc03 = ?1)
            AND (?2 IS NULL OR ohc06 LIKE ?2 || '%')
            """, 
           nativeQuery = true)
    long countWithFilters(String status, String customerCode);
}