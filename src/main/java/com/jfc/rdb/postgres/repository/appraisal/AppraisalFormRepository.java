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
}
