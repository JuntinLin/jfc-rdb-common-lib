package com.jfc.rdb.tiptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jfc.rdb.tiptop.entity.OhcFile;
/*客訴處理單單頭檔(ohc_file)
 * */
public interface OhcRepository extends JpaRepository<OhcFile, String>, JpaSpecificationExecutor<OhcFile> {
	
	/**
     * 根據狀態查詢客訴
     * @param ohc03 狀態碼 ohc03	varchar2(1)	目前狀態	目前狀態'0':未處理'1':處理中'2':結案
     * @return 客訴列表
     */
    List<OhcFile> findByOhc03(String ohc03);
    
    /**
     * 根據多個狀態查詢客訴
     * @param statuses 狀態碼列表
     * @return 客訴列表
     */
    List<OhcFile> findByOhc03In(List<String> statuses);
    
    /**
     * 根據客戶編號查詢
     * @param ohc06 客戶編號
     * @return 客訴列表
     */
    List<OhcFile> findByOhc06(String ohc06);
    
    /**
     * 根據產品編號查詢
     * @param ohc08 產品編號
     * @return 客訴列表
     */
    List<OhcFile> findByOhc08(String ohc08);
	
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