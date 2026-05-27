package com.jfc.rdb.tiptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfc.rdb.tiptop.entity.OhgFile;
import com.jfc.rdb.tiptop.entity.OhgFilePK;

public interface OhgRepository extends JpaRepository<OhgFile, OhgFilePK> {
	/**
     * 根據客訴單號查詢所有處理說明
     * @param ohg01 客訴單號
     * @return 處理說明列表
     */
	@Query("SELECT o FROM OhgFile o WHERE o.id.ohg01 = :ohg01")
    List<OhgFile> findByOhg01(@Param("ohg01") String ohg01);
    
    /**
     * 根據客訴單號和類別查詢
     * @param ohg01 客訴單號
     * @param ohg02 類別 (對應 ohf02) ohg02	varchar2(1)	類別	類別1.調查結果2.處理對策及改善對策3.審核4.核決5.結案註記
     * @return 處理說明列表
     */
	@Query("SELECT o FROM OhgFile o WHERE o.id.ohg01 = :ohg01 AND o.id.ohg02 = :ohg02")
    List<OhgFile> findByOhg01AndOhg02(@Param("ohg01") String ohg01, @Param("ohg02") String ohg02);
    
    /**
     * 根據客訴單號、類別和序號查詢特定記錄
     * @param ohg01 客訴單號
     * @param ohg02 類別
     * @param ohg03 序號
     * @return 處理說明
     */
	@Query("SELECT o FROM OhgFile o WHERE o.id.ohg01 = :ohg01 AND o.id.ohg02 = :ohg02 AND o.id.ohg03 = :ohg03")
    OhgFile findByOhg01AndOhg02AndOhg03(@Param("ohg01") String ohg01, @Param("ohg02") String ohg02, @Param("ohg03") Long ohg03);
    
    List<OhgFile> findByIdOhg01AndIdOhg02OrderByIdOhg03Asc(String complaintNo, String processType);
	
	
}