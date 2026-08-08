package com.jfc.rdb.postgres.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.LoginLog;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, UUID> {

    @Query(value = "SELECT * FROM login_log WHERE " +
           "(CAST(:username AS VARCHAR) IS NULL OR username LIKE '%' || :username || '%') AND " +
           "(CAST(:action AS VARCHAR) IS NULL OR action = :action) AND " +
           "(CAST(:authType AS VARCHAR) IS NULL OR auth_type = :authType) AND " +
           "(CAST(:startDate AS TIMESTAMP) IS NULL OR created_at >= :startDate) AND " +
           "(CAST(:endDate AS TIMESTAMP) IS NULL OR created_at <= :endDate) " +
           "ORDER BY created_at DESC",
           countQuery = "SELECT count(*) FROM login_log WHERE " +
           "(CAST(:username AS VARCHAR) IS NULL OR username LIKE '%' || :username || '%') AND " +
           "(CAST(:action AS VARCHAR) IS NULL OR action = :action) AND " +
           "(CAST(:authType AS VARCHAR) IS NULL OR auth_type = :authType) AND " +
           "(CAST(:startDate AS TIMESTAMP) IS NULL OR created_at >= :startDate) AND " +
           "(CAST(:endDate AS TIMESTAMP) IS NULL OR created_at <= :endDate)",
           nativeQuery = true)
    Page<LoginLog> search(
            @Param("username") String username,
            @Param("action") String action,
            @Param("authType") String authType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT COUNT(DISTINCT l.username) FROM LoginLog l WHERE l.action = 'LOGIN' AND l.createdAt >= :since")
    long countDistinctLoginSince(@Param("since") LocalDateTime since);

    @Query("SELECT COUNT(l) FROM LoginLog l WHERE l.action = 'LOGIN_FAIL' AND l.createdAt >= :since")
    long countFailedLoginSince(@Param("since") LocalDateTime since);
}
