package com.jfc.rdb.postgres.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jfc.rdb.postgres.entity.SysUserRole;
import com.jfc.rdb.postgres.entity.SysUserRoleId;

@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, SysUserRoleId> {

    List<SysUserRole> findByUserId(UUID userId);

    @Query("SELECT ur.roleCode FROM SysUserRole ur WHERE ur.userId = :userId")
    List<String> findRoleCodesByUserId(@Param("userId") UUID userId);
}
