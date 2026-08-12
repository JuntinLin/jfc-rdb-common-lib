package com.jfc.rdb.postgres.repository.pcn;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.pcn.PcnCounterSignRecord;

@Repository
public interface PcnCounterSignRecordRepository extends JpaRepository<PcnCounterSignRecord, UUID> {

    List<PcnCounterSignRecord> findByPcnIdAndProcessInstanceIdOrderBySignedAtAsc(UUID pcnId, String processInstanceId);

    void deleteByPcnIdAndProcessInstanceId(UUID pcnId, String processInstanceId);
}
