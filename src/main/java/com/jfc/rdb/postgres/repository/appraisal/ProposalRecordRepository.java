package com.jfc.rdb.postgres.repository.appraisal;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.appraisal.ProposalRecord;

@Repository
public interface ProposalRecordRepository extends JpaRepository<ProposalRecord, UUID> {

    List<ProposalRecord> findByPeriodIdAndEmpNoOrderByProposalDateDesc(UUID periodId, String empNo);

    List<ProposalRecord> findByPeriodIdOrderByEmpNoAscProposalDateDesc(UUID periodId);

    long countByPeriodIdAndEmpNo(UUID periodId, String empNo);
}
