package com.jfc.rdb.postgres.repository.pcn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.pcn.PcnForm;

@Repository
public interface PcnFormRepository extends JpaRepository<PcnForm, UUID> {

    Optional<PcnForm> findByPcnNo(String pcnNo);

    Optional<PcnForm> findByProcessInstanceId(String processInstanceId);

    List<PcnForm> findByApplyEmpNoOrderByCreatedAtDesc(String applyEmpNo);

    List<PcnForm> findByApplyDeptCodeOrderByCreatedAtDesc(String applyDeptCode);

    List<PcnForm> findAllByOrderByCreatedAtDesc();

    long countByPcnNoStartingWith(String pcnNoPrefix);
}
