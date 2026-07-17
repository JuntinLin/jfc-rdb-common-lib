package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.AppraisalForm;

@Repository
public interface AppraisalFormRepository extends JpaRepository<AppraisalForm, UUID> {

    List<AppraisalForm> findByPeriodIdOrderByDeptCodeAscEmpNoAsc(UUID periodId);

    List<AppraisalForm> findByPeriodIdAndDeptCodeOrderByEmpNoAsc(UUID periodId, String deptCode);

    Optional<AppraisalForm> findByPeriodIdAndEmpNo(UUID periodId, String empNo);

    @Query("SELECT AVG(f.finalScore) FROM AppraisalForm f WHERE f.empNo = :empNo AND f.periodId IN :periodIds")
    Double findAverageScoreByEmpNoAndPeriodIds(@Param("empNo") String empNo, @Param("periodIds") List<UUID> periodIds);

    long countByPeriodId(UUID periodId);

    // ===== ext 外網精簡實例用：以 PG 快照取代 HRM 查詢 =====

    boolean existsByEmpNo(String empNo);

    Optional<AppraisalForm> findFirstByEmpNoOrderByCreatedAtDesc(String empNo);

    @Query("SELECT DISTINCT f.empNo FROM AppraisalForm f WHERE f.supervisorEmpNo = :supervisorEmpNo")
    List<String> findEmpNosBySupervisor(@Param("supervisorEmpNo") String supervisorEmpNo);

    @Query("SELECT DISTINCT f.deptCode FROM AppraisalForm f WHERE f.supervisorEmpNo = :supervisorEmpNo AND f.deptCode IS NOT NULL")
    List<String> findDeptCodesBySupervisor(@Param("supervisorEmpNo") String supervisorEmpNo);
}
