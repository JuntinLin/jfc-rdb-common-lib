package com.jfc.rdb.postgres.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.AuditLog;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

    @Query(value = "SELECT * FROM audit_log WHERE " +
           "(CAST(:tableName AS VARCHAR) IS NULL OR table_name = :tableName) AND " +
           "(CAST(:actorEmpNo AS VARCHAR) IS NULL OR actor_emp_no = :actorEmpNo) AND " +
           "(CAST(:action AS VARCHAR) IS NULL OR action = :action) AND " +
           "(CAST(:startDate AS TIMESTAMP) IS NULL OR created_at >= :startDate) AND " +
           "(CAST(:endDate AS TIMESTAMP) IS NULL OR created_at <= :endDate) " +
           "ORDER BY created_at DESC",
           countQuery = "SELECT count(*) FROM audit_log WHERE " +
           "(CAST(:tableName AS VARCHAR) IS NULL OR table_name = :tableName) AND " +
           "(CAST(:actorEmpNo AS VARCHAR) IS NULL OR actor_emp_no = :actorEmpNo) AND " +
           "(CAST(:action AS VARCHAR) IS NULL OR action = :action) AND " +
           "(CAST(:startDate AS TIMESTAMP) IS NULL OR created_at >= :startDate) AND " +
           "(CAST(:endDate AS TIMESTAMP) IS NULL OR created_at <= :endDate)",
           nativeQuery = true)
    Page<AuditLog> search(
            @Param("tableName") String tableName,
            @Param("actorEmpNo") String actorEmpNo,
            @Param("action") String action,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    List<AuditLog> findByTableNameAndRecordIdOrderByCreatedAtDesc(String tableName, String recordId);
}
