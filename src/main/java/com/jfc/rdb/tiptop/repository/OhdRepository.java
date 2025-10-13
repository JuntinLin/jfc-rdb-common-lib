package com.jfc.rdb.tiptop.repository;
/*客訴單號單身檔(ohd_file)*/
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jfc.rdb.tiptop.entity.OhdFile;
import com.jfc.rdb.tiptop.entity.OhdFilePK;

public interface OhdRepository extends JpaRepository<OhdFile, OhdFilePK> {
    List<OhdFile> findByIdOhd01(String complaintNo);

}
