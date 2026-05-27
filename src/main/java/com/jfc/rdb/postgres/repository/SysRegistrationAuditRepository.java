package com.jfc.rdb.postgres.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.SysRegistrationAudit;

@Repository
public interface SysRegistrationAuditRepository extends JpaRepository<SysRegistrationAudit, Integer> {

    List<SysRegistrationAudit> findByStatus(String status);

    List<SysRegistrationAudit> findByUserIdOrderByCreatedAtDesc(UUID userId);

    Optional<SysRegistrationAudit> findFirstByUserIdAndStatusOrderByCreatedAtDesc(UUID userId, String status);
}
