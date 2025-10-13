package com.jfc.rdb.tiptop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jfc.rdb.tiptop.entity.EcmFile;
import com.jfc.rdb.tiptop.entity.EcmFilePK;
import com.jfc.rdb.tiptop.entity.SfbFile;

public interface EcmRepository extends JpaRepository<EcmFile, EcmFilePK> {
	public Optional<EcmFile> findById(EcmFilePK pk);

	public List<EcmFile> findBySfbFile(SfbFile sfb);
	
	
	/*WIP量(ecm301+ecm302+ecm303-ecm311-ecm312-ecm313-ecm314-ecm316)
	 * ecm301	number(15,3)	良品轉入量       (+)	
	 * ecm302	number(15,3)	重工轉入量       (+)	
	 * ecm303	number(15,3)	工單轉入量       (+)	
	 * ecm311	number(15,3)	良品轉出量       (-)	
	 * ecm312	number(15,3)	重工轉出         (-)	
	 * ecm313	number(15,3)	當站報廢量       (-)	
	 * ecm314	number(15,3)	當站下線量(入庫) 	(-)	
	 * ecm315	number(15,3)	Bonus Qty        (-)	
	 * ecm316	number(15,3)	工單轉出量       (-)	
	 * */
	//ecm06	varchar2(10)	工作站編號	工作站編號該生產程序/作業在何一工作站被生產

	@Query("select e from EcmFile e where ecm301+ecm302+ecm303-ecm311-ecm312-ecm313-ecm314-ecm316 = 0 "
			+ "	and e.sfbFile.sfb04 <> '8' and e.sfbFile.sfb87='Y' " + " and e.ecaFile.eca01 = ?1"
			+ " and e.sfbFile.sfb08 - e.ecm311 > 0")
	public List<EcmFile> findNoWipByEcm06(String ecm06);
	
	@Query("SELECT max(p.id.ecm03) FROM EcmFile r "+
			"LEFT JOIN EcmFile p ON p.id.ecm01 = r.id.ecm01 AND p.id.ecm03 < r.id.ecm03 " +
			"WHERE r.id.ecm01 = :ecm01 AND r.id.ecm03 = :ecm03 " +
			"GROUP BY r.id.ecm03")
	Integer findPreviousRoutingEcm03(@Param("ecm01") String ecm01, @Param("ecm03") int ecm03);
	
	@Query("SELECT min(n.id.ecm03) FROM EcmFile r "+
			"LEFT JOIN EcmFile n ON n.id.ecm01 = r.id.ecm01 AND n.id.ecm03 > r.id.ecm03 " +
			"WHERE r.id.ecm01 = :ecm01 AND r.id.ecm03 = :ecm03 " +
			"GROUP BY r.id.ecm03")
	Integer findNextRoutingEcm03(@Param("ecm01") String ecm01, @Param("ecm03") Integer ecm03);
}
