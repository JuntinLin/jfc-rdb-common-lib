package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.RewardPenaltyRecord;

@Repository
public interface RewardPenaltyRecordRepository extends JpaRepository<RewardPenaltyRecord, UUID> {

    List<RewardPenaltyRecord> findByPeriodIdAndEmpNoOrderByRecordDateDesc(UUID periodId, String empNo);

    List<RewardPenaltyRecord> findByPeriodIdOrderByEmpNoAscRecordDateDesc(UUID periodId);

    @Query("SELECT COALESCE(SUM(r.scoreAdj * r.rpCount), 0) FROM RewardPenaltyRecord r WHERE r.periodId = :periodId AND r.empNo = :empNo")
    Double sumScoreAdjByPeriodIdAndEmpNo(@Param("periodId") UUID periodId, @Param("empNo") String empNo);
}
