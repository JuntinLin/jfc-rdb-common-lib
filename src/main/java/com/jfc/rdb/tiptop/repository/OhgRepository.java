package com.jfc.rdb.tiptop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfc.rdb.tiptop.entity.OhgFile;
import com.jfc.rdb.tiptop.entity.OhgFilePK;

public interface OhgRepository extends JpaRepository<OhgFile, OhgFilePK> {
	List<OhgFile> findByIdOhg01AndIdOhg02OrderByIdOhg03Asc(String complaintNo, String processType);
}