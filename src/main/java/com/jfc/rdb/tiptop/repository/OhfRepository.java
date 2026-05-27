package com.jfc.rdb.tiptop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.OhfFile;
import com.jfc.rdb.tiptop.entity.OhfFilePK;

@Repository
public interface OhfRepository extends JpaRepository<OhfFile, OhfFilePK> {
	/**
     * 根據客訴單號查詢所有經手人員記錄
     * @param ohf01 客訴單號
     * @return 經手人員記錄列表
     */
	@Query("SELECT o FROM OhfFile o WHERE o.id.ohf01 = :ohf01")
    List<OhfFile> findByOhf01(@Param("ohf01") String ohf01);
	/**
     * 根據客訴單號和類別查詢
     * @param ohf01 客訴單號
     * @param ohf02 類別 (0-5) ohf02	varchar2(1)	類別	類別0.客訴原因1.調查結果2.處理對策及改善對策3.審核4.核決5.結案註記
     * @return 經手人員記錄
     */
	@Query("SELECT o FROM OhfFile o WHERE o.id.ohf01 = :ohf01 AND o.id.ohf02 = :ohf02")
    List<OhfFile> findByOhf01AndOhf02(@Param("ohf01") String ohf01, @Param("ohf02") String ohf02);
}
