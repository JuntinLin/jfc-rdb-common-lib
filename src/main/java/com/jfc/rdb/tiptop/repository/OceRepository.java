package com.jfc.rdb.tiptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.OceFile;
import com.jfc.rdb.tiptop.entity.OceFilePK;
@Repository
public interface OceRepository extends JpaRepository<OceFile, OceFilePK> {
	List<OceFile> findByIdOce01(String customerCode);
}
