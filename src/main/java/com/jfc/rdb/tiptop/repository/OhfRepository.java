package com.jfc.rdb.tiptop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.tiptop.entity.OhfFile;
import com.jfc.rdb.tiptop.entity.OhfFilePK;

@Repository
public interface OhfRepository extends JpaRepository<OhfFile, OhfFilePK> {
	List<OhfFile> findByIdOhf01(String complaintNo);
    Optional<OhfFile> findByIdOhf01AndIdOhf02(String complaintNo, String processType);
}
