package com.jfc.rdb.tiptop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jfc.rdb.tiptop.entity.EcaFile;

public interface EcaRepository extends JpaRepository<EcaFile, String> {
	Optional<EcaFile> findById(String eca01);

	//eca02	varchar2(80)	說明	說明簡述工作站特性說明
	//eca03	varchar2(10)	工作站所屬部門別	工作站所屬部門別需存在公司部門檔內

	@Query("SELECT i FROM EcaFile i WHERE i.eca02 LIKE %?1%")
	java.util.List<EcaFile> findByEca02Like(String eca02);
	
	// 使用 JPQL 查詢帶有部門信息的工作站列表
    @Query("SELECT e FROM EcaFile e LEFT JOIN FETCH e.department")
    List<EcaFile> findAllWithDepartment();
    
    // 根據部門編號查詢工作站
    @Query("SELECT e FROM EcaFile e LEFT JOIN FETCH e.department WHERE e.eca03 = :departmentId")
    List<EcaFile> findByDepartmentId(String departmentId);
    
    // 根據工作站編號或說明搜索
    @Query("SELECT e FROM EcaFile e LEFT JOIN FETCH e.department " +
           "WHERE LOWER(e.eca01) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(e.eca02) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<EcaFile> searchByKeyword(String keyword);
    
    // 根據部門名稱模糊查詢
    @Query("SELECT e FROM EcaFile e LEFT JOIN FETCH e.department d " +
           "WHERE LOWER(d.gem02) LIKE LOWER(CONCAT('%', :departmentName, '%'))")
    List<EcaFile> findByDepartmentNameContaining(String departmentName);
}
